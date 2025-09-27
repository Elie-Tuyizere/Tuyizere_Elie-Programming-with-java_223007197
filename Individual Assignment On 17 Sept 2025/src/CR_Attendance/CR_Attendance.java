package CR_Attendance;

import java.util.Scanner;

public class CR_Attendance {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

       
        System.out.print("Enter total number of students in class: ");
        int totalStudents = sc.nextInt();

        int[] attendance = new int[30];
        int days = 0;
        String choice;
        do {
            System.out.print("Enter number of students present for Day " + (days + 1) + ": ");
            attendance[days] = sc.nextInt();
            days++;

          
            System.out.print("Do you want to enter attendance for another day? (yes/no): ");
            choice = sc.next();
        } while (choice.equalsIgnoreCase("yes") && days < 30);

        // Step 3: Analysis
        int totalAttendance = 0;
        int lowAttendanceDays = 0;

        for (int i = 0; i < days; i++) {
            totalAttendance += attendance[i];
            if (attendance[i] < totalStudents / 2) {
                lowAttendanceDays++;
            }
        }

        double averageAttendance = (double) totalAttendance / days;
        double lowAttendancePercent = (lowAttendanceDays * 100.0) / days;

        // Step 4: Print results
        System.out.println("\n===== Attendance Report=====");
        System.out.println("Day\tNumber Present");
        for (int i = 0; i < days; i++) {
            System.out.println((i + 1) + "\t" + attendance[i]);
        }

        System.out.println("\nAverage Attendance: " + averageAttendance);
        System.out.println("Days with Low Attendance (<50%): " + lowAttendanceDays);
        System.out.println("Percentage of Low Attendance Days: " + lowAttendancePercent + "%");

        sc.close();
    }
}