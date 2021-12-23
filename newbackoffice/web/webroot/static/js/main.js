$(document).ready(function(){

        //注意：导航 依赖 element 模块，否则无法进行功能性操作
        layui.use('element', function(){
        var $ = layui.jquery
        ,element = layui.element;
        //获取所有的菜单
        $.ajax({
        url:"/newbackoffice/loadui/tree",
        type:"POST",
        dataType:"json",
        success:function (res) {
        // console.log(res);
        //先添加所有的主材单
        $.each(res,function(i,obj){
        console.log(obj.children);
        var content='<li class="layui-nav-item" data-name='+obj.action+'>';
        //判断是否存在下一级栏目，存在就不跳转，不存在就跳转
        if (obj.url != "" && obj.url != null) {
            // alert(obj.name)
        content+='<a data-src="'+obj.url+'" class="' + obj.id + '"><cite>'+obj.name+'</cite></a>';
    }else{
        content+='<a data-src="javascript:;"><cite>'+obj.name+'</cite></a>';
    }


        //这里是添加所有的子菜单
        content+=loadchild(obj);
        content+='</li>';
        $(".layui-nav-tree").append(content);
    });
        element.init();
    },error:function(jqXHR){
        alert("发生错误："+ jqXHR.status);
    }

    });


        //组装子菜单的方法
        function loadchild(obj){
        if(obj==null){
        return;
    }
        var content='';
        if(obj.children!=null && obj.children.length>0){
        content+='<dl class="layui-nav-child">';
    }else{
        content+='<dl>';
    }

        if(obj.children!=null && obj.children.length>0){
        $.each(obj.children,function(i,note){
        content+='<dd class="main_left">';
        if(note.url != "" && note.url != null){
            // alert(obj.name)
            content+='<a data-src="'+note.url+'" class="' + obj.id + '" target="_top">'+note.name+'</a>';
        }else {
            content+='<a data-src="javascript:;"><cite>'+note.name+'</cite></a>';
        }

        if(note.children==null){
        return;
    }
        content+=loadchild(note);
        content+='</dd>';
    });

        content+='</dl>';
    }
        return content;
    }
    });



    $(".layui-nav-tree").on('click', '.main_left a', function(){
        var address =$(this).attr("data-src");
        if(address != null && address != ""){
            $("iframe").attr("src","/newbackoffice" + address);
        }

    })


    //根据窗口高度在设置iframe的高度
    var frame = $("#aa");
    var frameheight = $(window).height();
    console.log(frameheight);
    frame.css("height",frameheight);


});

