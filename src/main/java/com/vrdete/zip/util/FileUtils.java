package com.vrdete.zip.util;

import java.io.*;

/**
 * @author Auzero
 */
public class FileUtils {

    public static void inputStreamToFile(InputStream is, File file) {

        try {
            OutputStream os = new FileOutputStream(file);
            byte[] buf = new byte[8192];
            int len;
            while ((len = is.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delTempFile(File file) {
        if (file != null) {

            File del = new File(file.toURI());
            System.out.println(file.toURI());

            if (del.delete()) {
                System.err.println("ok");
            } else {
                System.err.println("no");
            }

        }
    }

}
