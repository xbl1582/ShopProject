package com.example.kstupgrade.web.driver;

import com.example.kstupgrade.com.obj.DriverPoJo;
import com.example.kstupgrade.com.obj.ZipPoJo;
import com.example.kstupgrade.com.obj.dto.*;
import com.example.kstupgrade.com.util.MyResponseBody;
import com.example.kstupgrade.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
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
 * 驱动
 */
@RestController
@RequestMapping("/driver")
@Slf4j
public class DriverController {
    @Value("${filebasepath}")
    private String basefilepath;
    @Autowired
    private DriverService driverService;

    //删除
    @DeleteMapping("/del")
    public MyResponseBody delete(String uuid){
        System.out.println(uuid);
        return driverService.deleteDriver(uuid);
    }
    //提供上传+数据库添加
    @PostMapping("/upload")
    public MyResponseBody upload(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            System.out.println("数据为空");
        }
        System.out.println(file);
        return driverService.saveFile(file);
    }

    @PostMapping("/multiUpload")
    public MyResponseBody multiUpload(@RequestParam("file") MultipartFile[] files)  {
        System.out.println(files);
        System.out.println("文件的个数:" + files.length);
        for (MultipartFile f : files) {
            driverService.saveFile(f);
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
        log.info(path.split("/")[1]);
        FindDriverDto findDriverDto=new FindDriverDto();
        findDriverDto.setDrivername(path.split("/")[1]);
        List<DriverPoJo> dirverpojos =driverService.findByName(findDriverDto);
        DriverPoJo driverPoJo=dirverpojos.get(0);
        DriverDto driverDto =new DriverDto();
        BeanUtils.copyProperties(driverPoJo,driverDto);
        Integer time=driverDto.getDownloadtime()+1;
        driverDto.setDownloadtime(time);
        driverService.updateDriver(driverDto);


    }
    //列表查询
    @GetMapping("/driverlist")
    public MyResponseBody getdirverslist(FindDriverDto findDriverDto) {
        return driverService.findDriversList(findDriverDto);
    }

    @PutMapping("/setMaxPriority")
    public MyResponseBody setMaxPriority(@RequestBody DriverDto driverDto){
        log.info(driverDto.toString());

        return  driverService.setMaxPriority(driverDto);
    }

    @GetMapping("/maxPriority")
    public MyResponseBody getMaxPriority(){
        return  driverService.getMaxPriority();
    }

}
