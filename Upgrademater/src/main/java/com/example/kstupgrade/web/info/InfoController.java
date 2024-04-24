package com.example.kstupgrade.web.info;

import com.example.kstupgrade.com.obj.DriverPoJo;
import com.example.kstupgrade.com.obj.InfoPoJo;
import com.example.kstupgrade.com.obj.dto.*;
import com.example.kstupgrade.com.util.MyResponseBody;
import com.example.kstupgrade.service.DriverService;
import com.example.kstupgrade.service.InfoService;
import com.example.kstupgrade.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 说明书
 */
@RestController
@RequestMapping("/info")
@Slf4j
public class InfoController {
    @Value("${filebasepath}")
    private String basefilepath;
    @Autowired
    private InfoService infoService;

    //删除
    @DeleteMapping("/del")
    public MyResponseBody delete(String uuid){
        System.out.println(uuid);
        return infoService.deleteInfo(uuid);
    }
    //提供上传+数据库添加
    @PostMapping("/upload")
    public MyResponseBody upload(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            System.out.println("数据为空");
        }
        System.out.println(file);
        return infoService.saveFile(file);
    }

    @PostMapping("/multiUpload")
    public MyResponseBody multiUpload(@RequestParam("file") MultipartFile[] files)  {
        System.out.println(files);
        System.out.println("文件的个数:" + files.length);
        for (MultipartFile f : files) {
            infoService.saveFile(f);
        }
        return MyResponseBody.success();
    }
    /**
     * @param path     想要下载的文件的路径
     * @param response
     * @功能描述 下载文件:
     */
    @RequestMapping("/download")
    public void download(String path, HttpServletResponse response) {
        try {
            // 读到流中
            InputStream inputStream = new FileInputStream(basefilepath + path);// 文件的存放路径
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

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        FindInfoDto findInfoDto=new FindInfoDto();
        findInfoDto.setInfoname(path.split("/")[1]);
        List<InfoPoJo> infoPoJos =infoService.findByName(findInfoDto);
       InfoPoJo infoPoJo=infoPoJos.get(0);
        InfoDto infoDto =new InfoDto();
        BeanUtils.copyProperties(infoPoJo,infoDto);
        Integer time=infoDto.getDownloadtime()+1;
        infoDto.setDownloadtime(time);
        infoService.updateInfo(infoDto);


    }

    //列表查询
    @GetMapping("/infoslist")
    public MyResponseBody getZipslist(FindInfoDto findInfoDto) {
        return infoService.findInfos(findInfoDto);
    }


    //图片预览
    @GetMapping("/image")
    public  void showimage(String path,HttpServletResponse response){

        File imageFile = new File( basefilepath+"Info/"+path);
        log.info(basefilepath+"Info/"+path);
        if(imageFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(imageFile);
                byte[] bytes = new byte[(int) imageFile.length()];
                fis.read(bytes);
                fis.close();
                switch (path.substring(path.lastIndexOf("\\.") + 1)) {
                    case "jpg":
                        response.setContentType("image/jpg");
                        break;
                    case "jpeg":
                        response.setContentType("image/jpeg");
                        break;
                    case "png":
                        response.setContentType("image/png");
                        break;
                    case "tif":
                        response.setContentType("image/tif");
                        break;
                    case "tiff":
                        response.setContentType("image/tiff");
                        break;
                }
//            response.setContentType( "image/jpg" );
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.flushBuffer();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    //word文件预览
    @GetMapping(value = "file",produces = {"application/octet-stream;charset=UTF-8"})
    public  void showword(String path,HttpServletResponse response){

        File filepath = new File( basefilepath+"Info/"+path);
        log.info(basefilepath+"Info/"+path);
        if(filepath.exists()) {
            try {
                FileInputStream fis = new FileInputStream(filepath);
                byte[] bytes = new byte[(int) filepath.length()];
                fis.read(bytes);
                fis.close();
                switch (path.substring(path.lastIndexOf("\\.") + 1)) {
                    case "doc":
                        response.setContentType("application/msword");
                        break;
                    case "docx":
                        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                        break;
                    case "pdf": {
                        response.setContentType("application/pdf");
                        response.setHeader("content-disposition", "inline;filename=" + path);

                    }
                        break;
                    case "ppt":
                        response.setContentType("application/x-ppt");
                        break;
                    case "txt":
                        response.setContentType( "text/html" );
                        break;
                }
//            response.setContentType( "image/jpg" );
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.flushBuffer();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PutMapping("/setMaxPriority")
    public MyResponseBody setMaxPriority(@RequestBody  InfoDto infoDto){
        log.info(infoDto.toString());

        return  infoService.setMaxPriority(infoDto);
    }

    @GetMapping("/maxPriority")
    public MyResponseBody getMaxPriority(){
        return  infoService.getMaxPriority();
    }
}
