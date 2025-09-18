package Case2;

import java.util.Scanner;

public class TestCircle {

	public static void main(String[] args) {
	Scanner cir=new Scanner(System.in);
	Circle ci=new Circle();
	System.out.println("Choose one to calculate: ");
	System.out.println("1. Radius ");
	System.out.println("2. Circumference ");
	
	double radius=cir.nextDouble();
	System.out.print("Enter radius: ");
	ci.setRadius(radius);
	int opera=cir.nextInt();
	
	switch(opera){
	case 1:
	
		System.out.print("The area is:  "+ ci.calculatearea());
		break;
	case 2:
		
		System.out.print("The area is:  "+ ci.calculateCircumference());
		break;
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

}
