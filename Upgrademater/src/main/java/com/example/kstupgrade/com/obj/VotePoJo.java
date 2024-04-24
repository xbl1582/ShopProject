package com.example.kstupgrade.com.obj;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@TableName("votes")
@Slf4j
public class VotePoJo {
    @TableId
    private Integer id;
    private  String uuid;
    private  String votename;
    private  String     type;
    private  String     path;
    private  Integer     downloadtime;
    private  String     updatetime;
    private  String votesize;


    private  Integer priority;



}
