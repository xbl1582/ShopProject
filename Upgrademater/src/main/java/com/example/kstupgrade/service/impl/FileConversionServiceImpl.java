package com.example.kstupgrade.service.impl;


import com.example.kstupgrade.com.util.MyResponseBody;
import com.example.kstupgrade.com.util.ResponseEnumCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
@Slf4j
public class FileConversionServiceImpl {
    @Value("${filebasepath}")
    private String basefilepath;
    public MyResponseBody saveFile(MultipartFile file) {
        File temp = new File(basefilepath + "temp/");
        if (!temp.exists()) {
            temp.mkdirs();
        }
        //创建文件对象

        //判断前端传递过来的文件是否为空，为空返回错误
        if (file.isEmpty()) {
            return MyResponseBody.failure(ResponseEnumCode.CHOOSE_NO_FILE);

        }
        //创建文件对象
        File localFile = new File(basefilepath + file.getOriginalFilename());
        boolean flag = true;
        if (localFile.exists()) {
            flag = localFile.delete();
        }
        log.info(basefilepath + file.getOriginalFilename());
        if (flag) {
            try {
                file.transferTo(localFile); //把上传的文件保存至本地

                log.info(file.getOriginalFilename() + " 上传成功");
                return MyResponseBody.success("上传成功", null, 1L);
            } catch (IOException e) {
                e.printStackTrace();
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }

        }else{
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }
    public boolean image2pdf(String filepath){
        try {
            String imagesPath = basefilepath + "temp/";
            File temp = new File(basefilepath + "temp2/");
            if (!temp.exists()) {
                temp.mkdirs();
            }
            File file = new File(basefilepath + "temp2/"+filepath);

            // 第一步：创建一个document对象。
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);
            // 第二步：
            // 创建一个PdfWriter实例，
            PdfWriter.getInstance(document, new FileOutputStream(file));
            // 第三步：打开文档。
            document.open();
            // 第四步：在文档中增加图片。
            if (true) {
//                Image img = Image.getInstance(imagesPath);
//                img.setAlignment(Image.ALIGN_CENTER);
//                // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
//                document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
//                document.newPage();
//                document.add(img);
                //下面是对应一个文件夹的图片
            File files = new File(imagesPath);
            String[] images = files.list();
            int len = images.length;

            for (int i = 0; i < len; i++)
            {
                if (images[i].toLowerCase().endsWith(".bmp")
                        || images[i].toLowerCase().endsWith(".jpg")
                        || images[i].toLowerCase().endsWith(".jpeg")
                        || images[i].toLowerCase().endsWith(".gif")
                        || images[i].toLowerCase().endsWith(".png")) {
                    String temp3 = imagesPath + "\\" + images[i];
                    Image img = Image.getInstance(temp3);
                    img.setAlignment(Image.ALIGN_CENTER);
                    // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
                    document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    document.newPage();
                    document.add(img);
                }
            }
                // 第五步：关闭文档。
                document.close();
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return false;
    }




}
