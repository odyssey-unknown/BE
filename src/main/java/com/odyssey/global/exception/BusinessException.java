package com.odyssey.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 비즈니스 규칙 위반을 나타내는 예외.
 *
 * <p>던질 때 HTTP 상태 코드와 에러 코드를 함께 지정한다.
 * {@link GlobalExceptionHandler} 가 이 예외를 받아
 * {@link com.odyssey.global.response.ApiResult} 형식으로 변환한다.
 *
 * <p>담는 값
 * <ul>
 *   <li>status - 응답에 쓸 HTTP 상태 코드</li>
 *   <li>code - 프론트가 분기에 쓰는 문자열 코드</li>
 *   <li>message - 사용자에게 보여줄 문구 ({@link RuntimeException} 으로 전달)</li>
 * </ul>
 *
 * <p>사용 예
 * <pre>{@code
 * throw new BusinessException(
 *         HttpStatus.NOT_FOUND,
 *         "POPULATION_NOT_FOUND",
 *         "해당 지역 데이터가 없습니다.");
 * }</pre>
 *
 * <p>위 예외가 만들어 내는 응답 (HTTP 404)
 * <pre>{@code
 * {
 *     "success": false,
 *     "data": null,
 *     "error": {
 *         "code": "POPULATION_NOT_FOUND",
 *         "message": "해당 지역 데이터가 없습니다."
 *     }
 * }
 * }</pre>
 */
public class BusinessException extends RuntimeException {

	private final HttpStatus status;
	private final String code;

	/**
	 * @param status  응답으로 내보낼 HTTP 상태 코드
	 * @param code    에러를 구분하는 문자열 코드 (예: POPULATION_NOT_FOUND)
	 * @param message 사용자에게 보여줄 메시지
	 */
	public BusinessException(HttpStatus status, String code, String message) {
		super(message);
		this.status = status;
		this.code = code;
	}

	/** @return 응답에 사용할 HTTP 상태 코드 */
	public HttpStatus getStatus() {
		return status;
	}

	/** @return 에러를 구분하는 문자열 코드 */
	public String getCode() {
		return code;
	}
}
