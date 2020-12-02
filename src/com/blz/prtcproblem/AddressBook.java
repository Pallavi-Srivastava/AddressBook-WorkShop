package com.blz.prtcproblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {

	Scanner sc = new Scanner(System.in);
	List<PersonDetails> lst = new ArrayList<PersonDetails>();

	public void addContact() {
		System.out.println("Enter your firstName : ");
		String firstName = sc.nextLine();
		System.out.println("Enter your lastName : ");
		String lastName = sc.nextLine();
		System.out.println("Enter your address : ");
		String address = sc.nextLine();
		System.out.println("Enter your city : ");
		String city = sc.nextLine();
		System.out.println("Enter your state : ");
		String state = sc.nextLine();
		System.out.println("Enter your zipCode : ");
		int zip = sc.nextInt();
		System.out.println("Enter your phoneNo : ");
		long phoneNo = sc.nextLong();
		sc.nextLine();
		System.out.println("Enter your emailId : ");
		String email = sc.nextLine();
		PersonDetails obj = new PersonDetails(firstName, lastName, address, city, state, zip, phoneNo, email);
		lst.add(obj);
		System.out.println(lst);
	}
}
