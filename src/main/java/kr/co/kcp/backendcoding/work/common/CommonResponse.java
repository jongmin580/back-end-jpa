package kr.co.kcp.backendcoding.work.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
	private int code;
	private String message;
	private T data;

	public static <T> CommonResponse<T> success(T data) {
		return new CommonResponse<>(0, "성공", data);
	}

	public static <T> CommonResponse<T> error(int code, String message) {
		return new CommonResponse<>(code, message, null);
	}
}
