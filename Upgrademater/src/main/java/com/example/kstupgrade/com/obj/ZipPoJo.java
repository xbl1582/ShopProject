package com.example.kstupgrade.com.obj;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@TableName("zips")
@Slf4j
public class ZipPoJo  implements Serializable {
    @TableId
    private  Integer id;
    private String uuid;//主键
    private  String zipname;//压缩包名词
    private  Integer updateloadtime;// 上传时间
    private String zipsize;//压缩包大小
    private Integer type;//终端类型
    private String  os;//系统
    private String versiontype;//版本类型
    private Integer version;//版本
    private  String versionchar;//版本
    private  String path;//存储路径
    private String md5code;//md5校验
    private Integer downloadtime;//下载次数
    private Integer order;//优先级
    private  String nowuploadtime;//上传时间



}
