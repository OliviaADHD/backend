package com.adhd.Olivia;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adhd.Olivia.services.FilesStorageService;

@SpringBootApplication
public class AdhaBackendApplication implements CommandLineRunner {

	  @Resource
	  FilesStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(AdhaBackendApplication.class, args);
	}

	  @Override
	  public void run(String... arg) throws Exception {
	    storageService.deleteAll();
	    storageService.init();
	  }
}
