package space.khagesh.school.dto;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

	private String id;
    private String name;
    private Set<String> subjectIds; // Changed to UUID to hold only subject IDs
}
