import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CSVUtil {
    private static final NumberFormat PH_NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);
    
    // Indexes of numeric fields in your CSV
    private static final int[] NUMERIC_FIELD_INDEXES = {13, 14, 15, 16, 17, 18};
    
    public static List<Employee> loadEmployees(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read header to verify structure
            String header = br.readLine();
            if (!validateHeader(header)) {
                throw new IOException("Invalid CSV header structure");
            }
            
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = parseCSVLine(line);
                    if (data.length >= 19) {
                        // Convert numeric fields
                        Double[] numericData = new Double[6];
                        for (int i = 0; i < NUMERIC_FIELD_INDEXES.length; i++) {
                            numericData[i] = parseMoney(data[NUMERIC_FIELD_INDEXES[i]]);
                        }
                        
                        Employee employee = new Employee(
                            data[0],  // employeeNumber
                            data[1],  // lastName
                            data[2],  // firstName
                            data[3],  // birthday
                            data[4],  // address
                            data[5],  // phoneNumber
                            data[6],  // sssNumber
                            data[7],  // philHealthNumber
                            data[8],  // tinNumber
                            data[9],  // pagIbigNumber
                            data[10], // status
                            data[11], // position
                            data[12], // supervisor
                            numericData[0], // basicSalary
                            numericData[1], // riceSubsidy
                            numericData[2], // phoneAllowance
                            numericData[3], // clothingAllowance
                            numericData[4], // grossSemiMonthlyRate
                            numericData[5]  // hourlyRate
                        );
                        employees.add(employee);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading employee file: " + e.getMessage());
        }
        return employees;
    }

    private static boolean validateHeader(String header) {
        return header != null && header.contains("EmployeeNumber") && header.contains("HourlyRate");
    }

    private static String[] parseCSVLine(String line) {
        line = line.replace("\"N/A\"", "\"0\"") // Handle quoted N/A
                  .replace("N/A", "0");        // Handle unquoted N/A
        
        // Improved CSV parsing that handles commas in quoted fields
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString().trim());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        fields.add(currentField.toString().trim());
        
        return fields.toArray(new String[0]);
    }

    private static Double parseMoney(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("N/A")) {
            return 0.0;
        }
        
        try {
            // Remove currency symbols and thousands separators
            String cleanValue = value.replace("₱", "")
                                   .replace(",", "")
                                   .trim();
            return PH_NUMBER_FORMAT.parse(cleanValue).doubleValue();
        } catch (ParseException e) {
            System.err.println("Warning: Could not parse numeric value: " + value);
            return 0.0;
        }
    }
    
}