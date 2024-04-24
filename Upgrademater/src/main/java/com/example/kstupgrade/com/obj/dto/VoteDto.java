package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class VoteDto  extends BaseDto{
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
