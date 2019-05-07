package CMPS280;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Host extends EmployeeUI {

	// These data fields are set to private because these properties should NOT be
	// allowed to be edited from anywhere else besides its current class.
	private JFrame resMenuF;
	private JPanel resMenuP;

	private JScrollPane databaseScrollArea;

	private Reader databaseFileReader;

	private JButton reservation;
	private JButton add;
	private JButton updateText;
	private JButton updateFile;

	private JLabel reservationTitle;

	private PrintWriter write;

	private DefaultTableModel model;
	private JTable dataTable;

	private Object[] row;

	Host(int newEmployeeIndex, String newEmployeesFile) throws Exception {
		super(newEmployeeIndex, newEmployeesFile);

		buildUI();

		genderCheck();
	}// end of constructor

	public void buildUI() {// overriding the buildUI method
		super.buildUI();// adding in super buildUI, with extra stuff

		reservation = new JButton("Reservations");

		reservation.setBounds(162, 160, 120, 20);

		reservationUIEvent();

		userMenuF.add(reservation);
		userMenuF.add(userMenuP);// add panel in last*
	}// end of contructor

	void reservationUIEvent() {// what happens when the reservation button is pressed

		reservation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				userMenuF.dispose();// close main menu
				buildReservationUI();// build reservation menu

			}

		});
	}// end of reservationEvent

	void buildReservationUI() {// builds the reservation menu
								// Similar to databaseUIs in manager, explained there

		resMenuF = new JFrame("Reservation tables");

		// This is adding the stuff below the window options and setting a layout
		resMenuP = new JPanel();

		reservationTitle = new JLabel("Customer Reservations");

		dataTable = new JTable();

		Object[] collums = { "Table #", "Customer Name", "# of Guest", "Add Date", "Phone #", "Plan Time", "Status" };

		model = new DefaultTableModel();

		model.setColumnIdentifiers(collums);

		dataTable.setModel(model);

		databaseScrollArea = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		updateText = new JButton("Update Text Box");
		updateFile = new JButton("Update Menu File");
		add = new JButton("Add Item");
		goBack = new JButton("Go Back");

		// This is used to create a label, or just plane words.

		reservationTitle.setBounds(250, 50, 200, 20);
		databaseScrollArea.setBounds(75, 75, 470, 450);
		goBack.setBounds(500, 565, 100, 20);
		updateText.setBounds(250, 565, 150, 20);
		updateFile.setBounds(30, 565, 175, 20);
		add.setBounds(30, 535, 145, 20);

		// events that happens when buttons are pushed vv
		updateEvent(resMenuF, 7, "reservations.txt");
		updateDatabaseEvent(resMenuF, "reservations.txt");
		goBackEvent(resMenuF);
		addEvent(7);
		// events that happens when buttons are pushed ^^

		resMenuF.add(add);
		resMenuF.add(reservationTitle);
		resMenuF.add(databaseScrollArea);
		resMenuF.add(updateText);
		resMenuF.add(updateFile);
		resMenuF.add(goBack);
		resMenuF.add(resMenuP);// add panel in last*

		resMenuP.setLayout(null);
		resMenuF.setSize(620, 650);
		resMenuF.setLocationRelativeTo(null);
		resMenuF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		resMenuF.setVisible(true);

	}// end of buildReservationUI

	void genderCheck() {// checks the gender of the employee and changes their titles if they are female

		if (employees.get(singleEmployeeIndex).getGender().equals("F"))
			employees.get(singleEmployeeIndex).setTitle("Hostess");

	}// end of genderCheck

	void updateEvent(JFrame isCurrent, int line, String fileToRead) {// line is the amount of variables per line within
		// a file, same as managers updateEvent, check there for more info as to whats
		// happening

		updateText.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				databaseFileReader = new Reader(fileToRead);
				model.setRowCount(0);
				row = new Object[line];

				try {
					databaseFileReader.readFile();

					for (int i = 0, j = 0; i < databaseFileReader.getSize(); i++, j++) {
						if (j == line) {
							j = 0;
							model.addRow(row);
						}

						row[j] = databaseFileReader.getDataInfo(i);

					}

					for (int i = databaseFileReader.getSize() - line, j = 0; i < databaseFileReader
							.getSize(); i++, j++) {
						row[j] = databaseFileReader.getDataInfo(i);
					}
					model.addRow(row);

				} catch (Exception e1) {
					System.out.print("ERROR, cannot read file, check dir!");
					errorUI(isCurrent, "ERROR, cannot read file, check dir!");
					e1.printStackTrace();
				}

			}

		});

	}// end of updateEvent

	void updateDatabaseEvent(JFrame isCurrent, String fileToRead) {
		// same as managers updateDatabaseEvent, check there for more info as to whats
		// happening in this method

		updateFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					writeToFile(fileToRead);
				} catch (Exception e1) {
					errorUI(isCurrent, "ERROR, cannot read file, check dir!");
					System.out.print("ERROR, cannot write to file, check dir!");

					e1.printStackTrace();
				}

			}

		});

	}// end of updateDatabaseEvent

	void exitEvent(JFrame isCurrent) {// event that happens when exit is pushed
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCurrent.dispose();
				buildReservationUI();
			}
		});
	}// end ofexitEvent

	void writeToFile(String textFile) throws FileNotFoundException {
		// writes to the file that the JTable is being read from
		write = new PrintWriter(Reader.filePath + textFile);

		for (int row = 0; row < model.getRowCount(); row++) {// for loop to look at every row and column value in the
																// JTable
			for (int col = 0; col < model.getColumnCount(); col++) {

				write.print(model.getValueAt(row, col) + "\n");
			}
		}

		write.close();
	}// end of writeToFile

	void addEvent(int line) {
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					for (int i = 0; i < line; i++) {
						row[i] = null;
					}
					model.addRow(row);
				} catch (NullPointerException e1) {
					errorUI(resMenuF, "Invalid Input Action");
					throw new WrongInputException();
				}
			}
		});
	}// end of addEvent

	void errorUI(JFrame isCurrent, String errorMessage) {

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

	}// end of errorUI

}// end of Host class
