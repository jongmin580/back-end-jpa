package kr.co.kcp.backendcoding.work.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleAllException(Exception ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", 500);
		error.put("message", "서버 오류가 발생했습니다.");
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", 404);
		error.put("message", "요청한 API를 찾을 수 없습니다.");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}