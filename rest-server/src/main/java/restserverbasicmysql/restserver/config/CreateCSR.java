package restserverbasicmysql.restserver.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
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
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;

public class CreateCSR {

	private static final String BC = "BC";
	private static final String SHA256WITH_RSA = "SHA256withRSA";
	private static final String P12_PASSWORD = "Th2S5p2rStr4ngP1ss";
	private static final int MILLIS_PER_DAY = 24*60*60*1000;

	public static String getCertificateFromCSR(String csr) {
		
		try {
			
			
			System.out.println("csr : " + csr);
			
			JcaPKCS10CertificationRequest jcaPKCS10CertificationRequest;

			PEMParser pemParser = new PEMParser(new StringReader(csr));
			Object parsedObj = pemParser.readObject();
			pemParser.close();
			System.out.println("PemParser returned: " + parsedObj);
			if (!(parsedObj instanceof PKCS10CertificationRequest)){
				throw new Exception("No ha llegado un CSR correcto!");
			}
			jcaPKCS10CertificationRequest = new JcaPKCS10CertificationRequest((PKCS10CertificationRequest)parsedObj);
			System.out.println("PublicKey : " + jcaPKCS10CertificationRequest.getPublicKey());
			
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509", BC);
			
			File fileCAP12 = new File("/Users/jherranzm/Dropbox/Jose_Luis/TFM_2019/PKI/CAkeystore.p12");
            InputStream isCAP12 = new FileInputStream(fileCAP12);
			
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			keystore.load(isCAP12, P12_PASSWORD.toCharArray());
			PrivateKey key = (PrivateKey)keystore.getKey("ca", P12_PASSWORD.toCharArray());
			
			File fileCACrt = new File("/Users/jherranzm/Dropbox/Jose_Luis/TFM_2019/PKI/certs/CA.crt");
            InputStream isCACrt = new FileInputStream(fileCACrt);
            
            X509Certificate caCert = (X509Certificate) certFactory.generateCertificate(isCACrt);
            
            int validity = 365; //No of days the certificate should be valid
            String serialNo = "0001"; // a unique number

            Date issuedDate = new Date();
            Date expiryDate = new Date(System.currentTimeMillis() + validity * MILLIS_PER_DAY); //MILLIS_PER_DAY=86400000l
            //JcaPKCS10CertificationRequest jcaRequest = new JcaPKCS10CertificationRequest(csr);
            X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(caCert,
                    new BigInteger(serialNo), issuedDate, expiryDate, jcaPKCS10CertificationRequest.getSubject(), jcaPKCS10CertificationRequest.getPublicKey());
            JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
            certificateBuilder.addExtension(Extension.authorityKeyIdentifier, false,
                    extUtils.createAuthorityKeyIdentifier(caCert))
                    .addExtension(Extension.subjectKeyIdentifier, false, extUtils.createSubjectKeyIdentifier(jcaPKCS10CertificationRequest
                            .getPublicKey()))
                    .addExtension(Extension.basicConstraints, true, new BasicConstraints(0))
                    .addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage
                            .keyEncipherment))
                    .addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
            ContentSigner signer = new JcaContentSignerBuilder(SHA256WITH_RSA).setProvider(BC).build(key);
 
             X509Certificate signedCert = new JcaX509CertificateConverter().setProvider(BC).getCertificate
                    (certificateBuilder.build(signer));
            
            System.out.println(signedCert.getSubjectDN().getName());
            System.out.println(signedCert.getIssuerDN().getName());
            
            System.out.println(signedCert);
            
            System.out.println("-----BEGIN CERTIFICATE-----\n" + Base64.getEncoder().encodeToString(signedCert.getEncoded()) + "\n-----END CERTIFICATE-----");
            
            return Base64.getEncoder().encodeToString(signedCert.getEncoded());
		}catch (Exception e) {
			System.out.println("" + e.getClass().getCanonicalName() + " : " + e.getLocalizedMessage() + " : " + e.getMessage());
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
