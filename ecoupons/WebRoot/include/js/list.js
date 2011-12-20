var highlightcolor = "#d5f4fe";
var clickcolor = "#51b2f6";
function changeto() {
	source = event.srcElement;
	if (source.tagName == "TR" || source.tagName == "TABLE") {
		return;
	}
	while (source.tagName != "TD") {
		source = source.parentElement;
	}
	source = source.parentElement;
	cs = source.children;
	if (cs[1].style.backgroundColor != highlightcolor && source.id != "nc" && cs[1].style.backgroundColor != clickcolor) {
		for (i = 0; i < cs.length; i++) {
			cs[i].style.backgroundColor = highlightcolor;
		}
	}
}
function changeback() {
	if (event.fromElement.contains(event.toElement) || source.contains(event.toElement) || source.id == "nc") {
		return;
	}
	if (event.toElement != source && cs[1].style.backgroundColor != clickcolor) {
	}
	for (i = 0; i < cs.length; i++) {
		cs[i].style.backgroundColor = "";
	}
}

//点击全选链接
var selFlag = false;    //全选标志
function selAll(obj)
{
    var i;
     var objfrm=eval(obj);
    //没有列表时返回
    if(objfrm==null)
    {
        return;
    }
    if (objfrm.length!=null)
    {   //有多个选框
        if (selFlag)
        {
            for (i=0;i<objfrm.length;i++)
                objfrm[i].checked=false;
            selFlag=false;
        }
        else
        {
            for (i=0;i<objfrm.length;i++)
                objfrm[i].checked=true;

            selFlag=true;
        }
    }
    else
    {   //只有一个选框
        if (selFlag)
        {
            objfrm.checked=false;
            selFlag=false;
        }
        else
        {
            objfrm.checked=true;
            selFlag=true;
        }
    }
}

//返回选中的项目数
function iCheckedNumber(obj)
{
     var objfrm=eval(obj);
    //没有列表时返回
    if(objfrm==null)
    {
        return 0;
    }
    var i;
    var j=0;
    if (objfrm.length!=null)
    {   //多个选项
        for (i=0;i<objfrm.length;i++)
        {
            if (objfrm[i].checked)  j++;
        }
    }
    else
    {   //只有单个选项
        if (objfrm.checked)  j++;
    }
    return j;
}
//返回选中的信息
function strCheckedId(obj)
{
     var objfrm=eval(obj);
    //没有列表时返回
    if(objfrm==null)
    {
        return 0;
    }
    var i;
    var tStrId="";
    if (objfrm.length!=null)
    {   //多个选项
        for (i=0;i<objfrm.length;i++)
        {
            if (objfrm[i].checked){
             if(tStrId=="")
                tStrId=objfrm[i].value;
             else
                 tStrId+=","+objfrm[i].value;
            }

        }
    }
    else
    {   //只有单个选项
        if (objfrm.checked)
         tStrId=objfrm.value;
    }
    return tStrId;
}
//修改，选中且只能选中一条记录
//参数是修改提交的页面
function update(frmObj,obj,actPage)
{
    var frmName=eval(frmObj);
    if(iCheckedNumber(obj)!=1)
    {
        alert("请在列表中选择其中一条记录，再进行操作。");
        return;
    }
     frmName.action=actPage;
     frmName.submit();
}

//删除一条记录
//参数是修改提交的页面
function delOne(frmObj,obj,actPage)
{
    var frmName=eval(frmObj);
    if(iCheckedNumber(obj)!=1)
    {
         alert("请在列表中选择其中一条记录，再进行操作。");
        return;
    }

    if (!confirm("您是否确认要操作所选中的所有记录？"))
    {
        return;
    }
    frmName.action=actPage;
    frmName.submit();
}
//删除，至少选中一条记录
//参数是修改提交的页面
function del(frmObj,obj,actPage)
{
    var frmName=eval(frmObj);
    if(iCheckedNumber(obj)==0)
    {
        alert("请在列表中选择您要操作的记录。");
        return;
    }

    if (!confirm("您是否确认要操作所选中的所有记录？"))
    {
        return;
    }
    frmName.action=actPage;
    frmName.submit();
}
//模糊查询，选中选中一种类型
//参数frmObj是表单名称
function frmSearch(frmObj,actPage)
{
    var frmName=eval(frmObj);
    if (frmName.keyName.value=="")
    {
        alert("请选择查询类别！！！");
        return;
    }
     frmName.action=actPage;
     frmName.PageSizeIndex.value=1;
     frmName.submit();
}
//单项添加的回车事件
function frmSchEnter(frmObj,actPage)
{
    if (event.keyCode==13)
    {
        frmSearch(frmObj,actPage);
    }
}
function setFrameSize(){
        parent.parent.document.all.frmMain.style.height=document.body.scrollHeight;
 }
 //刷新
 function re()
{
    window.location.reload();
   // window.location=window.location;
}
 //定时刷新 times--周期时间-毫秒
var times;
function dorun(times)
{
    timeId=setInterval(re,times);
}
//列表排序
function schSort(sortFeild,sortType){
        if(sortFeild==frm.strSortFeild.value){
            if(frm.strSortType.value=="DESC")
               frm.strSortType.value="ASC";
            else
               frm.strSortType.value="DESC";
        }else {
           frm.strSortFeild.value=sortFeild;
           frm.strSortType.value=sortType;
        }
         frm.submit();
}