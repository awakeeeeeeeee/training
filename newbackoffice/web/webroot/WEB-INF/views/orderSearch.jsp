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

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
    </div>
</script>

<table class="layui-hide" id="orderSearch" lay-filter="orderSearch"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>


<script>
    layui.use('table', function(){
        var table = layui.table;

        table.render({
             elem: '#orderSearch'
            ,url:'/newbackoffice/seachOrder'
            ,method:'post'
            ,request: {
                pageName: 'page' //页码的参数名称，默认：page
                ,limitName: 'rows' //每页数据量的参数名，默认：limit
            },
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                ,groups: 1 //只显示 1 个连续页码
                ,first: false //不显示首页
                ,last: false //不显示尾页

            }
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                ,layEvent: 'LAYTABLE_TIPS'
                ,icon: 'layui-icon-tips'
            }]
            ,title: '用户数据表'
            ,parseData:function(res){
                 return{
                     "code":0
                     ,"msg":""
                     ,"data":res
                 }
            }
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'code', width:'20%', title: '编码', sort: true}
                ,{field:'total', width:'20%', title: '总金额'}
                ,{field:'userName', width:'20%', title: '客户名称', sort: true}
                ,{field:'creationtime', width:'20%', title: '创建时间'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
                // ,{field:'sign', title: '签名', width: '30%', minWidth: 100} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
                // ,{field:'experience', title: '积分', sort: true}
                // ,{field:'score', title: '评分', sort: true}
                // ,{field:'classify', title: '职业'}
                // ,{field:'wealth', width:137, title: '财富', sort: true}
            ]]
        });

        //头工具栏事件
        table.on('toolbar(orderSearch)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;

                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(orderSearch)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.prompt({
                    formType: 2
                    ,value: data.userName
                }, function(value, index){
                    obj.update({
                        userName: value
                    });
                    layer.close(index);
                });
            }
        });
    });




</script>



</body>
</html>
