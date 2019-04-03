package restserverbasicmysql.restserver.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import restserverbasicmysql.restserver.config.FileStorageProperties;
import restserverbasicmysql.restserver.error.CustomErrorType;
import restserverbasicmysql.restserver.model.Factura;
import restserverbasicmysql.restserver.model.UploadFactura;
import restserverbasicmysql.restserver.repos.FacturaRepository;

@RestController
public class FacturaResource {
	
	public static final Logger logger = LoggerFactory.getLogger(FacturaResource.class);
	
	private final Path fileStorageLocation;

    @Autowired
    public FacturaResource(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/facturas", method = RequestMethod.GET)
	public ResponseEntity<List<Factura>> retrieveAllFacturas() {
		List<Factura> facturas = facturaRepository.findAll();
        if (facturas.isEmpty()) {
        		logger.warn("Sin facturas!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Se han recuperado [{}] facturas", facturas.size());
        return new ResponseEntity<List<Factura>>(facturas, HttpStatus.OK);
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

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@RequestMapping(value = "/facturas", method = RequestMethod.POST)
//    public ResponseEntity<?> postFactura(@RequestBody Factura factura) {
//        logger.info("Subiendo la factura  [{}]", factura);
//        Optional<Factura> optional = facturaRepository.findById(factura.getId());
//        if (optional.isPresent()) {
//            logger.error("Factura with id {} found!.", factura.getId());
//            return new ResponseEntity(new CustomErrorType("Factura with id " + factura.getId()  + " found"), HttpStatus.FOUND);
//        }
//        Optional<Factura> optionalNumFactura = facturaRepository.findByNumFactura(factura.getNumFactura());
//        if (optionalNumFactura.isPresent()) {
//        		logger.error("Factura with numFactura {} found!.", factura.getNumFactura());
//        		return new ResponseEntity(new CustomErrorType("Factura with numFactura " + factura.getNumFactura()  + " found"), HttpStatus.FOUND);
//        }
//        
//        Factura novaFactura = facturaRepository.save(factura);
//        logger.info("Guardando la factura  [{}]", novaFactura);
//        return new ResponseEntity<Factura>(novaFactura, HttpStatus.OK);
//    }

	@SuppressWarnings("rawtypes")
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

            }
            //saveUploadedFiles(model.getNumFactura(), Arrays.asList(model.getFiles()));
            

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Factura>(novaFactura, HttpStatus.OK);

    }
	
	private void saveUploadedFiles(String numFactura, List<MultipartFile> files) throws IOException {

		logger.info("saveUploadedFiles...");
		logger.info("Number of files..." + files.size());
		
        for (MultipartFile file : files) {

        		logger.info("" + file.getName());
            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            logger.info("" + fileStorageLocation + File.separator + numFactura + "_" + file.getOriginalFilename());
            Path path = Paths.get(fileStorageLocation + File.separator + numFactura + "_" + file.getOriginalFilename());
            Files.write(path, bytes);
            File f = new File(path.toString());
            if(f.exists()) {
            		logger.info("EXISTE!! " + fileStorageLocation + File.separator + numFactura + "_" + file.getOriginalFilename());
            }else {
            		logger.warn("ERROR!! No existe " + fileStorageLocation + File.separator + numFactura + "_" + file.getOriginalFilename());
            }

        }

    }
}
