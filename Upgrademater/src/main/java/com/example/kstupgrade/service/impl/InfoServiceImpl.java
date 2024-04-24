package com.example.kstupgrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kstupgrade.com.obj.DriverPoJo;
import com.example.kstupgrade.com.obj.InfoPoJo;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.dto.*;
import com.example.kstupgrade.com.util.*;
import com.example.kstupgrade.dao.info.InfoMapper;
import com.example.kstupgrade.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
public class InfoServiceImpl extends ServiceImpl<InfoMapper, InfoPoJo> implements InfoService {
    @Value("${filebasepath}")
    private String basefilepath;
    @Autowired
    private  InfoMapper infoMapper;

    @Autowired
    private MyThreadDoSql<FindInfoDto, Object> myThreadDoSql;
    @Override
    public MyResponseBody deleteInfo(String uuid) {
        //删除文件
        //查询
       InfoPoJo infoPoJo= infoMapper.findByuuid(uuid);
        if(infoPoJo!=null){
            File dir = new File(basefilepath + "Info/" + infoPoJo.getInfoname());
            if (dir.exists()) {
                boolean result = dir.delete();
                if (!result) {
                    return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                }
            }
            long result = infoMapper.deleteInfo(uuid);
            if (result >= 1) {
                return MyResponseBody.success();
            } else {
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }
        }else{
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }

    @Override
    public MyResponseBody saveFile(MultipartFile file) {
        try {
            //添加数据库
           InfoDto infoDto = new InfoDto();
            infoDto.setInfoname(file.getOriginalFilename());
            //根据文件名称来判断类型
            //判断文件名：
            //获取文件名称
            String filename = file.getOriginalFilename();
//            String[] filenamepath = filename.split(".");
            int lastIndexOf = filename.lastIndexOf(".");
            //获取文件的后缀名
            String suffix = filename.substring(lastIndexOf + 1);
            infoDto.setType(suffix);


            String[] temps = UUID.randomUUID().toString().split("-");
            StringBuffer uuidsb = new StringBuffer();
            for (String temp : temps) {
                uuidsb.append(temp);
            }
            infoDto.setUuid(uuidsb.toString());
            infoDto.setInfoname(filename);
            infoDto.setInfosize(Formatter.formatFileSize(file.getSize()));
            infoDto.setUpdatetime(String.valueOf(System.currentTimeMillis()));
            infoDto.setDownloadtime(0);
            String basepath = basefilepath + "Info";
            File temp2 = new File(basepath);
            if (!temp2.exists()) {
                temp2.mkdirs();
            }
            infoDto.setPath("Info");
            long result = 0;
            FindInfoDto findInfodto = new FindInfoDto();
            findInfodto.setInfoname(infoDto.getInfoname());
            List<InfoPoJo> infoPoJos =infoMapper.findInfoByName(findInfodto);
            if (infoPoJos.isEmpty()) {
                result = infoMapper.addInfos(infoDto);
            } else {
                infoDto.setUuid(infoPoJos.get(0).getUuid());
                result = infoMapper.updateInfo(infoDto);
            }

            //保存数据库

            if (result >= 0) {
                //保存文件
                //保存文件的根地址

                log.info(basepath);
                //创建文件对象

                File dir = new File(basefilepath + "Info");
                //判断根路径是否存在不存在创建
                File dir2 = new File(basefilepath);
                if (!dir2.exists()) {
                    try {
                        dir2.mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                    }
                }
                //判断根子目录是否存在
                if (!dir.exists()) {
                    try {
                        dir.mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                    }
                }
                //判断前端传递过来的文件是否为空，为空返回错误
                if (file.isEmpty()) {
                    return MyResponseBody.failure(ResponseEnumCode.CHOOSE_NO_FILE);

                }
                //创建文件对象
                File localFile = new File(basepath + "/" + filename);
                //如果文件存在将原来的文件进行删除，然后覆盖
                boolean flag = true;
                if (localFile.exists()) {
                    flag = localFile.delete();
                }
                log.info(basepath + "/" + filename);
                if (flag) {
                    try {
                        file.transferTo(localFile); //把上传的文件保存至本地

                        System.out.println(file.getOriginalFilename() + " 上传成功");
                        return MyResponseBody.success("上传成功", null, 1L);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                    }
                } else {
                    return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                }

            } else {
                //数据库保存失败。
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }


        }catch (Exception e) {
            e.printStackTrace();
            return MyResponseBody.failure(ResponseEnumCode.DATA_IS_WRONG);

        }
    }


    @Override
    public List<InfoPoJo> findByName(FindInfoDto findInfoDto) {
        return  infoMapper.findInfoByName( findInfoDto);
    }

    @Override
    public long updateInfo(InfoDto infoDto) {
        return infoMapper.updateInfo(infoDto);

    }

    @Override
    public MyResponseBody findInfos(FindInfoDto findInfoDto) {
        Function<FindInfoDto, Object> selectfun = zips -> infoMapper.findInfos(findInfoDto);
        Function<FindInfoDto, Object> countfun = zipcounts ->infoMapper.getcount(findInfoDto);
        try {
            Long count = 0L;

            Object o = myThreadDoSql.dosql(findInfoDto, selectfun);
            Object c = myThreadDoSql.dosql(findInfoDto, countfun);
            if (c instanceof Long) {
                count = (long) c;
            }
            return MyResponseBody.success(o, count);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }


    @Override
    public MyResponseBody setMaxPriority(InfoDto infoDto) {
        long setinipriority=infoMapper.updateini();
        int maxpriority=infoMapper.getMaxPriority();
        infoDto.setPriority(maxpriority+1);
        Long result=updateInfo(infoDto);
        if(result>0){
            return  MyResponseBody.success();
        }else{
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }

    }

    @Override
    public MyResponseBody getMaxPriority() {

        int maxpriority=infoMapper.getMaxPriority();
        if(maxpriority>0){
            return  MyResponseBody.success("",Long.valueOf(maxpriority));
        }else{
            return MyResponseBody.success("",-1L);
        }
    }
}
