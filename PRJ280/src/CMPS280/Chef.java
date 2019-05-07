package CMPS280;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;

public class Chef extends EmployeeUI {

	private JFrame foodOrderUIF;
	private JPanel foodOrderUIP;

	// text area and scroll area for employee&menu database
	private JTextArea textArea;
	private JScrollPane orderScrollArea;

	private JButton checkOrders;
	private JButton updateText;
	private JButton updateOrders;

	private Reader orderFileReader;

	private PrintWriter write;

	Chef(int newEmployeeIndex, String newEmployeesFile) {// Constructor

		super(newEmployeeIndex, newEmployeesFile);

		buildUI();// building mainUI

	}// end of constructor

	public void buildUI() {// building main UI with some additions

		super.buildUI();// adding basic basic template from super class

		checkOrders = new JButton("Check Order");

		checkOrders.setBounds(162, 160, 120, 20);

		getOrders();// even that happens when button is pushed

		userMenuF.add(checkOrders);
		userMenuF.add(userMenuP);// add panel in last*

	}// end of buildUI

	void getOrders() {// event that happens when you press the check order button

		checkOrders.addActionListener(new ActionListener() {

			// Adds an event to the listener so that something happens whenever the botton
			// is pushed
			public void actionPerformed(ActionEvent e) {

				userMenuF.dispose();// disposes main menu

				buildOrdersUI();// builds the UI for checking orders

			}

		});
	}// end of getOrders

	void buildOrdersUI() {// builds the UI for checking orders

		foodOrderUIF = new JFrame("Customer Food Orders");

		// This is adding the stuff below the window options and setting a layout
		foodOrderUIP = new JPanel();

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		orderScrollArea = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		goBack = new JButton("Go Back");
		updateText = new JButton("Update");
		updateOrders = new JButton("Update Orders");

		// This is used to create a label, or just plane words.

		orderScrollArea.setBounds(75, 75, 245, 450);
		goBack.setBounds(270, 550, 100, 20);
		updateText.setBounds(160, 550, 100, 20);
		updateOrders.setBounds(10, 550, 140, 20);

		// Events that happens when a button is pressed VV
		updateEvent();
		updateOrderEvent();
		goBackEvent(foodOrderUIF);
		// Events that happens when a button is pressed ^^

		foodOrderUIF.add(goBack);
		foodOrderUIF.add(updateText);
		foodOrderUIF.add(updateOrders);
		foodOrderUIF.add(orderScrollArea);
		foodOrderUIF.add(foodOrderUIP);// add panel in last*

		foodOrderUIP.setLayout(null);
		foodOrderUIF.setSize(400, 635);
		foodOrderUIF.setLocationRelativeTo(null);
		foodOrderUIF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		foodOrderUIF.setVisible(true);
	}// end of buildOrdersUI

	void updateEvent() {// event that happens when the update button is pressed

		updateText.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);// sets the text area to null
				orderFileReader = new Reader("orders.txt");// reading into orders.txt file

				try {
					orderFileReader.readFile();

					for (int i = 0; i < orderFileReader.getSize(); i++) {
						textArea.append(orderFileReader.getDataInfo(i) + "\n");
					} // end of for

				} catch (FileNotFoundException e1) {// error handling
					System.out.print("ERROR cannot read orders.txt, please check your dir for orders.txt");
					errorUI(foodOrderUIF, "ERROR cannot read orders.txt, please check your dir for orders.txt");
					e1.printStackTrace();
				} // end of catch

			}

		});

	}// end of updateEvent

	void writeToFile(String textFile) throws FileNotFoundException {// writes to orders file

		write = new PrintWriter(Reader.filePath + textFile);

		write.print(textArea.getText());// input whats in the text area to the orders file

		write.close();
	}// end of writeToFile

	void updateOrderEvent() {// updates order file with whats in current text area

		updateOrders.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					writeToFile("orders.txt");

				} catch (FileNotFoundException e1) {

					System.out.print("ERROR, cannot find orders.txt to write to, please check your dir for orders.txt");
					errorUI(foodOrderUIF,
							"ERROR, cannot find orders.txt to write to, please check your dir for orders.txt");
					e1.printStackTrace();
				} // end of catch

			}

		});

	}// end of updateOrderEvent

	void exitEvent(JFrame isCurrent) {// used to exit out of error windows back into buildOrdersUI
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				isCurrent.dispose();
				buildOrdersUI();
			}
		});
	}// end of exitEvent

	void errorUI(JFrame isCurrent, String errorMessage) {//Error UI as explained in previous classes
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

}// end of chef class
