package com.example.springbasic.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

        // 결과 : 생성자에서도, 서비스 시작시에도 url=null
        // 생성자 호출, url = null
        // connect : null
        // call : null message = 초기화 연결 메시지

        // 인터페이스 InitializingBean, DisposableBean =====
        // @Bean의 destroyMethod, destroyMethod속성 =====
        // @PostConstruct, @PreDestroy =====
        // 결과 : 생성자에서는 url = null, 서비스 시작 및 종료 시에는 url 출력

    }

    @Configuration
    static class LifeCycleConfig{

        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

    }

}