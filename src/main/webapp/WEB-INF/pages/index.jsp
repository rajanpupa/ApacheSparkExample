<%@page import="javax.naming.Context"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="line" items="${lines}"><c:out value="${line }"></c:out></c:forEach>
