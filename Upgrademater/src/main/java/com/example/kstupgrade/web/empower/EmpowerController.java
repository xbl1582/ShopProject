package com.example.kstupgrade.web.empower;

import com.example.kstupgrade.com.obj.EmpowerPoJo;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.dto.EmpowerDto;
import com.example.kstupgrade.com.obj.dto.FindEmpowerDto;
import com.example.kstupgrade.com.obj.dto.FindVoteDto;
import com.example.kstupgrade.com.obj.dto.VoteDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import com.example.kstupgrade.service.EmpowerService;
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
 * 选票
 */
@RestController
@RequestMapping("empower")
@Slf4j
public class EmpowerController {

    @Value("${filebasepath}")
    private String basefilepath;
    @Autowired
    private EmpowerService empowerService;


    //删除
    @DeleteMapping("/del")
    public MyResponseBody delete(String uuid){
        log.info(uuid);
        return empowerService.deleteEmpower(uuid);
    }


    @PutMapping("/update")
    public MyResponseBody updated(EmpowerDto empowerDto){
        log.info(empowerDto.toString());
        return empowerService.updatedEmpower(empowerDto);
    }
    //提供上传+数据库添加
    @PostMapping("/upload")
    public MyResponseBody upload(@RequestParam("file") MultipartFile file) {

        if (file == null) {
            log.info("数据为空");
        }
        return empowerService.saveFile(file);
    }

    @PostMapping("/multiUpload")
    public MyResponseBody multiUpload(@RequestParam("file") MultipartFile[] files)  {

        for (MultipartFile f : files) {

            empowerService.saveFile(f);
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
            File file=new File(basefilepath + path);
            if(!file.exists()){
                response.sendError(404);
                return ;
            }
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
        FindEmpowerDto findEmpowerDto=new FindEmpowerDto();
        log.info(path.split("/")[1]);
        if(path.endsWith(".zip")||path.endsWith(".rar")){
            findEmpowerDto.setLic(path.split("/")[1]);
        }else if(path.endsWith(".txt")) {
            findEmpowerDto.setSn(path.split("/")[1]);
        }
        List<EmpowerPoJo> empowerPoJos =empowerService.findByName(findEmpowerDto);
        if(!empowerPoJos.isEmpty()) {

        EmpowerPoJo empowerPoJo=empowerPoJos.get(0);
        EmpowerDto empowerDto =new EmpowerDto();
        BeanUtils.copyProperties(empowerPoJo,empowerDto);
        log.info(empowerDto.toString());
        Integer time=0;
        if(path.endsWith(".txt")){
            if(empowerDto.getSndownloadtime()!=null){
                time=empowerDto.getSndownloadtime()+1;
            }else{
                time+=1;
            }
            empowerDto.setSndownloadtime(time);
        }
        else if(path.endsWith(".zip")||path.endsWith(".rar")){

            if(empowerDto.getLicdownloadtime()!=null){
                time=empowerDto.getLicdownloadtime()+1;
            }else{
                time+=1;
            }
            empowerDto.setLicdownloadtime(time);

        }



        empowerService.updateEmpower(empowerDto);

        }else{
            return ;
        }



    }

    //列表查询
    @GetMapping("/empowerlist")
    public MyResponseBody getZipslist(FindEmpowerDto findEmpowerDto) {
        return empowerService.findEmpower(findEmpowerDto);
    }
    @GetMapping("/empowerbytxtname")
    public MyResponseBody getempowerbytxtname(FindEmpowerDto findEmpowerDto){
        return empowerService. getempowerbytxtname(findEmpowerDto);

    }


    //txt文件预览
    @GetMapping("file")
    public  void showtxt(String path,HttpServletResponse response){

        File filepath = new File( basefilepath+"empower/"+path);
        log.info(basefilepath+"empower/"+path);
        if(filepath.exists()){
            try {

                FileInputStream fis = new FileInputStream(filepath);

                byte[]bytes = new byte[(int) filepath.length()];
                fis.read(bytes);
                fis.close();
                switch (path.substring(path.lastIndexOf("\\.")+1)){
                    case "txt":
                        response.setContentType( "text/html" ); break;
                }
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.flushBuffer();

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
    @GetMapping("selectlic")
    public MyResponseBody selectlicbyname(String lic){
            return empowerService.selectlicbyname(lic);
    }



}
