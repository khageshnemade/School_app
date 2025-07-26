package space.khagesh.school.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    Instant timestamp;
    int status;
    String error;
    String message;
    String path;
    List<Map<String, String>> validationErrors; // for field validation errors
}
