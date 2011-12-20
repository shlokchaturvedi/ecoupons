// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RemotePic.java

package com.ejoysoft.util;

import java.io.*;
import java.net.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
public class RemotePic
{

    public String picurl;
    public String savepath;

    public RemotePic()
    {
    }

    public boolean download() throws IOException
    {
        String s;
        String s1;
        s = picurl;
        s1 = savepath;
        URLConnection urlconnection;
        URL url = new URL(s);
        urlconnection = url.openConnection();
        urlconnection.connect();
        HttpURLConnection httpurlconnection = (HttpURLConnection)urlconnection;
        int i = httpurlconnection.getResponseCode();
        if(i == 200)
        { 
        	//break MISSING_BLOCK_LABEL_89;
        System.out.println("Connect to " + s + " failed,return code:" + i);
        return false;
        }
        else
        {
        InputStream inputstream;
        FileOutputStream fileoutputstream;
        int j = urlconnection.getContentLength();
        inputstream = urlconnection.getInputStream();
        byte abyte0[] = new byte[1024];
        File file = new File(s1);
        if(!file.exists())
            file.createNewFile();
        fileoutputstream = new FileOutputStream(file);
        int k = 0;
        if(j < 0)
        {
            while(k > -1) 
            {
                k = inputstream.read(abyte0);
                if(k > 0)
                    fileoutputstream.write(abyte0, 0, k);
            }
           // break MISSING_BLOCK_LABEL_277;
        }
        int l;
        for(l = 0; l < j && k != -1;)
        {
            k = inputstream.read(abyte0);
            if(k > 0)
            {
                fileoutputstream.write(abyte0, 0, k);
                l += k;
            }
        }

        if(l >= j)
        {
       // break MISSING_BLOCK_LABEL_276;
        System.out.println("download error");
        inputstream.close();
        fileoutputstream.close();
        file.delete();
        return false;
        }
        fileoutputstream.flush();
        fileoutputstream.close();
        inputstream.close();
       // break MISSING_BLOCK_LABEL_303;       
        Exception exception = null;
        //exception;
        exception.printStackTrace();
        return false;
        }
    }
}

