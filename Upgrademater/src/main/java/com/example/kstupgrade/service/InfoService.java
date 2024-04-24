package com.example.kstupgrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kstupgrade.com.obj.DriverPoJo;
import com.example.kstupgrade.com.obj.InfoPoJo;
import com.example.kstupgrade.com.obj.dto.FindInfoDto;
import com.example.kstupgrade.com.obj.dto.InfoDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InfoService  extends IService<InfoPoJo> {
    MyResponseBody deleteInfo(String uuid);

    MyResponseBody saveFile(MultipartFile file);

    List<InfoPoJo> findByName(FindInfoDto findInfoDto);

    long updateInfo(InfoDto infoDto);

    MyResponseBody findInfos(FindInfoDto findInfoDto);
    MyResponseBody setMaxPriority(InfoDto infoDto);

    MyResponseBody getMaxPriority();
}
