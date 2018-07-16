package kr.kim.springboard.dao;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.kim.springboard.config.ApplicationConfig;
import kr.kim.springboard.dto.Log;

public class GusetBookDaoTest {

	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		GuestBookDao guestbookdao = ac.getBean(GuestBookDao.class);
		
//		GuestBook guestbook = new GuestBook();
//		
//		guestbook.setName("김용환");
//		guestbook.setContent("무슨내용을 넣어볼까");
//		guestbook.setRegdate(new Date());
//		Long id = guestbookdao.insert(guestbook);
//		System.out.println("id :" +id );

		LogDao logdao = ac.getBean(LogDao.class);
		Log log = new Log();
		log.setIp("127.0.0.1");
		log.setMethod("insert");
		log.setRegdate(new Date());
		logdao.insert(log);
		
	}

}
