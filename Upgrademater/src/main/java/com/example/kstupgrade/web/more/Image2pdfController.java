package com.example.kstupgrade.web.more;

import com.example.kstupgrade.com.obj.InfoPoJo;
import com.example.kstupgrade.com.obj.dto.FindInfoDto;
import com.example.kstupgrade.com.obj.dto.InfoDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import com.example.kstupgrade.service.impl.FileConversionServiceImpl;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("cover")
public class Image2pdfController {
    @Value("${filebasepath}")
    private String basefilepath;
    @Autowired
    FileConversionServiceImpl fileConversionService;
    @PostMapping("/multiUpload")
    public MyResponseBody multiUpload(@RequestParam("file") MultipartFile[] files)  {
        System.out.println(files);
        System.out.println("文件的个数:" + files.length);
        for (MultipartFile f : files) {
            fileConversionService.saveFile(f);
        }
        return MyResponseBody.success();
    }
    @RequestMapping("/download")
    public void download(String path, HttpServletResponse response) {
        try {
            boolean falg=fileConversionService.image2pdf(path);
            if(falg){
                // 读到流中
                InputStream inputStream = new FileInputStream(basefilepath+"temp2/" + path);// 文件的存放路径
                response.reset();
                response.setContentType("application/octet-stream");
                String filename = new File(path).getName();
                response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
                ServletOutputStream outputStream = response.getOutputStream();
                byte[] b = new byte[1024];
                int len;
                //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
                while ((len = inputStream.read(b)) > 0) {
                    outputStream.write(b, 0, len);
                }
                inputStream.close();

            }else{
                FileUtils.deleteDirectory(new File(basefilepath+"temp"));
            }

            FileUtils.deleteDirectory(new File(basefilepath+"temp"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
