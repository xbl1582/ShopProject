package com.example.kstupgrade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.File;

@SpringBootApplication
@MapperScan("com.example.kstupgrade.dao.**.*")
@CrossOrigin("*")
public class KstUpgradeApplication {

	public static void main(String[] args) {

		SpringApplication.run(KstUpgradeApplication.class, args);
	}

}
