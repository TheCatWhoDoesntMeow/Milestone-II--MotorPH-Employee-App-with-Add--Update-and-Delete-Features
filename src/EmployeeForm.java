import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class EmployeeForm extends JDialog {
    private JTextField employeeNumberField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField birthdayField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField sssField;
    private JTextField philHealthField;
    private JTextField tinField;
    private JTextField pagIbigField;
    private JComboBox<String> statusCombo;
    private JTextField positionField;
    private JTextField supervisorField;
    private JTextField basicSalaryField;
    private JTextField riceSubsidyField;
    private JTextField phoneAllowanceField;
    private JTextField clothingAllowanceField;
    private JTextField grossSemiMonthlyField;
    private JTextField hourlyRateField;
    
    private Employee employee;
    private boolean isUpdating;
    private EmployeeManager employeeManager;
    private EmployeeTable employeeTable;

    public EmployeeForm(JFrame parent, EmployeeManager manager, EmployeeTable table, Employee emp, boolean isUpdating) {
        super(parent, isUpdating ? "Update Employee" : "Add New Employee", true);
        this.employeeManager = manager;
        this.employeeTable = table;
        this.employee = emp;
        this.isUpdating = isUpdating;
        
        setSize(600, 800);
        setLayout(new BorderLayout());
        
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveAction());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        if (isUpdating && emp != null) {
            populateForm(emp);
        }
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        panel.add(new JLabel("Employee Number:"));
        employeeNumberField = new JTextField();
        panel.add(employeeNumberField);
        
        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);
        
        panel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);
        
        panel.add(new JLabel("Birthday (YYYY-MM-DD):"));
        birthdayField = new JTextField();
        panel.add(birthdayField);
        
        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);
        
        panel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        panel.add(phoneField);
        
        panel.add(new JLabel("SSS Number:"));
        sssField = new JTextField();
        panel.add(sssField);
        
        panel.add(new JLabel("PhilHealth Number:"));
        philHealthField = new JTextField();
        panel.add(philHealthField);
        
        panel.add(new JLabel("TIN:"));
        tinField = new JTextField();
        panel.add(tinField);
        
        panel.add(new JLabel("Pag-IBIG Number:"));
        pagIbigField = new JTextField();
        panel.add(pagIbigField);
        
        panel.add(new JLabel("Status:"));
        statusCombo = new JComboBox<>(new String[]{"Probationary", "Regular", "Contractual", "Resigned", "Terminated"});
        panel.add(statusCombo);
        
        panel.add(new JLabel("Position:"));
        positionField = new JTextField();
        panel.add(positionField);
        
        panel.add(new JLabel("Supervisor:"));
        supervisorField = new JTextField();
        panel.add(supervisorField);
        
        panel.add(new JLabel("Basic Salary:"));
        basicSalaryField = new JTextField();
        panel.add(basicSalaryField);
        
        panel.add(new JLabel("Rice Subsidy:"));
        riceSubsidyField = new JTextField();
        panel.add(riceSubsidyField);
        
        panel.add(new JLabel("Phone Allowance:"));
        phoneAllowanceField = new JTextField();
        panel.add(phoneAllowanceField);
        
        panel.add(new JLabel("Clothing Allowance:"));
        clothingAllowanceField = new JTextField();
        panel.add(clothingAllowanceField);
        
        panel.add(new JLabel("Gross Semi-Monthly Rate:"));
        grossSemiMonthlyField = new JTextField();
        panel.add(grossSemiMonthlyField);
        
        panel.add(new JLabel("Hourly Rate:"));
        hourlyRateField = new JTextField();
        panel.add(hourlyRateField);
        
        return panel;
    }

    private void populateForm(Employee emp) {
        employeeNumberField.setText(emp.getEmployeeNumber());
        lastNameField.setText(emp.getLastName());
        firstNameField.setText(emp.getFirstName());
        birthdayField.setText(emp.getBirthday());
        addressField.setText(emp.getAddress());
        phoneField.setText(emp.getPhoneNumber());
        sssField.setText(emp.getSssNumber());
        philHealthField.setText(emp.getPhilHealthNumber());
        tinField.setText(emp.getTin());
        pagIbigField.setText(emp.getPagIbigNumber());
        statusCombo.setSelectedItem(emp.getStatus());
        positionField.setText(emp.getPosition());
        supervisorField.setText(emp.getSupervisor());
        basicSalaryField.setText(String.valueOf(emp.getBasicSalary()));
        riceSubsidyField.setText(String.valueOf(emp.getRiceSubsidy()));
        phoneAllowanceField.setText(String.valueOf(emp.getPhoneAllowance()));
        clothingAllowanceField.setText(String.valueOf(emp.getClothingAllowance()));
        grossSemiMonthlyField.setText(String.valueOf(emp.getGrossSemiMonthlyRate()));
        hourlyRateField.setText(String.valueOf(emp.getHourlyRate()));
    }

    private class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String employeeNumber = employeeNumberField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String birthday = birthdayField.getText().trim();
                String address = addressField.getText().trim();
                String phone = phoneField.getText().trim();
                String sss = sssField.getText().trim();
                String philHealth = philHealthField.getText().trim();
                String tin = tinField.getText().trim();
                String pagIbig = pagIbigField.getText().trim();
                String status = (String) statusCombo.getSelectedItem();
                String position = positionField.getText().trim();
                String supervisor = supervisorField.getText().trim();
                double basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
                double riceSubsidy = Double.parseDouble(riceSubsidyField.getText().trim());
                double phoneAllowance = Double.parseDouble(phoneAllowanceField.getText().trim());
                double clothingAllowance = Double.parseDouble(clothingAllowanceField.getText().trim());
                double grossSemiMonthly = Double.parseDouble(grossSemiMonthlyField.getText().trim());
                double hourlyRate = Double.parseDouble(hourlyRateField.getText().trim());

                // Validation
                if (employeeNumber.isEmpty() || lastName.isEmpty() || firstName.isEmpty()) {
                    throw new IllegalArgumentException("Employee Number, Last Name and First Name are required");
                }

                if (!Pattern.matches("^\\d{2}-\\d{4}$", employeeNumber)) {
                    throw new IllegalArgumentException("Employee Number must be in format XX-XXXX");
                }

                Employee newEmployee = new Employee(
                    employeeNumber, lastName, firstName, birthday,
                    address, phone, sss, philHealth, tin, pagIbig,
                    status, position, supervisor, basicSalary,
                    riceSubsidy, phoneAllowance, clothingAllowance,
                    grossSemiMonthly, hourlyRate
                );

                if (isUpdating) {
                    int selectedRow = employeeTable.getSelectedRow();
                    employeeManager.updateEmployee(selectedRow, newEmployee);
                } else {
                    employeeManager.addEmployee(newEmployee);
                }

                employeeTable.refreshTable(employeeManager.getEmployees());
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(EmployeeForm.this,
                    "Please enter valid numbers for salary fields",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(EmployeeForm.this,
                    ex.getMessage(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

