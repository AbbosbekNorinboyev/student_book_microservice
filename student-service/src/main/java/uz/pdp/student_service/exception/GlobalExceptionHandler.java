package uz.pdp.student_service.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.student_service.dto.ApiResponse;
import uz.pdp.student_service.dto.ErrorDTO;

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
                            String.format("defaultMessage: '%s', rejectValue: '%s'", defaultMessage, rejectedValue));
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

    /**
     *
     * @param illegalArgumentException
     * @return studentCreateDTO ni ichidagi integer toifasidagi fieldlar ustiga qo'yilgan @Positive annotatsiyasi o'chirilganida ishlaydi
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return ApiResponse.<Void>builder()
                .code(400)
                .message(illegalArgumentException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception exception) {
        return ApiResponse.<Void>builder()
                .code(500)
                .message("An unexpected error occurred -> " + exception.getMessage())
                .success(false)
                .build();
    }
}
