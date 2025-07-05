import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EmployeeTable extends JScrollPane {
    private JTable table;
    private DefaultTableModel tableModel;

    public EmployeeTable(List<Employee> employees) {
        String[] columnNames = {
            "Employee No", "Last Name", "First Name", 
            "Position", "Status", "Basic Salary"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        setViewportView(table);
        
        loadEmployees(employees);
    }

    public void loadEmployees(List<Employee> employees) {
        tableModel.setRowCount(0);
        for (Employee emp : employees) {
            Object[] rowData = {
                emp.getEmployeeNumber(),
                emp.getLastName(),
                emp.getFirstName(),
                emp.getPosition(),
                emp.getStatus(),
                emp.getBasicSalary()
            };
            tableModel.addRow(rowData);
        }
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public String getSelectedEmployeeNumber() {
        int row = table.getSelectedRow();
        if (row != -1) {
            return (String) tableModel.getValueAt(row, 0);
        }
        return null;
    }

    public void refreshTable(List<Employee> employees) {
        loadEmployees(employees);
    }
}

