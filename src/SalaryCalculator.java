import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SalaryCalculator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Map<String, Double> SSS_CONTRIBUTION = new HashMap<>();
    private static final Map<String, Double> PHILHEALTH_CONTRIBUTION = new HashMap<>();
    private static final double PAGIBIG_CONTRIBUTION = 100.0;
    private static final double TAX_RATE = 0.15;

    static {
        // Initialize SSS contribution table
        SSS_CONTRIBUTION.put("10000", 400.00);
        SSS_CONTRIBUTION.put("15000", 600.00);
        SSS_CONTRIBUTION.put("20000", 800.00);
        SSS_CONTRIBUTION.put("25000", 1000.00);
        SSS_CONTRIBUTION.put("30000", 1150.00);
        SSS_CONTRIBUTION.put("35000", 1300.00);
        SSS_CONTRIBUTION.put("40000", 1450.00);
        SSS_CONTRIBUTION.put("45000", 1600.00);
        SSS_CONTRIBUTION.put("50000", 1750.00);
        
        // Initialize PhilHealth contribution table
        PHILHEALTH_CONTRIBUTION.put("10000", 150.00);
        PHILHEALTH_CONTRIBUTION.put("15000", 225.00);
        PHILHEALTH_CONTRIBUTION.put("20000", 300.00);
        PHILHEALTH_CONTRIBUTION.put("25000", 375.00);
        PHILHEALTH_CONTRIBUTION.put("30000", 450.00);
        PHILHEALTH_CONTRIBUTION.put("35000", 525.00);
        PHILHEALTH_CONTRIBUTION.put("40000", 600.00);
        PHILHEALTH_CONTRIBUTION.put("45000", 675.00);
        PHILHEALTH_CONTRIBUTION.put("50000", 750.00);
    }

    public static double calculateMonthlySalary(Employee employee, String monthYear) {
        YearMonth yearMonth = YearMonth.parse(monthYear, DateTimeFormatter.ofPattern("MM-yyyy"));
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        
        // Get basic salary and allowances
        double basicSalary = employee.getBasicSalary();
        double riceSubsidy = employee.getRiceSubsidy();
        double phoneAllowance = employee.getPhoneAllowance();
        double clothingAllowance = employee.getClothingAllowance();
        
        // Calculate gross salary (assuming full month attendance)
        double grossSalary = basicSalary + riceSubsidy + phoneAllowance + clothingAllowance;
        
        // Calculate deductions
        double sss = getSSSContribution(grossSalary);
        double philhealth = getPhilHealthContribution(grossSalary);
        double pagibig = PAGIBIG_CONTRIBUTION;
        double tax = (grossSalary - sss - philhealth - pagibig) * TAX_RATE;
        
        // Calculate net salary
        return grossSalary - sss - philhealth - pagibig - tax;
    }

    private static double getSSSContribution(double salary) {
        if (salary <= 10000) return SSS_CONTRIBUTION.get("10000");
        if (salary <= 15000) return SSS_CONTRIBUTION.get("15000");
        if (salary <= 20000) return SSS_CONTRIBUTION.get("20000");
        if (salary <= 25000) return SSS_CONTRIBUTION.get("25000");
        if (salary <= 30000) return SSS_CONTRIBUTION.get("30000");
        if (salary <= 35000) return SSS_CONTRIBUTION.get("35000");
        if (salary <= 40000) return SSS_CONTRIBUTION.get("40000");
        if (salary <= 45000) return SSS_CONTRIBUTION.get("45000");
        return SSS_CONTRIBUTION.get("50000");
    }

    private static double getPhilHealthContribution(double salary) {
        if (salary <= 10000) return PHILHEALTH_CONTRIBUTION.get("10000");
        if (salary <= 15000) return PHILHEALTH_CONTRIBUTION.get("15000");
        if (salary <= 20000) return PHILHEALTH_CONTRIBUTION.get("20000");
        if (salary <= 25000) return PHILHEALTH_CONTRIBUTION.get("25000");
        if (salary <= 30000) return PHILHEALTH_CONTRIBUTION.get("30000");
        if (salary <= 35000) return PHILHEALTH_CONTRIBUTION.get("35000");
        if (salary <= 40000) return PHILHEALTH_CONTRIBUTION.get("40000");
        if (salary <= 45000) return PHILHEALTH_CONTRIBUTION.get("45000");
        return PHILHEALTH_CONTRIBUTION.get("50000");
    }
}

