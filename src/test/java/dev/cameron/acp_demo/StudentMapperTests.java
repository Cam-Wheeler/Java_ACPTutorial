package dev.cameron.acp_demo;

import dev.cameron.acp_demo.dto.AcpStudentDTO;
import dev.cameron.acp_demo.mapper.StudentMapper;
import dev.cameron.acp_demo.model.AcpStudent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StudentMapperTests {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void testACPStudentToACPStudentDTO() {
        AcpStudent acpStudent = new AcpStudent();
        acpStudent.setId("s1234567");
        acpStudent.setFirstName("Cameron");
        acpStudent.setLastName("Wheeler");
        AcpStudentDTO mappedStudent = this.studentMapper.mapToDTO(acpStudent);

        AcpStudentDTO expectedDTO = new AcpStudentDTO();
        expectedDTO.setId("s1234567");
        expectedDTO.setFirstName("Cameron");

        assertThat(mappedStudent)
                .usingRecursiveComparison()
                .isEqualTo(expectedDTO);
    }


}
