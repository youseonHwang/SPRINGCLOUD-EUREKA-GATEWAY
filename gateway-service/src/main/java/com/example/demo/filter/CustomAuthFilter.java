package com.example.demo.filter;


import java.util.List;
import java.util.Objects;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

// pring Cloud Gateway 가 추상화해 놓은 gatewayFilterFactory
@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

	public CustomAuthFilter() {
		super(Config.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public GatewayFilter apply(Config config) {
		// 첫 번째 매개변수 exchange는 ServerWebExchange 형태고 
		// exchange 의 Request를 받아오면 Pre Filter로 적용되고 Post Filter는 Response로 받아오며 된다.
		
		// 두 번째 파라미터가 GatewayFilterChain 람다 함수
		
		return ((exchange,chain)->{
			ServerHttpRequest request = exchange.getRequest();

            // Request Header 에 token 이 존재하지 않을 때
            if(!request.getHeaders().containsKey("token")){
                return handleUnAuthorized(exchange); // 401 Error
            }

            // Request Header 에서 token 문자열 받아오기
            List<String> token = request.getHeaders().get("token");
            String tokenString = Objects.requireNonNull(token).get(0);

            // 토큰 검증
            if(!tokenString.equals("A.B.C")) {
                return handleUnAuthorized(exchange); // 토큰이 일치하지 않을 때
            }

            return chain.filter(exchange); // 토큰이 일치할 때
		});
    }
	
	private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
	    ServerHttpResponse response = exchange.getResponse();

	    response.setStatusCode(HttpStatus.UNAUTHORIZED);
	    return response.setComplete();
	}


    public static class Config {

    }
	
	
}
