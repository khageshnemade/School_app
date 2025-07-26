package space.khagesh.school.exception;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import space.khagesh.school.api.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream().map(this::toValidationError)
				.toList();

		return buildError(HttpStatus.BAD_REQUEST, "Validation Failed", "One or more fields have invalid values.",
				request.getServletPath(), errors);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
		return buildError(HttpStatus.BAD_REQUEST, "Bad Request", "Malformed JSON request body.",
				request.getServletPath(), null);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuth(AuthenticationException ex, HttpServletRequest request) {
		return buildError(HttpStatus.UNAUTHORIZED, "Unauthorized", "Access denied. Token is missing or invalid.",
				request.getServletPath(), null);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
		return buildError(HttpStatus.FORBIDDEN, "Forbidden", "You don't have permission to access this resource.",
				request.getServletPath(), null);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex, HttpServletRequest request) {
		return buildError(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request.getServletPath(), null);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAll(Exception ex, HttpServletRequest request) {
		return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Something went wrong.",
				request.getServletPath(), null);
	}

	private Map<String, String> toValidationError(FieldError fe) {
		return Map.of("field", fe.getField(), "message",
				fe.getDefaultMessage() == null ? "Invalid value" : fe.getDefaultMessage());
	}

	private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String error, String message, String path,
			List<Map<String, String>> validationErrors) {
		ErrorResponse body = ErrorResponse.builder().timestamp(Instant.now()).status(status.value()).error(error)
				.message(message).path(path).validationErrors(validationErrors).build();

		return ResponseEntity.status(status).body(body);
	}

	@ExceptionHandler(TeacherNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTeacherNotFound(TeacherNotFoundException ex,
			HttpServletRequest request) {
		return buildError(HttpStatus.NOT_FOUND, "Teacher Not Found", ex.getMessage(), request.getServletPath(), null);
	}

	@ExceptionHandler(SubjectNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSubjectNotFound(SubjectNotFoundException ex,
			HttpServletRequest request) {
		return buildError(HttpStatus.NOT_FOUND, "Subject Not Found", ex.getMessage(), request.getServletPath(), null);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSStudentNotFound(StudentNotFoundException ex,
			HttpServletRequest request) {
		return buildError(HttpStatus.NOT_FOUND, "Student Not Found", ex.getMessage(), request.getServletPath(), null);
	}

}
