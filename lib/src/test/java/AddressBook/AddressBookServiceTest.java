package AddressBook;

import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import AddressBook.AddressBookService.IOService;

public class AddressBookServiceTest {

	private static AddressBookService addressBook;

	@BeforeClass
	public static void createServiceObj() {
		addressBook = new AddressBookService();
		System.out.println("Welcome to the Address Book System.. ");
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchContactsCount() throws AddressBookException, SQLException {
		List<PersonData> personData = addressBook.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(3, personData.size());
	}

	@Test
	public void givenAddressBook_WhenUpdate_ShouldSyncWithDB() throws AddressBookException {
		List<PersonData> personDetails = addressBook.readAddressBookData(IOService.DB_IO);
		addressBook.updateContactAddress("Pallavi", "EcoVillage");
		boolean result = addressBook.checkAddressBookInSyncWithDatabase("Pallavi");
		Assert.assertTrue(result);
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchAddressBookCountInGivenRange() throws AddressBookException {
		List<PersonData> personDetails = addressBook.readEmployeePayrollData(IOService.DB_IO, "2020-11-01",
				"2020-11-22");
		Assert.assertEquals(2, personDetails.size());
	}

	@Test
	public void givenAddresBook_WhenRetrieved_wShouldReturnTotalNoOfCity() throws AddressBookException {
		Assert.assertEquals(1, addressBook.readEmployeePayrollData("Count", "Varanasi"));
	}
}
