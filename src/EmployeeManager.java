import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;
    private String employeeFile;

    public EmployeeManager() {
        this("data/employees.csv");
    }

    public EmployeeManager(String employeeFile) {
        this.employeeFile = employeeFile;
        this.employees = CSVUtil.loadEmployees(employeeFile);
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        CSVUtil.saveEmployees(employees, employeeFile);
    }

    public void updateEmployee(int index, Employee employee) {
        if (index >= 0 && index < employees.size()) {
            employees.set(index, employee);
            CSVUtil.saveEmployees(employees, employeeFile);
        }
    }

    public void deleteEmployee(int index) {
        if (index >= 0 && index < employees.size()) {
            employees.remove(index);
            CSVUtil.saveEmployees(employees, employeeFile);
        }
    }

    public Employee getEmployeeByNumber(String employeeNumber) {
        for (Employee emp : employees) {
            if (emp.getEmployeeNumber().equals(employeeNumber)) {
                return emp;
            }
        }
        return null;
    }
}

