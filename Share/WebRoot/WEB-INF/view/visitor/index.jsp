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
    	$(function(){
    		getShareList();
    	});
    </script>

</head>
<body>
<%@include file="/WEB-INF/view/visitor/visitor_navbar.jsp"%><!-- 导航栏 -->

<div class="container">
    <div class="row">
		
        <div class="col-md-9 col-md-offset-1">
        	<h3>最新分享</h3>

            <div id="shareList"></div><!-- 展示分享区域 -->
             
        </div>
        <div class="col-md-10 col-md-offset-1">
        	<hr/>
    		<div id="pageNavigator"></div>
        </div>
        
    </div>
</div>


<!-- 页脚 -->
<%@include file="/WEB-INF/view/layout/footer.jsp" %>
</body>
</html>
