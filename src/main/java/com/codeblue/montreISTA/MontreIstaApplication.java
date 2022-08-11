package com.codeblue.montreISTA;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class MontreIstaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MontreIstaApplication.class, args);

	}
	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloud_name", "zainalabiddin");
		config.put("api_key", "285182391613339");
		config.put("api_secret", "YBiskt-OauutQJ2jOfMCWxbyWK0");
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}

}
