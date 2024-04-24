package com.example.kstupgrade.dao.empower;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kstupgrade.com.obj.EmpowerPoJo;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.dto.EmpowerDto;
import com.example.kstupgrade.com.obj.dto.FindEmpowerDto;
import com.example.kstupgrade.com.obj.dto.FindInfoDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpowerMapper  extends BaseMapper<EmpowerPoJo> {
    List<EmpowerPoJo> findEmpowerBySn(FindEmpowerDto findEmpowerDto);

    long addEmpower(EmpowerDto empowerDto);

    long updateEmpower(EmpowerDto empowerDto);

    EmpowerPoJo findByuuid(String uuid);

    long deleteEmpower(String uuid);

     List<EmpowerPoJo> findEmpower(FindEmpowerDto findEmpowerDto);

    long getcount(FindEmpowerDto findEmpowerDto);

    List<EmpowerPoJo> findByPath(FindEmpowerDto findEmpowerDto);

    List<EmpowerPoJo> getempowerbytxtname(FindEmpowerDto findEmpowerDto);

    List<EmpowerPoJo> selectlicbyname(String lic);
}
