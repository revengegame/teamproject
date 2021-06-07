package com.oraclejava.project;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.oraclejava.project.controller.HomeController;
import com.oraclejava.project.dao.ShoppingUserRepository;
import com.oraclejava.project.dto.ShoppingUser;

@Component
public class MailSend{

	@Autowired ShoppingUserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	// 로그를 남겨줌
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
		
	
	// 회원가입 인증 메일
	public String sendMail(String email) throws MessagingException, IOException{
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		
		/* 인증번호(난수) 생성 */
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        logger.info("인증번호 " + checkNum);
		
		/* 이메일 보내기 */
    	String setFrom = "jeonej1728@gmail.com";
        String toMail = email;
        String title = "회원가입 인증 이메일 입니다.";
        String content = Integer.toString(checkNum);
		
		// 메일 제목 설정
		helper.setSubject(title);
		
		// 발신자 설정
		helper.setFrom(setFrom);
		
		// 수신자 설정
		helper.setTo(toMail);
				
		// 템플릿에 전달한 데이터 설정
		Context context = new Context();
		context.setVariable("mailContnet", content);
		
		// 메일 내용 설정 : 템플릿 프로세스
		String html = templateEngine.process("mailNumCheck-template.html", context);
		helper.setText(html, true);
		
		// 메일 보내기
		javaMailSender.send(message);
		
		return content;
	}
	
	// 회원가입 인증 메일
	@Transactional 
	public void sendPwMail(ShoppingUser user) throws Exception {
		
        // 비밀번호 변경
        	MimeMessage message = javaMailSender.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
    		
    		/* 이메일 보내기 */
            String setFrom = "jeonej1728@gmail.com";
            String toMail = user.getUser_email();
            String title = "임시 비밀번호 입니다.";
            String pwNum = getRamdomPassword(8);
            String username = user.getUser_id();
                        
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    		String password = passwordEncoder.encode(pwNum);
            
			userRepository.changePwd(password, username);
			
			// 메일 제목 설정
			helper.setSubject(title);
			
			// 발신자 설정
			helper.setFrom(setFrom);
			
			// 수신자 설정
			helper.setTo(toMail);
			
			// 템플릿에 전달한 데이터 설정
			Context context = new Context();
			context.setVariable("pwNum", pwNum);
			context.setVariable("userId", username);
			
			// 메일 내용 설정 : 템플릿 프로세스
			String html = templateEngine.process("mailPWCheck-template.html", context);
			helper.setText(html, true);
			

			// 메일 보내기
			javaMailSender.send(message);
			
	}
	
	// 임시 비밀번호 생성
	public String getRamdomPassword(int size) { 
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
									'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
									'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
									'!', '@', '#', '$', '%', '^', '&' }; 
		StringBuffer sb = new StringBuffer(); 
		SecureRandom sr = new SecureRandom(); 
		sr.setSeed(new Date().getTime()); 
		int idx = 0; 
		int len = charSet.length; 
		for (int i=0; i<size; i++) { 
			// idx = (int) (len * Math.random()); 
			idx = sr.nextInt(len); 
			// 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다. 
			sb.append(charSet[idx]); 
		} return sb.toString(); 
	}
			
}
