package com.blz.prtcproblem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressBook {

	Scanner sc = new Scanner(System.in);
	static List<PersonDetails> lst = new ArrayList<PersonDetails>();
	PersonDetails newEntry;
	boolean isExist;

	public void addContact() {
		isExist = false;
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
		if (lst.size() > 0) {
			for (PersonDetails details : lst) {
				newEntry = details;
				if (firstName.equals(newEntry.firstName) && lastName.equals(newEntry.lastName)) {
					System.out.println("Contact " + newEntry.firstName + " " + newEntry.lastName + " already exists");
					isExist = true;
					break;
				}
			}
		}
		if (!isExist) {
			newEntry = new PersonDetails(firstName, lastName, address, city, state, zip, phoneNo, email);
			lst.add(newEntry);
		}
		System.out.println(lst);
	}

	public void editContact() {
		System.out.println(" Enter the first name ");
		String fName = sc.nextLine();
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).getFirstName().equals(fName)) {
				System.out.println("index : " + lst.get(i));
				Scanner editInput = new Scanner(System.in);
				System.out.println(" Enter a choice 1.first name 2.last name 3. city 4.state 5.zip 6.phone 7.email ");
				int selection = sc.nextInt();
				switch (selection) {
				case 1:
					System.out.println(" Enter first name ");
					String first_Name = editInput.nextLine();
					lst.get(i).setFirstName(first_Name);
					break;
				case 2:
					System.out.println(" Enter last name ");
					String second_Name = editInput.nextLine();
					lst.get(i).setLastName(second_Name);
					break;
				case 3:
					System.out.println(" Enter city name ");
					String input_City = editInput.nextLine();
					lst.get(i).setCity(input_City);
					break;
				case 4:
					System.out.println(" Enter State ");
					String input_State = editInput.nextLine();
					lst.get(i).setState(input_State);
					break;
				case 5:
					System.out.println(" Enter pincode ");
					int input_Zip = editInput.nextInt();
					lst.get(i).setZip(input_Zip);
					break;
				case 6:
					System.out.println(" Enter Mobile number ");
					long input_Phone = editInput.nextInt();
					lst.get(i).setPhoneNo(input_Phone);
					break;
				case 7:
					System.out.println(" Enter Email id ");
					String input_Email = editInput.nextLine();
					lst.get(i).setEmail(input_Email);
					break;
				default:
					System.out.println(" Enter valid input ");
					break;
				}
			}
		}
		System.out.println(lst);
	}

	public void deleteContact() {
		for (int i = 0; i < lst.size(); i++) {
			System.out.println("Enter First Name : ");
			Scanner sc = new Scanner(System.in);
			String fname = sc.nextLine();
			if (lst.get(i).getFirstName().equalsIgnoreCase(fname)) {
				lst.remove(i);
			} else {
				System.out.println("No Data Found");
			}
		}
		System.out.println(lst);
	}

	public void searchContactByCity() {
		System.out.println("Enter City Name : ");
		Scanner sc = new Scanner(System.in);
		String city = sc.nextLine();
		lst.stream().filter(n -> n.getCity().equals(city)).forEach(i -> System.out.println(i));
	}

	public void viewContactByCity() {
		System.out.println("Enter City Name : ");
		Scanner sc = new Scanner(System.in);
		String city = sc.nextLine();
		lst.stream().filter(n -> n.getCity().equals(city)).forEach(i -> System.out.println(i));
	}

	public void countContactByCity() {
		int count = 0;
		System.out.println("Enter City Name : ");
		Scanner sc = new Scanner(System.in);
		String city = sc.nextLine();
		count = (int) lst.stream().filter(n -> n.getCity().equals(city)).count();
		System.out.println(count);
	}

	public void sortByName() {
		lst = lst.stream().sorted(Comparator.comparing(PersonDetails::getFirstName)).collect(Collectors.toList());
		lst.forEach(i -> System.out.println(i));
	}

	public void sortByCityOrState() {
		lst = lst.stream().sorted(Comparator.comparing(PersonDetails::getCity)).collect(Collectors.toList());
		lst.forEach(i -> System.out.println(i));
	}

	public void sortByZip() {
		lst = lst.stream().sorted(Comparator.comparing(PersonDetails::getZip)).collect(Collectors.toList());
		lst.forEach(i -> System.out.println(i));
	}
}
