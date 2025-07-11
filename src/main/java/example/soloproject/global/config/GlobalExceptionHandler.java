package example.soloproject.global.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.YearMonth;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() == YearMonth.class) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "'month' 파라미터는 yyyy-MM 형식이어야 합니다.")
            );
        }
        return ResponseEntity.badRequest().body(
                Map.of("error", "요청 파라미터 형식이 올바르지 않습니다.")
        );
    }
}
