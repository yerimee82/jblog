<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("crcn", "\r\n");
	pageContext.setAttribute("br", "<br/>");
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/blog/includes/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test="${not empty posts}">
							<h4>${posts[0].title}</h4>
							<p><c:out escapeXml="false" value="${fn:replace(posts[0].contents, crcn, br)}"/></p>
						</c:when>
						<c:otherwise>
							<p>포스트가 없습니다.</p>
						</c:otherwise>
					</c:choose>
				</div>
				<ul class="blog-list">
					<c:if test="${fn:length(posts) > 1}">
						<c:forEach items="${posts}" var="post">
							<li>
								<a href="${pageContext.request.contextPath}/${blog.id}/${post.categoryNo}/${post.no}">
										${post.title}
								</a>
								<span>${post.regDate}</span>
							</li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img id="logo" src="${pageContext.request.contextPath}${blog.logo}">
			</div>
		</div>
		<c:import url="/WEB-INF/views/blog/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp" />
	</div>
</body>
</html>