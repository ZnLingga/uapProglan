package test;

import controller.PatientController;
import model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientControllerTest {

    private PatientController controller;

    @BeforeEach
    void setUp() {
        controller = new PatientController();
    }

    @Test
    void testAddPatient() {
        // Menambah pasien baru
        controller.addPatient("John Doe", 30, "123 Street", "555-1234", "Doctor A");
        Patient patient = controller.getPatientById("A1");

        assertNotNull(patient);
        assertEquals("John Doe", patient.getName());
        assertEquals(30, patient.getAge());
        assertEquals("123 Street", patient.getAddress());
        assertEquals("555-1234", patient.getPhone());
        assertEquals("Doctor A", patient.getDoctor());
    }

    @Test
    void testUpdatePatient() {
        // Menambah pasien untuk diupdate
        controller.addPatient("John Doe", 30, "123 Street", "555-1234", "Doctor A");
        boolean result = controller.updatePatient("A1", "John Smith", 35, "456 Avenue", "555-5678", "Doctor B");

        assertTrue(result);
        Patient updatedPatient = controller.getPatientById("A1");
        assertEquals("John Smith", updatedPatient.getName());
        assertEquals(35, updatedPatient.getAge());
        assertEquals("456 Avenue", updatedPatient.getAddress());
        assertEquals("555-5678", updatedPatient.getPhone());
        assertEquals("Doctor B", updatedPatient.getDoctor());
    }

    @Test
    void testDeletePatient() {
        // Menambah pasien untuk dihapus
        controller.addPatient("John Doe", 30, "123 Street", "555-1234", "Doctor A");
        boolean result = controller.deletePatient("A1");

        assertTrue(result);
        Patient patient = controller.getPatientById("A1");
        assertNull(patient);
    }

    @Test
    void testGetPatientByIdNotFound() {
        Patient patient = controller.getPatientById("A1");
        assertNull(patient);
    }
}
