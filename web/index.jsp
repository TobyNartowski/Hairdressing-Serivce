<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h1>hej</h1>
    <div style="color: #FF0000;">${errorMessage}</div>
    <form method="post" action="/login">
        <input type="text" placeholder="Login">
        <input type="password" placeholder="Password">
        <input type="submit" placeholder="Sign in">
    </form>
</body>
</html>
