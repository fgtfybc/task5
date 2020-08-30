<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
<h3>${requestScope.error}</h3>
<form action="/login" method="post">
    用户名：<input type="text" name="username"/><br>
    密码：<input type="text" name="password"/><br>
    <input type="submit" value="登录"/>
</form><a href="/toregister">注册</a>


</form>
</body>
</html>