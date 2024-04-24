package com.example.kstupgrade.com.obj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@TableName("compentlist")
public class CompentlistPoJo {
    private String uuid;
    private String title;
    private Integer type;
    private String urlpath;
    private String desc;
    private String typechar;
}
