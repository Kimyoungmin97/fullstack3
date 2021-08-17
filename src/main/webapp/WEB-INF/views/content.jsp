<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 읽기</title>

<!-- 		jsp의 내장객체  -->
<!-- 		1.ServletContext application -->
<!-- 		2.HttpSession session -->
<!-- 		3.HttpServletRequest request -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	글번호 : ${article.articleNum}<br> 
	아이디 : ${article.id}<br>
	제목 : ${article.title}<br> 
	본문 : ${article.content}<br>
	날짜 : ${article.writeDate}<br><br>
	<hr/><br>
	
	<div>
		<ul>
			<c:forEach var="file" items="${fileList}">
				<li><a href="/fullstack/download.bbs?savedFileName=${file.savedFileName}">${file.originalFileName}</a></li>
			</c:forEach>
		</ul>
	</div>
	
	<c:if test="${id!=null}">
		<c:if test="${id == article.id}">
			<input type="button" value="수정하기"
				onclick="document.location.href='/fullstack/update.bbs?articleNum=${article.articleNum}'">
			<input type="button" value="삭제하기"
				onclick="document.location.href='/fullstack/delete.bbs?articleNum=${article.articleNum}'">
		</c:if>
		<c:if test="${id != article.id}">
			<input type="button" value="수정하기" disabled="disabled">
			<input type="button" value="삭제하기" disabled="disabled">
		</c:if>
		<input type="button" value="목록하기" onclick="document.location.href='/fullstack/list.bbs'">
	</c:if>
	
	<c:if test="${id == null }">
		<input type="button" value="수정하기" disabled="disabled">
		<input type="button" value="삭제하기" disabled="disabled">
		<input type="button" value="목록하기" onclick="document.location.href='/fullstack/list.bbs'"> 
	</c:if>
	
	<br><br>
	<div>
		<textarea rows="5" cols="70" id="commentContent"></textarea><br>
		<c:if test="${id == null}">
			<input type="button" value="comment 쓰기" disabled="disabled">
		</c:if>
		<c:if test="${id!=null}">
			<input type="button" value="comment 쓰기" id="commentWrite" > 
		</c:if>
		<input type="button" value="comment 읽기(${commentCount})" onclick="getComment(1,event)"
			id="commentRead">
	</div><br>
	<div id="showComment"></div>
	
<!-- 	JQuery 태그 선택 방법(선택자) -->
<!-- 	1.$("#human") : 태그의 id값이 human인 태그를 선택함 -->
<!-- 	1.$(".human") : 태그의 class값이 human인 태그를 선택함 -->
<!-- 	1.$("human") :  human인 태그를 선택함 -->
	<script type="text/javascript">
		$.ajaxSetup({
			type : "POST",
			async : true,//비동기
			dataType : "json",
			error : function(xhr){
				alert("error html = " + xhr.statusText);
			}
		});
	
		$(document).ready(function(){
			$("#commentWrite").on("click",function(){				
				$.ajax({
					//서버 주소, 전송할 데이터, 서버에서 응답이 오며 처리할 코드....
					url:"/fullstack/commentWrite.comment",
// 					data{}에서는 EL을 ""로 감싸야함..그외에는 그냥 사용
					data :{
						articleNum : "${article.articleNum}",
						commentContent : $("#commentContent").val()
					},
					beforeSend : function(){
// 						alert("시작전");
					},
					complete: function(){
// 						alert("완료후");
					},	
					success:function(data){						
						showHtml(data,1);		
					}
				});
			})
			
		});
		
		function getComment(commPageNum, event){
// 	 	 	event.preventDefault(); 원래 이벤트를 멈추는거
// 	 	 	event.stopPropagation(); 전파되어있던 이벤트 멈추기
			$.ajax({							
				url:"/fullstack/commentRead.comment",	
				data:{
					articleNum:"${article.articleNum}",
					commPageNum:commPageNum,
//		 			숫자와 문자연산에서 + 를 제외하고는 숫자 우선
					commentRow:commPageNum*10
				},
				success:function(data){				
					showHtml(data,commPageNum);
				}
			}); 
		}
		
		function showHtml(data,pageNum){
			let html = "<table border='1' width='500' align='center'>";
			$.each(data,function(index,item){
				html +="<tr>";
				html +="<td>"+item.commentNum+"</td>";
				html +="<td>"+item.id+"</td>";
				html +="<td>"+item.commentContent+"</td>";
				let date = new Date(item.commentDate);
				html +="<td>"+date.toDateString()+"</td>";
				html +="</tr>";
			});

			if("${commentCount}">pageNum*10){
				nextPageNum = pageNum+1;
				html += "<td cols='4'><input type='button' onclick='getComment(nextPageNum,event)' value='다음'></td>";
			}
			
			html +="</table>";
			
			$("#showComment").html(html);
// 			$("#showComment").html(html);
			$("#commentContent").val("");
			$("#commentContent").focus();
		
		}
	</script>
	
	
</body>
</html>