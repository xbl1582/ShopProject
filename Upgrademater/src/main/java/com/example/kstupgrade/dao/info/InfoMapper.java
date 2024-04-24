package com.example.kstupgrade.dao.info;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kstupgrade.com.obj.InfoPoJo;
import com.example.kstupgrade.com.obj.dto.FindInfoDto;
import com.example.kstupgrade.com.obj.dto.InfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InfoMapper extends BaseMapper<InfoPoJo> {
    List<InfoPoJo> findInfoByName(FindInfoDto findInfodto);
    List<InfoPoJo> findInfos(FindInfoDto findInfodto);
    long addInfos(InfoDto infoDto);

    long updateInfo(InfoDto infoDto);

    long deleteInfo(String uuid);
    long getcount(FindInfoDto findInfodto);

    InfoPoJo findByPath(FindInfoDto findInfoDto);
    InfoPoJo findByuuid(String uuid);

    int getMaxPriority();
    long  updateini();
}
