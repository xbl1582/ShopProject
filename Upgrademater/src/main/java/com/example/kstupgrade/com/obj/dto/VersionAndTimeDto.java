package com.example.kstupgrade.com.obj.dto;

import com.example.kstupgrade.com.obj.ZipPoJo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VersionAndTimeDto  extends  BaseDto{
    private  String versionchar;
    private  Integer updateloadtime;
    private List<ZipPoJo> zippojos=new ArrayList<>();

}
