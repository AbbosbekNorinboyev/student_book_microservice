package uz.pdp.book_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private int code;
    private String message;
    private boolean success;
    private T data;
    private List<ErrorResponse> errors;
}
