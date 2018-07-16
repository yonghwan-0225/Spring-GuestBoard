package kr.kim.springboard.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.kim.springboard.dao.GuestBookDao;
import kr.kim.springboard.dao.LogDao;
import kr.kim.springboard.dto.GuestBook;
import kr.kim.springboard.dto.Log;
import kr.kim.springboard.service.GuestbookService;

@Service
public class GuestbookServicempl implements GuestbookService {
	@Autowired
	GuestBookDao guestbookDao;
	
	@Autowired
	LogDao logDao;
	
	@Override
	@Transactional //읽기만 하는 method : Readonly connection
	public List<GuestBook> getGuestbooks(Integer start){
		List<GuestBook> list = guestbookDao.selectAll(start, GuestbookService.LIMIT);
		return list;
	}

	@Override
	@Transactional(readOnly=false)
	public int deleteGuestbook(Long id, String ip) {
		int deleteCount = guestbookDao.deleteById(id);
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("delete");
		log.setRegdate(new Date());
		logDao.insert(log);
		return deleteCount;
	}

	@Override
	@Transactional(readOnly=false)
	public GuestBook addGuestbook(GuestBook guestbook, String ip) {
		guestbook.setRegdate(new Date());
		Long id = guestbookDao.insert(guestbook);
		guestbook.setId(id);
		
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("insert");
		log.setRegdate(new Date());
		logDao.insert(log);
		
		return guestbook;
	}

	@Override
	public int getCount() {
		return guestbookDao.selectCount();
	}

}
