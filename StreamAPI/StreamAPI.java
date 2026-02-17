import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamAPI {

    record Staff(String employeeName, int employeeAge, String employeeGender,
                 double employeeSalary, String employeeRole, String employeeDepartment) {
    }

    public static void main(String[] args) {

        List<Staff> staffList = Arrays.asList(
            new Staff("Rahul Sharma", 25, "Male", 52000, "Software Engineer", "IT"),
new Staff("Ananya Iyer", 45, "Female", 98000, "HR Manager", "HR"),
new Staff("Vikram Singh", 30, "Male", 64000, "Backend Developer", "IT"),
new Staff("Suresh Reddy", 55, "Male", 130000, "Sales Director", "Sales"),
new Staff("Neha Gupta", 28, "Female", 58000, "Financial Analyst", "Finance"),
new Staff("Arjun Mehta", 40, "Male", 90000, "Project Manager", "IT"),
new Staff("Priya Nair", 22, "Female", 42000, "HR Intern", "HR"),
new Staff("Karthik Kumar", 35, "Male", 78000, "Full Stack Developer", "IT"),
new Staff("Meera Joshi", 50, "Female", 115000, "Finance Manager", "Finance"),
new Staff("Rohit Verma", 32, "Male", 67000, "Business Analyst", "Sales"),
new Staff("Pooja Das", 29, "Female", 60000, "Frontend Developer", "IT"),
new Staff("Manoj Patil", 48, "Male", 95000, "Regional Manager", "Sales"),
new Staff("Sneha Kulkarni", 26, "Female", 54000, "Software Engineer", "IT"),
new Staff("Amit Agarwal", 38, "Male", 82000, "Team Lead", "Finance"),
new Staff("Divya Bansal", 42, "Female", 91000, "Delivery Manager", "IT"),
new Staff("Naveen Rao", 31, "Male", 65000, "Developer", "Sales"),
new Staff("Lakshmi Narayanan", 52, "Female", 108000, "HR Director", "HR")

        );

        // 1. Highest paid employee
        System.out.println("--- Highest Paid Employee ---");
        staffList.stream()
                .max(Comparator.comparingDouble(Staff::employeeSalary))
                .ifPresent(System.out::println);

        // 2. Count employees by gender
        System.out.println("\n--- Employee Count by Gender ---");
        Map<String, Long> genderWiseCount = staffList.stream()
                .collect(Collectors.groupingBy(Staff::employeeGender, Collectors.counting()));

        genderWiseCount.forEach((gender, count) ->
                System.out.println(gender + " : " + count));

        // 3. Total salary expense per department
        System.out.println("\n--- Salary Expense by Department ---");
        Map<String, Double> departmentSalaryExpense = staffList.stream()
                .collect(Collectors.groupingBy(
                        Staff::employeeDepartment,
                        Collectors.summingDouble(Staff::employeeSalary)
                ));

        departmentSalaryExpense.forEach((dept, total) ->
                System.out.println(dept + " : " + total));

        // 4. Top 5 oldest employees
        System.out.println("\n--- Top 5 Oldest Employees ---");
        staffList.stream()
                .sorted(Comparator.comparingInt(Staff::employeeAge).reversed())
                .limit(5)
                .forEach(System.out::println);

        // 5. List all managers
        System.out.println("\n--- List of Managers ---");
        staffList.stream()
                .filter(emp -> "Manager".equalsIgnoreCase(emp.employeeRole()))
                .map(Staff::employeeName)
                .forEach(System.out::println);

        // 6. Increase salary by 20% for non-managers
        System.out.println("\n--- Updated Salaries (Non-Managers) ---");
        List<Staff> updatedSalaryList = staffList.stream()
                .map(emp -> !"Manager".equalsIgnoreCase(emp.employeeRole())
                        ? new Staff(
                                emp.employeeName(),
                                emp.employeeAge(),
                                emp.employeeGender(),
                                emp.employeeSalary() * 1.20,
                                emp.employeeRole(),
                                emp.employeeDepartment())
                        : emp)
                .toList();

        updatedSalaryList.forEach(emp ->
                System.out.println(emp.employeeName() + " : " + emp.employeeSalary()));

        // 7. Total employees
        System.out.println("\n--- Total Employees ---");
        System.out.println("Total : " + staffList.stream().count());

        // 8. Average salary of each department (NEW QUERY)
        System.out.println("\n--- Average Salary by Department ---");
        Map<String, Double> avgSalary = staffList.stream()
                .collect(Collectors.groupingBy(
                        Staff::employeeDepartment,
                        Collectors.averagingDouble(Staff::employeeSalary)));

        avgSalary.forEach((dept, avg) ->
                System.out.println(dept + " : " + avg));

        // 9. Highest paid employee in each department (NEW QUERY)
        System.out.println("\n--- Highest Paid in Each Department ---");
        Map<String, Staff> highestDept = staffList.stream()
                .collect(Collectors.groupingBy(
                        Staff::employeeDepartment,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(Staff::employeeSalary)),
                                opt -> opt.orElse(null)
                        )));

        highestDept.forEach((dept, emp) ->
                System.out.println(dept + " : " + emp));
    }
}
