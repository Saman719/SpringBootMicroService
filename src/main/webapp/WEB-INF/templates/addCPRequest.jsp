<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Add Card Print Request</title>
</head>
<body>
<h2>Add Card Print Request</h2>

<br>
<form:form action="addCPRequest" modelAttribute="cpRequest">
    IPADDRESS : <form:input path="ipAddress"/>
    BRANCHCODE : <form:input path="branchCode"/>
    CARDPAN : <form:input path="cardPAN"/>
    PERSONNELCODE : <form:input path="personnelCode"/>
    ISSUEDDATE : <form:input type="date" path="issuedDate"/>
    <input type="submit" value="Add Card">
</form:form>
</body>
</html>