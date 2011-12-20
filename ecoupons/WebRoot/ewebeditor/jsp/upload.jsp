<%
/***********************************************************
*   感谢你使用 eWebEditor在线文本编辑器杨伟昌修改版(v5.2)
*   本代码完全公开和免费，你可以任意复制、传播、修改和使用，
*   但不得公开发表代码 不得用做商业用途，不得向其他使用者收费，
*   请勿用于商业用途，仅供学习使用(使用时，保留此段信息，谢谢配合)。
*                                            
*                                         修改者：杨伟昌 
*                                          Q Q  ：343004545
************************************************************/ 
%>
<%@ page contentType="text/html; charset=GB2312"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.regex.*"%>
<%@ page import="com.pub.File"%>
<%@ page import="com.pub.SmartUpload"%> 
<%@ page import="com.ejoysoft.util.RemotePic"%> 
<%@ page import="com.pub.Tools"%>
<%@ page import="com.pub.ImageUtils"%>
<%@ include file="../../include/jsp/head.jsp"%>
<%!// 参数变量
	String sType, sStyleName;
	//' 设置变量
	String sAllowExt, sUploadDir, sBaseUrl, sContentPath;
	int nAllowSize;

	//' 接口变量
	String sFileExt, sSaveFileName, sOriginalFileName, sPathFileName, FileName,
			nFileNum, realpatha;
	String sAction;

	%>
<%!/*' ============================================
	*' 去除Html格式，用于从数据库中取出值填入输入框时
	 ' 注意：value="?"这边一定要用双引号
	 ' ============================================*/
	public String inHTML(String str) {
		String sTemp;
		sTemp = str;
		if (sTemp.equals("")) {
			sTemp="&nbsp;";
		}
		sTemp = sTemp.replaceAll("&", "&amp;");
		sTemp = sTemp.replaceAll("<", "&lt;");
		sTemp = sTemp.replaceAll(">", "&gt;");
		sTemp = sTemp.replaceAll("\"", "&quot;");
		return sTemp;
	}
	//初始化上传限制数据
	public void InitUpload(String realpath,String sUploadFilePath,int year,String strType,String strAffairId) {
		try {
			//String=config.getServletContext().getRealPath("/");
			realpatha=getServletContext().getRealPath("/")+"/"+sUploadFilePath+"/"+year+"/"+strType+"/"+strAffairId+"/";
			 //图片和新闻同步,并且将图片存放在UPLOAD下面
			 //动态的创建UPLOAD目录
			 java.io.File picStoreDir=new java.io.File(realpatha);
			 picStoreDir.mkdirs();
			//此处设置上传文件保存路径,注意路径要由ROOT开始=========================
			ServletContext app=(ServletContext)com.ejoysoft.conf.SysConfig.application;
			sUploadDir=("file".equals(sType)?"..":app.getServletContextName())+"/"+sUploadFilePath
					+ year + "/"
					+ strType + "/"
					+ strAffairId + "/";
				System.out.println("<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>"+sType);	
			if (sType.equalsIgnoreCase("remote")) {
				sAllowExt = "gif|jpg|jpeg|bmp|png|JPG|JPEG|GIF|BMP|PNG";
				nAllowSize = 100000;
			} else if (sType.equalsIgnoreCase("file")) {
				sAllowExt = "rar|zip|doc|xls|chm|hlp|pdf|txt|gif|jpg|jpeg|bmp|png|swf|txt|ceb|ppt|PPT";
				nAllowSize = 100000;
			} else if (sType.equalsIgnoreCase("media")) {
				sAllowExt = "rm|mp3|wav|mid|midi|ra|avi|mpg|mpeg|asf|asx|wma|mov";
				nAllowSize = 100000;
			} else if (sType.equalsIgnoreCase("flash")) {
				sAllowExt = "swf";
				nAllowSize = 100000;
			} else {
				sAllowExt = "gif|jpg|jpeg|bmp|png|JPG|JPEG|GIF|BMP|PNG";
				nAllowSize = 100000;
			}
		} catch (Exception e) {
		}
	}

	%>
<%//设置类型
			sType = request.getParameter("type");
			if (sType == null) {
				sType = "image";
			} else {
				sType = request.getParameter("type").trim();
			}
			//设置样式
			sStyleName = request.getParameter("style");
			if (sStyleName == null) {
				sStyleName = "coolblue";
			} else {
				sStyleName = request.getParameter("style").trim();
			}
			//设置动作
			sAction = request.getParameter("action");
			if (sAction == null) {
				sAction = "sun";
			} else {
				sAction = request.getParameter("action").trim();
			}

			%>
<%//初始化上传变量
			
			String strType=ParamUtil.getString(request,"strType","");
			String strAffairId=ParamUtil.getString(request,"strAffairId","");
			Calendar calendar = Calendar.getInstance();
			InitUpload(config.getServletContext().getRealPath("/"),globa.getPropValue("PATH_WEB_FILE"),calendar.get(Calendar.YEAR),strType,strAffairId);
			//断开数据库连接
			//sAction = UCase(Trim(Request.QueryString("action"));
			if (sAction.equalsIgnoreCase("remote")) { //远程自动获取
				String sContent;
				String RemoteFileurl = null;
				String Protocol, sUrl;
				int Port;
				String LocalFileurl = null;
				String SrcFileurl = null;
				String SaveFileName = null;
				sContent = request.getParameter("eWebEditor_UploadText");
				if (sContent == null) {
					sContent = "sunshanfeng";
				} else
					sContent = request.getParameter("eWebEditor_UploadText");
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println("替换前的html标记为:" + "\n" + sContent);
				if (sAllowExt != "") {
					Pattern pRemoteFileurl = Pattern
							.compile("((http|https|ftp|rtsp|mms):(//|\\\\){1}(([A-Za-z0-9_-])+[.]){1,}(net|com|cn|org|cc|tv|[0-9]{1,3})(\\S*/)((\\S)+[.]{1}("
									+ sAllowExt + ")))");//取得网页上URL的正则表达式
					Matcher mRemoteFileurl = pRemoteFileurl.matcher(sContent);//对传入的字符串进行匹配
					Protocol = request.getProtocol();//取得通讯的协议
					String ProtocolA[] = Protocol.split("/");//取得协议前面的字母，如HTTP/1.1,变为"HTTP","1.1"
					sUrl = ProtocolA[0] + "://" + request.getServerName();//取得本地URL路径,如http://localhost
					//ProtocolA[]=null;
					Port = request.getServerPort();//取得端口值
					if (Port != 80) {//查看端口是否为80，如果不是还需要在联接上加上端口
						sUrl = sUrl + ":" + Port;
					}
					String context = request.getContextPath();
					sUrl = sUrl + context + "/" + sUploadDir;
					System.out.println(sUrl);
					StringBuffer sb = new StringBuffer();
					boolean result = mRemoteFileurl.find();
					int i = 0;

					while (result) {

						i++;
						RemoteFileurl = mRemoteFileurl.group(0);
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println("需要替换的远程连接：" + "\n" + RemoteFileurl);
						sOriginalFileName = RemoteFileurl
								.substring(RemoteFileurl.lastIndexOf("/"));
						Pattern pFileType = Pattern.compile("[.]{1}("
								+ sAllowExt + ")");//二次匹配取得文件的类型
						Matcher mFileType = pFileType.matcher(RemoteFileurl);
						while (mFileType.find()) {
							String SaveFileType = mFileType.group();
							LocalFileurl = sUploadDir + i + SaveFileType;//文件的路径，以时间戳命名
						}

						String LoadFile = sUploadDir.substring(0, sUploadDir
								.length() - 1);
						SaveFileName = config.getServletContext().getRealPath(
								"/")
								+ LoadFile
								+ LocalFileurl.substring(LocalFileurl
										.lastIndexOf("/") + 1);
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println("远程文件保存的路径和文件名：" + "\n"
								+ SaveFileName);
						sSaveFileName = LocalFileurl.substring(LocalFileurl
								.lastIndexOf("/"));
						RemotePic Down = new RemotePic();
						Down.picurl = RemoteFileurl;
						Down.savepath = SaveFileName;
						if (Down.download())//如果上载保存成功，则更换html标记里的文件路径
						{
							mRemoteFileurl.appendReplacement(sb, LocalFileurl);//替换路径
						}
						result = mRemoteFileurl.find();
					}
					mRemoteFileurl.appendTail(sb);
					sContent = sb.toString();
				}
				sContent = inHTML(sContent);
			    System.out.println(sContent);
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.print("替换后的html标记:" + "\n" + sContent);
				out.println("<HTML><HEAD><TITLE>远程上传</TITLE><meta http-equiv='Content-Type' content='text/html; charset=utf-8'></head><body>");
				out.print("<input type=hidden id=UploadText value=\"");
				out.print(sContent);
				out.println("\">");
				out.println("</body></html>");
				out.println("<script language=javascript>");out.println("alert(UploadText.value)");
				out.print("parent.setHTML(UploadText.value);try{parent.addUploadFile('");//为什么只取一半的值？且只取复制网页插入位置之前的值？
				out.print(sOriginalFileName);
				out.print("', '");
				out.print(sSaveFileName);
				out.print("', '");
				out.print(SaveFileName);
				out.println("');} catch(e){} parent.remoteUploadOK();");
				out.println("</script>");

				//DoRemote();
			} else if (sAction.equalsIgnoreCase("save")) {
				//显示上传菜单
				System.out.println(sAction);
				out.println("<HTML>");
				out.println("<HEAD>");
				out.println("<TITLE>文件上传</TITLE>");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
				out.println("<style type=\"text/css\">");
				out.println("body, a, table, div, span, td, th, input, select{font:9pt;font-family: \"宋体\", Verdana, Arial, Helvetica, sans-serif;}");
				out.println("body {padding:0px;margin:0px}");
				out.println("</style>");
				out.println("<script language=\"JavaScript\" src=\"../dialog/dialog.js\">");
				out.println("</script>");
                out.println("<link href=\"../dialog/dialog.css\" type=\"text/css\" rel=\"stylesheet\">");
				out.println("</head>");
				out.println("<body bgcolor=menu>");
				out.print("<form action=\"?action=save&type=");//注意此处为什么不用println()
				out.print(sType);
				out.print("&style=");
				out.print(sStyleName);
				out.println("\" method=post name=myform id=myform enctype=\"multipart/form-data\">");
				//out.println("<input type=submit name=Submit value=\"上传它！\">");
				out.println("</form>");
				out.println("<script language=javascript>");
				out.print("var sAllowExt = \"");
				out.print(sAllowExt);
				out.println("\";");
				out.println("// 检测上传表单");
				out.println("function CheckUploadForm() {");
				out.println("if (!IsExt(document.myform.uploadfile.value,sAllowExt)){");
				out.println("alert(\"提示：\\n\\n请选择一个有效的文件，\\n支持的格式有（\"+sAllowExt+\"）！\");");
				out.println("return false;");
				out.println("}");
				out.println("return true");
				out.println("}");
				out.println("// 提交事件加入检测表单");
				out.println("var oForm = document.myform;");
				out.println("oForm.attachEvent(\"onsubmit\", CheckUploadForm) ;");
				out.println("if (! oForm.submitUpload) oForm.submitUpload = new Array() ;");
				out.println("oForm.submitUpload[oForm.submitUpload.length] = CheckUploadForm ;");
				out.println("if (! oForm.originalSubmit) {");
				out.println("oForm.originalSubmit = oForm.submit ;");
				out.println("oForm.submit = function() {");
				out.println("if (this.submitUpload) {");
				out.println("for (var i = 0 ; i < this.submitUpload.length ; i++) {");
				out.println("this.submitUpload[i]() ;");
				out.println("			}");
				out.println("		}");
				out.println("		this.originalSubmit() ;");
				out.println("	}");
				out.println("}");
				out.println("// 上传表单已装入完成");
				out.println("try {");
				out.println("	parent.UploadLoaded();");
				out.println("}");
				out.println("catch(e){");
				out.println("}");
				out.println("</script>");
				out.println("</body>");
				out.println("</html>");
				//存文件
				//DoSave();
				SmartUpload up = new SmartUpload();
				ImageUtils image=new ImageUtils(); //添加水印;
				String logo="E:\\logo.gif";     //水印文件路径
				String fonts="杨伟昌修改版QQ:343004545";
				//初始化上传组件
				up.initialize(pageContext);
				//设置上传文件大小
				up.setMaxFileSize(nAllowSize * 1024);
				//设置上传文件类型
				System.out.println(sAllowExt);
				String setExt = sAllowExt.replace('|', ',');
				//System.out.println(setExt);
				up.setAllowedFilesList(setExt);
				// Upload
				up.upload();
				// Select each file			   
				for (int i = 0; i < up.getFiles().getCount(); i++) {
					// Retreive the current file
					File myFile = up.getFiles().getFile(i);
					if (!myFile.isMissing()) {
						String strDate = Tools.PrintDate();
						//FileName=(String)date.Time_Stamp();
						sOriginalFileName=myFile.getFileName();
						//System.out.println("sOriginalFileName=rere="+myFile.getFilePathName());
						 int count = up.save(realpatha);	//System.out.println("realpatha=="+realpatha);	
						//myFile.saveAs("/upload"+myFile.getFileName());
						//myFile.saveAs("/"+sUploadDir+strDate+"."+myFile.getFileExt());
						sSaveFileName =strDate+"."+myFile.getFileExt();
						sPathFileName = sUploadDir +strDate+"."+myFile.getFileExt();
						//判断是否是图片,是则添加水印
						if(myFile.getFileExt().equals("gif")|| myFile.getFileExt().equals("jpg") || myFile.getFileExt().equals("png")){
						//image.pressImage(logo,getServletContext().getRealPath("/")+sPathFileName,0,0,0.5f);//添加图片水印
						image.pressText(fonts, getServletContext().getRealPath("/")+sPathFileName,"隶书",12,12,20, 30, 30,0.5f);//添加文字水印
						}
					}
				}
				out.println("<script language=javascript>");
				out.print("parent.UploadSaved('");
				out.print(sPathFileName);
				//out.print(",");
				//out.print(sSaveFileName);
				out.print("');var obj=parent.dialogArguments.dialogArguments;alert(obj);if (!obj) obj=parent.dialogArguments;try{obj.addUploadFile('");
				out.print(sOriginalFileName);
				out.print("', '");
				out.print(sSaveFileName);
				out.print("', '");
				out.print(sPathFileName);
				out.print("');} catch(e){}");
				out.println(";history.back()</script>");
			} else {
				//显示上传表单
				out.println("<HTML>");
				out.println("<HEAD>");
				out.println("<TITLE>文件上传</TITLE>");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
				out.println("<style type=\"text/css\">");
				out.println("body, a, table, div, span, td, th, input, select{font:9pt;font-family: \"宋体\", Verdana, Arial, Helvetica, sans-serif;}");
				out.println("body {padding:0px;margin:0px}");
				out.println("</style>");
				out.println("<script language=\"JavaScript\" src=\"../dialog/dialog.js\">");
				out.println("</script>");
                out.println("<link href=\"../dialog/dialog.css\" type=\"text/css\" rel=\"stylesheet\">");
				out.println("</head>");
				out.println("<body bgcolor=menu>");
				out.print("<form action=\"?action=save&type=");
				out.print(sType);
				out.print("&style=");
				out.print(sStyleName);
				out.println("\" method=post name=myform id=myform enctype=\"multipart/form-data\">");
				out.println("<input type=file name=uploadfile size=1 style=\"width:100%\" onchange=\"originalfile.value=this.value\">");
				out.println("<input type=hidden name=originalfile value=\"\">");
				//out.println("<input type=submit name=Submit value=\"上传它！\">");
				out.println("</form>");
				out.println("<script language=javascript>");
				out.print("var sAllowExt = \"");
				out.print(sAllowExt);
				out.println("\";");
				out.println("// 检测上传表单");
				out.println("function CheckUploadForm() {");
				out.println("	if (!IsExt(document.myform.uploadfile.value,sAllowExt)){");
				out.println("		alert(\"提示：\\n\\n请选择一个有效的文件，\\n支持的格式有（\"+sAllowExt+\"）！\");");
				out.println("		return false;");
				out.println("	}");
				out.println("	return true");
				out.println("}");
				out.println("// 提交事件加入检测表单");
				out.println("var oForm = document.myform ;");
				out.println("oForm.attachEvent(\"onsubmit\", CheckUploadForm) ;");
				out.println("if (! oForm.submitUpload) oForm.submitUpload = new Array() ;");
				out.println("oForm.submitUpload[oForm.submitUpload.length] = CheckUploadForm ;");
				out.println("if (! oForm.originalSubmit) {");
				out.println("	oForm.originalSubmit = oForm.submit ;");
				out.println("	oForm.submit = function() {");
				out.println("		if (this.submitUpload) {");
				out.println("			for (var i = 0 ; i < this.submitUpload.length ; i++) {");
				out.println("				this.submitUpload[i]() ;");
				out.println("			}");
				out.println("		}");
				out.println("		this.originalSubmit() ;");
				out.println("	}");
				out.println("}");
				out.println("// 上传表单已装入完成");
				out.println("try {");
				out.println("	parent.UploadLoaded();");
				out.println("}");
				out.println("catch(e){");
				out.println("}");
				out.println("</script>");
				out.println("</body>");
				out.println("</html>");
			}

		%>


