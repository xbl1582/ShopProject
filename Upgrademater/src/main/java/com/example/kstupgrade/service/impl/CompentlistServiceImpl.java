package com.example.kstupgrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kstupgrade.com.obj.CompentlistPoJo;
import com.example.kstupgrade.com.obj.DriverPoJo;
import com.example.kstupgrade.dao.compentlist.CompentlistMapper;
import com.example.kstupgrade.dao.driver.DriverMapper;
import com.example.kstupgrade.service.CompentlistService;
import com.example.kstupgrade.service.DriverService;
import org.springframework.stereotype.Service;

@Service
public class CompentlistServiceImpl extends ServiceImpl<CompentlistMapper, CompentlistPoJo> implements CompentlistService {
}
