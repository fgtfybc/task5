<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body style="text-align: center;margin: auto;">
<div id="head">
    <t:insertAttribute name="head"/>
</div>
<div id="content">
    <t:insertAttribute name="body"/>
</div>
<div id="footer">
    <t:insertAttribute name="footer"/>
</div>
</body>
</html>
