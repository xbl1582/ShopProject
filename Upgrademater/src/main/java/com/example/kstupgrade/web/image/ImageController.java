package com.example.kstupgrade.web.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {
    @Value("${filebasepath}")
    private String basefilepath;
    //图片预览
    @GetMapping
    public  void showimage(String path, HttpServletResponse response){

        File imageFile = new File( basefilepath+"image/"+path);
        log.info(basefilepath+"image/"+path);
        if(!imageFile.exists()){
           imageFile = new File( basefilepath+"image/Default.png");
        }
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
        }else{
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




    @GetMapping("/icon")
    public  void showicon(String path, HttpServletResponse response){

        File imageFile = new File( basefilepath+"icon/"+path);
        log.info(basefilepath+"image/"+path);
        if(!imageFile.exists()){
            imageFile = new File( basefilepath+"icon/Default.png");
        }
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
        }else{
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
}
