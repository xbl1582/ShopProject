package com.example.kstupgrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kstupgrade.com.obj.EmpowerPoJo;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.dto.EmpowerDto;
import com.example.kstupgrade.com.obj.dto.FindEmpowerDto;
import com.example.kstupgrade.com.obj.dto.FindVoteDto;
import com.example.kstupgrade.com.obj.dto.VoteDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmpowerService extends IService<EmpowerPoJo> {

    MyResponseBody deleteEmpower(String uuid);

    MyResponseBody saveFile(MultipartFile file);

    List<EmpowerPoJo> findByName(FindEmpowerDto findEmpowerDto);

    MyResponseBody updateEmpower(EmpowerDto empowerDto);

    MyResponseBody findEmpower(FindEmpowerDto findEmpowerDto);

    MyResponseBody getempowerbytxtname(FindEmpowerDto findEmpowerDto);

    MyResponseBody updatedEmpower(EmpowerDto empowerDto);


    MyResponseBody selectlicbyname(String lic);
}
