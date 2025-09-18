package Case5;



import java.util.Scanner;


public class TestBig{

	public static void main(String[] args) {
		Scanner cir=new Scanner(System.in);
		Circle ci=new Circle();
		Rectangle tr=new Rectangle();
		System.out.println("Choose one shape to calculate: ");
		System.out.println("1. Circle  ");
		System.out.println("2. Rectangle");
		
		
		
			System.out.println("Enter radius of circle: ");
			double radius=cir.nextDouble();
			ci.setRadius(radius);
			
			
			System.out.println("Enter length: ");
			double length=cir.nextDouble();
			System.out.println("Enter width: ");
			double width=cir.nextDouble();
			tr. setLength(length);
			tr.setWidth(width);
			
			
			if(ci.calculatearea()>tr. calculatearea()){
				
				System.out.println("The area of circle is: "+ ci.calculatearea());
				System.out.println("big shape is circle ");
			}
			else if(ci.calculatearea()<tr. calculatearea()){
				
				System.out.println("The area of rectangle is: "+ tr.calculatearea());
				System.out.println("big shape is Rectanglr ");
			}
			else{
				System.out.println("Equal ");
			}
		}
/**
 TUYIZERE Elie 223007197
 MUREKATETE Kellen 223008892
 TWIZEYIMANA Onesphore 223008132
 HABUMUGISHA ERIC 223009063
 KAYITESI BELYSE 223019996
 NIKUBWAYO LEANDRE 223015716
 MUNEZERO ENOCK 223024831
 GUHIRWA IMURINDE DIVINE 223019984
 NYIRAMBARUSHIMANA ASSUMPTA 223016189
 **/
	}

