<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Skitter Login</title>
  <link rel="stylesheet" type="text/css" href="/skitter/resources/css/login.css" />
</head>
<body>

<c:if test="${failed}">
    Wrong username or password, please try again!
</c:if>

<div class="login-page">
  <div class="form">
	<form action="/skitter/login" method="POST">
      <input type="text" placeholder="username" name="username" required/>
      <input type="password" placeholder="password" name="password" required/>
      <button>login</button>
      <p class="message">Not registered? <a href="/skitter/register">Create an account</a></p>
    </form>
  </div>
</div>

</body>
</html>