<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="navigation">
    <h2>카테고리</h2>
    <ul>
        <c:forEach items="${categories}" var="category">
            <li><a href="${pageContext.request.contextPath}/${blog.id}/${category.no}">${category.name}</a></li>
        </c:forEach>
    </ul>
</div>