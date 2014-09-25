package com.meadidea.mobile.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

public final class RSAKeyUtil {

	public static final String SESSION_SECURITY_PRIVATE_KEY = "SESSION_SECURITY_PRIVATE_KEY";
	public static final String SESSION_SECURITY_PUBLIC_KEY = "SESSION_SECURITY_PUBLIC_KEY";

	/**
	 * 返回PEM格式的public公钥.<br>
	 * 如果发生错误，返回空字符串
	 * 
	 * @param pubkey
	 * @return
	 */
	public static String getPEMkey(PublicKey pubkey) {
		StringWriter writer = new StringWriter();
		PEMWriter pem = new PEMWriter(writer);
		try {
			pem.writeObject(pubkey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("getPEMkey#PublicKey");
			pem.flush();
			writer.flush();
			writer.close();
			pem.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return writer.toString();
	}

	/**
	 * 返回PEM格式的private私钥.<br>
	 * 如果发生错误，返回空字符串
	 * 
	 * @param prikey
	 * @return
	 */
	public static String getPEMkey(PrivateKey prikey) {
		StringWriter writer = new StringWriter();
		PEMWriter pem = new PEMWriter(writer);
		try {
			pem.writeObject(prikey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("getPEMkey#PrivateKey");
			pem.flush();
			writer.flush();
			writer.close();
			pem.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return writer.toString();
	}

	public static void main(String[] args) throws Exception {
		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		// rsaEncrypt.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
		// rsaEncrypt.genKeyPair();
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// keyPairGen.initialize(1024);
		// KeyPair keyPair = keyPairGen.generateKeyPair();
		// RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		//
		// String pemPub = org.apache.commons.codec.binary.Base64
		// .encodeBase64String(publicKey.getEncoded());
		//
		// String pemPri = org.apache.commons.codec.binary.Base64
		// .encodeBase64String(privateKey.getEncoded());
		// // byte[] buffer= base64Decoder.decodeBuffer(privateKey);
		// System.out.println("/n##pemPub##########\n" + pemPub);
		// System.out.println("/n##pemPri##########\n" + pemPri);

		System.out.println("/n##DEFAULT_PUBLIC_KEY##########\n"
				+ RSAEncrypt.DEFAULT_PUBLIC_KEY);
		System.out.println("/n##DEFAULT_PRIVATE_KEY##########\n"
				+ RSAEncrypt.DEFAULT_PRIVATE_KEY);

		// 加载公钥
		try {
			rsaEncrypt.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
			System.out.println("加载公钥成功");
			String key = RSAKeyUtil.getPEMkey(rsaEncrypt.getPublicKey());
			System.out.println("key=\n" + key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("加载公钥失败");
		}

		// 加载私钥
		try {
			rsaEncrypt.loadPrivateKey(RSAEncrypt.DEFAULT_PRIVATE_KEY);
			System.out.println("加载私钥成功");
			String key = RSAKeyUtil.getPEMkey(rsaEncrypt.getPrivateKey());
			System.out.println("key=\n" + key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("加载私钥失败");
		}
		
		int i = 1;
		if (i == 1) {
			return;
		}

		//X509V3CertificateGenerator x = new X509V3CertificateGenerator();

		Date startDate = new Date(); // time from which certificate is valid
		Date expiryDate = new Date(); // time after which certificate is not
										// valid
		BigInteger serialNumber = new BigInteger("7989"); // serial number for
															// certificate
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();

		PrivateKey caKey = (RSAPrivateKey) keyPair.getPrivate();
		; // private key of the certifying authority (ca) certificate
		X509Certificate caCert = null; // public key certificate of the
										// certifying authority

		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
		X500Principal subjectName = new X500Principal("CN=Test V3 Certificate");

		// ##########
		InputStream is = new FileInputStream(new File("D://L_java//RootCa.pfx"));
		/*
		 * CertificateFactory object is used for reading Certificates, CRL and
		 * CertPaths. Create a factory object using the standard SPI pattern
		 * used in JCA.
		 */
		CertificateFactory factory = CertificateFactory.getInstance("X.509");

		/*
		 * Generate a X509 Certificate initialized with the data read from the
		 * input stream.
		 */
		X509Certificate mastercert = (X509Certificate) factory
				.generateCertificate(is);
		// ##########
		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(mastercert.getSubjectX500Principal());
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		certGen.setSubjectDN(subjectName);
		certGen.setPublicKey(keyPair.getPublic());
		certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

		certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
				new AuthorityKeyIdentifierStructure(caCert));
		certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
				new SubjectKeyIdentifierStructure(keyPair.getPublic()));

		//X509Certificate cert = certGen.generate(caKey, "BC"); // note: private
																// key of CA
	}
}
