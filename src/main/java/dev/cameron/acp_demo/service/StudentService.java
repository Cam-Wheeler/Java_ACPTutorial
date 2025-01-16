package dev.cameron.acp_demo.service;


import com.azure.core.util.BinaryData;
import com.nimbusds.jose.shaded.gson.Gson;
import dev.cameron.acp_demo.dto.AcpStudentDTO;
import dev.cameron.acp_demo.model.AcpStudent;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class StudentService {

    private final BlobService blobService;

    public StudentService(BlobService blobService) {
        this.blobService = blobService;
    }

    // Validates the student JSON is what we expect it to be.
    public boolean validateStudent(AcpStudent student) {
        boolean valid = false;
        do {
            // Typical null checking
            if (student == null || student.getId() == null || student.getFirstName() == null || student.getLastName() == null) {
                break;
            }
            // Check the names are not an empty string.
            if (ObjectUtils.isEmpty(student.getFirstName()) || ObjectUtils.isEmpty(student.getLastName())) {
                break;
            }
            // Enforce length and starting character (it must be lower case to be valid).
            if ((student.getId().length() < 8) || (student.getId().charAt(0) != 's')) {
                break;
            }
            // Check all values following s are numerical
            if (!this.validateNumbers(student.getId())) {
                break;
            }
            valid = true;
        } while (false);
        return valid;
    }

    /*
    Private method validateStudent uses to validate all values following s in student
    are in fact numbers.
     */
    private boolean validateNumbers(String studentId) {
        String numbersOfId = studentId.substring(1);
        for (char c : numbersOfId.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // Checks to see if the student is present within our BLOB storage.
    public AcpStudentDTO isAccepted(String studentId) {
        String blobData;
        try {
            blobData = this.blobService.getBlobClient(studentId).downloadContent().toString();
        } catch (Exception e) {
            return null;
        }
        return new Gson().fromJson(blobData, AcpStudentDTO.class);
    }

    // Uploads a student with the file name {studentId}.json to BLOB storage.
    public boolean createStudent(AcpStudent student) {
        // code that uploads the student to the blob storage.
        String studentString = new Gson().toJson(student);
        try {
            this.blobService.getBlobClient(student.getId()).upload(BinaryData.fromString(studentString), true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}