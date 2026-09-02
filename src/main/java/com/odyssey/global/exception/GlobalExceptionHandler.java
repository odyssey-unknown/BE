package com.odyssey.global.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.odyssey.global.response.ApiResult;

import lombok.extern.slf4j.Slf4j;

// 컨트롤러에서 터진 예외를 한곳에서 받아 ApiResult 형식으로 변경
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// 지정 예외 방식
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResult<Void>> handleBusiness(BusinessException e) {
		ApiResult<Void> result = ApiResult.fail(e.getCode(), e.getMessage());
		// 상태 코드는 던질 때 정한 값
		return new ResponseEntity<>(result, e.getStatus());
	}

	// 500(INTERNAL_SERVER_ERROR)
	// 위에서 안 잡힌 나머지 전부
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResult<Void>> handleUnexpected(Exception e) {
		// 원인은 로그에만 남긴다
		log.error("처리되지 않은 예외", e);

		// 응답 메시지는 고정 (내부 정보 노출 방지)
		ApiResult<Void> result = ApiResult.fail("INTERNAL_ERROR", "서버 오류가 발생했습니다.");
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 400(BAD_REQUEST)
	// @Valid 검증 실패
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		// 오류가 여러 개여도 첫 번째만 쓴다
		FieldError fieldError = exception
				.getBindingResult()
				.getFieldError();

		// 필드 오류가 없을 때 쓸 기본 문구
		String message = "잘못된 요청입니다.";
		if (fieldError != null) {
			// 예) size: 1 이상이어야 합니다
			message = fieldError.getField() + ": " + fieldError.getDefaultMessage();
		}

		ApiResult<Void> result = ApiResult.fail("INVALID_INPUT", message);
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	// 404, 405 처럼 Spring 이 자체 처리하는 예외를 ApiResult 형식으로
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

		// code 는 NOT_FOUND 같은 상태 이름을 사용합니다.
		ApiResult<Void> result = ApiResult.fail(
				HttpStatus.valueOf(statusCode.value()).name(),
				exception.getMessage());

		return new ResponseEntity<>(result, headers, statusCode);
	}
}
