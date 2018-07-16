package kr.kim.springboard.service;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.kim.springboard.config.ApplicationConfig;
import kr.kim.springboard.dto.GuestBook;

public class GuestbookServiceTest {

	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		GuestbookService guestbookService = ac.getBean(GuestbookService.class);
		
		
		GuestBook guestbook = new GuestBook();
		guestbook.setName("김용환");
		guestbook.setContent("방가루");
		guestbook.setRegdate(new Date());
		GuestBook result = guestbookService.addGuestbook(guestbook, "127.0.0.1");
		System.out.println(result);
	}

}
