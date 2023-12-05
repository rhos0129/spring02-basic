package com.example.springbasic.order;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.discount.FixDiscountPolicy;
import com.example.springbasic.member.Member;
import com.example.springbasic.member.MemberRepository;
import com.example.springbasic.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component("service")
@Component
public class OrderServiceImpl implements OrderService{

    // 생성자 주입 ==========
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("생성자 주입의 memberRepository = " + memberRepository);
        System.out.println("생성자 주입의 discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


//    // 수정자 주입 ==========
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository){
//        System.out.println("setter 주입의 memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy){
//        System.out.println("setter 주입의 discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }


//    // 필드 주입 ==========
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;


//    // 일반 메서드 주입 ==========
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }


    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

}
