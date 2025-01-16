package dev.cameron.acpdemo.mapper;

// Local Imports
import dev.cameron.acpdemo.dto.AcpStudentDTO;
import dev.cameron.acpdemo.model.AcpStudent;

// Java Imports
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public AcpStudentDTO mapToDTO(AcpStudent student) {
        AcpStudentDTO studentDTO = new AcpStudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        return studentDTO;
    }
}