package AddressBook;

import java.time.LocalDate;
import java.util.List;

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
}
