package com.example.springbasic.order;

import com.example.springbasic.discount.FixDiscountPolicy;
import com.example.springbasic.member.Grade;
import com.example.springbasic.member.Member;
import com.example.springbasic.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceImplTest {

    @Test
    void createOrder(){
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

//        // 생성자 주입 >> 컴파일 오류
//        // 수정자 주입 >> 실행시 NPE 발생
//        OrderServiceImpl orderService = new OrderServiceImpl();

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}
