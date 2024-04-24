package com.example.kstupgrade.dao.pathclass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kstupgrade.com.obj.PathClassPoJo;
import com.example.kstupgrade.com.obj.dto.FindZipDto;
import com.example.kstupgrade.com.obj.dto.PathClassDto;
import com.example.kstupgrade.com.obj.dto.VersionAndTimeDto;
import com.example.kstupgrade.com.obj.dto.ZipDto;
import com.example.kstupgrade.com.obj.ZipPoJo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PathClassMapper  extends BaseMapper<PathClassPoJo>{
    long addPathclass(PathClassDto pathClassdto);
   long updatedPathclass(PathClassDto pathClassDto);

    List<PathClassPoJo> selectAllPathClass();

    PathClassPoJo setectPathclassByPath(String path);
}
