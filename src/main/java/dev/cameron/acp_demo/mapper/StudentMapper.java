package dev.cameron.acp_demo.mapper;

import dev.cameron.acp_demo.dto.AcpStudentDTO;
import dev.cameron.acp_demo.model.AcpStudent;
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