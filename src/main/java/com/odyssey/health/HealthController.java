package com.odyssey.health;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odyssey.global.response.ApiResult;

@RestController
public class HealthController {

	@GetMapping("/health")
	public ApiResult<Map<String, String>> health() {
		return ApiResult.ok(Map.of("Status", "Ok"));
	}
}
