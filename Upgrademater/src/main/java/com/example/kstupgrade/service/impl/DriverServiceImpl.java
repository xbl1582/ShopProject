package com.example.kstupgrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kstupgrade.com.annotation.Sqllitessave;
import com.example.kstupgrade.com.obj.DriverPoJo;
import com.example.kstupgrade.com.obj.dto.*;
import com.example.kstupgrade.com.util.*;
import com.example.kstupgrade.dao.driver.DriverMapper;
import com.example.kstupgrade.service.DriverService;
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
public class DriverServiceImpl extends ServiceImpl<DriverMapper, DriverPoJo> implements DriverService {
   @Autowired
   private  DriverMapper driverMapper;
    @Value("${filebasepath}")
    private String basefilepath;

    @Autowired
    private MyThreadDoSql<FindDriverDto, Object> myThreadDoSql;
    @Override
    public MyResponseBody deleteDriver(String uuid) {
        //删除文件
        //查询
       DriverPoJo driverPoJo = driverMapper.findByuuid(uuid);
        if(driverPoJo!=null){
            File dir = new File(basefilepath +"Driver/"+ driverPoJo.getDrivername());
            if (dir.exists()) {
                boolean result = dir.delete();
                if (!result) {
                    return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                }
            }
            long result = driverMapper.deleteDriver(uuid);
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
        try {
            //添加数据库
            DriverDto driverDto = new DriverDto();
            driverDto.setDrivername(file.getOriginalFilename());
            //根据文件名称来判断类型
            //判断文件名：
            //获取文件名称
            String filename = file.getOriginalFilename();
//            String[] filenamepath = filename.split(".");
            int lastIndexOf = filename.lastIndexOf(".");
            //获取文件的后缀名
            String suffix = filename.substring(lastIndexOf + 1);
            driverDto.setType(suffix);


            String[] temps = UUID.randomUUID().toString().split("-");
            StringBuffer uuidsb = new StringBuffer();
            for (String temp : temps) {
                uuidsb.append(temp);
            }
            driverDto.setUuid(uuidsb.toString());
            driverDto.setDrivername(filename);
            driverDto.setDriversize(Formatter.formatFileSize(file.getSize()));
            driverDto.setUpdatetime(String.valueOf(System.currentTimeMillis()));
            driverDto.setDownloadtime(0);
            String basepath = basefilepath + "Driver";
            File temp2 = new File(basepath);
            if (!temp2.exists()) {
                temp2.mkdirs();
            }
            driverDto.setPath("Driver");
            File temp = new File(basefilepath + "temp/");
            if (!temp.exists()) {
                temp.mkdirs();
            }
            File file2 = new File(basefilepath + "temp/" + file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), file2);
            driverDto.setMd5code(MD5Util.getFileChecksumMD5(file2));
            file2.delete();
            FileUtils.deleteDirectory(temp);
            long result = 0;
            if (driverDto.getMd5code() != null) {
                FindDriverDto findDriverdto = new FindDriverDto();
                findDriverdto.setDrivername(driverDto.getDrivername());
                List<DriverPoJo> driverPoJos = driverMapper.findDriverByName(findDriverdto);
                if (driverPoJos.isEmpty()) {
                    result = driverMapper.addDriver(driverDto);
                } else {
                    driverDto.setUuid(driverPoJos.get(0).getUuid());
                    result = driverMapper.updateDriver(driverDto);
                }

            }
            //保存数据库

            if (result >= 0) {
                //保存文件
                //保存文件的根地址

                log.info(basepath);
                //创建文件对象

                File dir = new File(basefilepath + "Driver");
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
                System.out.println(basepath + "/" + filename);
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
    public MyResponseBody updateDri(DriverDto driverDto) {
        long result = driverMapper.updateDriver(driverDto);
        if (result >= 1) {
            return MyResponseBody.success();
        } else {
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }

    @Override
    public List<DriverPoJo> findByName(FindDriverDto findDriverDto) {
        return  driverMapper.findDriverByName(findDriverDto);
    }

    @Override
    public long updateDriver(DriverDto driverDto) {
            return  driverMapper.updateDriver(driverDto);
    }

    @Override
    public MyResponseBody findDriversList(FindDriverDto findDriverDto) {
        Function<FindDriverDto, Object> selectfun = zips -> driverMapper.findDriver(findDriverDto);
        Function<FindDriverDto, Object> countfun = zipcounts ->driverMapper.getcount(findDriverDto);
        try {
            Long count = 0L;

            Object o = myThreadDoSql.dosql(findDriverDto, selectfun);
            Object c = myThreadDoSql.dosql(findDriverDto, countfun);
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
    public MyResponseBody setMaxPriority(DriverDto driverDto) {
        long setinipriority=driverMapper.updateini();
        int maxpriority=driverMapper.getMaxPriority();
        driverDto.setPriority(maxpriority+1);
        Long result=driverMapper.updateDriver(driverDto);
        if(result>0){
            return  MyResponseBody.success();
        }else{
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }

    }

    @Override
    public MyResponseBody getMaxPriority() {
        int maxpriority=driverMapper.getMaxPriority();
        if(maxpriority>0){
            return  MyResponseBody.success("",Long.valueOf(maxpriority));
        }else{
            return MyResponseBody.success("",-1L);
        }
    }
}
