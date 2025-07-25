package space.khagesh.school.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)  
    @Convert(converter = UUIDToStringConverter.class)
	private String id;

	private String name;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "subjects")
	private Set<Student> students = new HashSet<>();

	@ManyToMany(mappedBy = "subjects")
	private Set<Teacher> teachers = new HashSet<>();
}