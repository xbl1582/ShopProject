package com.example.kstupgrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kstupgrade.com.annotation.Sqllitessave;
import com.example.kstupgrade.com.obj.EmpowerPoJo;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.dto.EmpowerDto;
import com.example.kstupgrade.com.obj.dto.FindEmpowerDto;
import com.example.kstupgrade.com.obj.dto.FindVoteDto;
import com.example.kstupgrade.com.obj.dto.VoteDto;
import com.example.kstupgrade.com.util.*;
import com.example.kstupgrade.dao.empower.EmpowerMapper;
import com.example.kstupgrade.service.EmpowerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
public class EmpowerServiceImpl  extends ServiceImpl<EmpowerMapper, EmpowerPoJo> implements EmpowerService {
    @Value("${filebasepath}")
    private String basefilepath;
    @Autowired
    private  EmpowerMapper empowerMapper;
    //多线程查询数据库
    @Autowired
    private MyThreadDoSql<FindEmpowerDto, Object> myThreadDoSql;
    @Override
    public MyResponseBody deleteEmpower(String uuid) {
        //删除文件
        //查询
        EmpowerPoJo empowerPoJo= empowerMapper.findByuuid(uuid);
        if(empowerPoJo!=null){
            File file1 = new File(basefilepath + "empower/" + empowerPoJo.getSn());
            File file2 = new File(basefilepath + "empower/" + empowerPoJo.getLic());
            if (file1.exists()) {
                boolean result = file1.delete();
                if (!result) {
                    return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                }
            }
            if (file2.exists()) {
                boolean result = file2.delete();
                if (!result) {
                    return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                }
            }
            long result =empowerMapper.deleteEmpower(uuid);
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
    @Sqllitessave("锁操作")
    public MyResponseBody saveFile(MultipartFile file) {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            String basepath = basefilepath + "empower";

            File temp2 = new File(basepath);
            if (!temp2.exists()) {
                temp2.mkdirs();
            }
            long result = 0;
            //添加数据库
            EmpowerDto empowerDto = new EmpowerDto();

            //根据文件名称来判断类型
            //判断文件名：
            //获取文件名称
            String filename = file.getOriginalFilename();
            int lastIndexOf = filename.lastIndexOf(".");
            //获取文件的后缀名
            String suffix = filename.substring(lastIndexOf + 1);
            empowerDto.setType("txt");
            FindEmpowerDto findEmpowerDto=new FindEmpowerDto();
            if(suffix.equals("zip")||suffix.equals("rar")){
                if(suffix.equals("zip")) {
                    findEmpowerDto.setSncode(filename.split("_")[1].replaceAll(".zip", ""));
                }
                else {
                    findEmpowerDto.setSncode(filename.split("_")[1].replaceAll(".rar", ""));
                }
                log.info(filename.split("_")[1].replaceAll(".zip",""));
            }else{
                findEmpowerDto.setSncode(filename.split("_")[1].replaceAll(".txt",""));
                log.info(filename.split("_")[1].replaceAll(".txt",""));
            }
            List<EmpowerPoJo> empowerPoJos=empowerMapper.findEmpowerBySn(findEmpowerDto);
            log.info(empowerPoJos.toString());
            if(suffix.equals("zip")||suffix.equals("rar")){
                String sn;
                if(suffix.equals("rar")){
                    sn=filename.split("_")[1].replaceAll(".rar",".txt");
                }else{
                    sn=filename.split("_")[1].replaceAll(".zip",".txt");
                }

                empowerDto.setSn(sn);
                empowerDto.setLic(filename);
                findEmpowerDto.setSn(sn);
                if(empowerPoJos.isEmpty()){
                    String[] temps = UUID.randomUUID().toString().split("-");
                    StringBuffer uuidsb = new StringBuffer();
                    for (String temp : temps) {
                        uuidsb.append(temp);
                    }
                    empowerDto.setUuid(uuidsb.toString());
//                    empowerDto.setSn(sn);

                    empowerDto.setUploadtime(simpleDateFormat.format(System.currentTimeMillis()));
                    empowerDto.setSnsize(Formatter.formatFileSize(0));
                    empowerDto.setLic(file.getOriginalFilename());
                    empowerDto.setLicsize(Formatter.formatFileSize(file.getSize()));
                    empowerDto.setSndownloadtime(0);
                    empowerDto.setLicdownloadtime(0);
                    empowerDto.setSnpath("empower");
                    empowerDto.setLicpath("empower");
                    empowerDto.setSncode(filename.split("_")[1].replaceAll(".zip",""));
                    empowerDto.setType("txt");
                    File file2 = new File(basefilepath+"temp/" + file.getOriginalFilename());
                    FileUtils.copyInputStreamToFile(file.getInputStream(), file2);
                    empowerDto.setLicmd5code(MD5Util.getFileChecksumMD5(file2));
                    result=empowerMapper.addEmpower(empowerDto);
                }
                else{
                    BeanUtils.copyProperties(empowerPoJos.get(0),empowerDto);
                    empowerDto.setLic(file.getOriginalFilename());
                    empowerDto.setLicdownloadtime(0);
                    File file2 = new File(basefilepath+"temp/" + file.getOriginalFilename());
                    FileUtils.copyInputStreamToFile(file.getInputStream(), file2);
                    empowerDto.setLicmd5code(MD5Util.getFileChecksumMD5(file2));
                    empowerDto.setType("txt");
                    result=empowerMapper.updateEmpower(empowerDto);
                }

            }
            else{
                if (empowerPoJos.isEmpty()) {
                    empowerDto.setSn(file.getOriginalFilename());
                    String[] temps = UUID.randomUUID().toString().split("-");
                    StringBuffer uuidsb = new StringBuffer();
                    for (String temp : temps) {
                        uuidsb.append(temp);
                    }
                    empowerDto.setUuid(uuidsb.toString());
                    empowerDto.setSn(filename);
                    empowerDto.setSnsize(Formatter.formatFileSize(file.getSize()));

                    empowerDto.setUploadtime(simpleDateFormat.format(System.currentTimeMillis()));
                    empowerDto.setSndownloadtime(0);
                    empowerDto.setLicdownloadtime(0);
                    empowerDto.setSnpath("empower");
                    empowerDto.setSncode(filename.split("_")[1].replaceAll(".txt",""));
                    empowerDto.setLicpath("empower");
                    result = empowerMapper.addEmpower(empowerDto);
                }
                else {
                    BeanUtils.copyProperties(empowerPoJos.get(0),empowerDto);
                    empowerDto.setSnsize(Formatter.formatFileSize(file.getSize()));
                   empowerDto.setUuid(empowerPoJos.get(0).getUuid());
                   empowerDto.setSn(filename);
                    result = empowerMapper.updateEmpower(empowerDto);
                }
            }
            //保存数据库

            if (result >= 0) {
                //保存文件
                //保存文件的根地址
                //创建文件对象

                File dir = new File(basefilepath + "empower");
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

                if (flag) {
                    try {
                        file.transferTo(localFile); //把上传的文件保存至本地
                        log.info(file.getOriginalFilename() + " 上传成功");
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
    public List<EmpowerPoJo> findByName(FindEmpowerDto findEmpowerDto) {
        return  empowerMapper.findEmpowerBySn(findEmpowerDto);
    }

    @Override
    public MyResponseBody updateEmpower(EmpowerDto empowerDto) {
        long result = empowerMapper.updateEmpower(empowerDto);
        if (result >= 1) {
            return MyResponseBody.success();
        } else {
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }

    @Override
    public MyResponseBody findEmpower(FindEmpowerDto findEmpowerDto) {
        Function<FindEmpowerDto, Object> selectfun = zips -> empowerMapper.findEmpower(findEmpowerDto);
        Function<FindEmpowerDto, Object> countfun = zipcounts ->empowerMapper.getcount(findEmpowerDto);
        try {
            Long count = 0L;

            Object o = myThreadDoSql.dosql(findEmpowerDto, selectfun);
            Object c = myThreadDoSql.dosql(findEmpowerDto, countfun);
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
    public MyResponseBody getempowerbytxtname(FindEmpowerDto findEmpowerDto) {
        List<EmpowerPoJo> empowerPoJos=empowerMapper.getempowerbytxtname(findEmpowerDto);
        return MyResponseBody.success(empowerPoJos,Long.valueOf(empowerPoJos.size()));
    }

    @Override
    public MyResponseBody updatedEmpower(EmpowerDto empowerDto) {
            Long result=empowerMapper.updateEmpower(empowerDto);
        if(result>0){
            return MyResponseBody.success();
        }else{
            return  MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }

    @Override
    public MyResponseBody selectlicbyname(String lic) {
       String sncode =lic.split("_")[1].replaceAll(".zip", "");
        List<EmpowerPoJo> empowerPoJos=empowerMapper.selectlicbyname(sncode) ;
        return MyResponseBody.success(empowerPoJos,Long.valueOf(empowerPoJos.size()));
    }


}
