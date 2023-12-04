package com.example.springbasic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbasicApplicationTests {

	@Test
	void contextLoads() {

		// 수동 빈 등록 vs 자동 빈 등록
		// 이름이 중복되는 경우 BeanDefinitionOverrideException 예외 발생
		// 의도적으로 수동이 우선권을 가지도록 하려면 설정값을 세팅해야한다.
		// >> spring.main.allow-bean-definition-overriding=true

	}

}
