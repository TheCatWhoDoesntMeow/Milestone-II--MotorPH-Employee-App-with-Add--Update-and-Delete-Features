import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainFrame extends JFrame {
    private EmployeeManager employeeManager;
    private EmployeeTable employeeTable;

    public MainFrame() {
        employeeManager = new EmployeeManager();
        employeeTable = new EmployeeTable(employeeManager.getEmployees());

        setTitle("MotorPH Employee Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add title label at the top
        JLabel titleLabel = new JLabel("Employee Records", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Add employee table in the center
        mainPanel.add(employeeTable, BorderLayout.CENTER);

        // Button panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton viewButton = new JButton("View Details");
        viewButton.addActionListener(new ViewDetailsAction());

        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(new AddEmployeeAction());

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateEmployeeAction());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteEmployeeAction());

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> employeeTable.refreshTable(employeeManager.getEmployees()));

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private Employee getSelectedEmployee() {
        String employeeNumber = employeeTable.getSelectedEmployeeNumber();
        if (employeeNumber != null) {
            return employeeManager.getEmployeeByNumber(employeeNumber);
        }
        return null;
    }

    private class ViewDetailsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Employee selectedEmployee = getSelectedEmployee();
            if (selectedEmployee != null) {
                // Prompt for month
                String month = (String) JOptionPane.showInputDialog(
                    MainFrame.this,
                    "Select Month:",
                    "Salary Computation",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    getMonths(),
                    new SimpleDateFormat("MM-yyyy").format(Calendar.getInstance().getTime())
                );

                if (month != null) {
                    double salary = SalaryCalculator.calculateMonthlySalary(selectedEmployee, month);
                    
                    // Create detailed view panel
                    JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
                    detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
                    
                    addDetailField(detailsPanel, "Employee Number:", selectedEmployee.getEmployeeNumber());
                    addDetailField(detailsPanel, "Name:", selectedEmployee.getLastName() + ", " + selectedEmployee.getFirstName());
                    addDetailField(detailsPanel, "Position:", selectedEmployee.getPosition());
                    addDetailField(detailsPanel, "Status:", selectedEmployee.getStatus());
                    addDetailField(detailsPanel, "Basic Salary:", String.format("₱%,.2f", selectedEmployee.getBasicSalary()));
                    addDetailField(detailsPanel, "Rice Subsidy:", String.format("₱%,.2f", selectedEmployee.getRiceSubsidy()));
                    addDetailField(detailsPanel, "Phone Allowance:", String.format("₱%,.2f", selectedEmployee.getPhoneAllowance()));
                    addDetailField(detailsPanel, "Clothing Allowance:", String.format("₱%,.2f", selectedEmployee.getClothingAllowance()));
                    addDetailField(detailsPanel, "Hourly Rate:", String.format("₱%,.2f", selectedEmployee.getHourlyRate()));
                    addDetailField(detailsPanel, "Selected Month:", month);
                    addDetailField(detailsPanel, "Computed Salary:", String.format("₱%,.2f", salary));
                    
                    JOptionPane.showMessageDialog(
                        MainFrame.this,
                        detailsPanel,
                        "Employee Details - " + selectedEmployee.getLastName() + ", " + selectedEmployee.getFirstName(),
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Please select an employee first",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }

        private void addDetailField(JPanel panel, String label, String value) {
            panel.add(new JLabel(label));
            panel.add(new JLabel(value));
        }

        private String[] getMonths() {
            String[] months = new String[12];
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
            
            for (int i = 0; i < 12; i++) {
                calendar.set(Calendar.MONTH, i);
                months[i] = sdf.format(calendar.getTime());
            }
            
            return months;
        }
    }

    private class AddEmployeeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EmployeeForm employeeForm = new EmployeeForm(
                MainFrame.this,
                employeeManager,
                employeeTable,
                null,
                false
            );
            employeeForm.setVisible(true);
        }
    }

    private class UpdateEmployeeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Employee selectedEmployee = getSelectedEmployee();
            if (selectedEmployee != null) {
                EmployeeForm employeeForm = new EmployeeForm(
                    MainFrame.this,
                    employeeManager,
                    employeeTable,
                    selectedEmployee,
                    true
                );
                employeeForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Please select an employee to update",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private class DeleteEmployeeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Are you sure you want to delete this employee?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    employeeManager.deleteEmployee(selectedRow);
                    employeeTable.refreshTable(employeeManager.getEmployees());
                }
            } else {
                JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Please select an employee to delete",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}

