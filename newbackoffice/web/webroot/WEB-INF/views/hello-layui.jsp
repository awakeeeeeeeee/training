<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>layui</title>
    <link rel="stylesheet" href="<c:url value="/static/layui/css/layui.css"/>" type="text/css"
          media="screen, projection"/>
</head>
<body>

<!-- 你的 HTML 代码 -->

<script src="<c:url value="/static/layui/layui.js"/>"></script>
<script>
    layui.use(['layer', 'form'], function(){
        var layer = layui.layer
            ,form = layui.form;

        layer.msg('Hello World');
    });
</script>
</body>
</html>