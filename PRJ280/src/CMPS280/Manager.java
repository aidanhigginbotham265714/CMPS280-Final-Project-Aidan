package CMPS280;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Manager extends EmployeeUI {

	private JFrame dataUIF;
	private JPanel dataUIP;

	private JScrollPane databaseScrollArea;// adds a scroll pane into the JTable

	private Reader databaseFileReader;

	private JButton add;
	private JButton sort;
	private JButton updateText;
	private JButton updateFile;
	private JButton menuData;
	private JButton employeeData;

	private JLabel employeeDatabaseTitle;

	private PrintWriter write;// Writes to files

	private JFrame menuDataUIF;
	private JPanel menuDataUIP;

	private DefaultTableModel model;
	private JTable dataTable;

	private Object[] row;// an array dedicated to taking in values for each row being inputted into the
							// JTable

	private ArrayList<String> forSorting = new ArrayList<String>();

	Manager(int newEmployeeIndex, String newEmployeesFile) throws Exception {
		super(newEmployeeIndex, newEmployeesFile);
		buildUI();
	}// end of constructor

	public void buildUI() {// building main UI with some additions
		super.buildUI();// adding in the basic template for buildUI from super

		employeeData = new JButton("Employee Database");
		menuData = new JButton("Menu Database");

		employeeData.setBounds(142, 160, 160, 20);
		menuData.setBounds(142, 190, 160, 20);

		// events that happen when buttons are pushed vv
		getDatabase();
		getMenuDatabase();
		// events that happen when buttons are pushed ^^

		userMenuF.add(menuData);
		userMenuF.add(employeeData);
		userMenuF.add(userMenuP);// add panel in last*

	}// end of buildUI

	void getDatabase() {// happens when the employeeData button is pushed
						// this disposed of the main menu then opens a new UI

		employeeData.addActionListener(new ActionListener() {

			// Adds an event to the listener so that something happens whenever the botton
			// is pushed
			public void actionPerformed(ActionEvent e) {

				userMenuF.dispose();

				buildDataBaseUI();// builds the employee database UI

			}

		});
	}// end of getDatabase

	void getMenuDatabase() {// happens when the menuData button is pushed
		// this disposed of the main menu then opens a new UI

		menuData.addActionListener(new ActionListener() {

			// Adds an event to the listener so that something happens whenever the botton
			// is pushed
			public void actionPerformed(ActionEvent e) {

				userMenuF.dispose();

				buildMenuUIDatabase();// builds the menu databaseUI

			}

		});
	}// end of getMenuDatabase

	void buildDataBaseUI() {// building the databaseUI for employees
		dataUIF = new JFrame("Employee Database");

		// This is adding the stuff below the window options and setting a layout
		dataUIP = new JPanel();

		dataTable = new JTable();

		Object[] columns = { "Title:", "FName:", "LName:", "BDate:", "Email:", "PWord:", "Salary:", "Level:",
				"Gender:" };// This is a single array of the base columns added to the top of the JTabel

		model = new DefaultTableModel();// creating the DefaultTableModel and naming it to model

		model.setColumnIdentifiers(columns);// This is what sets the columns to the model template

		dataTable.setModel(model);// setting the model we made to the JTable we have made

		databaseScrollArea = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);// adding the scrollpane to the JTable

		sort = new JButton("Sort List");
		updateText = new JButton("Update Text Box");
		updateFile = new JButton("Update Employee File");
		goBack = new JButton("Go Back");
		add = new JButton("Add Item");

		employeeDatabaseTitle = new JLabel("Employee Database");

		// This is used to create a label, or just plane words.
		sort.setBounds(250, 400, 140, 20);
		databaseScrollArea.setBounds(25, 75, 600, 300);
		goBack.setBounds(500, 450, 100, 20);
		updateText.setBounds(250, 450, 150, 20);
		updateFile.setBounds(30, 450, 175, 20);
		employeeDatabaseTitle.setBounds(250, 50, 200, 20);
		add.setBounds(30, 400, 140, 20);

		// Events that happen when buttons are pushed vv
		updateEvent(dataUIF, 9, "employees.txt");
		updateDatabaseEvent(dataUIF, "employees.txt");
		goBackEvent(dataUIF);
		addEvent(dataUIF, 9);
		sortEvent("employees.txt");
		// Events that happen when buttons are pushed ^^

		dataUIF.add(sort);
		dataUIF.add(employeeDatabaseTitle);
		dataUIF.add(databaseScrollArea);
		dataUIF.add(updateText);
		dataUIF.add(updateFile);
		dataUIF.add(goBack);
		dataUIF.add(add);
		dataUIF.add(dataUIP);// add panel in last*

		dataUIP.setLayout(null);
		dataUIF.setSize(660, 525);
		dataUIF.setLocationRelativeTo(null);
		dataUIF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dataUIF.setVisible(true);

	}// end of builBataBaseUI

	void buildMenuUIDatabase() {// building the menu database *similar to employeeDatabase*
		menuDataUIF = new JFrame("Menu Database");

		// This is adding the stuff below the window options and setting a layout
		menuDataUIP = new JPanel();

		dataTable = new JTable();

		Object[] columns = { "Item Number", "Item Name", "Unit Cost", "Catagory" };

		model = new DefaultTableModel();

		model.setColumnIdentifiers(columns);

		dataTable.setModel(model);

		databaseScrollArea = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		updateText = new JButton("Update Text Box");
		updateFile = new JButton("Update Menu File");
		add = new JButton("Add Item");
		goBack = new JButton("Go Back");
		employeeDatabaseTitle = new JLabel("Menu Database");

		// This is used to create a label, or just plane words.

		databaseScrollArea.setBounds(75, 75, 470, 450);
		goBack.setBounds(500, 565, 100, 20);
		updateText.setBounds(250, 565, 150, 20);
		updateFile.setBounds(30, 565, 175, 20);
		employeeDatabaseTitle.setBounds(250, 50, 200, 20);
		add.setBounds(30, 535, 145, 20);

		// Events that happen when buttons are pushed vv
		updateEvent(menuDataUIF, 4, "menu.txt");
		updateDatabaseEvent(menuDataUIF, "menu.txt");
		addEvent(menuDataUIF, 4);
		goBackEvent(menuDataUIF);
		// Events that happen when buttons are pushed ^^

		menuDataUIF.add(employeeDatabaseTitle);
		menuDataUIF.add(databaseScrollArea);
		menuDataUIF.add(updateText);
		menuDataUIF.add(updateFile);
		menuDataUIF.add(goBack);
		menuDataUIF.add(add);
		menuDataUIF.add(menuDataUIP);// add panel in last*

		menuDataUIP.setLayout(null);
		menuDataUIF.setSize(620, 650);
		menuDataUIF.setLocationRelativeTo(null);
		menuDataUIF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuDataUIF.setVisible(true);
	}// end of buildMenuUIDatabase

	void updateEvent(JFrame isCurrent, int line, String fileToRead) {// line is the amount of variables per line within
																		// a file, this is updating the UI with the
																		// database info read from a file

		updateText.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				forSorting.clear();
				databaseFileReader = new Reader(fileToRead);
				model.setRowCount(0);// setting rows back to default 0
				row = new Object[line];// setting the size of the array to line, which is amount of var per line

				try {
					databaseFileReader.readFile();

					for (int i = 0, j = 0; i < databaseFileReader.getSize(); i++, j++) {
						if (j == line) {// if j == line, then reset j to 0, then add the row to model.
							j = 0; // this is done because the array can only be so big, but
									// the info in the file is large, so we add 1 line of info, then reset j to
									// start a new row
							model.addRow(row);
						}

						row[j] = databaseFileReader.getDataInfo(i);// adding information into row array, to be added to
																	// the JTable

					}

					for (int i = databaseFileReader.getSize() - line, j = 0; i < databaseFileReader
							.getSize(); i++, j++) {// adding the last line of info to rows
						row[j] = databaseFileReader.getDataInfo(i);
					}
					model.addRow(row);

				} catch (Exception e1) {// error handling
					System.out.print("ERROR, cannot read file, check dir!");
					errorUI(isCurrent, "ERROR, cannot read file, check dir!");
					e1.printStackTrace();
				}

			}

		});

	}// end of updateEvent

	void writeToFileSortEmployees(String textFile) throws FileNotFoundException {

		write = new PrintWriter(Reader.filePath + textFile);

		for (int i = 0; i < forSorting.size(); i++) {
			write.print(forSorting.get(i));
		}
		write.close();
	}

	void writeToFile(String textFile) throws Exception {// writes back to the JTables that theyre reading from

		write = new PrintWriter(Reader.filePath + textFile);

		for (int row = 0; row < model.getRowCount(); row++) {// for loop to look at every row and column value in the
																// JTable
			for (int col = 0; col < model.getColumnCount(); col++) {

				write.print(model.getValueAt(row, col) + "\n");
			}
		}

		write.close();
	}// end of writeToFile

	void updateDatabaseEvent(JFrame isCurrent, String fileToRead) {
		// ^^ This is what happens when the updateFile is pushed

		updateFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					writeToFile(fileToRead);
				} catch (Exception e1) {// error handling
					errorUI(isCurrent, "ERROR, cannot read file, check dir!");
					System.out.print("ERROR, cannot write to file, check dir!");

					e1.printStackTrace();
				}

			}

		});

	}// end of updateDatabaseEvent

	void exitEvent(JFrame isCurrent) {// this is what happens when the exit button is pushed
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCurrent.dispose();// close your current frame

				buildUI();// re build the main UI
			}
		});
	}// end of exitEvent

	void addEvent(JFrame isCurrent, int line) {// This is what happens when the add button is pushed
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					for (int i = 0; i < line; i++) {// adds a blank row to the JTable
						row[i] = null;
					}
					model.addRow(row);
				} catch (NullPointerException e1) {// error handling
					errorUI(isCurrent, "Invalid Input Action");
					throw new WrongInputException();
				}
			}
		});
	}// end of addEvent

	void sortEvent(String fileToRead) {// This is what happens when the sort button is pushed
		sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				forSorting.clear();// clears the array list
				databaseFileReader = new Reader(fileToRead);

				try {
					databaseFileReader.readFile();

					for (int i = 0; i < databaseFileReader.getSize(); i++) {// inputting data from the file
																						// into the array list with the
																						// correct format

						forSorting.add(new String(databaseFileReader.getDataInfo(i) + " "
								+ databaseFileReader.getDataInfo(i + 1) + " " + databaseFileReader.getDataInfo(i + 2)
								+ " " + databaseFileReader.getDataInfo(i + 3) + " "
								+ databaseFileReader.getDataInfo(i + 4) + " " + databaseFileReader.getDataInfo(i + 5)
								+ " " + databaseFileReader.getDataInfo(i + 6) + " "
								+ databaseFileReader.getDataInfo(i + 7) + " " + databaseFileReader.getDataInfo(i + 8)
								+ " "));

						i = i + 8;//adding 8 because there is 8 variable per employee line
					}//end of for

					Collections.sort(forSorting);
					writeToFileSortEmployees("employees.txt");
				} catch (Exception e1) {// error handling
					System.out.print("ERROR, cannot read file, check dir!");
					e1.printStackTrace();
				}//end of catch
			}
		});

	}// end of sortEvent

	void errorUI(JFrame isCurrent, String errorMessage) {// explained in previous classes
		isCurrent.dispose();

		errorF = new JFrame("Error");
		errorP = new JPanel();

		error = new JLabel(errorMessage);

		exit = new JButton("Exit");

		exitEvent(errorF);

		error.setBounds(10, 50, 500, 20);
		exit.setBounds(100, 100, 105, 20);

		errorF.add(error);
		errorF.add(exit);
		errorF.add(errorP);// add panel in last*

		errorP.setLayout(null);
		errorF.setSize(450, 500);
		errorF.setLocationRelativeTo(null);
		errorF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		errorF.setVisible(true);

	}// end errorUI

}// end of Manager class
