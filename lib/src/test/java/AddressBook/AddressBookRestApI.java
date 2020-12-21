package AddressBook;

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
	public void addedNewEmployee_ShouldMatch201ResponseAndTotalCount() throws AddressBookException, SQLException {
		PersonData gsonContacts[] = getEmployeeList();
		PersonData newContact = new PersonData(4, "Ridhi", "Srivastava","Shivpur","Vns","UP",234567,987654332,"jiya@gmail.com");
		Response response = addContactToJsonServer(newContact);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		newContact = new Gson().fromJson(response.asString(), PersonData.class);
		addressBook.addNewPersonUsingREST(Arrays.asList(newContact));
		long count = addressBook.countEnteries(IOService.REST_IO);
		Assert.assertEquals(4, count);
	}
}
