package com.oraclejava.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oraclejava.project.kakao.OAuthToken;

public class KakaoController {

	// 로그를 남겨줌
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	  @GetMapping("/auth/login/kakao") 
	  public @ResponseBody ResponseEntity<String> kakaoCallback(String code) {
		
		// POST방식으로 key-value 데이터를 요청(카카오쪽으로)
		RestTemplate rt = new RestTemplate(); //http 요청을 간단하게 해줄 수 있는 클래스
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); 
		
		// Parameter들을 사용해 POST로 보낼 수 있도록 Body 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	       params.add("grant_type","authorization_code");
	       params.add("client_id","4b695ade0d1816dab5bb094bc1f29fda");
	       params.add("redirect_uri","http://localhost:7080/auth/login/kakao");
	       params.add("code", code);
	       params.add("client_secret", "Sg7lMvciqonPk9igJWxbiBiC9ookir3E");
	       
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
		    new HttpEntity<>(params, headers);
		
		
		//실제로 요청하기
		//Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
		ResponseEntity<String> response = rt.exchange(
		        "https://kauth.kakao.com/oauth/token",
		        HttpMethod.POST,
		        kakaoTokenRequest,
		        String.class
		);
		
		//Gson Library, JSON SIMPLE LIBRARY, OBJECT MAPPER(Check)
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        //Model과 다르게 되있으면 그리고 getter setter가 없으면 오류가 날 것이다.
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		
        
		return response;
		
	  }
	  
}
