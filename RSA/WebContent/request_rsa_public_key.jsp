<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.meadidea.mobile.security.*" %>
<%@ page errorPage="error.jsp"%>

<%
	//功能说明：rsa公钥输出
	//
	//TODO:身份校验,检查是否合法客户请求公钥
	//TODO:清除SESSION_SECURITY_PRIVATE_KEY
	
	
	RSAEncrypt rsa = new RSAEncrypt();
	rsa.genKeyPair();
	//rsa.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
	//krsa.loadPrivateKey(RSAEncrypt.DEFAULT_PRIVATE_KEY);

	//转为PEM格式
	String keyPublic = RSAKeyUtil.getPEMkey(rsa.getPublicKey());
	String keyPrivate = RSAKeyUtil.getPEMkey(rsa.getPrivateKey());
	//保存私钥
	request.getSession().setAttribute(RSAKeyUtil.SESSION_SECURITY_PRIVATE_KEY, rsa.getPrivateKey());
	//输出公钥
	out.println(keyPublic);
	System.out.println("keyPrivate="+keyPrivate);

%>
