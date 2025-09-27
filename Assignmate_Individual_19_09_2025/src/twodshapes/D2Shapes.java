package twodshapes;

import java.util.Scanner;
abstract class Shape {
    protected String name;
    
    public Shape(String name) {
        this.name = name;
    }
    
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    
    public String getName() {
        return name;
    }
}


class Rectangle extends Shape {
    private double length, width;
    
    public Rectangle(double length, double width) {
        super("Rectangle");
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() { return length * width; }
    @Override
    public double calculatePerimeter() { return 2 * (length + width); }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        super("Circle");
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() { return Math.PI * radius * radius; }
    @Override
    public double calculatePerimeter() { return 2 * Math.PI * radius; }
}


class Square extends Shape {
    private double side;
    
    public Square(double side) {
        super("Square");
        this.side = side;
    }
    
    @Override
    public double calculateArea() { return side * side; }
    @Override
    public double calculatePerimeter() { return 4 * side; }
}

class Triangle extends Shape {
    private double base, height, side1, side2, side3;
    
    public Triangle(double base, double height, double side1, double side2, double side3) {
        super("Triangle");
        this.base = base;
        this.height = height;
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    
    @Override
    public double calculateArea() { return 0.5 * base * height; }
    @Override
    public double calculatePerimeter() { return side1 + side2 + side3; }
}

public class D2Shapes {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Welcome to Shape Calculator!");
        
        boolean continueCalculation = true;
        while (continueCalculation) {
            displayMenu();
            
            int shapeChoice = getIntInput("Enter shape choice (1-4): ", 1, 4);
            char calcChoice = getCharInput("Calculate Area (A) or Perimeter (B): ", 'A', 'B');
            
            Shape shape = createShape(shapeChoice);
            performCalculation(shape, calcChoice);
            
            continueCalculation = askToContinue();
        }
        
        System.out.println("\nThank you for using the system!");
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\nSelect a shape:");
        System.out.println("1. Rectangle");
        System.out.println("2. Circle");
        System.out.println("3. Square");
        System.out.println("4. Triangle");
    }
    
    private static Shape createShape(int choice) {
        switch (choice) {
            case 1: return new Rectangle(getDouble("Enter length: "), getDouble("Enter width: "));
            case 2: return new Circle(getDouble("Enter radius: "));
            case 3: return new Square(getDouble("Enter side length: "));
            case 4: return new Triangle(
                getDouble("Enter base: "), 
                getDouble("Enter height: "),
                getDouble("Enter side 1: "),
                getDouble("Enter side 2: "),
                getDouble("Enter side 3: ")
            );
            default: return null;
        }
    }
    
    private static void performCalculation(Shape shape, char choice) {
        if (shape != null) {
            double result = (choice == 'A') ? shape.calculateArea() : shape.calculatePerimeter();
            String calcType = (choice == 'A') ? "Area" : "Perimeter";
            System.out.printf("%s %s: %.2f%n", shape.getName(), calcType, result);
        }
    }
    
    private static boolean askToContinue() {
        System.out.print("\nWould you like to continue? (yes/no): ");
        return scanner.next().equalsIgnoreCase("yes");
    }
    
    private static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                if (input >= min && input <= max) return input;
                System.out.println("Please enter between " + min + " and " + max);
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                scanner.next();
            }
        }
    }
    
    private static double getDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                if (input > 0) return input;
                System.out.println("Enter positive number!");
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                scanner.next();
            }
        }
    }
    
    private static char getCharInput(String prompt, char opt1, char opt2) {
        while (true) {
            try {
                System.out.print(prompt);
                char input = scanner.next().toUpperCase().charAt(0);
                if (input == opt1 || input == opt2) return input;
                System.out.println("Please enter '" + opt1 + "' or '" + opt2 + "'");
            } catch (Exception e) {
                System.out.println("Invalid input!");
                scanner.next();
            }
        }
    }
}