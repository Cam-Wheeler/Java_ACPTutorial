package dev.cameron.acpdemo;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import dev.cameron.acpdemo.dto.AcpStudentDTO;
import dev.cameron.acpdemo.model.AcpStudent;
import dev.cameron.acpdemo.service.BlobService;
import dev.cameron.acpdemo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.azure.storage.blob.models.BlobStorageException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceTests {

    @Autowired
    private StudentService studentService;

    @MockitoBean
    private BlobService mockedBlobService;

    @Test
    void testValidateStudentCorrect() {
        AcpStudent student = new AcpStudent();
        student.setId("s1234567");
        student.setFirstName("Cameron");
        student.setLastName("Wheeler");
        assertTrue(studentService.validateStudent(student));
    }

    @Test
    void testValidateStudentIncorrectIdLength() {
        AcpStudent student = new AcpStudent();
        student.setId("s0000");
        student.setFirstName("Cameron");
        student.setLastName("Wheeler");
        assertFalse(studentService.validateStudent(student));
    }

    @Test
    void testValidateStudentIncorrectIdNumbers() {
        AcpStudent student = new AcpStudent();
        student.setId("s123v567");
        student.setFirstName("Cameron");
        student.setLastName("Wheeler");
        assertFalse(studentService.validateStudent(student));
    }

    // Test that null raises error, should also test second but just an example.
    @Test
    void testValidateStudentNullFirst() {
        AcpStudent student = new AcpStudent();
        student.setId("s1234567");
        student.setFirstName(null);
        student.setLastName("Wheeler");
        assertFalse(studentService.validateStudent(student));
    }

    // Test that the first name is not empty, should also test second but just an example.
    @Test
    void testValidateStudentEmptyFirst() {
        AcpStudent student = new AcpStudent();
        student.setId("s1234567");
        student.setFirstName("");
        student.setLastName("Wheeler");
        assertFalse(studentService.validateStudent(student));
    }

    /*
    We mock the blob interactions and test that when a student is in the blob storage our method returns the
    AcpStudentDTO object with the fields correctly assigned.
     */
    @Test
    void testStudentIsRegisteredWithMockedBlobService() {
        String jsonString = """
                {
                    "id": "s1234567",
                    "firstName": "Cameron",
                    "lastName": "Wheeler"
                }""";
        BlobClient mockblobClient = mock(BlobClient.class);
        when(mockedBlobService.getBlobClient("s1234567")).thenReturn(mockblobClient);
        when(mockblobClient.downloadContent()).thenReturn(BinaryData.fromString(jsonString));

        AcpStudentDTO acceptedStudent = studentService.isAccepted("s1234567");
        assertThat(acceptedStudent).isNotNull();
        assertThat(acceptedStudent.getId()).isEqualTo("s1234567");
        assertThat(acceptedStudent.getFirstName()).isEqualTo("Cameron");
    }

    // We mock the blob interactions and test that our StudentService handles the error and returns null.
    @Test void testStudentIsNotRegisteredWithBlobService() {
        BlobClient mockblobClient = mock(BlobClient.class);
        when(mockedBlobService.getBlobClient("s1234566")).thenReturn(mockblobClient);
        when(mockblobClient.downloadContent()).thenThrow(
                new BlobStorageException("The specified blob does not exist.",
                        null, null));

        // Check that we get null as the response.
        AcpStudentDTO acceptedStudent = studentService.isAccepted("s1234566");
        assertThat(acceptedStudent).isNull();
    }
}
