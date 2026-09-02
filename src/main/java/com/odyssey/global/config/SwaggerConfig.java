package com.odyssey.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI odysseyOpenAPI() {
		Info info = new Info();
		info.setTitle("Odyssey API");
		info.setDescription("U+ 유동인구 데이터 조회 API");
		info.setVersion("v0.0.1");

		OpenAPI openAPI = new OpenAPI();
		openAPI.setInfo(info);

		return openAPI;
	}
}
