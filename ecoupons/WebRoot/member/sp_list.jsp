<%@ page language="java" import="java.util.*,com.ejoysoft.common.exception.*,com.ejoysoft.common.*" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@ include file="../include/jsp/head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(!globa.userSession.hasRight("11005"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>

<%
  Member  member=null;
  Member obj=new Member(globa);
  String tWhere=" WHERE 1=1";
  tWhere += " ORDER BY dtCreateTime";
	//记录总数
  int intAllCount=obj.getCount(tWhere);
	//当前页
  int intCurPage=globa.getIntCurPage();
	//每页记录数
  int intPageSize=globa.getIntPageSize();
	//共有页数
  int intPageCount=(intAllCount-1)/intPageSize+1;
	// 循环显示一页内的记录 开始序号
  int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
  int intEndNum=intCurPage*intPageSize;   
	//获取到当前页面的记录集
  Vector<Member> vctObj=obj.list(tWhere,intStartNum,intPageSize);
	//获取当前页的记录条数
  int intVct=(vctObj!=null&&vctObj.size()>0?vctObj.size():0);




%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		 
		<title>会员管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/Style.css"/>
		<script type="text/javascript">
		
		function selectUser(obj) /* 分类处理，按钮事件*/
		{	
			var status = "false";
				
			var len=document.forms[0].elements.length;
		   
			var i;
			for (i=0;i<len;i++){
				if (document.forms[0].elements[i].type=="checkbox"){
					if(document.forms[0].elements[i].checked){
						status = "true";	
						break;			
					}		
				}
			}			
			if(status =="true")
			{
			  if(obj==1)
			   {
				  // if(true){
							//	alert("该用户已经是管理员!");
								//return;
					//}	
					if(confirm("您确定要将该用户设置为管理员吗？"))
						{			
						 document.forms[0].elements["operate"].value="toUpdateRole";
			   			 document.forms[0].elements["roleId"].value="1";
			   			 alert("设置为管理员成功!");
			      		 document.forms[0].submit();
			      		 }
			   }
			   else  if(obj==2)
			   {
					   //if(true){
									//alert("该用户已经是VIP会员!");
									//return;
						//}
						if(confirm("您确定要将该用户升级为VIP会员吗？"))
						{
						 document.forms[0].elements["operate"].value="toUpdateRole";
			   			 document.forms[0].elements["roleId"].value="2";
			   			 alert("升级为VIP会员成功!");
			      		 document.forms[0].submit();
			      		 }
			    }
			    else  if(obj==3)
			   {
			   
					if(confirm("您确定要删除该用户吗？"))
						{
				   			// document.forms[0].elements["roleId"].value="0";
				   			 //alert("删除用户成功!");
				      		 //document.forms[0].submit();
				      		 var dataUserInfo = document.getElementById("dataUserInfo");
								var len = dataUserInfo.rows.length;
								//alert(len+"=====");
								if(len==null||len=="undefined")
							       	   return;
							       		
								for (var i=len-1;i>0;i--) {
								     //
								     
									if (dataUserInfo.rows[i].cells[0].children[0].checked) { //4 是单元格 长度
									           
									          dataUserInfo.rows[i].style.display="none";
									         // dataUserInfo.rows[i].cells[0].children[0].value="delete";
									          //alert(dataBox.rows[i].cells[0].children[0].value);
									          //alert(dataBox.rows[i].cells[0].children[1].value);
									       
									}
									 
								} 
								document.forms[0].elements["operate"].value="toUpdateRole";
									 document.forms[0].elements["roleId"].value="0";
									 alert("删除用户成功!");
								     document.forms[0].submit();
							}
			    }
			     else
			   {
				    
					if(confirm("您确定要将该用户设置为普通会员吗？"))
			   		{
			   		
			   			 document.forms[0].elements["operate"].value="toUpdateRole";
			   			 document.forms[0].elements["roleId"].value="3";
			   			 alert("设置为普通会员成功!");
			      		 document.forms[0].submit();
					}
			    }
			  
			}
			else
			{
				alert("你至少要选择一个要操作的用户");
			} 		
			return false;
		}
		
		function  ScanUserDetail(userId)
		{
		 window.showModalDialog("userdetail.htm","用户详情","status=no;dialogWidth=380px;dialogHeight=450px;menu=no;resizeable=yes;scroll=yes;center=yes;edge=raise")
		}
	
    //实现页面上复选控件全选和全不选功能
    function changecolor(parameter)
    {
     if(parameter.checked)
     {
     parameter.parentElement.parentElement.style.background="#ff9900";
     }
     else
     {
     parameter.parentElement.parentElement.style.background="";
     }
    }
function GetAllCheckBox(parentItem)
    {
     var items = document.getElementsByTagName("input"); 
     
     for(i=0; i<items.length;i++)
     {
       
       if(parentItem.checked)
       {
         if(items[i].type=="checkbox")
          {
           items[i].checked = true;
           items[i].parentElement.parentElement.style.background="#ff9900";
           parentItem.parentElement.parentElement.style.background="#ffffff";
          }
       }
      else
      {
         if(items[i].type=="checkbox")
          {
           items[i].checked = false;
           items[i].parentElement.parentElement.style.background="";
          }
       }
      }
    }
    
   
    
</script>

	</head>

	<body>
		<div style="background-color:#F7F8F9">

			<form name="userForm" method="post" action="userlist.htm" style="background-color:#F7F8F9">
				<input type="hidden" name="operate" value="toUserManage" />
				<input type="hidden" name="roleId" value="">

				<div style="width:513px;height:30px; text-align:left;">
					当前位置：会员审批
				</div>
				<div
					style="width:97%;background-color:#F7F8F9; height:30px; border:1px solid gray; text-align:center; font-size:12px;line-height: 200%;">
					<b>会员审批</b>
				</div>
				
				
				<div
					style="width:97%;background-color:#EDDEA0; height:39px; border:1px solid gray; text-align:center;">
					<div style="float:left;width:10%; padding-top:6pxfont-size:12px; background-color:#F7F8F9">
						<input name="selectAll" type="checkbox" id="selectAll"
							onclick="GetAllCheckBox(this)" />

						全选
					</div>
					
					<div style="float:left;width:43%; padding-top:6px;font-size:12px; background-color:#F7F8F9">
						<span id="ctl00_ContentPlaceHolder1_lblUserType">用户类型：</span>

						<select name="item.roleInfo.roleId" onChange="doSubmit()"><option value="0">所有用户</option>
							<option value="1">超级管理员</option>
<option value="2">VIP会员</option>
<option value="3">普通会员</option>
<option value="4">待审核人员</option></select>
					</div>
					
					
					<div style="float:left;width:45%; text-align:left;padding-top:6px;background-color:#F7F8F9">
						&nbsp;&nbsp;
						<img alt="" src="../images/user.gif"
							style="text-align:justify; border:0" />
						&nbsp;
						<input name="btnPass" type="submit" id="btnPass"
							style="cursor:hand; width: 184px; height: 20px;"
							value="通过审核，设置为普通会员" onClick="return selectUser(4);" />
					</div>
					<!-- 用户详情 -->
					
					
					<div style="background-color:#F7F8F9">
					
					
							<table cellspacing="2" cellpadding="3" rules="all" bordercolor="#0099FF" border="1px"
							id="dataUserInfo"
							style="font-size:12px;background-color:#FFFFFF;border-width:1px;border-style:None;width:99%;">

							<tr
								style="color:#000;background-color:#B5D6E6;font-weight:bold;">
								<th scope="col" style="width:15%;">
									选定（√）
								</th>
								<th scope="col">
									用户ID
								</th>
								<th scope="col">
									姓名
								</th>
								<th scope="col">
									密码
								</th>
								<th scope="col">
									角色
								</th>
								<th align="center" scope="col">
									用户详情
								</th>
							</tr>
							
							<c:forEach var="listName" items="${list}">
								<tr style="color:#000066;background-color:#FFF7E7;">
									<td>
										<input type="checkbox" name="userId" value="5" onClick="changecolor(this);" id="userId">
									</td>
									<td>
										5
									</td>
									<td>
										admin
									</td>
									<td>
										123456
									</td>
									<td>
										超级管理员
									</td>
									<td>
										<span id="lblDetail" style="color:Blue;"><a
											href="javascript:ScanUserDetail('5')">查看详细</a> </span>
									</td>
								</tr>
							</c:forEach>
							
					
							

							<tr>
								<td colspan="6" style="text-align: center;">
									<!-- 分页显示 -->
									共15条记录 每页显示
									<input type="text" name="page.pageSize" size="3" value="5" style="width:26px;height:16px;">
									条 第
									<input type="text" name="page.curPage" size="3" value="1" style="width:26px;height:16px;">
									/ 共3页

									<a href="javascript:go(1)">第一页</a>

									

									
										<a href="javascript:goTo(2)">下一页</a>
									

									<a href="javascript:goTo(3)">最后一页</a>&nbsp;&nbsp;
									<button class="common_button" onClick="doTo();"
										style="cursor:hand; width: 44px; height: 21px;">
										GO
									</button>
								</td>
							</tr>
						</table>


						<div>
							<span><input type="button" name="del" value="删除选定用户" onClick="return selectUser(3);" style="cursor:hand; width: 184px; height: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span><IMG align="bottom" alt="升级为VIP会员"
									src="../images/vip.gif" />
								<input type="button" name="upVIP" value="升级为VIP会员" onClick="return selectUser(2);" style="cursor:hand; width: 184px; height: 20px;">
							</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<span><IMG alt="加为管理员" align="bottom" src="../images/xx.gif" />
								<input type="button" name="upManage" value="加为管理员" onClick="return selectUser(1);" style="cursor:hand; width: 184px; height: 20px;">
							</span>
						</div><br>
					</div>
				</div>
			</form>
		</div>
		
		
		<script>
			 function go(i)
 {
   document.all.item("page.curPage").value=i;
   document.forms[0].submit();
 }
 function goTo(i)
 {
     document.all.item("page.curPage").value=i;
    
    document.forms[0].submit();
 }
 function doTo()
 {
    var p=document.all.item("page.curPage").value;
    p=parseInt(p);
    if(isNaN(p))
    p=1;
    
    var total=parseInt("3");
    if(p>total){
   	 document.all.item("page.curPage").value=total;
    }else{
    	document.all.item("page.curPage").value=p;
    }
    document.forms[0].submit();
 }
  function doSubmit(){
   		 goTo(1);
    	document.forms[0].submit();
    }
			</script>
	</body>
</html>