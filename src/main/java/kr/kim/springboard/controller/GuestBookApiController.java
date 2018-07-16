package kr.kim.springboard.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.kim.springboard.dto.GuestBook;
import kr.kim.springboard.service.GuestbookService;

@RestController
@RequestMapping(path = "/guestbooks")
public class GuestBookApiController {

	@Autowired
	GuestbookService guestbookservice;

	@GetMapping
	public Map<String, Object> list(@RequestParam(name = "start", required = false, defaultValue = "0") int start) {

		List<GuestBook> list = guestbookservice.getGuestbooks(start);

		int count = guestbookservice.getCount();
		int pageCount = count / GuestbookService.LIMIT;
		if (count % GuestbookService.LIMIT > 0)
			pageCount++;

		List<Integer> pageStartList = new ArrayList<>();
		for (int i = 0; i < pageCount; i++) {
			pageStartList.add(i = GuestbookService.LIMIT);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("pageStartList", pageStartList);

		return map;
	}

	@PostMapping
	public GuestBook write(@RequestBody GuestBook guestbook, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();

		GuestBook resultGuestbook = guestbookservice.addGuestbook(guestbook, clientIp);
		return resultGuestbook;

	}

	@DeleteMapping("/{id}")
	public Map<String, String> delete(@PathVariable(name = "id") Long id, HttpServletRequest request) {

		String clientIp = request.getRemoteAddr();

		int deleteCount = guestbookservice.deleteGuestbook(id, clientIp);
		return Collections.singletonMap("success", deleteCount > 0 ? "true" : "false");

	}

}
