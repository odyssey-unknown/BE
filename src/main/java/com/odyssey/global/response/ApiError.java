package com.odyssey.global.response;

/**
 * 에러 응답의 error 필드에 들어가는 값.
 *
 * <pre>{@code
 * {
 *     "success": false,
 *     "data": null,
 *     "error": {
 *         "code": "NOT_FOUND",
 *         "message": "해당 지역 데이터가 없습니다."
 *     }
 * }
 * }</pre>
 */
public record ApiError(String code, String message) {
}
