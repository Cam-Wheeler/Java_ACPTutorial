package dev.cameron.acp_demo.controller;

// Local Imports
import dev.cameron.acp_demo.service.StudentService;
import dev.cameron.acp_demo.dto.AcpStudentDTO;
import dev.cameron.acp_demo.model.AcpStudent;
import dev.cameron.acp_demo.mapper.StudentMapper;

// Java Imports
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SimpleController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public SimpleController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    // Recommended to easily check if the service is active.
    @GetMapping("/isAlive")
    public boolean isAlive() { return true; }

    /*
    Path parameters
    Method checks BLOB storage to see if a specific student has been registered.
     */
    @GetMapping("/students/{id}/accepted")
    public boolean validateId(@PathVariable String id) {
        AcpStudentDTO student = this.studentService.isAccepted(id);
        return student != null;
    }

    /*
    Query Params
    Simply extracts the parameters from the url and concats.
     */
    @GetMapping("/concatName")
    public String concatName(@RequestParam String first, @RequestParam String second) {
        return first + " " + second;
    }

    /*
    Post Request with JSON in body.
    Method also submits BLOB object to Azure BLOB Storage through student service.
     */
    @PostMapping("/createACPStudent")
    public AcpStudentDTO createACPStudent(@RequestBody AcpStudent acpStudent) {
        if (!this.studentService.validateStudent(acpStudent)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid student information.");
        }
        if (this.studentService.createStudent(acpStudent)) {
            return this.studentMapper.mapToDTO(acpStudent);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student was not created.");
        }
    }
}
