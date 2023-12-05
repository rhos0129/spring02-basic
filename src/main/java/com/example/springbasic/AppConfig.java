package com.example.springbasic;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.discount.FixDiscountPolicy;
import com.example.springbasic.discount.RateDiscountPolicy;
import com.example.springbasic.member.MemberService;
import com.example.springbasic.member.MemberServiceImpl;
import com.example.springbasic.member.MemoryMemberRepository;
import com.example.springbasic.order.OrderService;
import com.example.springbasic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 공연기획자 AppConfig
// 애플리케이션의 실제 동작에 필요한 구현 객체를 생성하고
// 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입
@Configuration
public class AppConfig {

    // 각각 다른 2개의 MemoryMemberRepository가 생성되면서 싱글톤이 깨지는 것처럼 보인다.
    // @Bean memberService > new MemoryMemberRepository()
    // @Bean orderService > new MemoryMemberRepository()

    // 예상 호출 3번
    // call AppConfig.memberRepository *
    // call AppConfig.memberService
    // call AppConfig.memberRepository *
    // call AppConfig.orderService
    // call AppConfig.memberRepository *

    // 실제 호출 1번 > 싱글톤 보장
    // call AppConfig.memberRepository *
    // call AppConfig.memberService
    // call AppConfig.orderService

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy(); // 사용영역의 코드 변경 없이 여기 한줄만 수정하면 된다/
    }

}
