package AddressBook;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
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
		Assert.assertEquals(6, personData.size());
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

	@Test
	public void givenAddresBook_WhenAdded_ShouldSyncWithDB() throws AddressBookException, SQLException {
		addressBook.readAddressBookData(IOService.DB_IO);
		addressBook.addNewContact("Anika", "Srivastava", "Btm", "Bangalore", "KA", 561234, 985434211, "ani@gmail.com");
		boolean result = addressBook.checkAddressBookInSyncWithDatabase("Ani");
		Assert.assertTrue(result);
	}

	@Test
	public void given2Person_WhenAddedDataToDBUsingThread_ShouldMatchPersonsEnteries() throws AddressBookException {
		PersonData[] arrayOfEmps = {
				new PersonData("Anu", "Srivastava", "Btm", "Bangalore", "KA", 561234, 985434211, "ani@gmail.com"),
				new PersonData("Anvita", "Srivastava", "Btm2ndStage", "Bangalore", "KA", 561234, 985434211,
						"ani@gmail.com") };
		addressBook.readAddressBookData(IOService.DB_IO);
		addressBook.addMultipleRecordsInAddressBookWithThreads(Arrays.asList(arrayOfEmps));
		Assert.assertEquals(6, addressBook.addMultipleRecordsInAddressBookWithThreads(Arrays.asList(arrayOfEmps)));
	}
}
