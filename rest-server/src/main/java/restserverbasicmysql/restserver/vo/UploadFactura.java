package restserverbasicmysql.restserver.vo;

import org.springframework.web.multipart.MultipartFile;

public class UploadFactura {
	private String numFactura;

    private MultipartFile[] files;

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
    
    
    
}
