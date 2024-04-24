package com.example.kstupgrade.com.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 方法一:获取zip文件的MD5校验码
     *
     * @param file
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
//    public static String getFileChecksumMD5First(File file) {
//        String myChecksum = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(Files.readAllBytes(file.toPath()));
//            byte[] digest = md.digest();
//            myChecksum = DatatypeConverter.printHexBinary(digest).toUpperCase();
//        } catch (NoSuchAlgorithmException | IOException e) {
//            e.printStackTrace();
//        }
//        if(file.exists()){
//            file.delete();
//        }
//
//        return myChecksum;
//    }
//
    /**
     * 方法二:获取zip文件的MD5校验码
     *
     * @param file
     * @return
     */
    public static String getFileChecksumMD5(File file) {
        String md5 = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fileInputStream));
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5.toUpperCase();
    }

}