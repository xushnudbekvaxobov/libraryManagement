package project.dto.responseDto;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
