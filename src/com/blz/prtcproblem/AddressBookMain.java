package com.blz.prtcproblem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {

	static Map<String, AddressBookMain> addressBookObj = new HashMap<>();
	static AddressBookMain addressObj = new AddressBookMain();

	public static void addAddressBook() {
		Scanner input = new Scanner(System.in);
		System.out.println(
				"Enter choice \n1.Creating new addressbook \n2.Adding contacts in existing register \n3.Exit ");
		int entry = input.nextInt();
		if (entry != 3) {
			switch (entry) {
			case 1:
				Scanner nameInput = new Scanner(System.in);
				System.out.println(" Enter name of address book ");
				String nameOfNewBook = nameInput.nextLine();
				if (addressBookObj.containsKey(nameOfNewBook)) {
					System.out.println(" address book already exists");
					break;
				}
				addressBookObj.put(nameOfNewBook, addressObj);
				System.out.println("Address  book" + " " + nameOfNewBook + " " + "has been added");
				AddressBookMain.entryPickUp();
				break;
			case 2:
				Scanner existingAddressName = new Scanner(System.in);
				System.out.println(" Enter name of address book ");
				String nameOfExistingRegister = existingAddressName.nextLine();
				if (addressBookObj.containsKey(nameOfExistingRegister)) {
					addressBookObj.get(nameOfExistingRegister);
					AddressBookMain.entryPickUp();
				} else
					System.out.println("Address book is not found ");
			case 3:
				entry = 3;
				break;
			default:
				System.out.println("Enter valid input ");
				break;
			}
		}
	}

	public static void main(String[] args) {

		entryPickUp();
	}

	static void entryPickUp() {
		AddressBook addressBook = new AddressBook();
		Scanner sc = new Scanner(System.in);
		int flag = 1;
		while (flag == 1) {
			System.out.println("Welcome to address book program ");
			System.out.println("Enter choice \n1. AddContact \n2. Edit \n3. Delete \n4. Search \n5. View \n6. Exit ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				addressBook.addContact();
				break;
			case 2:
				if (AddressBook.lst.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.editContact();
				break;
			case 3:
				if (AddressBook.lst.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.deleteContact();
				break;
			case 4:
				if (AddressBook.lst.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.searchContactByCity();
				break;
			case 5:
				if (AddressBook.lst.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.viewContactByCity();
				break;
			case 6:
				addAddressBook();
				flag = 0;
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}
}
