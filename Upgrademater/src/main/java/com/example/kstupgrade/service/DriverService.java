package com.example.kstupgrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kstupgrade.com.obj.DriverPoJo;
import com.example.kstupgrade.com.obj.ZipPoJo;
import com.example.kstupgrade.com.obj.dto.*;
import com.example.kstupgrade.com.util.MyResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DriverService   extends IService<DriverPoJo> {
    MyResponseBody deleteDriver(String uuid);

    MyResponseBody saveFile(MultipartFile file);

    MyResponseBody updateDri(DriverDto driverDto);

    List<DriverPoJo> findByName(FindDriverDto findDriverDto);

    long updateDriver(DriverDto driverDto);

    MyResponseBody findDriversList(FindDriverDto findDriverDto);

    MyResponseBody setMaxPriority(DriverDto driverDto);

    MyResponseBody getMaxPriority();
}
