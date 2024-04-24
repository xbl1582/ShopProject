package com.example.kstupgrade.com.obj;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
@TableName("pathclass")
public class PathClassPoJo  implements Serializable {
    @TableId
    private Integer id;
    private String uuid;
    private  String path;
    private  String pathname;
    private  Integer type;




}
