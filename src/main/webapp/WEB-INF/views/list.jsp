<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 읽기</title>
<script type="text/javascript">
	function writeForm() {
		location.href="/fullstack/write.bbs";
	}
	
</script>
</head>
<body>
	<c:if test="${id != null}">
		<%@include file="loginOk.jsp" %>
	</c:if>
	<c:if test="${id == null}">
		<%@include file="login.jsp" %>
	</c:if>
	<br><br><br>
	<input type="button" onclick="writeForm()" value="글쓰기" ><br><br>
	<c:forEach var="article" items="${articleList}">
		글번호 : ${article.articleNum}&nbsp;&nbsp;&nbsp; 아이디 : ${article.id}<br>
		제목 :<a href="/fullstack/content.bbs?articleNum=${article.articleNum}">${article.title}</a>
		<hr/>	
	</c:forEach>
</body>
</html>