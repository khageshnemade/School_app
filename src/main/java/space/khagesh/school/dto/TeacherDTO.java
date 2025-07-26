package space.khagesh.school.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TeacherDTO {
    private String id;
    private String name;
    private Set<String> subjectIds;
}
