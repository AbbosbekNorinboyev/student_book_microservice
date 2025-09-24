package uz.pdp.book_service.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.book_service.dto.response.Response;
import uz.pdp.book_service.dto.response.ErrorResponse;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Response<Void> exception(MethodArgumentNotValidException e) {
        List<ErrorResponse> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String defaultMessage = fieldError.getDefaultMessage();
                    String rejectedValue = String.valueOf(fieldError.getRejectedValue());
                    return new ErrorResponse(field,
                            String.format("defaultMessage: '%s', rejectedValue: '%s'", defaultMessage, rejectedValue));
                }).toList();
        return Response.<Void>builder()
                .code(-1)
                .message("Validation error")
                .errors(errors)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Response<Void> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return Response.<Void>builder()
                .code(404)
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Response<Void> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return Response.<Void>builder()
                .code(400)
                .message(illegalArgumentException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> handleGeneralException(Exception e) {
        return Response.<Void>builder()
                .code(500)
                .message("An unexpected error occurred -> " + e.getMessage())
                .success(false)
                .build();
    }
}
