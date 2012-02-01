<%@ page contentType="text/html;charset=UTF-8"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="41%" height="22">
			&nbsp;&nbsp;共有
			<%=intAllCount%>
			条记录，当前第
			<%=intCurPage%>/<%=intPageCount%>
			页
		</td>
		<td width="44%" align="right">
			<img src="../images/first.gif"
					width="37" height="15" onclick="javascript:goto(1)" style="cursor:hand"/>
			<img
					src="../images/back.gif" width="43" height="15" onclick="javascript:goto(<%=intCurPage - 1%>)" style="cursor:hand"/>
			<img
					src="../images/next.gif" width="43" height="15" onclick="javascript:goto(<%=intCurPage + 1%>)" style="cursor:hand"/>
			<img
					src="../images/last.gif" width="37" height="15" onclick="javascript:goto(<%=intPageCount%>)" style="cursor:hand"/>
		<td width="10%" align="right">
			转到
			<select name="<%=com.ejoysoft.common.Constants.PAGE_SIZE_INDEX%>"
				onChange="goto(this.value)" type="text">
				<%
					for (int j = 0; j < intPageCount; j++) {
				%>
				<option value="<%=j + 1%>">
					第<%=j + 1%>页
				</option>
				<%
					}
				%>
			</select>&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td height="30" colspan="3">
			&nbsp;
		</td>
	</tr>
</table>
<script language="JavaScript">
//验证翻页的有效性
function goto(inputVlaue)
{
	if(inputVlaue==0){
	    alert("已经是第一页！");
	    return;
	}
	if(inputVlaue==<%=(intPageCount + 1)%>){
	    alert("已经是最后一页！");
	    return;
	}
	frm.<%=com.ejoysoft.common.Constants.PAGE_SIZE_INDEX%>.value=inputVlaue;
	frm.action ="<%=application.getServletContextName()%>/<%=request.getServletPath()%>";
	frm.submit();
}
</script>