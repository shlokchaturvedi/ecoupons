/*
 * Author:IDEA
 * Date:2002/11/12
 * Function:�̳�ZipEntry��,ʹ�ڲ���ΪZipOutputStreamEx�ɶ�
*/
package com.ejoysoft.util;

import java.util.zip.ZipEntry;

public class ZipEntryEx extends ZipEntry {

    // The following flags are used only by Zip{Input,Output}Stream
    int flag;       // bit flags
    int version;    // version needed to extract
    long offset;    // offset of loc header

    public ZipEntryEx(String name) {
        super(name);
    }

}
