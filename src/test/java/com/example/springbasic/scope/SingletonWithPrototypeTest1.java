package com.example.springbasic.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
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
        assertThat(count2).isEqualTo(2); // 1이 아닌 2 (문제발생)

        // clientBean이 생성될 때 마다 prototypeBean은 새로 생성되지 않는다. 따라서 count가 유지된다.
        // clientBean이 내부에 가지고 있는 prototypeBean은 생성 시점에 이미 주입받았기 때문이다.

    }

    @Scope("singleton")
    static class ClientBean {

        private final PrototypeBean prototypeBean; // 생성시점에 주입 * 01

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic(){
            prototypeBean.addCount();
            return prototypeBean.count;
        }
    }

    // 해결방안1 동일한 싱글톤 빈을 여러개 생성 (비권장)
    @Scope("singleton")
    static class ClientBean2 {

        private final PrototypeBean prototypeBean; // 생성시점에 주입 * 02

        @Autowired
        public ClientBean2(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic(){
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
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}