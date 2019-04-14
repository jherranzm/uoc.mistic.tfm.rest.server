package restserverbasicmysql.restserver.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import restserverbasicmysql.restserver.error.CustomErrorType;
import restserverbasicmysql.restserver.model.BackUp;
import restserverbasicmysql.restserver.model.Factura;
import restserverbasicmysql.restserver.model.Invoice;
import restserverbasicmysql.restserver.model.InvoiceData;
import restserverbasicmysql.restserver.model.UploadFactura;
import restserverbasicmysql.restserver.model.UploadObject;
import restserverbasicmysql.restserver.repos.BackUpRepository;
import restserverbasicmysql.restserver.repos.FacturaRepository;
import restserverbasicmysql.restserver.repos.InvoiceDataRepository;
import restserverbasicmysql.restserver.repos.InvoiceRepository;

@RestController
public class FacturaResource {
	
	public static final Logger logger = LoggerFactory.getLogger(FacturaResource.class);
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private InvoiceDataRepository invoiceDataRepository;
	
	@Autowired
	private BackUpRepository backUpRepository;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/facturas", method = RequestMethod.GET)
	public ResponseEntity<List<Invoice>> retrieveAllFacturas() {
		List<Invoice> facturas = invoiceRepository.findAll();
        if (facturas.isEmpty()) {
        		logger.warn("Sin facturas!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Se han recuperado [{}] facturas", facturas.size());
        return new ResponseEntity<List<Invoice>>(facturas, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/facturas/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFactura(@PathVariable("id") long id) {
        logger.info("Recuperando factura con id [{}]", id);
        Optional<Factura> factura = facturaRepository.findById(id);
        if (!factura.isPresent()) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id  + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Factura>(factura.get(), HttpStatus.OK);
    }

	@RequestMapping(value = "/facturas", method = RequestMethod.POST)
    public ResponseEntity<?> postFactura(@RequestBody UploadObject factura) {
        logger.info("Subiendo la factura  [{}]", factura);
        
        Invoice invoice = new Invoice();
        
        invoice.setUid(factura.getUidfactura());

        invoice.setTaxIdentificationNumber(factura.getSeller());
        invoice.setCorporateName("");

        invoice.setInvoiceNumber(factura.getInvoicenumber());
        invoice.setInvoiceTotal(new Double(factura.getTotal()));
        invoice.setTotalTaxOutputs(new Double(factura.getTotaltaxoutputs()));
        
        invoice.setIssueDate(factura.getData());
        
        invoice.setIv(factura.getIv());
        invoice.setSimKey(factura.getKey());
        
        invoice.setSignedInvoice(factura.getFile());
        
        logger.info("Guardando la factura  [{}]", invoice);
        Invoice newInvoice= new Invoice();
        Optional<Invoice> existingInvoice = invoiceRepository.findByUid(invoice.getUid());
        if(existingInvoice.isPresent()){
        	// The invoice is already in the system...
        	logger.info("La factura YA está en el sistema!  [{}]", existingInvoice.get());
        	return new ResponseEntity<Invoice>(existingInvoice.get(), HttpStatus.CONFLICT);
        }else {
        	newInvoice = invoiceRepository.save(invoice);
        	
        	InvoiceData invoiceData = new InvoiceData();
        	invoiceData.setF1(factura.getUidfactura());
        	invoiceData.setF2(factura.getSeller());
        	invoiceData.setF3("");
        	invoiceData.setF4(factura.getInvoicenumber());
        	
        	invoiceData.setF5(new Double(factura.getTotal()));
        	invoiceData.setF6(new Double(factura.getTotaltaxoutputs()));
        	invoiceData.setF7(new Double(factura.getTotalgrossamount()));
        	
        	BackUp backUp = new BackUp();
        	backUp.setF1(factura.getUidfactura());
        	backUp.setI(factura.getIv());
        	backUp.setK(factura.getKey());
        	backUp.setF(factura.getFile());
        	
        	
        	
            Optional<InvoiceData> existingInvoiceData = invoiceDataRepository.findByF1(invoice.getUid());
            if(existingInvoiceData.isPresent()){
            	// The invoice is already in the system...
            	logger.info("La factura YA está en el sistema!  [{}]", existingInvoiceData.get());
            	//return new ResponseEntity<Invoice>(existingInvoice.get(), HttpStatus.CONFLICT);
            }else {
            	InvoiceData newInvoiceData = invoiceDataRepository.save(invoiceData);
            	logger.info("Guardada los datos encriptados de la factura  [{}]", newInvoiceData);
            }

        	
            Optional<BackUp> existingBackUp = backUpRepository.findByF1(invoice.getUid());
            if(existingBackUp.isPresent()){
            	// The invoice is already in the system...
            	logger.info("La factura YA está en el sistema!  [{}]", existingBackUp.get());
            	//return new ResponseEntity<Invoice>(existingInvoice.get(), HttpStatus.CONFLICT);
            }else {
            	BackUp newBackUp = backUpRepository.save(backUp);
            	logger.info("Guardado backup encriptado de la factura  [{}]", newBackUp);
            }
            
        }
        
        logger.info("Guardada la factura  [{}]", newInvoice);
        return new ResponseEntity<Invoice>(newInvoice, HttpStatus.OK);
    }

	@RequestMapping(value = "/facturas/upload", method = RequestMethod.POST)
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadFactura model) {

		logger.info("Número de factura : [{}]", model.getNumFactura());
        Factura novaFactura = new Factura();

        try {

            for (MultipartFile file : Arrays.asList(model.getFiles())) {

        			logger.info("" + file.getName());
        			if (file.isEmpty()) {
        				continue; //next pls
        			}

        			byte[] bytes = file.getBytes();
        			logger.info("" + model.getNumFactura() + "_" + file.getOriginalFilename());
        			logger.info("" + bytes.length + " bytes!");
        			
        			Factura f = new Factura();
        			f.setNumFactura(model.getNumFactura());
        			f.setDetallFactura(bytes);
        			f.setDataCreacio(Calendar.getInstance().getTime());
        			novaFactura = facturaRepository.save(f);
        			
        			logger.info("" + novaFactura.toString());
        			facturaRepository.flush();
        			
        			Invoice invoice = new Invoice(
        					model.getNumFactura(), 
        					model.getNumFactura(), 
        					model.getNumFactura(), 
        					model.getNumFactura(), 
        					0.0, 
        					0.0, 
        					model.getNumFactura());
        			logger.info("" + invoice.toString());
        			Invoice newInvoice = invoiceRepository.save(invoice);
        			invoiceRepository.flush();
        			logger.info("" + newInvoice.toString());

            }
            //saveUploadedFiles(model.getNumFactura(), Arrays.asList(model.getFiles()));
            

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Factura>(novaFactura, HttpStatus.OK);

    }
}
