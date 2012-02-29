<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path3 = request.getContextPath();
String basePath3 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path3+"/";
%>
 <div class=pageSide>
总计<%=intAllCount%>条记录，
当前第<%=intCurPage%>/<%=intPageCount%>页
<input type="button" name="Button"  class="pageipt" value="第一页" onclick="javascript:goto(1)"/>
 <input type="button" name="Button2" class="pageipt" value="上一页" onclick="javascript:goto(<%=intCurPage - 1%>)"/>
 <input type="button" name="Button3" class="pageipt" value="下一页" onclick="javascript:goto(<%=intCurPage + 1%>)"/>
 <input type="button" name="Button4" class="pageipt" value="最末页" onclick="javascript:goto(<%=intPageCount%>)"/>
 <select name="<%=com.ejoysoft.common.Constants.PAGE_SIZE_INDEX%>" onChange="goto(this.value)" type="text">
 <%
					for (int j = 0; j < intPageCount; j++) {
				%>
				<option value="<%=j + 1%>">
					<%=j + 1%>
				</option>
				<%
					}
%>
 </select>
 </div>

<script language="JavaScript">
//验证翻页的有效性
function goto(inputVlaue)
{
	if(inputVlaue==0){
	    alert("已经是第一页！");
	    return false;
	    
	}
	if(inputVlaue==<%=(intPageCount + 1)%>){
	    alert("已经是最后一页！");
	    return false;
	}
	frm.<%=com.ejoysoft.common.Constants.PAGE_SIZE_INDEX%>.value=inputVlaue;
	frm.action ="<%=basePath3%>/<%=application.getServletContextName()%>/<%=request.getServletPath()%>";
	frm.submit();
}
</script>