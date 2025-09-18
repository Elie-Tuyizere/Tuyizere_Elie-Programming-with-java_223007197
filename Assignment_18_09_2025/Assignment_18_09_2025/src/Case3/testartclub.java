package Case3;

import java.util.Scanner;

public class testartclub {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner reg=new Scanner(System.in);
		double array[];
		artclub art=new artclub();
		for(int i= 1;i<4;i++){
			System.out.println("======Rectangle "+ i+":========");
			System.out.print("Enter length: ");
			double length=reg.nextDouble();
			System.out.print("Enter Width: ");
			double width=reg.nextDouble();
			double Area;
			
			
				
			
				art.setLength(length);
				art.setWidth(width);
				
				System.out.println("The area of rectangle is: "+ art.calculatearea());
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


