package view;

import controller.PatientController;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private PatientController controller = new PatientController();
    private DefaultTableModel tableModel;

    public MainFrame() {
        setTitle("Manajemen Data Pasien Klinik");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
        JLabel doctorLabel = new JLabel("Doctor:");
        JComboBox<String> doctorDropdown = new JComboBox<>(new String[]{"Doctor A", "Doctor B", "Doctor C"});
        JButton addButton = new JButton("Add");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(doctorLabel);
        inputPanel.add(doctorDropdown);
        inputPanel.add(new JLabel());
        inputPanel.add(addButton);

        // Panel Tabel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Address", "Phone", "Doctor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tabel tidak dapat diedit
            }
        };
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel Update & Delete
        JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JTextField idField = new JTextField();
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        actionPanel.add(new JLabel("ID:"));
        actionPanel.add(idField);
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);

        // Tambahkan Listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String address = addressField.getText();
                    String phone = phoneField.getText();
                    String doctor = (String) doctorDropdown.getSelectedItem();

                    if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                        throw new IllegalArgumentException("All fields must be filled.");
                    }

                    controller.addPatient(name, age, address, phone, doctor);
                    refreshTable();
                    // Clear input fields after adding patient
                    nameField.setText("");
                    ageField.setText("");
                    addressField.setText("");
                    phoneField.setText("");
                    doctorDropdown.setSelectedIndex(0);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Age must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(MainFrame.this, "ID must not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int option = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want to delete this patient?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    if (controller.deletePatient(id)) {
                        refreshTable();
                        idField.setText(""); // Clear ID field after deletion
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Patient not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText();
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String address = addressField.getText();
                    String phone = phoneField.getText();
                    String doctor = (String) doctorDropdown.getSelectedItem();

                    if (id.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                        throw new IllegalArgumentException("All fields must be filled.");
                    }

                    boolean updated = controller.updatePatient(id, name, age, address, phone, doctor);

                    if (updated) {
                        refreshTable();
                        JOptionPane.showMessageDialog(MainFrame.this, "Patient data updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Patient ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Age must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Tambahkan ke frame
        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Menghapus semua baris yang ada di tabel
        for (Patient patient : controller.getAllPatients()) {
            tableModel.addRow(new Object[]{
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getAddress(),
                    patient.getPhone(),
                    patient.getDoctor()
            });
        }
    }


    public static void main(String[] args) {
        new MainFrame();
    }
}
