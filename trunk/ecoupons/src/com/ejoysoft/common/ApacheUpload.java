package  com.ejoysoft.common;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
/**
 * 封装Apache的文件上传组件
 * 为应用系统提供简单的调用接口
 *

 */
public class ApacheUpload {

    private Properties fields = new Properties();   //保存表单的简单域
    private Vector files = new Vector();            //保存上传的文件对象
    private int fileCount = 0;                      //上传的文件数目

    /**
     * 构造
     * @param req request对象
     * @throws FileUploadException
     * @throws UnsupportedEncodingException 
     */
    public ApacheUpload(HttpServletRequest req) throws FileUploadException, UnsupportedEncodingException {
        //初始化ApacheUpload对象
        DiskFileUpload fu = new DiskFileUpload();
        fu.setHeaderEncoding("UTF-8");
        fu.setSizeMax(50*1024*1024);
        fu.setSizeThreshold(4096);
        fu.setRepositoryPath(req.getRealPath(""));
        System.out.println(req.getRealPath(""));
        List fileItems = fu.parseRequest(req);
        Iterator i = fileItems.iterator();
        //接收上传的内容
        while (i.hasNext()) {
            FileItem fi = (FileItem)i.next();
            if (fi.isFormField()) {
                fields.setProperty(fi.getFieldName(), fi.getString("UTF-8"));
            } else {
            	if (fi.getName().indexOf(".") >= 0) {
            		files.add(fi);
                } else {
                	files.add(null);
                }
            }                
        }
        fileCount = files.size();
        req.setAttribute("au", this);
    }

     public void initPath(String strRootPath,String path) {
        java.io.File file_dir = new java.io.File(String.valueOf(strRootPath) + String.valueOf(path));
        if (!file_dir.exists())
            file_dir.mkdirs();
    }
    public int getFileCount() {
        return fileCount;
    }

    /**
     * 保存文件
     * @param fileIndex 指定保存文件向量中的编号
     * @return 上传时的文件名的扩展名
     * @throws Exception
     */
    public String getFileExpName(int fileIndex) throws Exception {
        if (this.files == null || fileIndex >= this.files.size()) {
            return "";
        }
        FileItem fi = (FileItem)this.files.get(fileIndex);
         String newName = fi.getName();
         String expName = newName.substring(newName.lastIndexOf("."));
        return expName;
    }
     public String getFileName(int fileIndex) throws Exception {
        if (this.files == null || this.files.get(fileIndex) == null || fileIndex >= this.files.size()) {
            return "";
        }
        FileItem fi = (FileItem)this.files.get(fileIndex);
        String newName = fi.getName();
        return newName;
    }
     public String saveFile(String path, int fileIndex, boolean keepOldName) throws Exception {
        if (this.files == null || fileIndex >= this.files.size()) {
            return "";
        }
        FileItem fi = (FileItem)this.files.get(fileIndex);
        //create file name
        String fileName = fi.getName();
        if (keepOldName) {
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        } else {
            fileName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UID.getID() + fileName;
        }
        //save and return
        fi.write(new File(path + fileName));
        return fileName;
    }
    /**
     * 保存文件
     * @param path 保存路径
     * @param fileIndex 指定保存文件向量中的编号
     * @return 保存后的文件名
     * @throws Exception
     */
    public String saveFile(String path, int fileIndex) throws Exception {
        if (this.files == null || fileIndex >= this.files.size()) {
            return "";
        }
        FileItem fi = (FileItem)this.files.get(fileIndex);
        //create file name
        String fileName = fi.getName();
        fileName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UID.getID() + fileName;
        //save and return
        fi.write(new File(path + fileName));
        return fileName;
    }
    
    //保存文件 并指定文件名
    public String saveFile2(String path, int fileIndex, String name) throws Exception {
        if (this.files == null || fileIndex >= this.files.size()) {
            return "";
        }
        FileItem fi = (FileItem)this.files.get(fileIndex);
        //create file name
        String fileName = fi.getName();
        fileName = fileName.substring(fileName.lastIndexOf("."));
        fileName = name + fileName;
        //save and return
        fi.write(new File(path + fileName));
        return fileName;
    }

    /**
     * 保存文件
     * @param path 保存路径
     * @param fileIndex 指定保存文件向量中的编号
     * @return 保存后的文件名
     * @throws Exception
     */
    public String saveFile(String path, String fileName, int fileIndex) throws Exception {
        if (this.files == null || fileIndex >= this.files.size()) {
            return "";
        }
        FileItem fi = (FileItem)this.files.get(fileIndex);
         String newName = fi.getName();
         String expName = newName.substring(newName.lastIndexOf("."));
        //save and return
        fi.write(new File(path + fileName+expName));
        return (fileName+expName);
    }

    /**
     * 返回表单中的简单对象的值(string)
     * @param fieldName 对象名
     * @return 对象值，如果不存在返回""
     */
    public String getString(String fieldName) {
   		return this.fields.getProperty(fieldName, "");
    }

    /**
     * 返回表单中的简单对象的值(int)
     * @param fieldName 对象名
     * @return 对象值，如果不存在返回""
     */
    public int getInt(String fieldName) {
        try {
            return Integer.parseInt(this.fields.getProperty(fieldName));
        } catch (Exception e) {
            return 0;
        }
    }

    public int getFileSize(int fileIndex) {
        if (this.files == null || fileIndex >= this.files.size()) {
            return 0;
        }
        FileItem fi = (FileItem)this.files.get(fileIndex);
        return (int) fi.getSize();
    }
}