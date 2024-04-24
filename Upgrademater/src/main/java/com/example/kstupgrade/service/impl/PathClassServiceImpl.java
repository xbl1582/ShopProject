package com.example.kstupgrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kstupgrade.com.obj.PathClassPoJo;
import com.example.kstupgrade.com.obj.dto.PathClassDto;
import com.example.kstupgrade.dao.pathclass.PathClassMapper;
import com.example.kstupgrade.service.PathClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathClassServiceImpl extends ServiceImpl<PathClassMapper, PathClassPoJo> implements PathClassService {
    @Autowired
    private  PathClassMapper pathClassMapper;
    @Override
    public long addPathclass(PathClassDto pathClassdto) {
        return  pathClassMapper.addPathclass(pathClassdto);
    }

    @Override
    public long updatedPathclass(PathClassDto pathClassDto) {
        return pathClassMapper.updatedPathclass(pathClassDto);
    }

    @Override
    public List<PathClassPoJo> selectAllPathClass() {
        return   pathClassMapper.selectAllPathClass();
    }

    @Override
    public PathClassPoJo setectPathclassByPath(String path) {
        return pathClassMapper.setectPathclassByPath(path) ;
    }

    @Override
    public PathClassPoJo selectPathClassByPath(PathClassDto pathClassDto) {
        return pathClassMapper.setectPathclassByPath(pathClassDto.getPath());
    }
}
