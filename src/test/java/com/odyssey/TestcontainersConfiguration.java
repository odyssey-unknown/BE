package com.odyssey;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * 테스트가 실행될 때 postgres 컨테이너를 직접 띄운다.
 *
 * <p>{@code bootRun} 은 compose.yaml 을 쓰지만, spring-boot-docker-compose 가
 * {@code developmentOnly} 라 테스트 클래스패스에는 없다. 그래서 테스트는
 * 자기 컨테이너를 따로 띄운다. 로컬과 CI 에서 동일하게 동작한다.
 *
 * <p>{@link ServiceConnection} 이 컨테이너의 접속 정보(url / username / password)를
 * DataSource 에 자동으로 연결해 준다. 설정 파일에 DB 정보를 적을 필요가 없다.
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	// compose.yaml 과 같은 버전을 써서 로컬 DB 와 동작을 맞춘다
	@Bean
	@ServiceConnection
	public PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>("postgres:17");
	}
}
