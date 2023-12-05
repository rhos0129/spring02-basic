package com.example.springbasic.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
//@Scope(value = "request")
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // 해결방안2 프록시 적용
// proxyMode = ScopedProxyMode.TARGET_CLASS
// : 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다.
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("["+uuid+"]"+"["+requestURL+"]"+message);
    }

    @PostConstruct
    public void init(){ // 객체 생성시에 UUID.randomUUID()
        uuid = UUID.randomUUID().toString();
        System.out.println("["+uuid+"] request scope bean create: "+this);
    }

    @PreDestroy // 객체 소멸시에 로그
    public void close(){
        System.out.println("["+uuid+"] request scope bean close: "+this);
    }

}