package kr.kim.springboard.service;

import java.util.List;

import kr.kim.springboard.dto.GuestBook;

public interface GuestbookService {
	public static final Integer LIMIT = 5;
	public List<GuestBook> getGuestbooks(Integer start);
	public int deleteGuestbook(Long id, String ip);
	public GuestBook addGuestbook(GuestBook guestbook, String ip);
	public int getCount();
}
