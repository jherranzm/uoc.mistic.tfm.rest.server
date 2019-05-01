package restserverbasicmysql.restserver.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class CreateCSR {
	
	public static final Logger logger = LoggerFactory.getLogger(CreateCSR.class);

	public static String getCertificateFromCSR(String csr) {
		
		try {
			
			
			logger.info("csr : " + csr);
			
			JcaPKCS10CertificationRequest jcaPKCS10CertificationRequest;

			PEMParser pemParser = new PEMParser(new StringReader(csr));
			Object parsedObj = pemParser.readObject();
			pemParser.close();
			logger.info("PemParser returned: " + parsedObj);
			if (!(parsedObj instanceof PKCS10CertificationRequest)){
				throw new Exception("No ha llegado un CSR correcto!");
			}
			jcaPKCS10CertificationRequest = new JcaPKCS10CertificationRequest((PKCS10CertificationRequest)parsedObj);
			logger.info("PublicKey : " + jcaPKCS10CertificationRequest.getPublicKey());
			
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509", Configuration.SEC_PROVIDER);
			
			File fileCAP12 = ResourceUtils.getFile("classpath:keystore/CAkeystore.p12");
            InputStream isCAP12 = new FileInputStream(fileCAP12);
			
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			keystore.load(isCAP12, Configuration.P12_PASSWORD.toCharArray());
			PrivateKey key = (PrivateKey)keystore.getKey(Configuration.CA, Configuration.P12_PASSWORD.toCharArray());
			
			File fileCACrt = ResourceUtils.getFile("classpath:keystore/CA.crt");
            InputStream isCACrt = new FileInputStream(fileCACrt);
            
            X509Certificate caCert = (X509Certificate) certFactory.generateCertificate(isCACrt);
            
            int validity = 365; //No of days the certificate should be valid
            String serialNo = "0001"; // a unique number

            Date issuedDate = new Date();
            Date expiryDate = new Date(System.currentTimeMillis() + validity * Configuration.MILLIS_PER_DAY); //MILLIS_PER_DAY=86400000l
            X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(caCert,
                    new BigInteger(serialNo), 
                    issuedDate, 
                    expiryDate, 
                    jcaPKCS10CertificationRequest.getSubject(), 
                    jcaPKCS10CertificationRequest.getPublicKey());
            JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
            certificateBuilder
            		.addExtension(
            				Extension.authorityKeyIdentifier, 
            				false,
            				extUtils.createAuthorityKeyIdentifier(caCert))
                    .addExtension(
                    		Extension.subjectKeyIdentifier, 
                    		false, 
                    		extUtils.createSubjectKeyIdentifier(jcaPKCS10CertificationRequest.getPublicKey()))
                    .addExtension(
                    		Extension.basicConstraints, 
                    		true, 
                    		new BasicConstraints(0))
                    .addExtension(
                    		Extension.keyUsage, 
                    		true, 
                    		new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment))
                    .addExtension(
                    		Extension.extendedKeyUsage, 
                    		true, 
                    		new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
            ContentSigner signer = new JcaContentSignerBuilder(Configuration.SHA256WITH_RSA).setProvider(Configuration.SEC_PROVIDER).build(key);
 
             X509Certificate signedCert = new JcaX509CertificateConverter().setProvider(Configuration.SEC_PROVIDER).getCertificate
                    (certificateBuilder.build(signer));
            
            StringWriter sw = new StringWriter();
            JcaPEMWriter pemWriter = new JcaPEMWriter(sw);
            pemWriter.writeObject(signedCert);
            pemWriter.close();
            
            logger.info(sw.toString());
            
            return Base64.getEncoder().encodeToString(sw.toString().getBytes());
		}catch (Exception e) {
			logger.info("" + e.getClass().getCanonicalName() + " : " + e.getLocalizedMessage() + " : " + e.getMessage());
			e.printStackTrace();
		}
		
		return null;
		
	}

	static {
        Security.insertProviderAt(new org.bouncycastle.jce.provider.BouncyCastleProvider(), 1);
        // Works correctly with apache.santuario v1.5.8
        org.apache.xml.security.Init.init();
    }
}
