package com.blz.prtcproblem;

import java.util.Scanner;

public class AddressBookMain {

	public static void main(String[] args) {

		entryPickUp();
	}

	static void entryPickUp() {
		AddressBook addressBook = new AddressBook();
		Scanner sc = new Scanner(System.in);
		int flag = 1;
		while (flag == 1) {
			System.out.println("Welcome to address book program ");
			System.out.println("Enter choice \n1. AddContact \n2.Edit \n3. Exit ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				addressBook.addContact();
				break;
			case 2:
				if (AddressBook.lst.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.editContact();
				break;
			case 3:
				flag = 0;
				System.out.println("Exit");
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}
}
