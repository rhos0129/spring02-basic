package com.example.springbasic.scan;

import com.example.springbasic.AutoAppConfig;
import com.example.springbasic.member.MemberRepository;
import com.example.springbasic.member.MemberService;
import com.example.springbasic.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

    @Test
    void basicScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        // 생성자 주입 (권장)
        // 수정자 주입 (가끔사용)
        // 필드 주입 (권장X)
        // 일반 메서드 주입 (거의사용X)
        OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = orderService.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);

    }

}
