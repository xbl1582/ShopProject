package com.example.kstupgrade.dao.driver;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kstupgrade.com.obj.DriverPoJo;
import com.example.kstupgrade.com.obj.dto.DriverDto;
import com.example.kstupgrade.com.obj.dto.FindDriverDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DriverMapper extends BaseMapper<DriverPoJo> {
    DriverPoJo findByuuid(String uuid);

    List<DriverPoJo> findDriver(FindDriverDto findDriverdto);

    long deleteDriver(String uuid);

    long updateDriver(DriverDto driverDto);

    List<DriverPoJo> findDriverByName(FindDriverDto findDriverdto);

    long addDriver(DriverDto driverDto);
    Long getcount(FindDriverDto findDriverdto);

    long updateini();

    int getMaxPriority();
}
