<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JBlog</title>
    <Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
<div class="center-content">
    <c:import url="/WEB-INF/views/includes/header.jsp" />
    <p class="error-message">
        <span> 요청을 찾을 수 없습니다. </span>
        <br><br>
        <a href="${pageContext.request.contextPath}">메인으로 돌아가기</a>
    </p>
</div>
</body>
</html>
