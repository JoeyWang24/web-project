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
            <a class="navbar-brand" href="${path}/admin/indexUI.do" id="nav-brand">
                <img src="${path}/Static/assets/images/logo.png" id="img-brand"> Share
            </a>
            <h3 style="width:200px;float:left;margin:0px;padding:0px;margin-top:10px;color:white;">后台管理系统</h3>
        </div>

        <!-- 导航栏功能元素 -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <!-- 退出登录 -->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${path }/admin/logout.do" class="force-white">
                    <span class="glyphicon glyphicon-log-out"></span> 退出 </a></li>
            </ul>
            <!-- 显示用户名 -->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" class="force-white">
                     	欢迎您，${sessionScope.adminInfo.username}
                    <%-- <img src="${path}/${sessionScope.loginInfo.imgPath}" class="img-thumbnail">--%></a>
                </li>
            </ul>
            <!-- 关键词 -->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${path }/badword/badwordUI.do" class="force-white">关键词管理</a></li>
            </ul>
            
            <!-- 用户管理 -->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${path }/admin/indexUI.do" class="force-white"> 用户管理</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>