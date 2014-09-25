<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page errorPage="error.jsp"%>



<%@ page import="com.meadidea.mobile.security.*"%>
<%@ page import="java.security.interfaces.*"%>

<%
	//功能说明：登录接口
	//如果成功，设置会话session
	//示例：http://localhost:8080/mobile_front/crm/login.jsp?usercode=system&userpwd=351DD829E4ED2A91AE76B3103B65CD3C&devicetype=iphone&devicetoken=3389&deviceid=009&apiversion=1.0&deviceversion=ios4.2
	String usercode = request.getParameter("usercode");
	String userpwd = request.getParameter("userpwd");

	final String functionNO = "7701";

	System.err.println("User:" + usercode + ", Password:" + userpwd);
	if (usercode == null || userpwd == null) {
		//检查参数
		throw new RuntimeException("Illegal user, kick away.");
	}
%>
<%

	//#######################获取私钥
	RSAPrivateKey keyPrivate = (RSAPrivateKey) request.getSession()
			.getAttribute(RSAKeyUtil.SESSION_SECURITY_PRIVATE_KEY);
	//替换URLCoding
	userpwd = userpwd.replaceAll("%25", "%");
	userpwd = userpwd.replaceAll("%2B", "+");
	userpwd = userpwd.replaceAll("%2F", "/");
	userpwd = userpwd.replaceAll("%3F", "?");
	userpwd = userpwd.replaceAll("%23", "#");
	userpwd = userpwd.replaceAll("%26", "&");
	userpwd = userpwd.replaceAll("%3D", "=");

	userpwd = userpwd.replaceAll("%2B", "+");
	userpwd = userpwd.replaceAll("%2F", "/");
	userpwd = userpwd.replaceAll("%3F", "?");
	userpwd = userpwd.replaceAll("%25", "%");
	userpwd = userpwd.replaceAll("%23", "#");
	userpwd = userpwd.replaceAll("%26", "&");
	userpwd = userpwd.replaceAll("%3D", "=");

	System.out.println("userpwd密文=" + userpwd);
	byte[] chiperData = org.apache.commons.codec.binary.Base64
			.decodeBase64(userpwd);
	byte[] plainData = RSAEncrypt.decrypt(keyPrivate, chiperData);
	userpwd = new String(plainData);
	request.getSession()
	.removeAttribute(RSAKeyUtil.SESSION_SECURITY_PRIVATE_KEY);
	//#######################END_OF_decrpty_解密
	System.out.println("userpwd明文=" + userpwd);

	
%>
