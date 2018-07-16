package kr.kim.springboard.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.kim.springboard.argumentresolver.HeaderInfo;
import kr.kim.springboard.dto.GuestBook;
import kr.kim.springboard.service.GuestbookService;

@Controller
public class GuestBookController {
	@Autowired
	GuestbookService guestbookservice;

	@GetMapping(path = "/list")
	public String list(@RequestParam(name = "start", required = false, defaultValue = "0") int start, ModelMap model,
			@CookieValue(value = "count", defaultValue = "0", required = true) String value,
			HttpServletResponse response, HeaderInfo headerInfo) {
		
		
		System.out.println("-----------------------------------------------------");
		System.out.println(headerInfo.get("user-agent"));
		System.out.println("-----------------------------------------------------");
		

		try { // 쿠키가 존재한다면?
			int i = Integer.parseInt(value);
			value = Integer.toString(++i);
		} catch (Exception ex) {
			value = "1";
		}

		Cookie cookie = new Cookie("count", value);
		cookie.setMaxAge(60 * 60 * 24 * 365); // 1년유지, 각 초단위
		cookie.setPath("/");
		response.addCookie(cookie);

		// start 로 시작하는 방명록 목록 구하기
		List<GuestBook> list = guestbookservice.getGuestbooks(start);

		int count = guestbookservice.getCount();
		int pageCount = count / GuestbookService.LIMIT;
		if (count % GuestbookService.LIMIT > 0)
			pageCount++;

		// 페이지의 수만큼 start의 값을 리스트로 저장
		// 예를들면 페이지수가 3이면
		// 0, 5, 10 이렇게 저장
		// list?start=0, list?start=5, list?start=10 으로 링크가 걸림
		List<Integer> pageStartList = new ArrayList<>();
		for (int i = 0; i < pageCount; i++) {
			pageStartList.add(i * GuestbookService.LIMIT);
		}

		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("cookieCount", value);
		return "list";

	}

	@PostMapping(path = "/write")
	public String write(@ModelAttribute GuestBook guestbook, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		System.out.println("clientIp : " + clientIp);
		guestbookservice.addGuestbook(guestbook, clientIp);
		return "redirect:list";
	}

	@GetMapping(path = "/delete")
	public String delete(@RequestParam(name = "id", required = true) Long id,
			@SessionAttribute("isAdmin") String isAdmin, HttpServletRequest request, RedirectAttributes redirectAttr) {
		if (isAdmin == null || !"true".equals(isAdmin)) {
			redirectAttr.addFlashAttribute("errorMessage", "로그인을 하지 않았습니다.");
			return "redirect:loginform";

		}
		String clientIp = request.getRemoteAddr();
		guestbookservice.deleteGuestbook(id, clientIp);
		return "redirect:list";
	}

	@GetMapping(path = "/logout")
	public String login(HttpSession session) {
		session.removeAttribute("isAdmin");
		return "redirect:/list";
	}

}