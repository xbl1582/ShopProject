package com.example.kstupgrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kstupgrade.com.obj.PathClassPoJo;
import com.example.kstupgrade.com.obj.dto.PathClassDto;

import java.util.List;


public interface PathClassService  extends IService<PathClassPoJo> {
    long addPathclass(PathClassDto pathClassdto);

    long updatedPathclass(PathClassDto pathClassDto);


    List<PathClassPoJo> selectAllPathClass();

    PathClassPoJo setectPathclassByPath(String path);


    PathClassPoJo selectPathClassByPath(PathClassDto pathClassDto);
}
