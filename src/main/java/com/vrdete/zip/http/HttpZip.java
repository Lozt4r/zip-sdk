package com.vrdete.zip.http;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author Auzero
 */
public class HttpZip {

    private final static int BUFFER_SIZE = 8192;

    /**
     * 批量压缩文件，自定义文件
     * @param files 要压缩的文件
     * @param response HttpServletResponse
     */
    public static void zip(List<File> files, HttpServletResponse response) {
        byte[] buf = new byte[BUFFER_SIZE];
        try {
            response.setHeader("Content-Disposition", "attachment;filename=branch.zip");
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            List<String> fileNames = new ArrayList<>();
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                int x = 0;
                for (String fileName : fileNames) {
                    if (file.getName().equals(fileName)){
                        zos.putNextEntry(new ZipEntry(x + file.getName()));
                    }else{
                        x++;
                    }
                }
                if (x == fileNames.size()){
                    zos.putNextEntry(new ZipEntry(file.getName()));
                }

                fileNames.add(file.getName());
                int len;
                while ((len = fis.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    public static List<File> unzip(InputStream is, String dirPath) {
//        File file = new File("x");
//        FileUtils.inputStreamToFile(is, file);
//        FileUtils.delTempFile(file);
//        return unzip(file, dirPath);
//    }

    /**
     * 解压文件
     * @param zipFile 需要解压的zip文件的文件输入流
     * @param dirPath 解压目标路径
     */
    public static List<File> unzip(File zipFile, String dirPath) {
        List<File> files = new ArrayList<>();
        dirPath = dirPath + "\\";
        //判断生成目录是否生成，如果没有就创建
        File pathFile=new File(dirPath);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }

        ZipFile zp=null;
        try{
            //指定编码，否则压缩包里面不能有中文目录
            zp=new ZipFile(zipFile, Charset.forName("gbk"));
            //遍历里面的文件及文件夹
            Enumeration entries=zp.entries();
            while(entries.hasMoreElements()){
                ZipEntry entry= (ZipEntry) entries.nextElement();
                String zipEntryName=entry.getName();
                InputStream in=zp.getInputStream(entry);
                String outpath=(dirPath+zipEntryName).replace("/",File.separator);
                //判断路径是否存在，不存在则创建文件路径
                File file = new File(outpath.substring(0,outpath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是,不需要解压
                if(new File(outpath).isDirectory()) {
                    continue;
                }
                OutputStream out=new FileOutputStream(outpath);
                byte[] bf=new byte[2048];
                int len;
                while ((len=in.read(bf))>0){
                    out.write(bf,0,len);
                }
                File file1 = new File(outpath);
                files.add(file1);
                in.close();
                out.close();
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        return files;
    }

    private static void createFile(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }

}
