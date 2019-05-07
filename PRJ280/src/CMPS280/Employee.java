package CMPS280;

public class Employee {

	// These data fields are set to private because these properties should NOT be
	// allowed to be edited from anywhere else besides its current class.
	private String firstname;
	private String lastname;
	private String birthDate;
	private String email;
	private String password;
	private String jobTitle;
	private String salary;
	private String level;
	private String gender;

	Employee(String newTitle, String newFName, String newLName, String newBDay, String newEmail, String newPassword,
			String newSalary, String newLevel, String newGender) {
		firstname = newFName;
		lastname = newLName;
		birthDate = newBDay;
		email = newEmail;
		password = newPassword;
		jobTitle = newTitle;
		salary = newSalary;
		level = newLevel;
		gender = newGender;
	}// end of constructor

	void setFirst(String newFName) {
		firstname = newFName;
	}// end of setFirst

	void setLast(String newLName) {
		lastname = newLName;
	}// end of setLast

	void setBDay(String newBDay) {
		birthDate = newBDay;
	}// end of setBDay

	void setPass(String newPassword) {
		password = newPassword;
	}// end of setPass

	void setEmail(String newEmail) {
		email = newEmail;
	}// end of setEmail

	void setTitle(String newTitle) {
		jobTitle = newTitle;
	}// end of setTitle

	void setSalary(String newSalary) {
		salary = newSalary;
	}// end of setSalary

	void setLevel(String newLevel) {
		salary = newLevel;
	}// end of setLevel

	void setGender(String newGender) {
		gender = newGender;
	}// end of setGender

	String getFirst() {
		return firstname;
	}// end getFirst

	String getLast() {
		return lastname;
	}// end getLast

	String getBDay() {
		return birthDate;
	}// end getBDay

	String getEmail() {
		return email;
	}// end of getEmail

	String getPass() {
		return password;
	}// end of getPass

	String getTitle() {
		return jobTitle;
	}// end of getTitle

	String getSalary() {
		return salary;
	}// end of getSalary

	String getLevel() {
		return level;
	}// end of getLevel

	String getGender() {
		return gender;
	}// end of getGender

}// end of Employee class
