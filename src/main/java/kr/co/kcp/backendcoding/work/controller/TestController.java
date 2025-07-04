package kr.co.kcp.backendcoding.work.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/error")
	public String throwError() {
		throw new RuntimeException("강제 에러");
	}
}