package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class ZipDto extends BaseDto{
    private String uuid;//主键
    private  String zipname;//压缩包名词
    private Integer updateloadtime;// 上传时间
    private String zipsize;//压缩包大小
    private Integer type;//终端类型
    private String  os;//系统
    private  String versionchar;//版本
    private String versiontype;//版本类型
    private  int version;//版本
    private  String path;
    private String md5code;//md5校验
    private  Integer downloadtime;
    private  Integer order;
    private  String nowuploadtime;//上传时间
}
