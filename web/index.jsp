<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body><h1>hej</h1>
    <p>${errorMessage}</p>
    <form method="post" action="login">
        <input type="text" placeholder="Login" name="username">
        <input type="password" placeholder="Password" name="password">
        <input type="submit" placeholder="Sign in">
    </form>
</body>
</html>
