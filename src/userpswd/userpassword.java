package userpswd;

import java.util.Scanner;

public class userpassword {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		String user, pass;
		
		System.out.print("Enter your username: ");
		user = input.nextLine();
		
		System.out.print("Enter your password: ");
		pass = input.nextLine();
		
		if((user.equals("dbadmin")) && (pass.equals("password4"))){
			System.out.println("Welcome dbadmin.");
		}
		else{
			System.out.print("Incorrect Username or Password.");
			
		}
	}

}
