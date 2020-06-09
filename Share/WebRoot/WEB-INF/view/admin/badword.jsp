<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
pageContext.setAttribute("path", path);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>关键词管理</title>
    
    <%@include file="/WEB-INF/view/admin/common/head.jsp"%><!-- 包含公共引用的 js, css 等 -->
    <style>
    	.f_label{
    		border:1px solid #dce1e6;
    		box-shadow: 0 1px 2px #fff inset,0 -1px 0 #a8abae inset;
    		display:inline-block;
    		padding:5px;
    		padding-right:25px;
    		border-radius:5px;
    		position:relative;
    		margin:5px;
    	}
    	
    	.f_label .f_close{
    		position:absolute;
    		right:5px;
    		top:5px;
    		width:15px;
    		height:15px;
    		line-height:12px;
    		text-align:center;
    		border-radius:15px;
    		cursor:pointer;
    		background-color:#39896b;
    		color:white;
    	}
    	.f_label .f_close:hover{
    		background-color:#93cbe6;
    	}
    	
    	.badbox{
    		margin-bottom:40px;
    	}
    	
    	.error{
    		color:red;
    	}
    	
    </style>
    <script>
    	var path="${path}";
    	var adminId=${sessionScope.adminInfo.id};
    </script>
    
    <script>
    	$(function(){
    		getMyBadwordList();
    		getOtherBadwords();
    		
    		//为添加按钮添加点击事件
    		$("#addButton").click(function(){
    			var text=$("#badword").val();
    			if(text==""){
    				alert("关键字不能为空！");
    				return;
    			}
    			$.ajax({
        			url:path+"/badword/addBadword.do",
        			type:'post',
        			dataType:'json',
        			data:{
        				content:text
        			},
        			success:function(data){
        				if(data.status){
        					$("#myModal").modal('hide');
        					var badword=data.data;
        					var html="";
        					html+="<div class='f_label'>";
	   						html+=badword.content;
	   						html+='<div class="f_close" idAttr="'+badword.id+'" '
	   						+' onclick="deleteBadword('+badword.id+')">x</div>';
	   						html+="</div>";
        					$("#mybadword").append(html);
        					
        					toastr.success("添加关键字成功！");
        				}else{
        					alert(data.msg);
        					$("#myModal").modal('close');
        				}
        			}
        			});
    		});
    	});
    
    	//获取自己的关键字
    	function getMyBadwordList(){
    		$.ajax({
    			url:path+"/badword/getBadwordListByAdminId.do",
    			type:'post',
    			dataType:'json',
    			data:{
    				adminId:adminId
    			},
    			success:function(data){
    				if(data.status){
	    				var html="<p>您添加的关键词&nbsp;&nbsp;&nbsp;"
	    						+"<button class='btn btn-default' onclick='openAddDialog()'>添加</button></p>";
	    				var badwords=data.data;
	   					for(var i=0;i<badwords.length;i++){
	   						html+="<div class='f_label'>";
	   						html+=badwords[i].content;
	   						html+='<div class="f_close" idAttr="'+badwords[i].id+'" '
	   						+' onclick="deleteBadword('+badwords[i].id+')">x</div>';
	   						html+="</div>";
	   					}
	   					$("#mybadword").html(html);
    				}else{
    					alert("获取关键字失败！"+data.msg);
    				}
    			}
    		});
    	}
    	
    	//获取其他人的关键字列表
    	function getOtherBadwords(){
    		$.ajax({
    			url:path+"/badword/getBadwordListWithoutMe.do",
    			type:'post',
    			dataType:'json',
    			data:{
    			},
    			success:function(data){
    				if(data.status){
    					var html="";
	    				var admins=data.data;
	    				for(var i=0;i<admins.length;i++){
	    					var badwords=admins[i].badwords;
	    					
    						html+='<div class="badbox">';
    						html+='<p><b>'+admins[i].username+'</b>添加的关键词</p>';
	    					for(var j=0;j<badwords.length;j++){
	    						html+='<div class="f_label">'+badwords[j].content+'</div>';
	    					}
	    					html+='</div>';
	    					
	    				}
	    				$("#otherbadword").html(html);
    				}else{
    					alert("获取关键字失败！"+data.msg);
    				}
    			}
    		});
    	}
    	
    	//打开添加关键字对话框
    	function openAddDialog(){
    		$("#badword").val("");
    		$("#errorMsg").empty();
    		$("#addButton").removeClass("disabled");
    		$("#myModal").modal('show');
    		
    		//为输入框添加键盘事件
    		$("#badword").keyup(function(){
    			var text=$(this).val();
    			$.ajax({
        			url:path+"/badword/findBadwordByContent.do",
        			type:'post',
        			dataType:'json',
        			data:{
        				content:text
        			},
        			success:function(data){
        				if(data.data){
        					$("#errorMsg").html("该关键词已存在!");
        					$("#addButton").addClass("disabled");
        				}else{
        					$("#errorMsg").empty();
        					$("#addButton").removeClass("disabled");
        				}	
        			}
        			});
    		});
    		
    	
    	}
    	
    	function deleteBadword(id){
    		$.ajax({
    			url:path+"/badword/deleteBadword.do",
    			type:'post',
    			dataType:'json',
    			data:{
    				id:id
    			},
    			success:function(data){
    				if(data.status){
    					$(".f_close[idAttr='"+id+"']").parent().remove();
    					toastr.success("删除关键词成功！");
    				}else{
    					toastr.error("删除关键词失败！");
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
  				<h3>关键词管理</h3>
  				<hr>
  				<div id="mybadword" class="badbox">
  					
  				</div>
  				
  				<div id="otherbadword">
	  				
  				</div>
  			</div>
  		</div>
  	</div>
  	
  	
  	<!-- 添加关键字弹出框 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	     aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal">
	                    <span aria-hidden="true">&times;</span>
	                    <span class="sr-only">关闭</span>
	                </button>
	                <h4 class="modal-title" id="myModalLabel">添加关键词</h4>
	            </div>
	            <div class="modal-body">
	                <p class="form-inline">关键词&nbsp;&nbsp;&nbsp;
	                	<input type="text" id="badword" class="form-control"/>
	                	&nbsp;&nbsp;<span class="error" id="errorMsg"></span>
	                </p>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" id="addButton">添加</button>
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	            </div>
	        </div>
	    </div>
	</div>
  	
    <!-- 页脚 -->
	<%@include file="/WEB-INF/view/admin/common/footer.jsp" %>
  </body>
</html>