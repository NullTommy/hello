<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>

<head>
    <title>exportIndex</title>
</head>
<body>
<h2>本页路径：http://localhost:8080/exportIndex.do</h2>

<a href="">测试导出：普通导出，就是自己拼接导出的表头，以及自己塞每一行的数据，每次修改都需要重写。这里不做实现和介绍</a><br>
<a href="http://localhost:8080/export.do">测试导出-1：导出类中的某些字段，通过反射实现。每次只需传入表头字段、以及其对应的类的get方法名</a><br>
<a href="http://localhost:8080/export2.do">测试导出-2：导出类中的某些字段，通过Excel模板实现。自行定义Excel模板，后台保证数据和Excel模板中的内容对应即可</a><br>

</body>
</html>
