package com.example.springbasic.filter;

import java.lang.annotation.*;

// 컴포넌트 스캔 대상에 제외하는 커스텀 애노테이션
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {

}
