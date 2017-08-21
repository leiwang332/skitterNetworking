<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="context" scope="request" value="<%= request.getContextPath()%>" />
<html>
<head>
  <c:if test="${isFollower}">
  	<title>Skitter: ${username}'s followers</title>
  </c:if>
  <c:if test="${isFollowing}">
  	<title>Skitter: ${username}'s followings</title>
  </c:if>  
  <link rel="stylesheet" type="text/css" href="/skitter/resources/css/list.css" />
</head>
<body>
  <c:if test="${isFollower}">
  	<h1>Skitter: ${username}'s followers</h1>
  </c:if>
  <c:if test="${isFollowing}">
  	<h1>Skitter: ${username}'s followings</h1>
  </c:if>  

  <ul>
    <c:forEach items="${users}" var="user">
      <li> <h3> ${user.accountName} </h3> </li>
    </c:forEach>
  </ul>
</body>
</html>
