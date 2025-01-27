package dev.cameron.acpdemo;

import dev.cameron.acpdemo.controller.SimpleController;
import dev.cameron.acpdemo.model.AcpStudent;
import dev.cameron.acpdemo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SimpleController controller;

    @MockitoBean
    private StudentService mockStudentService;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void testIsAlive() {
        assertThat(restTemplate.getForObject("http://localhost:" + this.port + "/isAlive",
                String.class)).contains("true");
    }

    @Test
    void testConcatenate() {
        assertThat(restTemplate.getForObject("http://localhost:" + this.port + "/concatName"
                + "?first=Cameron&second=Wheeler", String.class)).contains("Cameron Wheeler");
    }

    /*
    An example where we mock the student service so we can test the controller logic.
    So here we are removing the dependencies on StudentService and StudentServices dependencies on BlobService by
    simply mocking the StudentService and the method we use.
     */
    @Test
    void testCreateStudentInvalidStudent() {
        when(mockStudentService.validateStudent(any(AcpStudent.class))).thenReturn(false);
        String invalidStudent = """
                {
                    "id": "sI234567",
                    "firstName": "Cameron",
                    "lastName": "Wheeler"
                }""";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + this.port + "/students",
                new HttpEntity<>(invalidStudent, headers), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
