package com.example.kstupgrade.dao.zip;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kstupgrade.com.obj.dto.FindZipDto;
import com.example.kstupgrade.com.obj.dto.VersionAndTimeDto;
import com.example.kstupgrade.com.obj.dto.ZipDto;
import com.example.kstupgrade.com.obj.ZipPoJo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZipMapper extends BaseMapper<ZipPoJo> {
    //添加
    long addZip(ZipDto zipDto);
    long updateZip(ZipDto zipDto);
    //删除
    long deleteZip(String uuid);
    //查询 分页+分组+排序
    List<ZipPoJo> findZips(FindZipDto findZipDto);
    List<ZipPoJo> findZipByName(FindZipDto findZipDto);
    //查询最新版本根据类型
    List<ZipPoJo> findbastNewZips(FindZipDto findZipDto);
    List<ZipPoJo> findNewZipsByPath(FindZipDto findZipDto);
    ZipPoJo findByuuid(String uuid);

    long getcount(FindZipDto findZipDto);


    List<ZipPoJo> findByUpdateloadtimeAndPath(FindZipDto findZipDto);

    List<ZipPoJo> findbeastnewrtmapp(FindZipDto findZipDto);

    List<ZipPoJo> findbeastnewupdatedapp(FindZipDto findZipDto);

    List<String> getPaths();

    List<VersionAndTimeDto> getVersions(FindZipDto findZipDto);
    List<ZipPoJo> getVersionsforZip(FindZipDto findZipDto);

    List<String> getOs(FindZipDto findZipDto);

    String getMaxVersions(FindZipDto findZipDto);
}
