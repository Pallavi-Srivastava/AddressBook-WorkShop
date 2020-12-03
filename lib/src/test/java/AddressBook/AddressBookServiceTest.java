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
}
