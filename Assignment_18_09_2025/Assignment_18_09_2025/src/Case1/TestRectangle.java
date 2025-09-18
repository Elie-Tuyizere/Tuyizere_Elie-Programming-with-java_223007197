package Case1;

import java.util.Scanner;


public class TestRectangle {

	
	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		Rectangle rect=new Rectangle();
		
		System.out.print("Enter length: ");
		double length=input.nextDouble();
		System.out.print("Enter Width: ");
		double width=input.nextDouble();
		double Area;
		
		if(length==width){
			System.out.print("The shape is square ");
		}
		else{
			rect.setLength(length);
			rect.setWidth(width);
			
			System.out.print("The area of rectangle is: "+ rect.calculatearea());
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
