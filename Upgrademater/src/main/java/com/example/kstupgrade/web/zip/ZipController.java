package com.example.kstupgrade.web.zip;

import com.example.kstupgrade.com.obj.ZipPoJo;
import com.example.kstupgrade.com.obj.dto.FindZipDto;
import com.example.kstupgrade.com.obj.dto.MaxVersionDto;
import com.example.kstupgrade.com.obj.dto.ZipDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import com.example.kstupgrade.service.ZipService;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/zip")
@Slf4j
public class ZipController {
    @Value("${filebasepath}")
    private String basefilepath;

    @Autowired
    private ZipService zipService;
    //删除
    @DeleteMapping("/del")
    public MyResponseBody delete(String uuid){
        log.info(uuid);
        return zipService.deleteZip(uuid);
    }


    //提供上传+数据库添加
    @PostMapping("/upload")
    public MyResponseBody upload(@RequestParam("file") MultipartFile file) {
        if (file == null) {
           System.out.println("数据为空");
        }
        log.info(file.toString());
        return zipService.saveFile(file);
    }

    @PostMapping("/multiUpload")
    public MyResponseBody multiUpload(@RequestParam("file") MultipartFile[] files)  {
        log.info(Arrays.toString(files));
        log.info("文件的个数:" + files.length);
        for (MultipartFile f : files) {
            zipService.saveFile(f);
        }
        return MyResponseBody.success();
    }

    //下载_最新版本

    /**
     * @param path     想要下载的文件的路径
     * @param response
     * @功能描述 下载文件:
     */
    @RequestMapping("/download")
    public void download(String path, HttpServletResponse response) {
        log.info(path);
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

        List<ZipPoJo> zipPoJos =zipService.findByName(path.split("/")[1]);
        ZipPoJo zipPoJo=zipPoJos.get(0);
        ZipDto zipDto=new ZipDto();
        BeanUtils.copyProperties(zipPoJo,zipDto);
        Integer time=zipPoJo.getDownloadtime()+1;
        zipDto.setDownloadtime(time);

        zipService.updateZip(zipDto);


    }

    //列表查询
    @GetMapping("/zipslist")
    public MyResponseBody getZipslist(FindZipDto findZipDto) {
        return zipService.findZips(findZipDto);
    }



    //最新版本查询
    @GetMapping("/getnewApp")
    public MyResponseBody getNewApp(FindZipDto findZipDto) {



        return zipService.findbastNewZips(findZipDto);
    }

    //最新版本查询
    @GetMapping("/getbeastnewrtmapp")
    public MyResponseBody getbeastnewrtmapp(FindZipDto findZipDto) {
        findZipDto.setVersiontype("rtm");
        return zipService.findbeastnewrtmapp(findZipDto);
    }
    //最新版本查询
    @GetMapping("/getbeastnewupdatedapp")
    public MyResponseBody getbeastnewupdatedapp(FindZipDto findZipDto) {
        findZipDto.setVersiontype("updated");
        return zipService.findbeastnewupdatedapp(findZipDto);
    }




    @GetMapping("/getnewAppbyPath")
    public MyResponseBody getNewAppByPath(FindZipDto findZipDto) {
        log.info("控制类："+findZipDto.toString());
        return zipService.findNewZipsByPath(findZipDto);
//        return zipService.findbastNewZips(findZipDto);
    }

    @RequestMapping("/setup")
    public void downloadsetup(String type,String path, HttpServletResponse response) {
//        System.out.println(type);
//        System.out.println(path);
                try {
            // 读到流中
            InputStream inputStream = new FileInputStream(basefilepath+"/setup"+"/"+path+"/"+type+"/"+ path+".zip");// 文件的存放路径
            response.reset();
            response.setContentType("application/octet-stream");
            String path2=basefilepath+"/setup"+"/"+path+"/"+type+"/"+ path+".zip";
            String filename = new File(path2).getName();
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

    }


    @RequestMapping("/getPaths")
    public MyResponseBody getPaths(){
        log.info("获取分类");
        return zipService.getPaths();
    }

    @RequestMapping("/getVersions")
    public MyResponseBody getVersions(FindZipDto findZipDto){

        log.info(findZipDto.toString());
        log.info("获取");
        return zipService.getVersions(findZipDto);
    }

    @RequestMapping("/getOs")
    public MyResponseBody getOs(FindZipDto findZipDto){
        log.info(findZipDto.toString());
        return zipService.getOs(findZipDto);
    }

    @RequestMapping("/getMaxVersions")
    public MyResponseBody getMaxVersions(FindZipDto findZipDto){
        log.info(findZipDto.toString());
        MaxVersionDto maxVersionDto= new MaxVersionDto();
        maxVersionDto.setPath(findZipDto.getPath());
        Object o=zipService.getMaxVersions(findZipDto).getData();
        maxVersionDto.setVersion(Integer.valueOf((String)o));
        maxVersionDto.setMyos(findZipDto.getOs());
        return MyResponseBody.success(maxVersionDto,1L);
    }

}
