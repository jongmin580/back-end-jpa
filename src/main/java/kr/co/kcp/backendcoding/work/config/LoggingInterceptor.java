package kr.co.kcp.backendcoding.work.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		String ip = request.getRemoteAddr();
		String method = request.getMethod();
		String uri = request.getRequestURI();

		// Header 수집
		Map<String, String> headers = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			headers.put(name, request.getHeader(name));
		}

		// 파라미터 수집
		Map<String, String[]> paramMap = request.getParameterMap();

		log.info("Request: Remote IP: {}, Headers: {}, Method: {}, URI: {}, Parameter: {}",
				ip,
				objectMapper.writeValueAsString(headers),
				method,
				uri,
				objectMapper.writeValueAsString(paramMap)
		);

		return true;
	}
}