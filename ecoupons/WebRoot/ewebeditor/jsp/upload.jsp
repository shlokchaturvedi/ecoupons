<%
/***********************************************************
*   ��л��ʹ�� eWebEditor�����ı��༭����ΰ���޸İ�(v5.2)
*   ��������ȫ��������ѣ���������⸴�ơ��������޸ĺ�ʹ�ã�
*   �����ù���������� ����������ҵ��;������������ʹ�����շѣ�
*   ����������ҵ��;������ѧϰʹ��(ʹ��ʱ�������˶���Ϣ��лл���)��
*                                            
*                                         �޸��ߣ���ΰ�� 
*                                          Q Q  ��343004545
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
<%!// ��������
	String sType, sStyleName;
	//' ���ñ���
	String sAllowExt, sUploadDir, sBaseUrl, sContentPath;
	int nAllowSize;

	//' �ӿڱ���
	String sFileExt, sSaveFileName, sOriginalFileName, sPathFileName, FileName,
			nFileNum, realpatha;
	String sAction;

	%>
<%!/*' ============================================
	*' ȥ��Html��ʽ�����ڴ����ݿ���ȡ��ֵ���������ʱ
	 ' ע�⣺value="?"���һ��Ҫ��˫����
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
	//��ʼ���ϴ���������
	public void InitUpload(String realpath,String sUploadFilePath,int year,String strType,String strAffairId) {
		try {
			//String=config.getServletContext().getRealPath("/");
			realpatha=getServletContext().getRealPath("/")+"/"+sUploadFilePath+"/"+year+"/"+strType+"/"+strAffairId+"/";
			 //ͼƬ������ͬ��,���ҽ�ͼƬ�����UPLOAD����
			 //��̬�Ĵ���UPLOADĿ¼
			 java.io.File picStoreDir=new java.io.File(realpatha);
			 picStoreDir.mkdirs();
			//�˴������ϴ��ļ�����·��,ע��·��Ҫ��ROOT��ʼ=========================
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
<%//��������
			sType = request.getParameter("type");
			if (sType == null) {
				sType = "image";
			} else {
				sType = request.getParameter("type").trim();
			}
			//������ʽ
			sStyleName = request.getParameter("style");
			if (sStyleName == null) {
				sStyleName = "coolblue";
			} else {
				sStyleName = request.getParameter("style").trim();
			}
			//���ö���
			sAction = request.getParameter("action");
			if (sAction == null) {
				sAction = "sun";
			} else {
				sAction = request.getParameter("action").trim();
			}

			%>
<%//��ʼ���ϴ�����
			
			String strType=ParamUtil.getString(request,"strType","");
			String strAffairId=ParamUtil.getString(request,"strAffairId","");
			Calendar calendar = Calendar.getInstance();
			InitUpload(config.getServletContext().getRealPath("/"),globa.getPropValue("PATH_WEB_FILE"),calendar.get(Calendar.YEAR),strType,strAffairId);
			//�Ͽ����ݿ�����
			//sAction = UCase(Trim(Request.QueryString("action"));
			if (sAction.equalsIgnoreCase("remote")) { //Զ���Զ���ȡ
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
				System.out.println("�滻ǰ��html���Ϊ:" + "\n" + sContent);
				if (sAllowExt != "") {
					Pattern pRemoteFileurl = Pattern
							.compile("((http|https|ftp|rtsp|mms):(//|\\\\){1}(([A-Za-z0-9_-])+[.]){1,}(net|com|cn|org|cc|tv|[0-9]{1,3})(\\S*/)((\\S)+[.]{1}("
									+ sAllowExt + ")))");//ȡ����ҳ��URL��������ʽ
					Matcher mRemoteFileurl = pRemoteFileurl.matcher(sContent);//�Դ�����ַ�������ƥ��
					Protocol = request.getProtocol();//ȡ��ͨѶ��Э��
					String ProtocolA[] = Protocol.split("/");//ȡ��Э��ǰ�����ĸ����HTTP/1.1,��Ϊ"HTTP","1.1"
					sUrl = ProtocolA[0] + "://" + request.getServerName();//ȡ�ñ���URL·��,��http://localhost
					//ProtocolA[]=null;
					Port = request.getServerPort();//ȡ�ö˿�ֵ
					if (Port != 80) {//�鿴�˿��Ƿ�Ϊ80��������ǻ���Ҫ�������ϼ��϶˿�
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
						System.out.println("��Ҫ�滻��Զ�����ӣ�" + "\n" + RemoteFileurl);
						sOriginalFileName = RemoteFileurl
								.substring(RemoteFileurl.lastIndexOf("/"));
						Pattern pFileType = Pattern.compile("[.]{1}("
								+ sAllowExt + ")");//����ƥ��ȡ���ļ�������
						Matcher mFileType = pFileType.matcher(RemoteFileurl);
						while (mFileType.find()) {
							String SaveFileType = mFileType.group();
							LocalFileurl = sUploadDir + i + SaveFileType;//�ļ���·������ʱ�������
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
						System.out.println("Զ���ļ������·�����ļ�����" + "\n"
								+ SaveFileName);
						sSaveFileName = LocalFileurl.substring(LocalFileurl
								.lastIndexOf("/"));
						RemotePic Down = new RemotePic();
						Down.picurl = RemoteFileurl;
						Down.savepath = SaveFileName;
						if (Down.download())//������ر���ɹ��������html�������ļ�·��
						{
							mRemoteFileurl.appendReplacement(sb, LocalFileurl);//�滻·��
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
				System.out.print("�滻���html���:" + "\n" + sContent);
				out.println("<HTML><HEAD><TITLE>Զ���ϴ�</TITLE><meta http-equiv='Content-Type' content='text/html; charset=utf-8'></head><body>");
				out.print("<input type=hidden id=UploadText value=\"");
				out.print(sContent);
				out.println("\">");
				out.println("</body></html>");
				out.println("<script language=javascript>");out.println("alert(UploadText.value)");
				out.print("parent.setHTML(UploadText.value);try{parent.addUploadFile('");//Ϊʲôֻȡһ���ֵ����ֻȡ������ҳ����λ��֮ǰ��ֵ��
				out.print(sOriginalFileName);
				out.print("', '");
				out.print(sSaveFileName);
				out.print("', '");
				out.print(SaveFileName);
				out.println("');} catch(e){} parent.remoteUploadOK();");
				out.println("</script>");

				//DoRemote();
			} else if (sAction.equalsIgnoreCase("save")) {
				//��ʾ�ϴ��˵�
				System.out.println(sAction);
				out.println("<HTML>");
				out.println("<HEAD>");
				out.println("<TITLE>�ļ��ϴ�</TITLE>");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
				out.println("<style type=\"text/css\">");
				out.println("body, a, table, div, span, td, th, input, select{font:9pt;font-family: \"����\", Verdana, Arial, Helvetica, sans-serif;}");
				out.println("body {padding:0px;margin:0px}");
				out.println("</style>");
				out.println("<script language=\"JavaScript\" src=\"../dialog/dialog.js\">");
				out.println("</script>");
                out.println("<link href=\"../dialog/dialog.css\" type=\"text/css\" rel=\"stylesheet\">");
				out.println("</head>");
				out.println("<body bgcolor=menu>");
				out.print("<form action=\"?action=save&type=");//ע��˴�Ϊʲô����println()
				out.print(sType);
				out.print("&style=");
				out.print(sStyleName);
				out.println("\" method=post name=myform id=myform enctype=\"multipart/form-data\">");
				//out.println("<input type=submit name=Submit value=\"�ϴ�����\">");
				out.println("</form>");
				out.println("<script language=javascript>");
				out.print("var sAllowExt = \"");
				out.print(sAllowExt);
				out.println("\";");
				out.println("// ����ϴ���");
				out.println("function CheckUploadForm() {");
				out.println("if (!IsExt(document.myform.uploadfile.value,sAllowExt)){");
				out.println("alert(\"��ʾ��\\n\\n��ѡ��һ����Ч���ļ���\\n֧�ֵĸ�ʽ�У�\"+sAllowExt+\"����\");");
				out.println("return false;");
				out.println("}");
				out.println("return true");
				out.println("}");
				out.println("// �ύ�¼��������");
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
				out.println("// �ϴ�����װ�����");
				out.println("try {");
				out.println("	parent.UploadLoaded();");
				out.println("}");
				out.println("catch(e){");
				out.println("}");
				out.println("</script>");
				out.println("</body>");
				out.println("</html>");
				//���ļ�
				//DoSave();
				SmartUpload up = new SmartUpload();
				ImageUtils image=new ImageUtils(); //���ˮӡ;
				String logo="E:\\logo.gif";     //ˮӡ�ļ�·��
				String fonts="��ΰ���޸İ�QQ:343004545";
				//��ʼ���ϴ����
				up.initialize(pageContext);
				//�����ϴ��ļ���С
				up.setMaxFileSize(nAllowSize * 1024);
				//�����ϴ��ļ�����
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
						//�ж��Ƿ���ͼƬ,�������ˮӡ
						if(myFile.getFileExt().equals("gif")|| myFile.getFileExt().equals("jpg") || myFile.getFileExt().equals("png")){
						//image.pressImage(logo,getServletContext().getRealPath("/")+sPathFileName,0,0,0.5f);//���ͼƬˮӡ
						image.pressText(fonts, getServletContext().getRealPath("/")+sPathFileName,"����",12,12,20, 30, 30,0.5f);//�������ˮӡ
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
				//��ʾ�ϴ���
				out.println("<HTML>");
				out.println("<HEAD>");
				out.println("<TITLE>�ļ��ϴ�</TITLE>");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
				out.println("<style type=\"text/css\">");
				out.println("body, a, table, div, span, td, th, input, select{font:9pt;font-family: \"����\", Verdana, Arial, Helvetica, sans-serif;}");
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
				//out.println("<input type=submit name=Submit value=\"�ϴ�����\">");
				out.println("</form>");
				out.println("<script language=javascript>");
				out.print("var sAllowExt = \"");
				out.print(sAllowExt);
				out.println("\";");
				out.println("// ����ϴ���");
				out.println("function CheckUploadForm() {");
				out.println("	if (!IsExt(document.myform.uploadfile.value,sAllowExt)){");
				out.println("		alert(\"��ʾ��\\n\\n��ѡ��һ����Ч���ļ���\\n֧�ֵĸ�ʽ�У�\"+sAllowExt+\"����\");");
				out.println("		return false;");
				out.println("	}");
				out.println("	return true");
				out.println("}");
				out.println("// �ύ�¼��������");
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
				out.println("// �ϴ�����װ�����");
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


