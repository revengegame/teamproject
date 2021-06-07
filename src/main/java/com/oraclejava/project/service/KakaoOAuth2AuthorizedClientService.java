package com.oraclejava.project.service;

import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.oraclejava.project.dao.KakaoUserRepository;
import com.oraclejava.project.dto.KakaoUser;
import com.oraclejava.project.dto.ShoppingUserRole;

public class KakaoOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService{

	@Autowired
	private KakaoUserRepository KakaoUserRepository;
	
	// 로그를 남겨줌
	private static final Logger logger = LoggerFactory.getLogger(KakaoOAuth2AuthorizedClientService.class);
	
	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
			String principalName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
		
		String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        
        HashMap<String, Object> userInfo= (HashMap<String, Object>) oauth2User.getAttributes().get("kakao_account");
        HashMap<String, Object> userInfo2= (HashMap<String, Object>) userInfo.get("profile");     
		
        String username = String.valueOf(oauth2User.getAttributes().get("id"));
        
        // DB에 아이디 있는지
        KakaoUser userChk = KakaoUserRepository.findByUsername(username);
        
        if(userChk == null) {
        	String name = (String) userInfo2.get("nickname");
			String userEmail = (String) userInfo.get("email");
			ShoppingUserRole userRole = new ShoppingUserRole();
			userRole.setRoleName("ROLE_USER");

			logger.info(name);

			KakaoUser user = new KakaoUser();

			user.setUsername(username);
			user.setNickname(name);
			user.setUserEmail(userEmail);
			user.setAccess_token(accessToken.getTokenValue());
			user.setUserRole(Arrays.asList(userRole));

			KakaoUserRepository.save(user);
        }
        
	}

	@Override
	public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
		// TODO Auto-generated method stub
		
	}

	
}
