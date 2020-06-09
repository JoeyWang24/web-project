<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<nav class="navbar navbar-fixed-top navbar-default" role="navigation" id="navigator">
    <div class="container">
        <!-- 导航栏标志 -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${path}/visitor/indexUI.do" id="nav-brand">
                <img src="${path}/Static/assets/images/logo.png" id="img-brand"> Share</a>
        </div>

        <!-- 导航栏功能元素 -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <!-- 搜索框 -->
            <form name="form-search" class="navbar-form navbar-left" role="search">
                <div class="input-group">
                    <input type="text" id="text-search" name="condition" value="${param.condition}" class="form-control"
                           placeholder="搜索分享、用户">
                    <span id="btn-search" class="input-group-addon search-btn-pointer"><span
                            class="glyphicon glyphicon-search"></span></span>
                </div>
            </form>

            <%-- 登录 --%>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${path }/user/loginUI.do" class="force-white">
                    <span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
            </ul>

            <!-- 注册 -->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${path }/user/registerUI.do" class="force-white">
                    <span class="glyphicon glyphicon-user"></span> 注册</a></li>
            </ul>

            <!-- 点赞榜 -->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${path}/visitor/rankingPage.do" class="force-white"><span
                        class="glyphicon glyphicon-thumbs-up"></span> 点赞榜</a></li>
            </ul>
            <!-- 首页 -->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${path}/visitor/indexUI.do" class="force-white"><span class="glyphicon glyphicon-home"></span>
                    首页</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<script>
    //注册搜索框点击事件
    $("#btn-search").click(function () {
        //去除两边空格
        $("#text-search").val($.trim($("#text-search").val()));
        //搜索栏为空不提交表单
        if ($("#text-search").val() != "") {
            document.forms['form-search'].action = "${path}/visitor/searchPage.do";
            document.forms['form-search'].method = "post";
            document.forms['form-search'].submit();
        }
    });

    //注册‘按下回车键搜索’事件
    $("#text-search").keypress(function (e) {
        // 回车键事件
        if (e.keyCode == 13) {
            $("#btn-search").click();
            //取消按下回车提交表单
            return false;
        }
    });
</script>