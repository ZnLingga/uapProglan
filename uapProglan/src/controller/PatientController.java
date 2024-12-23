package controller;

import model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private List<Patient> patientList = new ArrayList<>();
    private int doctorACount = 0;
    private int doctorBCount = 0;
    private int doctorCCount = 0;

    // Tambahkan pasien baru
    public void addPatient(String name, int age, String address, String phone, String doctor) {
        String id;
        if (doctor.equals("Doctor A")) {
            doctorACount++;
            id = "A" + doctorACount;
        } else if (doctor.equals("Doctor B")) {
            doctorBCount++;
            id = "B" + doctorBCount;
        } else {
            doctorCCount++;
            id = "C" + doctorCCount;
        }
        Patient patient = new Patient(id, name, age, address, phone, doctor);
        patientList.add(patient);
    }

    // Cari pasien berdasarkan ID
    public Patient getPatientById(String id) {
        return patientList.stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update data pasien
    public boolean updatePatient(String id, String name, int age, String address, String phone, String doctor) {
        Patient patient = getPatientById(id);
        if (patient != null) {
            patient.setName(name);
            patient.setAge(age);
            patient.setAddress(address);
            patient.setPhone(phone);
            patient.setDoctor(doctor);
            return true;
        }
        return false;
    }

    // Hapus pasien berdasarkan ID
    public boolean deletePatient(String id) {
        return patientList.removeIf(patient -> patient.getId().equals(id));
    }

    // Ambil semua data pasien
    public List<Patient> getAllPatients() {
        return patientList;
    }
}
