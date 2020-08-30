<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>注册页面</title>
</head>
<body>
<h3>${requestScope.error}</h3>
<form action="register" method="post">
    账户名:<input type="text" name="username"/><br>
    密码:<input type="text" name="password"/><br>
    <input type="submit" value="注册"/>
</form>
</body>
</html>