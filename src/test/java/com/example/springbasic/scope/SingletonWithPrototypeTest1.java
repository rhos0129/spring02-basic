package com.example.springbasic.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        System.out.println("clientBean1 = " + clientBean1.getClass());
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        System.out.println("clientBean2 = " + clientBean2.getClass());
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1); // 원하는대로 1 (문제해결)

    }

    @Scope("singleton")
    static class ClientBean {

//        // 해결방안2 ObjectFactory<>, ObjectProvider<>의 DL기능 이용 (권장)
//        // ObjectFactory : 기능이 단순, 별도의 라이브러리 필요 없음, 스프링에 의존
//        // ObjectProvider : ObjectFactory 상속받아 편의 기능이 많음, 별도의 라이브러리 필요 없음, 스프링에 의존
//        @Autowired
////        private ObjectFactory<PrototypeBean> prototypeBeanProvider;
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        // 해결방안3 Provider<> 이용
        // Provider : 별도의 라이브러리가 필요. 자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용 가능
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic(){
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0; // **

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy " + this);
        }
    }
}