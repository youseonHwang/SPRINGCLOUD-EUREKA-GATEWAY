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

// pring Cloud Gateway �� �߻�ȭ�� ���� gatewayFilterFactory
@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

	public CustomAuthFilter() {
		super(Config.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public GatewayFilter apply(Config config) {
		// ù ��° �Ű����� exchange�� ServerWebExchange ���°� 
		// exchange �� Request�� �޾ƿ��� Pre Filter�� ����ǰ� Post Filter�� Response�� �޾ƿ��� �ȴ�.
		
		// �� ��° �Ķ���Ͱ� GatewayFilterChain ���� �Լ�
		
		return ((exchange,chain)->{
			ServerHttpRequest request = exchange.getRequest();

            // Request Header �� token �� �������� ���� ��
            if(!request.getHeaders().containsKey("token")){
                return handleUnAuthorized(exchange); // 401 Error
            }

            // Request Header ���� token ���ڿ� �޾ƿ���
            List<String> token = request.getHeaders().get("token");
            String tokenString = Objects.requireNonNull(token).get(0);

            // ��ū ����
            if(!tokenString.equals("A.B.C")) {
                return handleUnAuthorized(exchange); // ��ū�� ��ġ���� ���� ��
            }

            return chain.filter(exchange); // ��ū�� ��ġ�� ��
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
