package AddressBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

	private PreparedStatement addressBookPreparedStatement;
	private static AddressBookDBService addressBookDBService;
	private List<PersonData> addressBookData;

	public static AddressBookDBService getInstance() {
		if (addressBookDBService == null)
			addressBookDBService = new AddressBookDBService();
		return addressBookDBService;
	}

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbookwk?useSSL=false";
		String username = "root";
		String password = "lovey";
		Connection con;
		System.out.println("Connecting to database:" + jdbcURL);
		con = DriverManager.getConnection(jdbcURL, username, password);
		System.out.println("Connection is successful:" + con);
		con.setAutoCommit(false);
		return con;
	}

	public List<PersonData> readData() throws AddressBookException {
		String query = null;
		query = "select * from addressbook";
		return getAddressBookDataUsingDatabase(query);
	}

	private List<PersonData> getAddressBookDataUsingDatabase(String query) throws AddressBookException {
		List<PersonData> addressBookData = new ArrayList();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			addressBookData = this.getAddressBookDetails(resultSet);
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.ConnectionFailed);
		}
		return addressBookData;
	}

	private List<PersonData> getAddressBookDetails(ResultSet resultSet) throws AddressBookException {
		List<PersonData> addressBookData = new ArrayList();
		try {
			while (resultSet.next()) {
				String firstName = resultSet.getString("FirstName");
				String lastName = resultSet.getString("LastName");
				String address = resultSet.getString("Address");
				String city = resultSet.getString("City");
				String state = resultSet.getString("State");
				int zip = resultSet.getInt("Zip");
				long phoneNumber = resultSet.getLong("PhoneNumber");
				String email = resultSet.getString("Email");
				addressBookData.add(new PersonData(firstName, lastName, address, city, state, zip, phoneNumber, email));
			}
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DatabaseException);
		}
		return addressBookData;
	}

	public int updateAddressBookData(String firstname, String address) throws AddressBookException {
		try (Connection connection = this.getConnection()) {
			String sql = String.format("update addressbook set Address = '%s' where FirstName = '%s';", address,
					firstname);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			return preparedStatement.executeUpdate(sql);
		} catch (SQLException sqlException) {
			throw new AddressBookException(sqlException.getMessage(),
					AddressBookException.ExceptionType.ConnectionFailed);
		}
	}

	public List<PersonData> getAddressBookData(String firstname) throws AddressBookException {
		if (this.addressBookPreparedStatement == null)
			this.prepareAddressBookStatement();
		try {
			addressBookPreparedStatement.setString(1, firstname);
			ResultSet resultSet = addressBookPreparedStatement.executeQuery();
			addressBookData = this.getAddressBookDetails(resultSet);
		} catch (SQLException sqlException) {
			throw new AddressBookException(sqlException.getMessage(),
					AddressBookException.ExceptionType.ConnectionFailed);
		}
		return addressBookData;
	}

	private void prepareAddressBookStatement() throws AddressBookException {
		try {
			Connection connection = this.getConnection();
			String sql = "select * from addressbook where FirstName = ?";
			addressBookPreparedStatement = connection.prepareStatement(sql);
		} catch (SQLException sqlException) {
			throw new AddressBookException(sqlException.getMessage(),
					AddressBookException.ExceptionType.DatabaseException);
		}
	}

	public List<PersonData> readData(LocalDate start, LocalDate end) throws AddressBookException {
		String query = null;
		if (start != null)
			query = String.format("select * from addressbook where startdate between '%s' and '%s';", start, end);
		if (start == null)
			query = "select * from addressbook";
		List<PersonData> addressBookList = new ArrayList<>();
		try (Connection con = this.getConnection();) {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			addressBookList = this.getAddressBookDetails(rs);
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DatabaseException);
		}
		return addressBookList;
	}

	public int readDataPayroll(String total, String city) throws AddressBookException {
		int count = 0;
		String query = String.format("select %s(state) from addressbook where city = '%s' group by city;", total, city);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			count = resultSet.getInt(1);
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DatabaseException);
		}
		return count;
	}

	public PersonData addNewContact(String firstName, String lastName, String address, String city, String state,
			int zip, long phoneNo, String email) throws AddressBookException, SQLException {
		int personID = -1;
		Connection connection = null;
		PersonData personDetails;
		String query = String.format(
				"insert into addressbook(FirstName, LastName, Address, City, State, Zip, PhoneNumber, Email) values ('%s', '%s', '%s', '%s', '%s', '%d', '%d', '%s')",
				firstName, lastName, address, city, state, zip, phoneNo, email);
		try {
			connection = this.getConnection();
			Statement statement = connection.createStatement();
			int rowChanged = statement.executeUpdate(query, statement.RETURN_GENERATED_KEYS);
			if (rowChanged == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					personID = resultSet.getInt(1);
			}
			personDetails = new PersonData(personID, firstName, lastName, address, city, state, zip, phoneNo, email);
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DatabaseException);
		} finally {
			connection.commit();
		}
		return personDetails;
	}
}
