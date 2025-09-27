package grades;
import java.util.Scanner;

public class StudentGrades {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int totalStudents = 0;
        int passes = 0;
        int fails = 0;

        while (true) {
            System.out.print("Enter student's marks (0-100) or -1 to stop: ");
            int marks = sc.nextInt();

            // Sentinel value check
            if (marks == -1) {
                break;
            }

            // Validate marks
            if (marks < 0 || marks > 100) {
                System.out.println("Invalid marks! Please enter between 0 and 100.");
                continue;
            }

            totalStudents++;

            
            if (marks >= 80 && marks <= 100) {
                System.out.println("Grade: A");
                passes++;
            } else if (marks >= 70) {
                System.out.println("Grade: B");
                passes++;
            } else if (marks >= 60) {
                System.out.println("Grade: C");
                passes++;
            } else if (marks >= 50) {
                System.out.println("Grade: D");
                passes++;
            } else {
                System.out.println("Grade: F");
                fails++;
            }
        }

        // Summary report
        System.out.println("\n===== Summary Report =====");
        System.out.println("Total students: " + totalStudents);
        System.out.println("Passed: " + passes);
        System.out.println("Failed: " + fails);

        if (totalStudents > 0) {
            double passRate = (passes * 100.0) / totalStudents;
            System.out.println("Class Pass Rate: " + passRate + "%");
        } else {
            System.out.println("No students entered.");
        }
    }
}
