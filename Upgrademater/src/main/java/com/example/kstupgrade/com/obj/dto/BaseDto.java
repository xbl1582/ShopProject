package com.example.kstupgrade.com.obj.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class BaseDto implements Serializable {
    private String searchvalue;

    private Integer page;
    private Integer pagesize;
}
