package uz.pdp.student_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.student_service.dto.response.Response;
import uz.pdp.student_service.dto.response.ErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.student_service.util.Util.localDateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Response<?> handleValidException(MethodArgumentNotValidException e,
                                               HttpServletRequest request) {
        List<ErrorResponse> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String defaultMessage = fieldError.getDefaultMessage();
                    String rejectedValue = String.valueOf(fieldError.getRejectedValue());
                    return new ErrorResponse(field,
                            String.format("defaultMessage: '%s', rejectValue: '%s'", defaultMessage, rejectedValue));
                }).toList();
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation error")
                .errors(errors)
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Response<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                          HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    /**
     *
     * @param illegalArgumentException
     * @return studentCreateDTO ni ichidagi integer toifasidagi fieldlar ustiga qo'yilgan @Positive annotatsiyasi o'chirilganida ishlaydi
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException,
                                                      HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message(illegalArgumentException.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception exception,
                                       HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unexpected error occurred -> " + exception.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }
}
