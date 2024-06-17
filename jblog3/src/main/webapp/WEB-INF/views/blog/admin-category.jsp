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
	<div id="container">
		<c:import url="/WEB-INF/views/blog/includes/header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/blog/includes/adminMenu.jsp" />
				<c:set var="count" value="${fn:length(list)}" />
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
					<c:forEach items="${list}" var="vo" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${vo.name}</td>
							<td>${vo.postCount}</td>
							<td>${vo.description}</td>
							<td>
								<c:if test="${status.index ne 0}">
									<form id="deleteForm_${status.index}" action="${pageContext.request.contextPath}/${blog.id}/admin/category/delete/${vo.no}" method="post" style="display:inline;">
										<a href="javascript:void(0);" onclick="confirmDelete('deleteForm_${status.index}')">
											<img src="${pageContext.request.contextPath}/assets/images/delete.jpg">
										</a>
									</form>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<script>
					function confirmDelete(formId) {
						if (confirm("정말 삭제 하시겠습니까?")) {
							document.getElementById(formId).submit();
							return true;
						}
						return false;
					}
				</script>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
				<form action="${pageContext.request.contextPath }/${blog.id}/admin/category/add" method="post">
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="desc"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp" />
	</div>
</body>
</html>