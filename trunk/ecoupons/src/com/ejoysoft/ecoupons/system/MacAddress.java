package com.ejoysoft.ecoupons.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2005-11-4
 * Time: 10:13:23
 * To change this template use Options | File Templates.
 */
public class MacAddress {

    public MacAddress()
    {}

    public static String getMACAddress()
    {
        String address = "";
        String os = System.getProperty("os.name");
        if ( os != null && os.startsWith("Windows")) {
        try {
            String command = "cmd.exe /c ipconfig /all";
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("Physical Address") > 0) {
                    int index = line.indexOf(":");
                    index += 2;
                    address = line.substring(index);
                    break;
                }
            }
            br.close();
            return address.trim();
        }
        catch (IOException e) {e.getStackTrace() ;}
        }
        return address;
        }
    }
