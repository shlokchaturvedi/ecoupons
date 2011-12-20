  //È¥³ý¿Õ¸ñ
function trim(str)
{
    rstr="";
    for(i=0;i<str.length;i++)
    {
        if(str.charAt(i)!="　"&&str.charAt(i)!=" ")
        {
            rstr=str.substring(i);
            break;
        }
    }
    for(i=rstr.length-1;i>0;i--)
    {
        if(rstr.charAt(i)!="　"&&rstr.charAt(i)!=" ")
        {
            rstr=rstr.substring(0,i+1);
            break;
        }
    }
    return rstr;
}

//ÑéÖ¤ÈÕÆÚ
function isDate(input)
{
        var pattern=/^\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2]\d|3[0-1])$/;
        return pattern.test(input);
}
//ÑéÖ¤Äê·Ý
function isYear(input)
{
        var pattern=/^\d{4}$/;
        return pattern.test(input);
}
//ÑéÖ¤ÓÐ0-4Î»Ð¡ÊýµÄÕýÊµÊý
function isZFourNumber(input)
{
        var pattern=/^[0-9]+(\.[0-9]{0,4})?$/;
        return pattern.test(input);
}
//ÑéÖ¤ÓÐ0-4Î»Ð¡ÊýµÄÊµÊý
function isFourNumber(input)
{
        var pattern=/^-?[0-9]+(\.[0-9]{0,4})?$/; 
        return pattern.test(input);
}
//ÊÇ·ñÊÇÊý×Ö
function isNumber(input)
{
        var pattern=/^\d+$/;
        return pattern.test(input);
}
//ÊÇ·ñÊÇ×ÖÄ¸
function isChar(input)
{
        var pattern=/^[A-Za-z]+$/;
        return pattern.test(input);
}
//ÅÐ¶ÏÊÇ·ñÎªºÏ·¨ÕûÊý
function isInt(str) {
    if (str == "")
        return false;
    for (i = 0; i < str.length; i++) {
        if (str.charAt(i) < '0' || str.charAt(i) > '9')
            return false;
    }
    return true;
}
//ÊÇ·ñÊÇÊý×Ö
function isMoney(input)
{
        var pattern=/^\d+(\.\d+){0,1}$/;  
        //^[0-9]{1,3}(,[0-9]{3})*(\.[0-9]{1,2})?$
        return pattern.test(input);
}
 //¼ì²éEMailµØÖ·
function isEmail(email)
{
    var stringTemp = trim(email);
    var filter=/^.+@.+\..{2,3}$/
    if(email!="")
    if (!filter.test(email))
        return false;
    return true;
}
//select Ñ¡ÖÐ
function selSelect(obj,selectedValue)
{
    //Èç¹ûÑ¡Ôñ²Ëµ¥Ä³ÏîµÄÖµµÈÓÚselectedValue£¬Ôò¸ÃÏîÎªÄ¬ÈÏÑ¡Ïî
    if(obj==null)return;
    if(obj.length==null){
        obj.checked=true;
        return
    }
    for (var i=0;i<obj.length;i++){
     if(obj[i].value==selectedValue)
    {
        obj[i].selected=true;
      	return;
    }
    if(selectedValue=="")
    {
        obj[i].selected=false;
     	return;
    }
  }
}
//radio ·µ»ØÖµ
function getRadioValue(obj){
    var objfrm=eval(obj);
    var tmp;
    tmp=""
    //Ã»ÓÐÁÐ±íÊ±·µ»Ø
    if(objfrm==null) {
        return tmp;
    }
    var i;
    var j=0;
    if (objfrm.length!=null){   //¶à¸öÑ¡Ïî
        for (i=0;i<objfrm.length;i++){
            if (objfrm[i].checked)
                tmp=objfrm[i].value ;
        }
    }
    return tmp;
}
//radio Ñ¡ÖÐ
function selRadio(obj,selectedValue)
{
    //Èç¹ûÑ¡Ôñ²Ëµ¥Ä³ÏîµÄÖµµÈÓÚselectedValue£¬Ôò¸ÃÏîÎªÄ¬ÈÏÑ¡Ïî
    if(obj==null)return;
    if(obj.length==null){
        //obj.checked=true;
        return;
    }
    for (var i=0;i<obj.length;i++){
     if(obj[i].value==selectedValue)
    {
        obj[i].checked=true;
    	return;
    }
    if(selectedValue=="")
    {
        obj[i].checked=false;
    	return;
    }
  }
}
//Ìí¼ÓµÄ»Ø³µÊÂ¼þ
function itemEnter()
{
    if (event.keyCode==13)
    {
          frm.submit();
    }
}
//Ñ¡ÔñÓÃ»§,´ø»Øµ÷º¯Êý¡¢·µ»ØÀàÐÍºÍ·µ»ØÊýÁ¿from-±íµ¥È«Ãû³Æ£¬unit-(0-µ±Ç°¶ÀÁ¢µ¥Î»,1-ËùÓÐµ¥Î»×é£©defaultGroup-Ä¬ÈÏµÄ×éid,  callback-»Øµ÷µÄjs·½·¨Ãû³Æ£¬returnNum-·µ»ØµÄÑ¡Ôñ¸öÊý
// returnType·µ»ØÀàÐÍ
  // 0±íÊ¾¡°ÀàÐÍ/±àºÅ/ÐÕÃû£¨×éÃû£©¡±£»
    // 1±íÊ¾¡°±àºÅ/ÐÕÃû£¨×éÃû£©¡±£»
    // 2±íÊ¾¡°ÐÕÃû£¨×éÃû£©¡±£»
    // 3±íÊ¾¡°ÀàÐÍ/±àºÅ¡±;
function selUserAll(from,unit,defaultGroup,callback,returnType,returnNum) {
    window.open("/include/jsp/selUnitUser.jsp?from=" + from + "&unit="+unit+"&defaultGroup=" + defaultGroup + "&callback=" + callback + "&returnType=" + returnType + "&returnNum=" + returnNum, "Ñ¡ÔñÓÃ»§", "width=650,height=370,scrollbars=yes,status=yes");
}
function selNodeUser(from,nodeId,frmDate,unit,defaultGroup,callback,returnType,returnNum) {
   if(trim(nodeId)==""){
         alert("ÇëÑ¡Ôñ½ÚµãÃû³Æºó£¬ÔÙÑ¡Ôñ½ÓÊÜÈË£¡");
         // frm.strCurNodeName.focus();
            return false;
   }
   else
        window.open("/include/jsp/selFlowNodeUser.jsp?from=" + from + "&nodeId="+nodeId+"&frmDate="+frmDate+"&unit="+unit+"&defaultGroup=" + defaultGroup + "&callback=" + callback + "&returnType=" + returnType + "&returnNum=" + returnNum, "Ñ¡ÔñÓÃ»§", "width=650,height=370,scrollbars=yes,status=yes");
}
function selUpUnit(from,unit,defaultGroup,callback,returnType,returnNum) {
    window.open("/include/jsp/selUnit.jsp?from=" + from + "&unit="+unit+"&defaultGroup=" + defaultGroup + "&callback=" + callback + "&returnType=" + returnType + "&returnNum=" + returnNum, "Ñ¡ÔñÓÃ»§", "width=650,height=370,scrollbars=yes,status=yes");
}