<html>
<head>
  <title>Skitter: register new account</title>
  <link rel="stylesheet" type="text/css" href="/skitter/resources/css/login.css" />
</head>
<body>
<div class="login-page">
  <div class="form">
    <form action="/skitter/register" method="POST" class="register-form">
	    <input type="text" placeholder="Username" name="username" required>
	    <input type="password" placeholder="Password" name="password" required>
	    <input type="email" placeholder="Email" name="email" required>
	    <input type="radio" name="gender" value="Male" checked> Male
	    <input type="radio" name="gender" value="Female"> Female
	    <input type="date" name="birthday">
	    <button>create</button>
	    <p class="message">Already registered? <a href="/skitter/login">Sign In</a></p>
    </form>
  </div>
</div>

</body>
</html>
