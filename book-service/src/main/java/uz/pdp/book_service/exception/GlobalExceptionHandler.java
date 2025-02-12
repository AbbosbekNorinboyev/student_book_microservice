package uz.pdp.book_service.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.book_service.dto.ApiResponse;
import uz.pdp.book_service.dto.ErrorDTO;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ApiResponse<Void> exception(MethodArgumentNotValidException e) {
        List<ErrorDTO> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String defaultMessage = fieldError.getDefaultMessage();
                    String rejectedValue = String.valueOf(fieldError.getRejectedValue());
                    return new ErrorDTO(field,
                            String.format("defaultMessage: '%s', rejectedValue: '%s'", defaultMessage, rejectedValue));
                }).toList();
        return ApiResponse.<Void>builder()
                .code(-1)
                .message("Validation error")
                .errors(errors)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<Void> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return ApiResponse.<Void>builder()
                .code(404)
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return ApiResponse.<Void>builder()
                .code(400)
                .message(illegalArgumentException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleGeneralException(Exception e) {
        return ApiResponse.<Void>builder()
                .code(500)
                .message("An unexpected error occurred -> " + e.getMessage())
                .success(false)
                .build();
    }
}
