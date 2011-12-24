var gcToggle = "#ffff00";
var gcBG = "#CCCCFF";
function IOFFICE_GetSelected(aCell)
{
        window.returnValue = aCell.innerText;
        window.close();
}
function fPopUpDlg(endtarget,ctl,WINname,WINwidth,WINheight){
    showx =  WINwidth + 120  ; // + deltaX;
    showy = WINheight -50 ; // + deltaY;
    newWINwidth = WINwidth + 4 + 18;
    var features =
        'dialogWidth:'  + newWINwidth  + 'px;' +
        'dialogHeight:' + WINheight + 'px;' +
        'dialogLeft:'   + showx     + 'px;' +
        'dialogTop:'    + showy     + 'px;' +
        'directories:no; localtion:no; menubar:no; status=no; toolbar=no;scrollbars:yes;Resizeable=no';
    retval = window.showModalDialog(endtarget, WINname , features );
    if( retval != null ){
        ctl.value = retval;
    }else{
       //alert("canceled");
    }
}
function fPopUpCalendarDlg(ctrlobj)
{
    showx = event.screenX - event.offsetX - 4 - 210 ; // + deltaX;
    showy = event.screenY - event.offsetY + 18; // + deltaY;
    newWINwidth = 210 + 4 + 18;

    retval = window.showModalDialog("CalendarDlg.htm", "", "dialogWidth:197px; dialogHeight:210px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no; ");
    if( retval != null ){
        ctrlobj.value = retval;
    }else{
        //alert("canceled");
    }
}
//
function fPopCalDlg(obj){
   endtarget="/priproj/include/date/calendar.htm";
   fPopUpDlg(endtarget, obj, 'winpop', 234, 261);
   obj.value = obj.value.replace("/", "-").replace("/", "-");
   //return false;
 }
 function seldate(obj){
      fPopUpDlg("/priproj/include/date/calendar.htm", obj, "winpop", 234, 261);
      return ;
}
function seldate2(obj){
   endtarget="/priproj/include/date/calendar.htm";
   fPopUpDlg(endtarget, obj, 'winpop', 234, 261);
   obj.value = obj.value.replace("-", "/").replace("-", "/");
  return ;
 }


