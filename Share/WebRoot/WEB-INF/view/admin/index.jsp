<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
pageContext.setAttribute("path", path);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>管理员首页</title>
    
    <%@include file="/WEB-INF/view/admin/common/head.jsp"%><!-- 包含公共引用的 js, css 等 -->
    <script>
    	var path="${path}";
    </script>
    <script>
    	$(function(){
    		getUserList(1,5,null);
    		
    		//为搜索按钮注册点击事件
    		$("#searchBtn").click(function(){
    			var username=$("#username").val();
    			getUserList(1,5,username);
    		});
    	});
    	
    	function getUserList(page,rows,username){
    		$.ajax({
    			url:path+"/admin/getUserList.do",
    			data:{
    				page:page,
    				rows:rows,
    				username:username
    			},
    			dataType:'json',
    			type:'post',
    			success:function(data){
    				if(data.status){
    					var result=data.data;
    					var userList=result.data;
    					var html="";
    					for(var i=0;i<userList.length;i++){
    						var user=userList[i];
    						html+="<tr>";
    						html+="<td>"+user.id+"</td>";
    						html+="<td>"+user.username+"</td>";
    						html+="<td><img class='img-thumbnail' style='margin-top:0px;' src='"+path+"/"+user.imgPath+"'/></td>";
    						html+="<td>"+user.gender+"</td>";
    						html+="<td idAttr='"+user.id+"'>"+createButton(user.id,user.status)+"</td>";
    						html+="</tr>";
    					}
    					//显示用户列表
    					$("#userList").html(html);
    					//分页条
    					var p=new Pagination({
    						containerId:'pageBar',
    						page:result.page,
    						rows:result.rows,
    						count:result.count,
    						toPage:function(page){
    							var username=$("#username").val();
    							getUserList(page,result.rows,username);
    						}
    					});
    					//还原搜索条件
    					$("#username").val(result.username);
    				}else{
    					alert(data.msg);
    				}
    			}
    		});
    	}
    	
    	function createButton(id,status){
    		var html="";
    		if(status==0){
    			html+="<button class='btn btn-default' onclick='hideUser("+id+")'>屏蔽用户</button>";
    		}else{
    			html+="<button class='btn btn-default' onclick='enableUser("+id+")'>取消屏蔽</button>";
    		}
    		return html;
    	}
    	
    	function hideUser(id){
    		$.ajax({
    			url:path+"/admin/hideUser.do",
    			data:{
    				id:id
    			},
    			dataType:'json',
    			type:'post',
    			success:function(data){
    				if(data.status){
    					var btnHtml=createButton(id,1);
    					$("td[idAttr='"+id+"']").html(btnHtml);
    					toastr.success('屏蔽用户成功！');
    				}else{
    					alert(data.msg);
    				}
    			}
    		});
    	}
    	
    	function enableUser(id){
    		$.ajax({
    			url:path+"/admin/enableUser.do",
    			data:{
    				id:id
    			},
    			dataType:'json',
    			type:'post',
    			success:function(data){
    				if(data.status){
    					var btnHtml=createButton(id,0);
    					$("td[idAttr='"+id+"']").html(btnHtml);
    					toastr.success('取消屏蔽成功！');
    				}else{
    					alert(data.msg);
    				}
    			}
    		});
    	}
    	
    </script>
  </head>
  
  <body style="padding-top:50px;">
  	<%@include file="/WEB-INF/view/admin/common/navbar.jsp"%><!-- 导航栏 -->
  	
  	<!-- 页面内容 -->
  	<div class="container">
  		<div class="row">
  			<div class="col-md-12">
  				<h3 style="padding-left:15px;">用户列表</h3>
  				<div class="form-inline" style="padding-left:15px;padding-bottom:10px;">
  					<!-- 搜索框 -->
  					用户名&nbsp;<input type="text" class="form-control" id="username">
  						<input type="submit" class="btn btn-default" value="搜索" id="searchBtn">
  				</div>
  				<div class="row">
  					<!-- 表格 -->
  					<div class="col-md-10">
  						<div class="table-responsive">
						  <table class="table table-hover table-bordered">
						    <thead>
						    	<tr>
						    		<th>ID</th>
						    		<th>用户名</th>
						    		<th>头像</th>
						    		<th>性别</th>
						    		<th>操作</th>
						    	</tr>
						    </thead>
						    <tbody id="userList">
						    	
						    </tbody>
						  </table>
						  <div id="pageBar"></div>
						</div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
  	
    <!-- 页脚 -->
	<%@include file="/WEB-INF/view/admin/common/footer.jsp" %>
  </body>
</html>
