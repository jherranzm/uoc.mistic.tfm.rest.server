package restserverbasicmysql.restserver.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.error.CustomErrorType;
import restserverbasicmysql.restserver.model.BackUp;
import restserverbasicmysql.restserver.model.Invoice;
import restserverbasicmysql.restserver.model.InvoiceData;
import restserverbasicmysql.restserver.repos.BackUpRepository;
import restserverbasicmysql.restserver.repos.InvoiceDataRepository;
import restserverbasicmysql.restserver.repos.InvoiceRepository;
import restserverbasicmysql.restserver.vo.UploadObject;

@RestController
public class FacturaResource {
	
	public static final Logger logger = LoggerFactory.getLogger(FacturaResource.class);
	
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
        Optional<Invoice> factura = invoiceRepository.findById(id);
        if (!factura.isPresent()) {
            logger.error("Factura con id {} NO encontrada.", id);
            return new ResponseEntity(new CustomErrorType("Factura con id " + id  + " NO encontrada."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Invoice>(factura.get(), HttpStatus.OK);
    }

	@RequestMapping(value = "/facturas", method = RequestMethod.POST)
    public ResponseEntity<?> postFactura(@RequestBody UploadObject factura) {

		logger.info("Objeto recibido:  [{}]", factura);
        Invoice invoice = getInvoiceFromUploadObject(factura);
        
        logger.info("Guardando la factura  [{}]", invoice);
        Invoice newInvoice= new Invoice();
        Optional<Invoice> existingInvoice = invoiceRepository.findByUid(invoice.getUid());

        if(existingInvoice.isPresent()){
        	logger.info("La factura YA está en el sistema!  [{}]", existingInvoice.get());
        	return new ResponseEntity<Invoice>(existingInvoice.get(), HttpStatus.CONFLICT);
        }else {
        	newInvoice = invoiceRepository.save(invoice);
        	
        	InvoiceData invoiceData = getInvoiceDataFromUploadObject(factura);
        	
        	BackUp backUp = getBackUpFromUploadObject(factura);
        	
            Optional<InvoiceData> existingInvoiceData = invoiceDataRepository.findByF1(invoice.getUid());
            if(existingInvoiceData.isPresent()){
            	logger.info("La factura YA está en el sistema!  [{}]", existingInvoiceData.get());
            }else {
            	InvoiceData newInvoiceData = invoiceDataRepository.save(invoiceData);
            	logger.info("Guardados los datos encriptados de la factura  [{}]", newInvoiceData);
            }

        	
            Optional<BackUp> existingBackUp = backUpRepository.findByF1(invoice.getUid());
            if(existingBackUp.isPresent()){
            	logger.info("La factura YA está en el sistema!  [{}]", existingBackUp.get());
            }else {
            	BackUp newBackUp = backUpRepository.save(backUp);
            	logger.info("Guardado backup encriptado de la factura  [{}]", newBackUp);
            }
            
        }
        
        logger.info("Guardada la factura  [{}]", newInvoice);
        return new ResponseEntity<Invoice>(newInvoice, HttpStatus.OK);
    }

	private Invoice getInvoiceFromUploadObject(UploadObject factura) {
		Invoice invoice = new Invoice();
        
        invoice.setUid(factura.getUidfactura());

        invoice.setTaxIdentificationNumber(factura.getSeller());
        invoice.setCorporateName("");

        invoice.setInvoiceNumber(factura.getInvoicenumber());
        invoice.setInvoiceTotal(new Double(factura.getTotal()));
        invoice.setTotalTaxOutputs(new Double(factura.getTotaltaxoutputs()));
        
        invoice.setIssueDate(factura.getIssueData());
        
        invoice.setIv(factura.getIv());
        invoice.setSimKey(factura.getKey());
        
        invoice.setSignedInvoice(factura.getFile());
		return invoice;
	}

	private BackUp getBackUpFromUploadObject(UploadObject factura) {
		BackUp backUp = new BackUp();
		backUp.setF1(factura.getUidfactura());
		backUp.setI(factura.getIv());
		backUp.setK(factura.getKey());
		backUp.setF(factura.getFile());
		return backUp;
	}

	private InvoiceData getInvoiceDataFromUploadObject(UploadObject factura) {
		InvoiceData invoiceData = new InvoiceData();
		invoiceData.setF1(factura.getUidfactura());
		invoiceData.setF2(factura.getSeller());
		invoiceData.setF3("");
		invoiceData.setF4(factura.getInvoicenumber());
		
		invoiceData.setF5(new Double(factura.getTotal()));
		invoiceData.setF6(new Double(factura.getTotaltaxoutputs()));
		invoiceData.setF7(new Double(factura.getTotalgrossamount()));
		invoiceData.setF8(factura.getIssueData());
		return invoiceData;
	}

}
