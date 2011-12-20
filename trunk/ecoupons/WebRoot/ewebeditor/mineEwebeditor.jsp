<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    String content=request.getParameter("content");
    StringBuffer ContentValue=new StringBuffer("parent.document.all.").append(content).append(".value");

%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<title>eWebEditor</title>
	<script type="text/javascript" src="js/buttons.js"></script>
	<script type="text/javascript" src="js/msgEditor.js"></script>
	<script type="text/javascript">
		LoadScript("style/mineCoolblue.js");
	</script>
	<script type="text/javascript">
		lang.Init();
		config.ServerExt = "jsp";
		config.AffairId = myParam.StrAffairId+"";
        config.FileType = myParam.StrType+"";
		LoadScript("language/" + lang.ActiveLanguage + ".js");
	</script>
	<script type="text/javascript">
		myEditor_Init();
		document.write ("<link href='" + myEditor.RootPath + "/language/" + lang.ActiveLanguage + ".css' type='text/css' rel='stylesheet'>");
		document.write ("<link href='" + myEditor.RootPath + "/skin/" + config.Skin + "/editor.css' type='text/css' rel='stylesheet'>");
	</script>
</head>

<body scrolling=no>
<script type="text/javascript">
config.InitMode = "EDIT";
if (myParam.Editable == "false") {
	config.InitMode = "VIEW";
}
showEditorBody();
</script>
</body>
</html>