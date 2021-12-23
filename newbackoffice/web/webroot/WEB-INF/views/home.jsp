<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--<%@ page import="de.hybris.platform.jalo.JaloSession" %>--%>
<%--<%@page import="de.hybris.platform.jalo.user.*"%>--%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>newbackoffice</title>
    <link rel="stylesheet" href="<c:url value="/static/layui/css/layui.css"/>" type="text/css" media="screen, projection"/>
    <script src="<c:url value="/static/layui/layui.js"/>"></script>
    <script src="<c:url value="/static/jquery/jquery.min.js"/>"></script>
    <script src="<c:url value="/static/js/main.js"/>"></script>
</head>
<body>


<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo layui-hide-xs layui-bg-black">layout demo</div>
        <!-- 头部区域（可配合layui 已有的水平导航） -->
<%--        <ul class="layui-nav layui-layout-left">--%>
<%--            <!-- 移动端显示 -->--%>
<%--            <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-header-event="menuLeft">--%>
<%--                <i class="layui-icon layui-icon-spread-left"></i>--%>
<%--            </li>--%>

<%--            <li class="layui-nav-item layui-hide-xs"><a href="">nav 1</a></li>--%>
<%--            <li class="layui-nav-item layui-hide-xs"><a href="">nav 2</a></li>--%>
<%--            <li class="layui-nav-item layui-hide-xs"><a href="">nav 3</a></li>--%>
<%--            <li class="layui-nav-item">--%>
<%--                <a href="javascript:    ;">nav groups</a>--%>
<%--                <dl class="layui-nav-child">--%>
<%--                    <dd><a href="">menu 11</a></dd>--%>
<%--                    <dd><a href="">menu 22</a></dd>--%>
<%--                    <dd><a href="">menu 33</a></dd>--%>
<%--                </dl>--%>
<%--            </li>--%>
<%--        </ul>--%>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item layui-hide layui-show-md-inline-block">
                <a href="javascript:;">
                    <img src="//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg" class="layui-nav-img">
                    ${jalosession.attributes['user'].displayName}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">Your Profile</a></dd>
                    <dd><a href="">Settings</a></dd>
                    <dd><a href="">Sign out</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item" lay-header-event="menuRight" lay-unselect>
                <a href="javascript:;">
                    <i class="layui-icon layui-icon-more-vertical"></i>
                </a>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test" id="test">
            </ul>

        </div>
    </div>

    <div class="layui-tab" lay-filter="demo" lay-allowclose="true" style="margin-left: 200px;">
        <ul class="layui-tab-title"></ul>
        <div class="layui-tab-content"></div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
<%--        <div style="padding: 15px;">内容主体区域。记得修改 layui.css 和 js 的路径</div>--%>

        <iframe frameborder="0" scrolling="yes" style="width: 100%" src="" id="aa">
        </iframe>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        底部固定区域
    </div>
</div>




</body>





</body>
</html>