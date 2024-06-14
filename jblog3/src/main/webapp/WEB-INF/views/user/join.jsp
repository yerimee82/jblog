<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>JBlog</title>
	<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
	<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
	<script>
		$(function () {
			$('#btn-checkId').click(function () {
				var id = $("#id").val();
				console.log("Entered ID: " + id);
				if (id === '') {
					alert("아이디를 입력하세요.");
					return;
				}

				$.ajax({
					url: "/jblog3/user/api/checkId?id=" + id,
					type: "get",
					dataType: "json",
					error: function (xhr, status, err) {
						console.error(err);
					},
					success: function(response) {
						if (response.exist) {
							alert("존재하는 아이디입니다. 다른 아이디를 사용해 주세요.");
							$("#id").val("");
							$("#id").focus();
							return;
						}
						// 사용 가능
						// $("#btn-checkId").hide();
						$("#img-checkId").show();
					}
				});
			});
		});
	</script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<form:form modelAttribute="userVo"
				   id="join-form"
				   name="joinForm"
				   method="post"
				   action="${pageContext.request.contextPath}/user/join">
			<label class="block-label" for="name"><spring:message code="user.join.label.name"/></label>
			<form:input path="name"/>
			<p style="color:#f00; text-align: center; padding: 0">
			<spring:hasBindErrors name="userVo">
			<c:if test="${errors.hasFieldErrors('name')}">
				<spring:message code="${errors.getFieldError('name').codes[0]}"/>
			</c:if>
			</spring:hasBindErrors>
			</p>
			<label class="block-label" for="id"><spring:message code="user.join.label.id"/></label>
				<form:input path="id"/>
			<input id="btn-checkId" type="button" value="id 중복체크">
			<img id="img-checkId" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">
			<p style="color:#f00; text-align: center; padding: 0">
				<form:errors path="id" />
			</p>

			<label class="block-label" for="password"><spring:message code="user.join.label.password"/></label>
			<form:password path="password" />
			<spring:hasBindErrors name="userVo">
			<c:if test="${errors.hasFieldErrors('password')}">
				<spring:message code="${errors.getFieldError('password').codes[0]}"/>
			</c:if>
			</spring:hasBindErrors>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
</html>
