<% String context_root = request.getContextPath();%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/main.css"/>
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template_header.css"/>
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template_footer.css"/>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
<%@page import = "com.petmaru.member.model.vo.MemberVo" %>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
<%@ include file="../admin_header.jsp" %>
</head>
<body>
   <main class="main">
      <h2 class="main title">회원정보</h2>

      <div class="search-form margin-top first align-right">
         <h3 class="hidden">회원 검색폼</h3>
         <form class="table-form">
            <fieldset>
               <legend class="hidden">회원정보 검색 필드</legend>
               <label class="hidden">검색분류</label> <select name="f">
                  <option value="member_name">이름</option>
                  <option value="member_id">아이디</option>
               </select> <label class="hidden">검색어</label> <input type="text" name="q" value="" /> <input class="btn btn-search" type="submit" value="검색" />
            </fieldset>
         </form>
      </div>

		<form action = "MemberList" method ="post">
			<div class="notice margin-top">
				<h3 class="hidden">회원 목록</h3>
				<table class="table">
					<thead>
						<tr>
							<th class="w60">아이디</th>
							<th class="expand">이름</th>
							<th class="w100">성별</th>
							<th class="w100">가입일</th>
							<th class="w60">적립금</th>
							<!-- <th class="w40">탈퇴</th> -->
						</tr>
					</thead>
					<tbody>
							<c:forEach var="n" items="${list}">
							<tr>
								<td>${n.member_id}</td>
								<td>${n.member_name}</td>
								<td>${n.member_gender}</td>
								<td>${n.member_regdate}</td>
								<td>${n.member_point}</td>
								<td><button type = "submit" class = "btn btn-outline-info btn-sm" onclick="location.href='/deleteMember?member_id=${n.member_id}'">탈퇴</button></td>
							</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>

			<c:set var="page" value="${(param.p==null)?1:param.p}" />
			<c:set var="startNum" value="${page-(page-1)%5}" />
			<c:set var="lastNum" value="${fn:substringBefore(Math.ceil(count/10),'.')}" />

			<div class="indexer margin-top align-right">
				<h3 class="hidden">현재페이지</h3>
				<div>
					<span class="text-orange text-strong">${(empty param.p)?1:param.p }</span>/${lastNum }page
				</div>
			</div>

		
		<div class="text-align-right margin-top">
		
      </div>
		</form>
      <div class = "margin-top align-center pager">
      
      <c:if test="${startNum > 1}">
         <a class="btn btn-prev" href="?p=${startNum-1}&t=&q=">이전</a>
      </c:if>
      <c:if test="${startNum <= 1}">
         <span class="btn btn-prev" onclick="alert('이전 페이지가 없습니다.');">이전</span>
      </c:if>
      
      </div>
      <div>
         <ul class="-list- center">
            <c:forEach var="i" begin="0" end="4">
            <c:if test="${(startNum+i) <= lastNum}">
               <li><a class = "-text- ${(page==(startNum+i))?'orange':''} bold" href = "?p=${startNum+i}&f=&q=">${startNum+i}</a>
            </c:if>
            </c:forEach>
         </ul>
      <div>
         <c:if test="${startNum+4<lastNum}">
            <a href="?p=${startNum+5}&t=&q=" class="btn btn-next">다음</a>
         </c:if>
         <c:if test="${startNum+4>=lastNum}">
            <span class="btn btn-next" onclick="alert('다음 페이지가 없습니다.');">다음</span>
         </c:if>
      </div>
   </div>
   </main>
</body>
   <%@ include file="../template_footer.jsp" %>
</html>