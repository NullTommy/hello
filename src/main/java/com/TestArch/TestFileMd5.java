package com.TestArch;

import com.MyUtils.FileToMultipartFileUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by NullTommy on 2018-07-27 14:38
 **/
public class TestFileMd5 {

    public static void main(String[] args) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 读入TXT文件 */
            // 相对路径,相对于project的路径
            String pathname = "src/main/resources/fileTemplate/input.txt";
            // 要读取以上路径的input.txt文件
            File filename = new File(pathname);
            MultipartFile multipartFile = FileToMultipartFileUtil.toMultipartFile("newName", filename);
            try {
                byte[] uploadBytes = multipartFile.getBytes();
                //file->byte[],生成md5
                String md5Hex = DigestUtils.md5Hex(uploadBytes);
                //file->InputStream,生成md5
                String md5Hex1 = DigestUtils.md5Hex(multipartFile.getInputStream());
                System.out.println("方式1：md5Hex:" + md5Hex);
                System.out.println("方式2：md5Hex1:" + md5Hex1);
                System.out.println("方式3：getMd5:" + getMd5(multipartFile));
                //对字符串生成md5
                String s = DigestUtils.md5Hex("字符串");
                System.out.println("字符串生成MD5-s:" + s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取上传文件的md5
     *
     * @param file
     * @return
     */
    public static String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}