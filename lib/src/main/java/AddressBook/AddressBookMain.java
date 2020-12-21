package AddressBook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {

	static Map<String, AddressBookMain> addressBookObj = new HashMap<>();
	static AddressBookMain addressObj = new AddressBookMain();
	static String addressBookName;

	public static void addAddressBook() throws IOException {
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

	public static void main(String[] args) throws IOException {

		entryPickUp();
	}

	static void entryPickUp() throws IOException {
		AddressBook addressBook = new AddressBook();
		Scanner sc = new Scanner(System.in);
		int flag = 1;
		while (flag == 1) {
			System.out.println("Welcome to address book program ");
			System.out.println(
					"Enter choice \n1. AddContact \n2. Edit \n3. Delete \n4. Search \n5. View \n6. Count \n7. SortByName "
							+ "\n8. SortByCityOrState \n9. SortByZip \n10. AddContactToFile \n11. ReadDataFromFile "
							+ "\n12. addContactsToCSVFile \n13. ReadDataFromCSVFile \n14.addContactsToJsonFile \n15.ReadDataFromJsonFile \n16. Exit ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				addressBook.addContact(addressBookName);
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
				if (AddressBook.lst.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.countContactByCity();
				break;
			case 7:
				if (AddressBook.lst.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.sortByName();
				break;
			case 8:
				if (AddressBook.lst.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.sortByCityOrState();
				break;
			case 9:
				if (AddressBook.lst.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.sortByZip();
				break;
			case 10:
				addressBook.addContact(addressBookName);
				System.out.println("Successfully Added to text file");
				break;
			case 11:
				addressBook.readDataFromFile();
				break;
			case 12:
				addressBook.addContact(addressBookName);
				System.out.println("Successfully Added to csv file");
				break;
			case 13:
				addressBook.readDataFromCSVFile();
				break;
			case 14:
				addressBook.addContact(addressBookName);
				System.out.println("Successfully Added to json file");
				break;
			case 15:
				addressBook.readDataFromJsonFile();
				break;
			case 16:
				addAddressBook();
				flag = 0;
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}
}
