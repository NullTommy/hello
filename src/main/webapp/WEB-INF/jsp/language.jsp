<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>

<head>
    <title>SpringMVC <spring:message code="internationalisation"/></title>
</head>
<body>
<h2>本页路径：http://localhost:8080/language.do</h2>

Language Change:
<a href="?lang=zh_CN"><spring:message code="language.cn"/></a> &nbsp;&nbsp;&nbsp;
<a href="?lang=en_US"><spring:message code="language.en"/></a>
<h1>
    <spring:message code="welcome"/>
</h1>

localLanguage: ${pageContext.response.locale }
</body>
</html>
