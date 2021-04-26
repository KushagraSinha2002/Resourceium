package com.bitlegion.server;

import javax.annotation.Resource;

import com.bitlegion.server.uploads.FilesStorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		// storageService.deleteAll();
		storageService.init();
	}

}
