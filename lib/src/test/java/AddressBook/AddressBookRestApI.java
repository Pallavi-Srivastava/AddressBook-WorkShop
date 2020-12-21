package AddressBook;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.gson.Gson;
import AddressBook.AddressBookService.IOService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookRestApI {

	private static AddressBookService addressBook;

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	@BeforeClass
	public static void createServiceObj() {
		addressBook = new AddressBookService();
		System.out.println("Welcome to the Address Book System.. ");
	}

	public PersonData[] getEmployeeList() {
		Response response = RestAssured.get("/address");
		System.out.println("Person enteries in jsonServer: " + response.asString());
		PersonData[] arrayOfEmps = new Gson().fromJson(response.asString(), PersonData[].class);
		return arrayOfEmps;
	}

	@Test
	public void givenContactsInJsonServer_WhenRetrieved_ShouldMatchTotalCount() {
		PersonData gsonContacts[] = getEmployeeList();
		addressBook = new AddressBookService(Arrays.asList(gsonContacts));
		long count = addressBook.countEnteries(IOService.REST_IO);
		Assert.assertEquals(4, count);
	}

	public Response addContactToJsonServer(PersonData newContact) {
		String gsonString = new Gson().toJson(newContact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(gsonString);
		return request.post("/address");
	}

	@Test
	public void addedNewEmployee_ShouldMatch201ResponseAndTotalCount() throws AddressBookException {
		PersonData gsonContacts[] = getEmployeeList();
		PersonData newContact = new PersonData(4, "Ridhi", "Srivastava", "Shivpur", "Vns", "UP", 234567, 987654332,
				"jiya@gmail.com");
		Response response = addContactToJsonServer(newContact);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		newContact = new Gson().fromJson(response.asString(), PersonData.class);
		addressBook.addNewPersonUsingREST(Arrays.asList(newContact));
		long count = addressBook.countEnteries(IOService.REST_IO);
		Assert.assertEquals(4, count);
	}

	@Test
	public void givenListOfEmployee_WhenAddedMultipleEmployee_ShouldMatch201ResponseAndCount()
			throws AddressBookException {
		PersonData[] gsonContacts = getEmployeeList();
		addressBook = new AddressBookService(Arrays.asList(gsonContacts));
		PersonData[] arrayOfAddressBooks = {
				new PersonData(5, "Riya", "Srivastava", "Shivpur", "Vns", "UP", 234567, 987654332, "jiya@gmail.com"),
				new PersonData(6, "Rima", "Srivastava", "Shivpur", "Vns", "UP", 234567, 987654332, "jiya@gmail.com") };
		for (PersonData addressBookData : arrayOfAddressBooks) {
			Response response = addContactToJsonServer(addressBookData);
			int statusCode = response.getStatusCode();
			assertEquals(201, statusCode);
			addressBookData = new Gson().fromJson(response.asString(), PersonData.class);
			addressBook.addNewPersonUsingREST(Arrays.asList(addressBookData));
			System.out.println(addressBookData);
		}
		long entries = addressBook.countEnteries(IOService.REST_IO);
		assertEquals(6, entries);
	}

	@Test
	public void givenNewAddressToEmployee_WhenUpdated_ShouldMatch200Responses() throws AddressBookException {
		PersonData[] gsonContacts = getEmployeeList();
		addressBook = new AddressBookService(Arrays.asList(gsonContacts));
		addressBook.updateContactAddress("Anika", "Ether");
		PersonData addressBookData = addressBook.getAddressBookData("Anika");
		String addressBookJson = new Gson().toJson(addressBookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(addressBookJson);
		Response response = request.put("/address/" + addressBookData.firstName);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
	}
}
