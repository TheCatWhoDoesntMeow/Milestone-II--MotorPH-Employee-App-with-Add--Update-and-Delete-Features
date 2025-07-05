import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    public static List<Employee> loadEmployees(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 19) {
                    Employee employee = new Employee(
                        data[0], data[1], data[2], data[3], 
                        data[4], data[5], data[6], data[7], 
                        data[8], data[9], data[10], data[11], 
                        data[12], Double.parseDouble(data[13]), 
                        Double.parseDouble(data[14]), 
                        Double.parseDouble(data[15]), 
                        Double.parseDouble(data[16]), 
                        Double.parseDouble(data[17]), 
                        Double.parseDouble(data[18])
                    );
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading employee file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric value: " + e.getMessage());
        }
        return employees;
    }

    public static void saveEmployees(List<Employee> employees, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("EmployeeNumber,LastName,FirstName,Birthday,Address,PhoneNumber," +
                      "SSSNumber,PhilHealthNumber,TIN,PagIbigNumber,Status,Position," +
                      "Supervisor,BasicSalary,RiceSubsidy,PhoneAllowance,ClothingAllowance," +
                      "GrossSemiMonthlyRate,HourlyRate");
            
            for (Employee emp : employees) {
                pw.println(emp.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving employee file: " + e.getMessage());
        }
    }
}

