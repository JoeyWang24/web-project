<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    pageContext.setAttribute("path", path);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>首页</title>
    <%@include file="/WEB-INF/view/visitor/header.jsp"%><!-- 包含公共引用的 js, css 等 -->

    <link href="${path}/Static/assets/bootstrap/css/awesome-bootstrap-checkbox.css" rel="stylesheet">


    <!-- toastr消息提示 -->
    <link href="${path }/Static/assets/toastr/toastr.css" rel="stylesheet"/>
    <script src="${path }/Static/assets/toastr/toastr.js"></script>

    <!-- 用户相关函数 -->
    <script src="${path }/Static/assets/custom/js/visitor.js"></script>

    <script>
        $(function () {
            //初始化分享列表
            getShareList(null, null, null, null, 4, 1);
        });
    </script>

</head>
<body>
<%@include file="/WEB-INF/view/visitor/visitor_navbar.jsp"%><!-- 导航栏 -->

<div class="container">
    <%-- 点赞榜样式--%>
    <%@include file="/WEB-INF/view/layout/like_ranking.jsp" %>
    <div id="pageNavigator">
    </div>
</div>
<!-- 警告弹出框 -->
<%@include file="/WEB-INF/view/layout/model_dialog.jsp" %>
<%@include file="/WEB-INF/view/layout/footer.jsp"%><!-- 页脚 -->
</body>
</html>