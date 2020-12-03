package AddressBook;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {

	public enum IOService {
		DB_IO, FILE_IO
	}

	private List<PersonData> addressBookList;
	private static AddressBookDBService addressBookDBService;

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public List<PersonData> readAddressBookData(IOService ioService) throws AddressBookException {
		if (ioService.equals(IOService.DB_IO))
			return this.addressBookList = addressBookDBService.readData();
		return this.addressBookList;
	}

	public void updateContactAddress(String firstname, String address) throws AddressBookException {
		int result = addressBookDBService.updateAddressBookData(firstname, address);
		if (result == 0)
			throw new AddressBookException("Address book update failed",
					AddressBookException.ExceptionType.DatabaseException);
		PersonData addressBookData = this.getAddressBookData(firstname);
		if (addressBookData != null)
			addressBookData.address = address;
	}

	private PersonData getAddressBookData(String firstname) {
		return this.addressBookList.stream().filter(addressBookItem -> addressBookItem.firstName.equals(firstname))
				.findFirst().orElse(null);
	}

	public boolean checkAddressBookInSyncWithDatabase(String firstname) throws AddressBookException {
		try {
			List<PersonData> addressBookData = addressBookDBService.getAddressBookData(firstname);
			return addressBookData.get(0).equals(getAddressBookData(firstname));
		} catch (AddressBookException addressBookException) {
			throw new AddressBookException("Cannot execute query",
					AddressBookException.ExceptionType.DatabaseException);
		}
	}

	public List<PersonData> readEmployeePayrollData(IOService ioService, String start, String end)
			throws AddressBookException {
		try {
			LocalDate startLocalDate = LocalDate.parse(start);
			LocalDate endLocalDate = LocalDate.parse(end);
			if (ioService.equals(IOService.DB_IO))
				return addressBookDBService.readData(startLocalDate, endLocalDate);
			return this.addressBookList;
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DatabaseException);
		}
	}

	public int readEmployeePayrollData(String function, String city) throws AddressBookException {
		return addressBookDBService.readDataPayroll(function, city);
	}

	public void addNewContact(String firstName, String lastName, String address, String city, String state, int zip,
			long phoneNo, String email) throws AddressBookException, SQLException {
		addressBookList.add(
				addressBookDBService.addNewContact(firstName, lastName, address, city, state, zip, phoneNo, email));
	}

	public int addMultipleRecordsInAddressBookWithThreads(List<PersonData> addressBookList)
			throws AddressBookException {
		Map<Integer, Boolean> contactsMap = new HashMap<>();
		addressBookList.forEach(contactsList -> {
			Runnable task = () -> {
				contactsMap.put(contactsList.hashCode(), false);
				System.out.println("Contact being added: " + Thread.currentThread().getName());
				try {
					this.addNewContact(contactsList.firstName, contactsList.lastName, contactsList.address,
							contactsList.city, contactsList.state, contactsList.zip, contactsList.phoneNo,
							contactsList.email);
				} catch (AddressBookException addressBookException) {
					new AddressBookException("Cannot update using threads",
							AddressBookException.ExceptionType.DatabaseException);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				contactsMap.put(contactsList.hashCode(), true);
				System.out.println("Contact added: " + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, contactsList.firstName);
			thread.start();
		});
		while (contactsMap.containsValue(false)) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException interruptedException) {

			}
		}
		System.out.println(addressBookList);
		return new AddressBookService().readAddressBookData(IOService.DB_IO).size();
	}
}
