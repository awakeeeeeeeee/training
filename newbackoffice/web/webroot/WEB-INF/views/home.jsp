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


<script src="<c:url value="/static/layui/layui.js"/>"></script>
<script>
    layui.use(['layer', 'form'], function(){
        var layer = layui.layer
            ,form = layui.form;

        layer.msg('Hello World');
    });

</script>


<div class="layui-panel">
    <ul class="layui-menu" id="docDemoMenu1">
        <li lay-options="{id: 100}">
            <div class="layui-menu-body-title">menu item 1</div>
        </li>
        <li lay-options="{id: 101}">
            <div class="layui-menu-body-title">
                <a href="#">menu item 2 <span class="layui-badge-dot"></span></a>
            </div>
        </li>
        <li class="layui-menu-item-divider"></li>
        <li class="layui-menu-item-group layui-menu-item-down" lay-options="{type: 'group'}">
            <div class="layui-menu-body-title">
                menu item 3 group <i class="layui-icon layui-icon-up"></i>
            </div>
            <ul>
                <li lay-options="{id: 1031}">menu item 3-1</li>
                <li lay-options="{id: 1032}">
                    <div class="layui-menu-body-title">menu item 3-2</div>
                </li>
            </ul>
        </li>
        <li class="layui-menu-item-divider"></li>
        <li lay-options="{id: 104}">
            <div class="layui-menu-body-title">menu item 4</div>
        </li>
        <li class="layui-menu-item-parent" lay-options="{type: 'parent'}">
            <div class="layui-menu-body-title">
                menu item 5
                <i class="layui-icon layui-icon-right"></i>
            </div>
            <div class="layui-panel layui-menu-body-panel">
                <ul>
                    <li lay-options="{id: 1051}">
                        <div class="layui-menu-body-title">menu item 5-1</div>
                    </li>
                    <li lay-options="{id: 1051}">
                        <div class="layui-menu-body-title">menu item 5-2</div>
                    </li>
                </ul>
            </div>
        </li>
        <li lay-options="{id: 106}">
            <div class="layui-menu-body-title">menu item 6</div>
        </li>

        <a href="/newbackoffice/j_spring_security_logout">log out</a>
    </ul>
</div>





</body>
</html>