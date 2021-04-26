package com.bitlegion.server;

import javax.annotation.Resource;

import com.bitlegion.server.uploads.FilesStorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		// storageService.deleteAll();
		storageService.init();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				String url = env.getProperty("cors.urls");
				registry.addMapping("/uploads").allowedOrigins(url);
				registry.addMapping("/accounts").allowedOrigins(url);
			}
		};
	}
}
