package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

@Data
public class FindVoteDto  extends  BaseDto{
    private  String uuid;
    private  String votename;
    private  String     type;
    private  String     path;
    private  String     downloadtime;
    private  String     updatetime;
}
