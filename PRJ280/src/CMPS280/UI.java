package CMPS280;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UI {

	// Private data fields to keep the information hidden outside of the class
	private JFrame frame;
	private JPanel panel;

	private JLabel passwordLabel;
	private JLabel usernameLabel;
	private JLabel success; // Label for a successful login

	private JTextField usernameBox;
	private JTextField passwordBox;

	private JButton selectPath;
	private JButton login;

	private ArrayList<Employee> employees = new ArrayList<Employee>(); // ArrayList for type Employee, stores all of the
																		// employees from the txt file to a list

	private int employeeIndex; // Index that is used later for the correctly logged in employee within the
								// arrayList

	private String databaseFileName; // String that holds the information gathered within the constructor, to get the
										// file name of the text file holding employee information

	private Reader readEmployeeFile; 

	// Default constructor
	UI() {
	}// end of default constructor

	// Constructor for UI that accepts a String for the file name that will
	// correlate to which file will be read to create the arrayList of employees
	UI(String newDatabaseFileName) throws Exception {

		databaseFileName = newDatabaseFileName;
		buildEmployeeDatabase();// goto buildEmployeeDatabase
		buildUI();// goto buildUI

	}// end of constructor

	// builds the "employees" ArrayList from the designated "databaseFileName" file
	void buildEmployeeDatabase() throws Exception {// throws exception in-case "readEmployeeFile.readFile();" cannot
													// find the file to read

		readEmployeeFile = new Reader(databaseFileName);// goto Reader.java code

		readEmployeeFile.readFile();// reads the designated file set to the Reader

		// for loop that adds information from the text file that is being read to, to
		// the ArrayList
		for (int i = 0; i < readEmployeeFile.getSize(); i++) {

			// adding an Employee to the ArrayList
			employees.add(new Employee(readEmployeeFile.getDataInfo(i), readEmployeeFile.getDataInfo(i + 1),
					readEmployeeFile.getDataInfo(i + 2), readEmployeeFile.getDataInfo(i + 3),
					readEmployeeFile.getDataInfo(i + 4), readEmployeeFile.getDataInfo(i + 5),
					readEmployeeFile.getDataInfo(i + 6), readEmployeeFile.getDataInfo(i + 7),
					readEmployeeFile.getDataInfo(i + 8)));

			i = i + 8;// i is set to "i=i+8" because there is 8 variables per employee line inside the
						// file being read to
		}

	}// end of buildEmployeeDatabase()

	// Method for creating the first UI inside of the program
	void buildUI() {

		frame = new JFrame("Wing Kingdom");

		panel = new JPanel();
		panel.setLayout(null);

		passwordLabel = new JLabel("Password: ");
		usernameLabel = new JLabel("Username: ");
		success = new JLabel("");// Success is set to a default empty string, until a user fails to login

		usernameBox = new JTextField();
		passwordBox = new JTextField();

		selectPath = new JButton("Select Path");
		login = new JButton("Login");

		// Setting the location for the button that we created
		login.setBounds(180, 225, 100, 40);// (x,y,width,height);
		selectPath.setBounds(180, 275, 100, 20);

		// Setting the location for the labels that we created
		passwordLabel.setBounds(110, 180, 100, 40);
		usernameLabel.setBounds(110, 145, 100, 40);

		// Setting the location for the TextFields that we created
		usernameBox.setBounds(175, 157, 118, 18);
		passwordBox.setBounds(175, 192, 118, 18);

		loginButtonEvent();
		selectEvent();

		// adding in our buttons, labels, and textFields to the panel
		panel.add(passwordLabel);
		panel.add(usernameLabel);
		panel.add(login);
		panel.add(usernameBox);
		panel.add(passwordBox);
		panel.add(selectPath);
		panel.add(success);

		// adding the panel to the frame that we made
		frame.add(panel);

		// setting the size of the frame, making it visible and closable
		frame.setSize(450, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}// end of buildUI

	void loginButtonEvent() {

		// adding the listener
		login.addActionListener(new ActionListener() {

			// Adds an event to the listener so that something happens whenever the button
			// is pushed
			public void actionPerformed(ActionEvent e) {
				try {
					if (isCorrect() == true) {// checking if isCorrect is == to true before continuing

						// Telling the user that the login was a success
						success.setText("Successful Login");
						// setting the location of the success text
						success.setBounds(70, 225, 100, 40);

						@SuppressWarnings("unused")
						Object currentLoggedinUI;

						switch (employees.get(employeeIndex).getTitle()) {

						case "Host":
							currentLoggedinUI = new Host(employeeIndex, databaseFileName);
							break;
						case "Waiter":
							currentLoggedinUI = new Waiter(employeeIndex, databaseFileName);
							break;
						case "Chef":
							currentLoggedinUI = new Chef(employeeIndex, databaseFileName);
							break;
						case "Manager":
							currentLoggedinUI = new Manager(employeeIndex, databaseFileName);
							break;

						}
						;

						// closing the login window
						frame.dispose();

					}

					// prompting the user that the email and password combination has failed
					else {
						success.setText("Login Failed...");
						success.setBounds(90, 225, 100, 40);
					}

				} catch (Exception e2) {

					e2.printStackTrace();
				}
			}
		});
	}// end of loginButtonEvent

	boolean isCorrect() {
		// For loop checking if the user entered employee email and password matches
		// with all of the employees in the ArrayList
		for (int i = 0; i < employees.size(); i++) {
			if (usernameBox.getText().equals(employees.get(i).getEmail())
					&& passwordBox.getText().equals(employees.get(i).getPass())) {
				employeeIndex = i;

				return true;
			}
		}
		return false;
	}// end of isCorrect method
	
	void selectEvent() {// This is what happens when the select button is pushed
		selectPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			frame.dispose();
			main.buildPathInputUI();
			}
		});
	}// end of selectEvent

}// end of UI class
