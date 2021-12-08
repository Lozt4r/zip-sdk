package com.vrdete.zip.util;

import com.vrdete.zip.http.HttpZip;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lozt
 */
public class VerifyUtil {

    public static boolean verifyFiles(List<File> fileList, String allowSuffix,boolean isPrint) throws IOException {

        boolean flag = true;

        for (File file : fileList) {
            if (file.length() != 0) {
                FileInputStream is = new FileInputStream(file);
                String head = bytesToHexString(is.readAllBytes()).substring(0, 7).toLowerCase();
                String type = getType(head);
                if(isPrint) {
                    System.out.println("文件名为: " + file.getName() + " 的数据头为: " + head + "该文件真实类型为: " + type);
                }
                if (flag) {
                    try {
                        flag = allowSuffix.contains(type.trim().toLowerCase());
                    }catch (NullPointerException e){
                        flag = false;
                        System.out.println("文件名为: " + file.getName() + " 数据头为 " + head
                                + "在map中找不到数据，请自行添加");
                    }

                    if(!flag) {
                        System.out.println("文件名为: " + file.getName() + "不符合文件类型要求");
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * 要读取文件头信息的文件的byte数组
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toLowerCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    private static String getType(String head) {

        String type = FILE_TYPE_MAP.get(head);
        return type;
    }

    public static final HashMap<String, String> FILE_TYPE_MAP= new HashMap<>();
    static {
        FILE_TYPE_MAP.put("ffd8ffe", "jpg");
        FILE_TYPE_MAP.put("ffd8ffe", "jpg");
        FILE_TYPE_MAP.put("89504e4", "png");
        FILE_TYPE_MAP.put("4749463", "gif");
        FILE_TYPE_MAP.put("3c21444", "html");
        FILE_TYPE_MAP.put("3c21646", "htm");
        FILE_TYPE_MAP.put("48544d4", "css");
        FILE_TYPE_MAP.put("696b2e7", "js");
        FILE_TYPE_MAP.put("7b5c727", "rtf");
        FILE_TYPE_MAP.put("3842505", "psd");
        FILE_TYPE_MAP.put("d0cf11e", "doc");
        FILE_TYPE_MAP.put("2521505", "ps");
        FILE_TYPE_MAP.put("2550444", "pdf");
        FILE_TYPE_MAP.put("2550444", "pdf");
        FILE_TYPE_MAP.put("2550444", "pdf");
        FILE_TYPE_MAP.put("2320737", "md");
        FILE_TYPE_MAP.put("2323232", "md");
        FILE_TYPE_MAP.put("464c560", "flv");
        FILE_TYPE_MAP.put("0000002", "mp4");
        FILE_TYPE_MAP.put("4944330", "mp3");
        FILE_TYPE_MAP.put("5249464", "wav");
        FILE_TYPE_MAP.put("5249464", "avi");
        FILE_TYPE_MAP.put("4d54686", "mid");
        FILE_TYPE_MAP.put("504b030", "zip");
        FILE_TYPE_MAP.put("5261722", "rar");
        FILE_TYPE_MAP.put("5261722", "rar");
        FILE_TYPE_MAP.put("2354686", "ini");
        FILE_TYPE_MAP.put("504b030", "docx");
        FILE_TYPE_MAP.put("4d5a900", "exe");
        FILE_TYPE_MAP.put("3c25402", "jsp");
        FILE_TYPE_MAP.put("4d616e6", "mf");
        FILE_TYPE_MAP.put("3c3f786", "xml");
        FILE_TYPE_MAP.put("2f2a0d0", "sql");
        FILE_TYPE_MAP.put("7061636", "java");
        FILE_TYPE_MAP.put("4065636", "bat");
        FILE_TYPE_MAP.put("cafebab", "class");
        FILE_TYPE_MAP.put("4954534", "chm");
        FILE_TYPE_MAP.put("0400000", "mxp");
        FILE_TYPE_MAP.put("504b030", "docx");
        FILE_TYPE_MAP.put("d0cf11e", "wps");
        FILE_TYPE_MAP.put("0000000", "null");
    }

}
