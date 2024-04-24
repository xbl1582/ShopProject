package com.example.kstupgrade.com.obj.dto;


import lombok.Data;

@Data
public class MaxVersionDto {
    private  String path;
    private  Integer version;
    private  String myos;
}
