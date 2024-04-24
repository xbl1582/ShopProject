package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class FindZipDto extends BaseDto{
    private String uuid;//主键
    private  String zipname;//压缩包名词
    private String updateloadtime;// 上传时间
    private String zipsize;//压缩包大小
    private Integer type;//终端类型
    private String  os;//系统
    private String versiontype;//版本类型
    private  Integer version;//版本
    private  String versionchar;//版本
    private String md5code;//md5校
    private String path;
    private  String nowuploadtime;//上传时间
//    private String kstupdatetime;;
//
//    private String kstlxupdatetime;
//
//    private String kstmainqupdatetime;
//
//    private String kstmainxupdatetime;
//
//    private String kstpxqupdatetime;
//
//    private String kstpxxupdatetime;
//
//    private String kstscanupdatetime;
//
//    private String  wtkpupdatetime;




}
