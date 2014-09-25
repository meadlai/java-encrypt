<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>




<%
	//功能说明：输出错误信息,
	//防止crm返回错误数据，
	String json = "{\"code\":\"-1\",\"result\":\""+exception.getMessage()+"\"}";
	out.print(json);
	exception.printStackTrace();
%>
