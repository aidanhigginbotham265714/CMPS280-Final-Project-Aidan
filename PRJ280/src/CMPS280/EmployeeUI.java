package CMPS280;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

//EmployeeUI is a superclass that contains a basic template for every type of employee within the program
//EmployeeUI is also an Abstract class, which contains the exitEvent, writeToFile, and errorUI abstract methods.
public abstract class EmployeeUI implements Builder {

	// ArrayList for all of the employees, used for creating the single employee,
	// and for the Management class to edit the employees
	protected ArrayList<Employee> employees = new ArrayList<Employee>();
	
	protected Reader employeesFile;// reader to read the file that contains all of the employees
	protected Reader menuFile;

	// ---------------------UI Properties for the abstract method errorUI&exitEvent
	protected JFrame errorF;

	protected JPanel errorP;

	protected JButton exit;

	protected JLabel error;
	// ----------------------UI Properties for the abstract method errorUI&exitEvent

	// -----------------------UI Properties for the programs main menu
	protected JFrame userMenuF;
	protected JPanel userMenuP;

	protected JLabel title;
	protected JLabel welcome;
	protected JLabel name;

	protected JButton information;
	protected JButton menu;
	protected JButton logout;
	protected JButton refresh;
	// -----------------------UI Properties for the programs main menu

	// -----------------------UI Properties for the info Menu
	protected JFrame infoMenuF;

	protected JPanel infoMenuP;

	// ArrayList of JLabels used for adding in employee information in the info menu
	protected ArrayList<JLabel> employeeLabels = new ArrayList<JLabel>();

	protected JButton goBack;
	// -----------------------UI Properties for the info Menu

	// -----------------------UI Properties for the food menu
	protected JFrame foodMenuF;
	protected JPanel foodMenuP;

	// ArrayList of JLabels used for adding in foodmenu info into the food menu
	protected ArrayList<JLabel> foodMenu = new ArrayList<JLabel>();
	// -----------------------UI Properties for the food menu


	// This is created for the data of the successfully logged in employee, so its
	// just one, not an array
	protected int singleEmployeeIndex;

	protected String employeesToRead;

	// Default constructor
	EmployeeUI() {
	}// end of default constructor

	// Constructor for inputting the successfully logged employees index within the
	// employee ArrayList, and the a string for the file name of the employee file
	EmployeeUI(int newEmployeeIndex, String newEmployeesFile) {

		singleEmployeeIndex = newEmployeeIndex;// Index for the Employee ArrayList
												// for the successfully logged in employee

		employeesFile = new Reader(newEmployeesFile);// Reader for reading the employees txt file

		employeesToRead = newEmployeesFile;

		try {
			buildEmployeeDatabase();
			buildMenuDatabase();

		} catch (Exception e) {
			// error handling
			System.out.print("File not found, check dir for menu.txt or " + newEmployeesFile + ".txt file!");
			e.printStackTrace();

		} // end of catch

		buildSingleEmployee();// building infro from the logged in user

	}// end of constructor

	public void buildUI() {

		// The paramaters add a window name, and this is adding in the console box
		userMenuF = new JFrame(employees.get(singleEmployeeIndex).getTitle() + ": "
				+ employees.get(singleEmployeeIndex).getFirst() + " " + employees.get(singleEmployeeIndex).getLast());

		// This is adding the stuff below the window options and setting a layout
		userMenuP = new JPanel();

		// Adding in UI properties AND seting their locations
		title = new JLabel("Title: " + employees.get(singleEmployeeIndex).getTitle());
		welcome = new JLabel("Welcome");
		name = new JLabel(
				employees.get(singleEmployeeIndex).getFirst() + " " + employees.get(singleEmployeeIndex).getLast());

		information = new JButton("My Information");
		menu = new JButton("Food Menu");
		logout = new JButton("Log out");
		refresh = new JButton("Refresh");

		title.setBounds(132, 35, 100, 40);
		welcome.setBounds(132, 50, 100, 40);
		name.setBounds(190, 50, 350, 40);
		information.setBounds(162, 90, 120, 20);
		menu.setBounds(162, 125, 120, 20);
		logout.setBounds(330, 380, 80, 20);
		refresh.setBounds(330, 420, 80, 20);

		// Events that occur when buttons are pressed VV
		logoutEvent();

		infoEvent();

		foodUIEvent();

		refreshEvent();
		// Events that occur when buttons are pressed^^

		userMenuF.add(title);
		userMenuF.add(welcome);
		userMenuF.add(name);
		userMenuF.add(information);
		userMenuF.add(menu);
		userMenuF.add(logout);
		userMenuF.add(refresh);
		userMenuF.add(userMenuP);// add panel in last*

		userMenuP.setLayout(null);
		userMenuF.setSize(450, 500);
		userMenuF.setLocationRelativeTo(null);
		userMenuF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userMenuF.setVisible(true);
	}// end of buildUI

	public void buildInfoUI() {

		infoMenuF = new JFrame(employees.get(singleEmployeeIndex).getFirst() + " "
				+ employees.get(singleEmployeeIndex).getLast() + "'s Information");

		// This is adding the stuff below the window options and setting a layout
		infoMenuP = new JPanel();

		// for loop to set the bounds of the ArrayList of employee information
		for (int i = 0; i < employeeLabels.size(); i++) {
			// i in here is creating a separation between each label,
			// y * i = lower value each itteration
			employeeLabels.get(i).setBounds(20, i * 25, 200, 40);
			infoMenuF.add(employeeLabels.get(i));
		} // end of for loop

		goBack = new JButton("Go Back");

		goBack.setBounds(125, 210, 100, 20);

		goBackEvent(infoMenuF);// button event

		infoMenuF.add(goBack);
		infoMenuF.add(infoMenuP);// add panel in last*
		infoMenuP.setLayout(null);
		infoMenuF.setSize(250, 280);
		infoMenuF.setLocationRelativeTo(null);
		infoMenuF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		infoMenuF.setVisible(true);
	}// end of buildInfoUI

	public void buildFoodUI() {
		foodMenuF = new JFrame("Food Menu");

		// This is adding the stuff below the window options and setting a layout
		foodMenuP = new JPanel();

		goBack = new JButton("Go Back");

		// for loop used add food items from the foodMenu arraylist to the frame
		for (int i = 0; i < foodMenu.size(); i++) {
			// setting the location for the JLabels for each itteration
			foodMenu.get(i).setBounds(20 * 1, i * 5, 200, 40);
			foodMenuF.add(foodMenu.get(i));
			foodMenu.get(i + 1).setBounds(20 * 3, i * 5, 200, 40);
			foodMenuF.add(foodMenu.get(i + 1));
			foodMenu.get(i + 2).setBounds(20 * 10, i * 5, 200, 40);
			foodMenuF.add(foodMenu.get(i + 2));
			foodMenu.get(i + 3).setBounds(20 * 13, i * 5, 200, 40);
			foodMenuF.add(foodMenu.get(i + 3));

			i = i + 3;// i + 3 is used here because there is 4 values food line
		} // end of for loop

		goBack.setBounds(430, 580, 100, 20);
		goBackEvent(foodMenuF);

		foodMenuF.add(goBack);
		foodMenuF.add(foodMenuP);// add panel in last*
		foodMenuP.setLayout(new FlowLayout());
		foodMenuF.setSize(550, 650);
		foodMenuF.setLocationRelativeTo(null);
		foodMenuF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		foodMenuF.setVisible(true);

	}// end of buildFoodUI

	public void infoEvent() {// used to build the info menu when the info button is pressed
		information.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				userMenuF.dispose();// closes the main menu window
				buildInfoUI();// builds the info menu window

			}
		});
	}// end of infoEvent

	void foodUIEvent() {// used to build the food menu when the menu button is pressed

		menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				userMenuF.dispose();// closes the main menu window
				buildFoodUI();// builds the food menu Window

			}
		});
	}// end of foodUIEvent

	void logoutEvent() {// sends the user back into the login window

		logout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// closes the current window
				userMenuF.dispose();

				try {
					main.buildPathInputUI();
				} catch (Exception e1) {
					System.out.print("Error, employees.txt, was not found! Please check your dir");
					e1.printStackTrace();
				} // end of catch

			}

		});
	}// end of logoutEvent

	void goBackEvent(JFrame isCurrent) {// sends the user back into the main menu
		goBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// closes the current window
				isCurrent.dispose();

				// and goes back to the login window
				buildUI();

			}
		});
	}// end of goBackEvent

	void refreshEvent() {// event that happens when the refresh button is pushed
							// this resends all of the information back into UI for updating
		refresh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				employeesFile = new Reader(employeesToRead);// re-reads the employee txt file
				menuFile = new Reader("menu.txt");
				// clear all ArrayLists for refresh
				employees.clear();
				foodMenu.clear();
				employeeLabels.clear();
				try {// rebuild all information for the refresh
					buildEmployeeDatabase();
					buildSingleEmployee();
					buildMenuDatabase();
				} catch (Exception e1) {

					System.out.print("File not found, check dir for menu.txt or " + singleEmployeeIndex + ".txt file!");

					e1.printStackTrace();
				} // end of catch

				userMenuF.dispose();// disposes of current main menu UI
				buildUI();// recreates the updated main menu UI
			}
		});
	}// end of refreshEvent

	void buildEmployeeDatabase() throws Exception {//
		employeesFile.readFile();// throws exception if employeesFile cannot be read IF it cannot be found.

		// for loop for adding information into employees ArrayList
		for (int i = 0; i < employeesFile.getSize(); i++) {
			employees.add(new Employee(employeesFile.getDataInfo(i), employeesFile.getDataInfo(i + 1),
					employeesFile.getDataInfo(i + 2), employeesFile.getDataInfo(i + 3),
					employeesFile.getDataInfo(i + 4), employeesFile.getDataInfo(i + 5),
					employeesFile.getDataInfo(i + 6), employeesFile.getDataInfo(i + 7),
					employeesFile.getDataInfo(i + 8)));
			i = i + 8;// i is set to "i=i+8" because there is 8 variables per employee line inside the
						// file being read to
		}
	}// end of buildEmployeeDatabase

	void buildMenuDatabase() throws Exception {
		menuFile = new Reader("menu.txt");
		menuFile.readFile();// throws exception if menuFile cannot be read IF it cannot be found.
		
		// for loop for adding information into foodMenu ArrayList
		for (int i = 0; i < menuFile.getSize(); i++) {
			foodMenu.add(new JLabel(menuFile.getDataInfo(i)));
		}

	}// end of buildMenuDatabase

	void buildSingleEmployee() {// building single employee label information
		employeeLabels.add(new JLabel("First Name: " + employees.get(singleEmployeeIndex).getFirst()));
		employeeLabels.add(new JLabel("Last Name: " + employees.get(singleEmployeeIndex).getLast()));
		employeeLabels.add(new JLabel("Birthdate: " + employees.get(singleEmployeeIndex).getBDay()));
		employeeLabels.add(new JLabel("Email: " + employees.get(singleEmployeeIndex).getEmail()));
		employeeLabels.add(new JLabel("Gender: " + employees.get(singleEmployeeIndex).getGender()));
		employeeLabels.add(new JLabel("Position: " + employees.get(singleEmployeeIndex).getTitle()));
		employeeLabels.add(new JLabel("Salary: " + employees.get(singleEmployeeIndex).getSalary()));
		employeeLabels.add(new JLabel("Job Level: " + employees.get(singleEmployeeIndex).getLevel()));
	}// end of buildSingleEmployee

	abstract void exitEvent(JFrame isCurrent);// abstract method for exiting other windows in other classes

	abstract void writeToFile(String textFile) throws Exception;// abstract method for writing to files

	abstract void errorUI(JFrame isCurrent, String errorMessage);// abstract method for making a UI when errors occur

}// end of EmployeeUI class