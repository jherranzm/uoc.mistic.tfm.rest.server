package restserverbasicmysql.restserver.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import restserverbasicmysql.restserver.model.BackUp;
import restserverbasicmysql.restserver.model.CustomUser;
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
	public ResponseEntity<List<Invoice>> retrieveAllFacturas(@AuthenticationPrincipal CustomUser user) {
		
		logger.info("Usuario [{}]", user.toString());
		
		List<Invoice> facturas = new ArrayList<Invoice>();
		
		logger.info("Recuperado las facturas del usuario [{}]", user.getUsuario().getEmail());
		facturas = invoiceRepository.findAllByUsuario(user.getUsuario());
        if (facturas.isEmpty()) {
        		logger.warn("Sin facturas!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Se han recuperado [{}] facturas", facturas.size());
        return new ResponseEntity<List<Invoice>>(facturas, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/facturas/{uid}", method = RequestMethod.GET)
    public ResponseEntity<?> getFactura(@AuthenticationPrincipal CustomUser user, @PathVariable("uid") String uid) {
		
		logger.info("Usuario [{}]", user.toString());
		
        logger.info("Recuperando factura con id [{}] del usuario [{}]", uid, user.getUsuario().getEmail());
        Optional<Invoice> factura = invoiceRepository.findByUidByUsuario(uid, user.getUsuario());
        if (!factura.isPresent()) {
            logger.error("Factura con uid {} NO encontrada.", uid);
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("responseCode", HttpStatus.BAD_REQUEST.value());
			json.put("message", String.format("User [%s] Invoice [%s] NOT FOUND!", user.getUsuario().getEmail(), uid));
            return new ResponseEntity<Map<String, Object>>(json, HttpStatus.OK);
        }
        return new ResponseEntity<Invoice>(factura.get(), HttpStatus.OK);
    }

	@RequestMapping(value = "/facturas", method = RequestMethod.POST)
    public ResponseEntity<?> postFactura(@AuthenticationPrincipal CustomUser user, @RequestBody UploadObject factura) {
		
		logger.info("Usuario [{}]", user.toString());

		logger.info("Objeto recibido:  [{}]", factura);
        Invoice invoice = getInvoiceFromUploadObject(factura);
        invoice.setUsuario(user.getUsuario());
        
        logger.info("Guardando la factura  [{}]", invoice);
        Invoice newInvoice= new Invoice();
        Optional<Invoice> existingInvoice = invoiceRepository.findByUid(invoice.getUid());

        if(existingInvoice.isPresent()){
        	logger.info("La factura YA está en el sistema!  [{}]", existingInvoice.get());
        	return new ResponseEntity<Invoice>(existingInvoice.get(), HttpStatus.CONFLICT);
        }else {
        	newInvoice = invoiceRepository.save(invoice);
        	
        	InvoiceData invoiceData = getInvoiceDataFromUploadObject(factura);
        	invoiceData.setUsuario(user.getUsuario());
        	
        	BackUp backUp = getBackUpFromUploadObject(factura);
        	backUp.setUsuario(user.getUsuario());
        	
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
        invoice.setCorporateName(factura.getCorporateName());

        invoice.setInvoiceNumber(factura.getInvoicenumber());
        invoice.setInvoiceTotal(factura.getTotal());
        invoice.setTotalTaxOutputs(factura.getTotaltaxoutputs());
        
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
		invoiceData.setF3(factura.getCorporateName());
		invoiceData.setF4(factura.getInvoicenumber());
		
		invoiceData.setF5(factura.getTotal());
		invoiceData.setF6(factura.getTotaltaxoutputs());
		invoiceData.setF7(factura.getTotalgrossamount());
		invoiceData.setF8(factura.getIssueData());
		return invoiceData;
	}

}
