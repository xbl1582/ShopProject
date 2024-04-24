package com.example.kstupgrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kstupgrade.com.annotation.Sqllitessave;
import com.example.kstupgrade.com.obj.PathClassPoJo;
import com.example.kstupgrade.com.obj.ZipPoJo;
import com.example.kstupgrade.com.obj.dto.FindZipDto;
import com.example.kstupgrade.com.obj.dto.PathClassDto;
import com.example.kstupgrade.com.obj.dto.VersionAndTimeDto;
import com.example.kstupgrade.com.obj.dto.ZipDto;
import com.example.kstupgrade.com.util.*;
import com.example.kstupgrade.com.util.Formatter;
import com.example.kstupgrade.dao.pathclass.PathClassMapper;
import com.example.kstupgrade.dao.zip.ZipMapper;
import com.example.kstupgrade.service.PathClassService;
import com.example.kstupgrade.service.ZipService;
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
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
public class ZipServiceImpl extends ServiceImpl<ZipMapper, ZipPoJo> implements ZipService {
    //注入数据处理
    @Value("${filebasepath}")
    private String basefilepath;
    SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private ZipMapper zipmapper;
    @Autowired
    private PathClassMapper pathClassMapper;
    //多线程查询数据库
    @Autowired
    private MyThreadDoSql<FindZipDto, Object> myThreadDoSql;

    //添加数据处理
    @Override
    public MyResponseBody addZip(ZipDto zipDto) {


        return null;
    }

    //删除处理
    @Override
    public MyResponseBody deleteZip(String uuid) {
        //删除文件
        //查询
        ZipPoJo zipPoJo = zipmapper.findByuuid(uuid);
        if(zipPoJo!=null){
            File dir = new File(basefilepath + zipPoJo.getZipname().split("_")[0] + "/" + zipPoJo.getZipname());
            if (dir.exists()) {
                boolean result = dir.delete();
                if (!result) {
                    return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                }
            }
            long result = zipmapper.deleteZip(uuid);
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
    public MyResponseBody updateZip(ZipDto zipDto) {
        long result = zipmapper.updateZip(zipDto);
        if (result >= 1) {
            return MyResponseBody.success();
        } else {
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }

    //查询处理
    @Override
    public MyResponseBody findZips(FindZipDto findZipDto) {
        if(findZipDto.getVersiontype()==null){
            switch (findZipDto.getSearchvalue()){

                case "扫描仪":{findZipDto.setType(2);findZipDto.setSearchvalue("");}break;
                case "票箱":{findZipDto.setType(1);findZipDto.setSearchvalue("");}break;
                case "后台":{findZipDto.setType(3);findZipDto.setSearchvalue("KST");}break;
                case "废票处理":{findZipDto.setType(3); findZipDto.setSearchvalue("KSTLX"); };break;
                default: {findZipDto.setType(null); findZipDto.setSearchvalue(findZipDto.getSearchvalue());}
            }
            Function<FindZipDto, Object> selectfun = zips -> zipmapper.findZips(findZipDto);
            Function<FindZipDto, Object> countfun = zipcounts -> zipmapper.getcount(findZipDto);
            try {
                Long count = 0L;

                Object o = myThreadDoSql.dosql(findZipDto, selectfun);
                Object c = myThreadDoSql.dosql(findZipDto, countfun);
                if (c instanceof Long) {
                    count = (long) c;
                }
                return MyResponseBody.success(o, count);
            } catch (Exception e) {
                e.printStackTrace();
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }
        }else{
            switch (findZipDto.getSearchvalue()){

                case "扫描仪":{findZipDto.setType(2);findZipDto.setSearchvalue("");}break;
                case "票箱":{findZipDto.setType(1);findZipDto.setSearchvalue("");}break;
                case "后台":{findZipDto.setType(3);findZipDto.setSearchvalue("KST");}break;
                case "废票处理":{findZipDto.setType(3); findZipDto.setSearchvalue("KSTLX"); };break;
                default: {findZipDto.setType(null); findZipDto.setSearchvalue(findZipDto.getSearchvalue());}
            }
            Function<FindZipDto, Object> selectfun = zips -> zipmapper.findZips(findZipDto);
            try {
                Long count = 0L;

                Object o = myThreadDoSql.dosql(findZipDto, selectfun);
                if (o instanceof List) {
                    count = (long) ((List<?>) o).size();
                }
                return MyResponseBody.success(o, count);
            } catch (Exception e) {
                e.printStackTrace();
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }

        }

    }

    //查询最新版本
    @Override
    public MyResponseBody findbastNewZips(FindZipDto findZipDto) {
        log.info("业务类："+findZipDto.toString());
        //对版本号和日期进行查询
        //1先对日期进行判断，2再对版本号进行判断
        List<ZipPoJo> list2=zipmapper.findByUpdateloadtimeAndPath(findZipDto);
        log.info("查询结果byupdate："+list2.toString());
        List<ZipPoJo> list =new ArrayList<ZipPoJo>() ;
        if(list2.isEmpty()){
           //获取到关于终端需要的对应的系统和类型的安装包
           list=zipmapper.findbastNewZips(findZipDto);
            System.out.println(list);
       }else{
          ZipPoJo zipPoJo=list2.get(0);
            BeanUtils.copyProperties(zipPoJo,findZipDto);
            list=zipmapper.findbastNewZips(findZipDto);
            log.info("查询结果findbast："+list.toString());
       }
        log.info("返回结果："+list.toString());
        if (list.isEmpty()) {
            return MyResponseBody.success(ResponseEnumCode.RESULE_DATA_NONE, null, 0L);
        } else {
            long l = list.size();
            return MyResponseBody.success(list, l);
        }
    }

    @Override
    public MyResponseBody findNewZipsByPath(FindZipDto findZipDto) {

        log.info("业务类："+findZipDto.toString());
        //对版本号和日期进行查询
        //1先对日期进行判断，2再对版本号进行判断
        List<ZipPoJo> list2=zipmapper.findByUpdateloadtimeAndPath(findZipDto);
        log.info("查询结果byupdate："+list2.toString());
        List<ZipPoJo> list =new ArrayList<>();
        if(list2.isEmpty()){
            //获取到关于终端需要的对应的系统和类型的安装包
            list=zipmapper.findNewZipsByPath(findZipDto);
            log.info("查询结果findbast："+list.toString());
        }else{
            ZipPoJo zipPoJo=list2.get(0);
            BeanUtils.copyProperties(zipPoJo,findZipDto);
            findZipDto.setVersion(zipPoJo.getVersion());
            log.info("findZipDto："+findZipDto.toString());
            list= zipmapper.findNewZipsByPath(findZipDto);
            log.info("查询结果findbast："+list.toString());
        }
        log.info("返回结果："+list.toString());



        if (list.isEmpty()) {
            return MyResponseBody.success(ResponseEnumCode.RESULE_DATA_NONE, null, 0L);
        } else {
            long l = list.size();
            return MyResponseBody.success(list, l);
        }
    }

    @Override
    public List<ZipPoJo> findByName(String name) {
        FindZipDto findZipDto=new FindZipDto();
        findZipDto.setZipname(name);
        return zipmapper.findZipByName(findZipDto);
    }

    //下载处理
    @Override
    public void downloadZipList() {

    }

    //上传处理
    @Override
    public void uploadZips() {

    }

    @Override
    @Sqllitessave("锁操作")
    public MyResponseBody saveFile(MultipartFile file) {

        try {
            //添加数据库
            ZipDto zipDto = new ZipDto();
            zipDto.setZipname(file.getOriginalFilename());
            //根据文件名称来判断类型
            //判断文件名：
            //获取文件名称
            String filename = file.getOriginalFilename();
            if(filename.toLowerCase().endsWith(".deb")){
                String[] filenamepath = filename.split("_");



                if (filenamepath.length == 3) {
                    //1.票箱 2.扫描仪
                    PathClassPoJo pathClassPoJo=pathClassMapper.setectPathclassByPath(filenamepath[0]);
                    if(pathClassPoJo==null){
                        PathClassDto pathClassDto=new PathClassDto();
                        pathClassDto.setPath(filenamepath[0]);
                        String uuid= UUID.randomUUID().toString().replaceAll("-","");
                        pathClassDto.setUuid(uuid);
                        switch (filenamepath[0]) {
                            case "APP":
                                zipDto.setType(3);
                                zipDto.setOrder(10);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("环境");
                                break;
                            case "APPQ":
                                zipDto.setType(1);
                                zipDto.setOrder(10);
                                pathClassDto.setType(1);
                                pathClassDto.setPathname("环境");
                                break;
                            case "APPX":
                                zipDto.setType(2);
                                zipDto.setOrder(10);
                                pathClassDto.setType(2);
                                pathClassDto.setPathname("环境");
                                break;
                            case "KSTMAIN":
                                zipDto.setType(0);
                                zipDto.setOrder(8);
                                pathClassDto.setType(0);
                                pathClassDto.setPathname("启动页");
                                break;
                            case "KSTPXX":
                                zipDto.setType(2);
                                zipDto.setOrder(2);
                                pathClassDto.setType(2);
                                pathClassDto.setPathname("计票(903计票机)");
                                break;
                            case "WTKPX":
                                zipDto.setType(2);
                                zipDto.setOrder(3);
                                pathClassDto.setType(2);
                                pathClassDto.setPathname("测评(903测评机)");
                                break;
                            case "KSTSCAN":
                                zipDto.setType(2);
                                zipDto.setOrder(4);
                                pathClassDto.setType(2);
                                pathClassDto.setPathname("扫描(903扫描仪)");
                                break;
                            case "KSTPXQ":
                                zipDto.setType(1);
                                zipDto.setOrder(1);
                                pathClassDto.setType(1);
                                pathClassDto.setPathname("计票");
                                break;
                            case "KST":
                                zipDto.setType(3);
                                zipDto.setOrder(5);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("选举计票后台");
                                break;
                            case "KSTLX":
                                zipDto.setType(3);
                                zipDto.setOrder(6);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("另选检查");
                                break;
                            case "WTKP":
                                zipDto.setType(3);
                                zipDto.setOrder(7);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("测评系统后台");
                                break;
                            case "YYEditor":
                                zipDto.setType(3);
                                zipDto.setOrder(9);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("智能模板编辑器");
                                break;
                            case "KSTSP":
                                zipDto.setType(3);
                                zipDto.setOrder(10);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("扫描加工");
                                break;

                            default: {
                                zipDto.setType(4);
                                zipDto.setOrder(11);
                                pathClassDto.setType(4);
                                pathClassDto.setPathname("其他程序软件");
                            }
                        }
                        pathClassMapper.addPathclass(pathClassDto);

                    }else{
                        switch (filenamepath[0]) {
                            case "APP":
                                zipDto.setType(3);
                                zipDto.setOrder(10);

                                break;
                            case "APPQ":
                                zipDto.setType(1);
                                zipDto.setOrder(10);

                                break;
                            case "APPX":
                                zipDto.setType(2);
                                zipDto.setOrder(10);

                                break;
                            case "KSTMAIN":
                                zipDto.setType(0);
                                zipDto.setOrder(8);

                                break;
                            case "KSTPXX":
                                zipDto.setType(2);
                                zipDto.setOrder(2);

                                break;
                            case "WTKPX":
                                zipDto.setType(2);
                                zipDto.setOrder(3);

                                break;
                            case "KSTSCAN":
                                zipDto.setType(2);
                                zipDto.setOrder(4);

                                break;
                            case "KSTPXQ":
                                zipDto.setType(1);
                                zipDto.setOrder(1);

                                break;
                            case "KST":
                                zipDto.setType(3);
                                zipDto.setOrder(5);

                                break;
                            case "KSTLX":
                                zipDto.setType(3);
                                zipDto.setOrder(6);

                                break;
                            case "WTKP":
                                zipDto.setType(3);
                                zipDto.setOrder(7);

                                break;
                            case "YYEditor":
                                zipDto.setType(3);
                                zipDto.setOrder(9);

                                break;
                            case "KSTSP":
                                zipDto.setType(3);
                                zipDto.setOrder(10);

                                break;

                            default: {
                                zipDto.setType(4);
                                zipDto.setOrder(11);

                            }
                        }
                    }
                    zipDto.setVersiontype("deb");
                    String[] temps = UUID.randomUUID().toString().split("-");
                    StringBuffer uuidsb = new StringBuffer();
                    for (String temp : temps) {
                        uuidsb.append(temp);
                    }
                    zipDto.setUuid(uuidsb.toString());
                    if(filenamepath[2].startsWith("arm")||filenamepath[2].startsWith("ARM")||filenamepath[2].startsWith("Arm")){
                        zipDto.setOs("arm");
                    }else{
                        zipDto.setOs("win");
                    }

                    zipDto.setVersionchar(filenamepath[1]);
                    zipDto.setVersion(Integer.valueOf(filenamepath[1].replaceAll("\\.","")));
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYYMMdd");
                    String time =simpleDateFormat.format(System.currentTimeMillis());
                    zipDto.setZipsize(Formatter.formatFileSize(file.getSize()));
                    zipDto.setUpdateloadtime(Integer.valueOf(time));
                    zipDto.setDownloadtime(0);
                    String basepath = basefilepath + filenamepath[0];
                    zipDto.setPath(filenamepath[0]);
                    File  temp = new File(basefilepath+"temp/");
                    if(!temp.exists()){
                        temp.mkdirs();
                    }
                    File file2 = new File(basefilepath+"temp/" + file.getOriginalFilename());
                    FileUtils.copyInputStreamToFile(file.getInputStream(), file2);
                    zipDto.setMd5code(MD5Util.getFileChecksumMD5(file2));
                    file2.delete();
                    FileUtils.deleteDirectory(temp);
                    long result =0;
                    if(zipDto.getMd5code()!=null){
                        FindZipDto findZipDto=new FindZipDto();
                        findZipDto.setZipname(zipDto.getZipname());
                        List<ZipPoJo> zipPoJos=zipmapper.findZipByName(findZipDto);
                        if(zipPoJos.isEmpty()){
                            zipDto.setNowuploadtime(simpleDateFormat2.format(System.currentTimeMillis()));
                            result=zipmapper.addZip(zipDto);
                        }else{
                            zipDto.setUuid(zipPoJos.get(0).getUuid());
                            zipDto.setNowuploadtime(simpleDateFormat2.format(System.currentTimeMillis()));
                            result=zipmapper.updateZip(zipDto);
                        }

                    }
                    //保存数据库

                    if (result >= 0) {
                        //保存文件
                        //保存文件的根地址

                        log.info(basepath);
                        //创建文件对象
                        File dir = new File(basefilepath + filenamepath[0]);
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

                } else {
                    return MyResponseBody.failure(ResponseEnumCode.DATA_IS_WRONG);
                }
            }
            else if(filename.toLowerCase().endsWith("x64.zip")){

                String[] filenamepath = filename.split("_");

                if (filenamepath.length == 3) {
                    //1.票箱 2.扫描仪
                    PathClassPoJo pathClassPoJo=pathClassMapper.setectPathclassByPath(filenamepath[0]);
                    if(pathClassPoJo==null){
                        PathClassDto pathClassDto=new PathClassDto();
                        pathClassDto.setPath(filenamepath[0]);
                        String uuid= UUID.randomUUID().toString().replaceAll("-","");
                        pathClassDto.setUuid(uuid);
                        switch (filenamepath[0]) {
                            case "APP":
                                zipDto.setType(3);
                                zipDto.setOrder(10);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("环境");
                                break;
                            case "APPQ":
                                zipDto.setType(1);
                                zipDto.setOrder(10);
                                pathClassDto.setType(1);
                                pathClassDto.setPathname("环境");
                                break;
                            case "APPX":
                                zipDto.setType(2);
                                zipDto.setOrder(10);
                                pathClassDto.setType(2);
                                pathClassDto.setPathname("环境");
                                break;
                            case "KSTMAIN":
                                zipDto.setType(0);
                                zipDto.setOrder(8);
                                pathClassDto.setType(0);
                                pathClassDto.setPathname("启动页");
                                break;
                            case "KSTPXX":
                                zipDto.setType(2);
                                zipDto.setOrder(2);
                                pathClassDto.setType(2);
                                pathClassDto.setPathname("计票(903计票机)");
                                break;
                            case "WTKPX":
                                zipDto.setType(2);
                                zipDto.setOrder(3);
                                pathClassDto.setType(2);
                                pathClassDto.setPathname("测评(903测评机)");
                                break;
                            case "KSTSCAN":
                                zipDto.setType(2);
                                zipDto.setOrder(4);
                                pathClassDto.setType(2);
                                pathClassDto.setPathname("扫描(903扫描仪)");
                                break;
                            case "KSTPXQ":
                                zipDto.setType(1);
                                zipDto.setOrder(1);
                                pathClassDto.setType(1);
                                pathClassDto.setPathname("计票");
                                break;
                            case "KST":
                                zipDto.setType(3);
                                zipDto.setOrder(5);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("选举计票后台");
                                break;
                            case "KSTLX":
                                zipDto.setType(3);
                                zipDto.setOrder(6);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("另选检查");
                                break;
                            case "WTKP":
                                zipDto.setType(3);
                                zipDto.setOrder(7);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("测评系统后台");
                                break;
                            case "YYEditor":
                                zipDto.setType(3);
                                zipDto.setOrder(9);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("智能模板编辑器");
                                break;
                            case "KSTSP":
                                zipDto.setType(3);
                                zipDto.setOrder(10);
                                pathClassDto.setType(3);
                                pathClassDto.setPathname("扫描加工");
                                break;

                            default: {
                                zipDto.setType(4);
                                zipDto.setOrder(11);
                                pathClassDto.setType(4);
                                pathClassDto.setPathname("其他程序软件");
                            }
                        }
                        pathClassMapper.addPathclass(pathClassDto);

                    }else{
                        switch (filenamepath[0]) {
                            case "APP":
                                zipDto.setType(3);
                                zipDto.setOrder(10);

                                break;
                            case "APPQ":
                                zipDto.setType(1);
                                zipDto.setOrder(10);

                                break;
                            case "APPX":
                                zipDto.setType(2);
                                zipDto.setOrder(10);

                                break;
                            case "KSTMAIN":
                                zipDto.setType(0);
                                zipDto.setOrder(8);

                                break;
                            case "KSTPXX":
                                zipDto.setType(2);
                                zipDto.setOrder(2);

                                break;
                            case "WTKPX":
                                zipDto.setType(2);
                                zipDto.setOrder(3);

                                break;
                            case "KSTSCAN":
                                zipDto.setType(2);
                                zipDto.setOrder(4);

                                break;
                            case "KSTPXQ":
                                zipDto.setType(1);
                                zipDto.setOrder(1);

                                break;
                            case "KST":
                                zipDto.setType(3);
                                zipDto.setOrder(5);

                                break;
                            case "KSTLX":
                                zipDto.setType(3);
                                zipDto.setOrder(6);

                                break;
                            case "WTKP":
                                zipDto.setType(3);
                                zipDto.setOrder(7);

                                break;
                            case "YYEditor":
                                zipDto.setType(3);
                                zipDto.setOrder(9);

                                break;
                            case "KSTSP":
                                zipDto.setType(3);
                                zipDto.setOrder(10);

                                break;

                            default: {
                                zipDto.setType(4);
                                zipDto.setOrder(11);

                            }
                        }
                    }
                    zipDto.setVersiontype("deb");
                    String[] temps = UUID.randomUUID().toString().split("-");
                    StringBuffer uuidsb = new StringBuffer();
                    for (String temp : temps) {
                        uuidsb.append(temp);
                    }
                    zipDto.setUuid(uuidsb.toString());
                    zipDto.setOs("win");

                    zipDto.setVersionchar(filenamepath[1]);
                    zipDto.setVersion(Integer.valueOf(filenamepath[1].replaceAll("\\.","")));
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYYMMdd");
                    String time =simpleDateFormat.format(System.currentTimeMillis());
                    zipDto.setZipsize(Formatter.formatFileSize(file.getSize()));
                    zipDto.setUpdateloadtime(Integer.valueOf(time));
                    zipDto.setDownloadtime(0);
                    String basepath = basefilepath + filenamepath[0];
                    zipDto.setPath(filenamepath[0]);
                    File  temp = new File(basefilepath+"temp/");
                    if(!temp.exists()){
                        temp.mkdirs();
                    }
                    File file2 = new File(basefilepath+"temp/" + file.getOriginalFilename());
                    FileUtils.copyInputStreamToFile(file.getInputStream(), file2);
                    zipDto.setMd5code(MD5Util.getFileChecksumMD5(file2));
                    file2.delete();
                    FileUtils.deleteDirectory(temp);
                    long result =0;
                    if(zipDto.getMd5code()!=null){
                        FindZipDto findZipDto=new FindZipDto();
                        findZipDto.setZipname(zipDto.getZipname());
                        List<ZipPoJo> zipPoJos=zipmapper.findZipByName(findZipDto);
                        if(zipPoJos.isEmpty()){
                            zipDto.setNowuploadtime(simpleDateFormat2.format(System.currentTimeMillis()));
                            result=zipmapper.addZip(zipDto);
                        }else{
                            zipDto.setUuid(zipPoJos.get(0).getUuid());
                            zipDto.setNowuploadtime(simpleDateFormat2.format(System.currentTimeMillis()));
                            result=zipmapper.updateZip(zipDto);
                        }

                    }
                    //保存数据库

                    if (result >= 0) {
                        //保存文件
                        //保存文件的根地址

                        log.info(basepath);
                        //创建文件对象
                        File dir = new File(basefilepath + filenamepath[0]);
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

                } else {
                    return MyResponseBody.failure(ResponseEnumCode.DATA_IS_WRONG);
                }

            }
            else{
                String[] filenamepath = filename.split("_");
                    if (filenamepath.length == 5) {

                        PathClassPoJo pathClassPoJo=pathClassMapper.setectPathclassByPath(filenamepath[0]);
                        if(pathClassPoJo==null){
                            PathClassDto pathClassDto=new PathClassDto();
                            String uuid= UUID.randomUUID().toString().replaceAll("-","");
                            pathClassDto.setUuid(uuid);
                            pathClassDto.setPath(filenamepath[0]);
                            switch (filenamepath[0]) {
                                case "APP":
                                    zipDto.setType(3);
                                    zipDto.setOrder(10);
                                    pathClassDto.setType(3);
                                    pathClassDto.setPathname("环境");
                                    break;
                                case "APPQ":
                                    zipDto.setType(1);
                                    zipDto.setOrder(10);
                                    pathClassDto.setType(1);
                                    pathClassDto.setPathname("环境");
                                    break;
                                case "APPX":
                                    zipDto.setType(2);
                                    zipDto.setOrder(10);
                                    pathClassDto.setType(2);
                                    pathClassDto.setPathname("环境");
                                    break;
                                case "KSTMAIN":
                                    zipDto.setType(0);
                                    zipDto.setOrder(8);
                                    pathClassDto.setType(0);
                                    pathClassDto.setPathname("启动页");
                                    break;
                                case "KSTPXX":
                                    zipDto.setType(2);
                                    zipDto.setOrder(2);
                                    pathClassDto.setType(2);
                                    pathClassDto.setPathname("计票(903计票机)");
                                    break;
                                case "WTKPX":
                                    zipDto.setType(2);
                                    zipDto.setOrder(3);
                                    pathClassDto.setType(2);
                                    pathClassDto.setPathname("测评(903测评机)");
                                    break;
                                case "KSTSCAN":
                                    zipDto.setType(2);
                                    zipDto.setOrder(4);
                                    pathClassDto.setType(2);
                                    pathClassDto.setPathname("扫描(903扫描仪)");
                                    break;
                                case "KSTPXQ":
                                    zipDto.setType(1);
                                    zipDto.setOrder(1);
                                    pathClassDto.setType(1);
                                    pathClassDto.setPathname("计票");
                                    break;
                                case "KST":
                                    zipDto.setType(3);
                                    zipDto.setOrder(5);
                                    pathClassDto.setType(3);
                                    pathClassDto.setPathname("选举计票后台");
                                    break;
                                case "KSTLX":
                                    zipDto.setType(3);
                                    zipDto.setOrder(6);
                                    pathClassDto.setType(3);
                                    pathClassDto.setPathname("另选检查");
                                    break;
                                case "WTKP":
                                    zipDto.setType(3);
                                    zipDto.setOrder(7);
                                    pathClassDto.setType(3);
                                    pathClassDto.setPathname("测评系统后台");
                                    break;
                                case "YYEditor":
                                    zipDto.setType(3);
                                    zipDto.setOrder(9);
                                    pathClassDto.setType(3);
                                    pathClassDto.setPathname("智能模板编辑器");
                                    break;
                                case "KSTSP":
                                    zipDto.setType(3);
                                    zipDto.setOrder(10);
                                    pathClassDto.setType(3);
                                    pathClassDto.setPathname("扫描加工");
                                    break;

                                default: {
                                    zipDto.setType(4);
                                    zipDto.setOrder(11);
                                    pathClassDto.setType(4);
                                    pathClassDto.setPathname("其他程序软件");
                                }
                            }
                            pathClassMapper.addPathclass(pathClassDto);

                        }
                        else {
                            //1.票箱 2.扫描仪
                            switch (filenamepath[0]) {
                                case "APP":
                                    zipDto.setType(3);
                                    zipDto.setOrder(10);
                                    break;
                                case "APPQ":
                                    zipDto.setType(1);
                                    zipDto.setOrder(10);
                                    break;
                                case "APPX":
                                    zipDto.setType(2);
                                    zipDto.setOrder(10);
                                    break;
                                case "KSTMAIN":
                                    zipDto.setType(0);
                                    zipDto.setOrder(8);
                                    break;
                                case "KSTPXX":
                                    zipDto.setType(2);
                                    zipDto.setOrder(2);
                                    break;
                                case "WTKPX":
                                    zipDto.setType(2);
                                    zipDto.setOrder(3);
                                    break;
                                case "KSTSCAN":
                                    zipDto.setType(2);
                                    zipDto.setOrder(4);
                                    break;
                                case "KSTPXQ":
                                    zipDto.setType(1);
                                    zipDto.setOrder(1);
                                    break;
                                case "KST":
                                    zipDto.setType(3);
                                    zipDto.setOrder(5);
                                    break;
                                case "KSTLX":
                                    zipDto.setType(3);
                                    zipDto.setOrder(6);
                                    break;
                                case "WTKP":
                                    zipDto.setType(3);
                                    zipDto.setOrder(7);
                                    break;

                                default:
                                    zipDto.setType(4);
                                    zipDto.setOrder(11);
                            }
                        }
                        zipDto.setVersiontype(filenamepath[1]);
                        String[] temps = UUID.randomUUID().toString().split("-");
                        StringBuffer uuidsb = new StringBuffer();
                        for (String temp : temps) {
                            uuidsb.append(temp);
                        }
                        zipDto.setUuid(uuidsb.toString());
                        zipDto.setOs(filenamepath[2]);
                        zipDto.setVersionchar(filenamepath[3]);
                        zipDto.setVersion(Integer.valueOf(filenamepath[3].replaceAll("\\.", "")));
                        String time = filenamepath[4].split("\\.")[0];
                        zipDto.setZipsize(Formatter.formatFileSize(file.getSize()));
                        zipDto.setUpdateloadtime(Integer.valueOf(time));
                        zipDto.setDownloadtime(0);
                        String basepath = basefilepath + filenamepath[0];
                        zipDto.setPath(filenamepath[0]);
                        File temp = new File(basefilepath + "temp/");
                        if (!temp.exists()) {
                            temp.mkdirs();
                        }
                        File file2 = new File(basefilepath + "temp/" + file.getOriginalFilename());
                        FileUtils.copyInputStreamToFile(file.getInputStream(), file2);
                        zipDto.setMd5code(MD5Util.getFileChecksumMD5(file2));
                        file2.delete();
                        FileUtils.deleteDirectory(temp);
                        long result = 0;
                        if (zipDto.getMd5code() != null) {
                            FindZipDto findZipDto = new FindZipDto();
                            findZipDto.setZipname(zipDto.getZipname());
                            List<ZipPoJo> zipPoJos = zipmapper.findZipByName(findZipDto);
                            if (zipPoJos.isEmpty()) {
                                zipDto.setNowuploadtime(simpleDateFormat2.format(System.currentTimeMillis()));
                                result = zipmapper.addZip(zipDto);
                            } else {
                                zipDto.setUuid(zipPoJos.get(0).getUuid());
                                zipDto.setNowuploadtime(simpleDateFormat2.format(System.currentTimeMillis()));
                                result = zipmapper.updateZip(zipDto);
                            }

                        }
                        //保存数据库

                        if (result >= 0) {
                            //保存文件
                            //保存文件的根地址

                            log.info(basepath);
                            //创建文件对象
                            File dir = new File(basefilepath + filenamepath[0]);
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

                    } else {
                        return MyResponseBody.failure(ResponseEnumCode.DATA_IS_WRONG);
                    }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return MyResponseBody.failure(ResponseEnumCode.DATA_IS_WRONG);

        }
    }

    @Override
    public MyResponseBody findbeastnewrtmapp(FindZipDto findZipDto) {
        log.info(findZipDto.toString());
        List<ZipPoJo> list=zipmapper.findbeastnewrtmapp(findZipDto);
        log.info(list.toString());
        if(list.isEmpty()){
            return MyResponseBody.success(ResponseEnumCode.RESULE_DATA_NONE);
        }else{
            return MyResponseBody.success(list);
        }

    }

    @Override
    public MyResponseBody findbeastnewupdatedapp(FindZipDto findZipDto) {
        log.info(findZipDto.toString());
        List<ZipPoJo> list=zipmapper.findbeastnewupdatedapp(findZipDto);
        log.info(list.toString());
        if(list.isEmpty()){
            return MyResponseBody.success(ResponseEnumCode.RESULE_DATA_NONE);
        }else{
            return MyResponseBody.success(list);
        }

    }

    @Override
    public MyResponseBody getPaths() {
        log.info("获取分组");
        List<String> list=zipmapper.getPaths();
        log.info(list.toString());
        if(list.isEmpty()){
            return MyResponseBody.success(ResponseEnumCode.RESULE_DATA_NONE);
        }else{
            return MyResponseBody.success(list,Long.valueOf(list.size()));
        }
    }

    @Override
    public MyResponseBody getVersions(FindZipDto findZipDto) {
        if(findZipDto.getVersionchar()==null) {
            if (findZipDto.getSearchvalue() != null){
                switch (findZipDto.getSearchvalue()) {

                    case "扫描仪": {
                        findZipDto.setType(2);
                        findZipDto.setSearchvalue("");
                    }
                    break;
                    case "票箱": {
                        findZipDto.setType(1);
                        findZipDto.setSearchvalue("");
                    }
                    break;
                    case "后台": {
                        findZipDto.setType(3);
                        findZipDto.setSearchvalue("KST");
                    }
                    break;
                    case "废票处理": {
                        findZipDto.setType(3);
                        findZipDto.setSearchvalue("KSTLX");
                    }
                    ;
                    break;
                    default: {
                        findZipDto.setType(null);
                        findZipDto.setSearchvalue(findZipDto.getSearchvalue());
                    }
                }
            }
            Function<FindZipDto, Object> selectfun = zips -> zipmapper.getVersionsforZip(findZipDto);
            Function<FindZipDto, Object> countfun = zipcounts -> zipmapper.getcount(findZipDto);
            try {
                Long count = 0L;

                Object o = myThreadDoSql.dosql(findZipDto, selectfun);
                Object c = myThreadDoSql.dosql(findZipDto, countfun);
                if (c instanceof Long) {
                    count = (long) c;
                }
                return MyResponseBody.success(o, count);
            } catch (Exception e) {
                e.printStackTrace();
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }
        }else{
            if (findZipDto.getSearchvalue() != null) {
                switch (findZipDto.getSearchvalue()) {

                    case "扫描仪": {
                        findZipDto.setType(2);
                        findZipDto.setSearchvalue("");
                    }
                    break;
                    case "票箱": {
                        findZipDto.setType(1);
                        findZipDto.setSearchvalue("");
                    }
                    break;
                    case "后台": {
                        findZipDto.setType(3);
                        findZipDto.setSearchvalue("KST");
                    }
                    break;
                    case "另选检查": {
                        findZipDto.setType(3);
                        findZipDto.setSearchvalue("KSTLX");
                    }
                    ;
                    break;
                    default: {
                        findZipDto.setType(null);
                        findZipDto.setSearchvalue(findZipDto.getSearchvalue());
                    }
                }
            }
            Function<FindZipDto, Object> selectfun = zips -> zipmapper.getVersionsforZip(findZipDto);
            try {
                Long count = 0L;

                Object o = myThreadDoSql.dosql(findZipDto, selectfun);
                if (o instanceof List) {
                    count = (long) ((List<?>) o).size();
                }
                return MyResponseBody.success(o, count);
            } catch (Exception e) {
                e.printStackTrace();
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }

        }
    }

    //获取压缩包有哪些系统类型

    public MyResponseBody getOs(FindZipDto findZipDto){
        List<String> list=zipmapper.getOs(findZipDto);
        log.info(list.toString());
        long count=list.size();
        return MyResponseBody.success(list,count);
    }

    @Override
    public MyResponseBody getMaxVersions(FindZipDto findZipDto) {
        String maxVersions=zipmapper.getMaxVersions(findZipDto);
        long count=1L;
        return MyResponseBody.success(maxVersions,count);
    }

}
