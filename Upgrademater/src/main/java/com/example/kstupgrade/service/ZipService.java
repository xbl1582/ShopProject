package com.example.kstupgrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kstupgrade.com.obj.ZipPoJo;
import com.example.kstupgrade.com.obj.dto.FindZipDto;
import com.example.kstupgrade.com.obj.dto.ZipDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ZipService extends IService<ZipPoJo> {
    //添加
    MyResponseBody addZip(ZipDto zipDto);

    //删除
    MyResponseBody deleteZip(String uuid);

    MyResponseBody updateZip(ZipDto zipDto);

    //查询
    //查询最新版本根据类型
    MyResponseBody  findZips(FindZipDto findZipDto);

    //查询最新版本
    MyResponseBody  findbastNewZips(FindZipDto findZipDto);

    MyResponseBody  findNewZipsByPath(FindZipDto findZipDto);

   List<ZipPoJo> findByName(String name);
    //下载
    void downloadZipList();

    //上传
    void uploadZips();

    MyResponseBody saveFile(MultipartFile file);


    MyResponseBody findbeastnewrtmapp(FindZipDto findZipDto);

    MyResponseBody findbeastnewupdatedapp(FindZipDto findZipDto);

    MyResponseBody getPaths();

    MyResponseBody getVersions(FindZipDto findZipDto);

    MyResponseBody getOs(FindZipDto  findZipDto);

    MyResponseBody getMaxVersions(FindZipDto findZipDto);
}
