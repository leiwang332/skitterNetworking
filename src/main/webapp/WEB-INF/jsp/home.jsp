<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<c:set var="context" scope="request" value="<%= request.getContextPath()%>" />
<html>
   <head>
      <title>Skitter: ${username}'s home</title>
      <link rel="stylesheet" type="text/css" href="https://www.w3schools.com/w3css/4/w3.css" />
      <link rel="stylesheet" type="text/css" href="/skitter/resources/css/list.css" />
   </head>
   <body>
      <img src="/skitter/resources/Picture/image.jpg" width="800" height="300">
      <br />
      <br />
      <h1> Welcome to Skitter home, ${username}! Below are all your skits:</h1>
      <form action="/skitter/postSkit" method="POST">
         <div class="container">
            <textarea name="skitText" cols="60" rows="2" required></textarea>
            <button type="submit">Post</button>
            <input type="hidden" name="userId" value="${userId}" />
         </div>
      </form>
      <br />
      <c:if test="${errMsg}">
      	<b> Error: ${errMsg}</b>
      </c:if>
      <form action="/skitter/addfollower" method="POST">
         <div class="container">
            <input type="text" name="followerName" required>
            <button type="submit">Add</button>
            <input type="hidden" value="${userId}" name="userId" />
         </div>
      </form>
      <br />
      <div>
         <a href="/skitter/follows?followers=true&userId=${userId}&username=${username}">
         Followers: ${followerCount} </a> <br /> 
         <a href="/skitter/follows?followers=false&userId=${userId}&username=${username}">
         Followings: ${followingCount} </a>
      </div>
      <ul>
         <c:forEach items="${skits}" var="skit">
            <li> <h3>${skit.text} - ${skit.postDate}</h3></li>
         </c:forEach>
      </ul>
   </body>
</html>