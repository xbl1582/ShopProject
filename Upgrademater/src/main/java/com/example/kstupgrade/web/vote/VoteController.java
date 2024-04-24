package com.example.kstupgrade.web.vote;

import com.example.kstupgrade.com.obj.InfoPoJo;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.dto.FindVoteDto;
import com.example.kstupgrade.com.obj.dto.FindZipDto;
import com.example.kstupgrade.com.obj.dto.InfoDto;
import com.example.kstupgrade.com.obj.dto.VoteDto;
import com.example.kstupgrade.com.util.MyResponseBody;
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
@RequestMapping("/vote")
@Slf4j
public class VoteController {
    @Value("${filebasepath}")
    private String basefilepath;
    @Autowired
    private  VoteService voteService;


    //删除
    @DeleteMapping("/del")
    public MyResponseBody delete(String uuid){
        log.info(uuid);
        return voteService.deletevote(uuid);
    }
    //提供上传+数据库添加
    @PostMapping("/upload")
    public MyResponseBody upload(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            log.info("数据为空");
        }
       log.info(file.toString());
        return voteService.saveFile(file);
    }

    @PostMapping("/multiUpload")
    public MyResponseBody multiUpload(@RequestParam("file") MultipartFile[] files)  {
        System.out.println(files);
        System.out.println("文件的个数:" + files.length);
        for (MultipartFile f : files) {
            voteService.saveFile(f);
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
        FindVoteDto findvoteDto=new FindVoteDto();
        log.info(path.split("/")[1]);
        findvoteDto.setVotename(path.split("/")[1]);
        List<VotePoJo> votePoJos =voteService.findByName(findvoteDto);
        VotePoJo votePoJo=votePoJos.get(0);
        VoteDto voteDto =new VoteDto();
        BeanUtils.copyProperties(votePoJo,voteDto);
        log.info(voteDto.toString());
        Integer time=0;
        if(voteDto.getDownloadtime()!=null){
            time=voteDto.getDownloadtime()+1;
        }else{
            time+=1;
        }

        voteDto.setDownloadtime(time);
        voteService.updatevote(voteDto);


    }

    //列表查询
    @GetMapping("/voteslist")
    public MyResponseBody getZipslist(FindVoteDto findVoteDto) {
        return voteService.findVotes(findVoteDto);
    }



    //图片预览
    @GetMapping("/image")
    public  void showimage(String path,HttpServletResponse response){

        File imageFile = new File( basefilepath+"vote/"+path);
        log.info(basefilepath+"vote/"+path);
        if(imageFile.exists()){


        try {
            FileInputStream fis = new FileInputStream(imageFile);
            byte[]bytes = new byte[(int) imageFile.length()];fis.read(bytes);
            fis.close();
            switch (path.substring(path.lastIndexOf("\\.")+1)){
                case "jpg":
                    response.setContentType( "image/jpg" ); break;
                case "jpeg":
                    response.setContentType( "image/jpeg" ); break;
                case  "png":
                    response.setContentType( "image/png" ); break;
                case "tif":
                    response.setContentType( "image/tif" ); break;
                case "tiff":
                    response.setContentType( "image/tiff" ); break;
            }
//            response.setContentType( "image/jpg" );
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
            response.flushBuffer();

        }catch (Exception e){
            e.printStackTrace();
        }
        }

    }


    //word文件预览
    @GetMapping("file")
    public  void showword(String path,HttpServletResponse response){

        File filepath = new File( basefilepath+"vote/"+path);
        log.info(basefilepath+"vote/"+path);
        if(filepath.exists()){
            try {

                FileInputStream fis = new FileInputStream(filepath);

                byte[]bytes = new byte[(int) filepath.length()];
                fis.read(bytes);
                fis.close();
                switch (path.substring(path.lastIndexOf("\\.")+1)){
                    case "doc":
                        response.setContentType( "application/msword" ); break;
                    case "docx":
                        response.setContentType( "application/vnd.openxmlformats-officedocument.wordprocessingml.document" ); break;
                    case  "pdf":
                        response.setContentType( "application/pdf" ); break;
                    case "ppt":
                        response.setContentType( "application/x-ppt" ); break;

                }
//            response.setContentType( "image/jpg" );
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.flushBuffer();

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
    @PutMapping("/setMaxPriority")
    public MyResponseBody setMaxPriority(@RequestBody  VoteDto voteDto){
        log.info(voteDto.toString());

        return  voteService.setMaxPriority(voteDto);
    }

    @GetMapping("/maxPriority")
    public MyResponseBody getMaxPriority(){
        return  voteService.getMaxPriority();
    }

}
