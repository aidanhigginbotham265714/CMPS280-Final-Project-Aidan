package CMPS280;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

//Waiter is extending EmployeeUI, so it inherits the basic get information and get food menu.
public class Waiter extends EmployeeUI {

	private Date todaysDate = new Date();// used to check the days date for the UI

	// ArrayList of checkBoxes that correlates with amount of food items
	private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

	// ArrayList of textBoxes that correlates with amount of food items
	private ArrayList<JTextField> textBoxes = new ArrayList<JTextField>();

	// ArrayList of JLabels that correlates with amount of food items
	private ArrayList<JLabel> orderedItemsL = new ArrayList<JLabel>();

	// ArrayList to get get an integer from the String number price in the items
	// text file that is being read into
	private ArrayList<Integer> numberPrice = new ArrayList<Integer>();

	// JLabels for food menu UI or BillUI
	private JLabel title;
	private JLabel address;
	private JLabel teleNumber;
	private JLabel server;
	private JLabel date;
	private JLabel itemsL;
	private JLabel priceL;
	private JLabel total;
	private JLabel tax;

	// Buttons for food menuUI or BillUI
	private JButton addSelected;
	private JButton removeSelected;
	private JButton createBill;
	private JButton sendOrder;

	// JTextArea and ScrollPane for adding food items
	private JTextArea addedItems;
	private JScrollPane scrollItems;

	private JTextField lineNumber;// used to input lineNumber to remove from txtArea

	private File billFile = new File(Reader.filePath + "bill.txt");
	private File ordersFile = new File(Reader.filePath + "orders.txt");

	private PrintWriter write;

	private JFrame billF;// Frame for bill menu
	private JPanel billP;// Panel for bill menu

	private int placeHolder = 0;// used later to increase a Buttons range as items are added into the menu
	static int totalOrders = 1;// static item to keep track of total orders for the day between any waiter

	private double priceTotal = 0;// used later, set to 0, so it stays as 0 default.
	private double taxNumber = 0;

	private Reader orderedItems;// reader to read into the orders file to look into ordered items

	Waiter(int newEmployeeIndex, String newEmployeesFile) {

		super(newEmployeeIndex, newEmployeesFile);// calling the super classes constructor
													// to build databases and construct employee info

		genderCheck();// checking gender, if female, change title to "Waitress"
		buildUI();// building the main UI

	}// end of constructor

	public void buildUI() {// overriding buildUI from super class
		super.buildUI();// building the UI with the superclasses buildUI method
	}// end of buildUI

	public void buildFoodUI() {// overriding buildFoodUI from super class
		checkBoxes.clear();// clearing checkboxes ArrayList for refresh
		textBoxes.clear();// clearing textBoxes ArrayList for refresh

		buildCheckBoxes();// re-building checkboxes
		buildTextBoxes();// re-building textboxes

		super.buildFoodUI();// Constructing the superclasses foodUI method

		addSelected = new JButton("Add Selected");
		removeSelected = new JButton("Remove Line");
		createBill = new JButton("Generate Bill");

		addedItems = new JTextArea(10, 10);
		addedItems.setLineWrap(true);
		scrollItems = new JScrollPane(addedItems, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		lineNumber = new JTextField("");

		// for loop for setting the check boxes and textboxes, correllating with the
		// amount of food items there are in the food txt file
		for (int i = 1; i < checkBoxes.size(); i++) {
			// i is being used here to correctly formate and separate
			// the checkboxes and text boxes
			checkBoxes.get(i).setBounds(20 * 16, ((i * 2) + 1) * 10, 20, 20);// 2*5, 2*15, 2*25, 2*35//1,3,5,7
			textBoxes.get(i).setBounds(20 * 17, ((i * 2) + 1) * 10, 20, 15);// 2*5, 2*15, 2*25, 2*35//1,3,5,7
			foodMenuF.add(checkBoxes.get(i));
			foodMenuF.add(textBoxes.get(i));
		} // end of for

		scrollItems.setBounds(370, 50, 190, 450);
		addSelected.setBounds(20, 500, 140, 20);
		removeSelected.setBounds(170, 500, 140, 20);
		createBill.setBounds(385, 505, 140, 20);
		lineNumber.setBounds(315, 500, 20, 20);

		// Events that occur when buttons are pressed VV
		addEvent();
		removeEvent();
		createBillEvent();
		// Events that occur when buttons are pressed ^^

		foodMenuF.add(scrollItems);
		foodMenuF.add(addSelected);
		foodMenuF.add(removeSelected);
		foodMenuF.add(lineNumber);
		foodMenuF.add(createBill);
		foodMenuF.setSize(585, 650);
		foodMenuF.add(foodMenuP);// add panel in last*

	}// end of buildFoodUI

	void genderCheck() {// checking the gender of of the waiter
		// if the waiter is a female, change their title to "waitress"
		if (employees.get(singleEmployeeIndex).getGender().equals("F"))
			employees.get(singleEmployeeIndex).setTitle("Waitress");
	}// end of genderCheck

	void buildCheckBoxes() {// creating check boxes
		// for loop for adding a check box for the size of the foodMenu ArrayList
		for (int i = 0; i < foodMenu.size() / 4; i++) {
			checkBoxes.add(new JCheckBox());
		} // end of for
	}// end of buildCheckBoxes

	void buildTextBoxes() {// creating text boxes
		// for loop for adding a text box for the size of the foodMenu ArrayList
		for (int i = 0; i < checkBoxes.size(); i++) {
			textBoxes.add(new JTextField(""));
		} // end of for
	}// end of buildTextBoxes

	// building the BillUI for when the bill is generated by waiter
	void buildBillUI() {
		placeHolder = 0;
		priceTotal = 0;
		taxNumber = 0;

		billF = new JFrame("Receipt");
		billP = new JPanel();

		title = new JLabel("Wing Kingdom");
		address = new JLabel("123 The Park");
		teleNumber = new JLabel("#: 555-555-5555");
		date = new JLabel("Date: " + todaysDate.toString());
		server = new JLabel("Server: " + employees.get(singleEmployeeIndex).getFirst() + "     Order#: " + totalOrders);
		itemsL = new JLabel("Food Items:");
		priceL = new JLabel("Price:");

		sendOrder = new JButton("Send Order");
		exit = new JButton("Exit");

		// for loop that formats the orderedItemsL JLabels ArrayList
		for (int i = 0; i < orderedItemsL.size(); i++) {
			placeHolder++;// add up placeholder for each iteration
							// this is counting how many items there will be

			// formatting the arraylist of labels into the UI
			orderedItemsL.get(i).setBounds(165, 125 + i * (10), 200, 100);
			billF.add(orderedItemsL.get(i));
			orderedItemsL.get(i + 1).setBounds(165 - 155, 125 + i * (10), 200, 100);
			billF.add(orderedItemsL.get(i + 1));
			orderedItemsL.get(i + 2).setBounds(165 * 2, 125 + i * (10), 200, 100);
			billF.add(orderedItemsL.get(i + 2));

			// adding the String price of the items into an actual arraylist of Integers
			numberPrice.add(new Integer(Integer.parseInt("0")));
			numberPrice.add(new Integer(Integer.parseInt(orderedItemsL.get(i + 1).getText())));
			numberPrice.add(new Integer(Integer.parseInt(orderedItemsL.get(i + 2).getText())));

			// adding up all of the numberPrice integers as they are made, to get a total
			// cost for the waiter and customer
			priceTotal += (numberPrice.get(i + 1) * (numberPrice.get(i + 2)));

			// i + 2 is used here because to overall help formatting shown in the first
			// lines of the for loop
			i = i + 2;

			placeHolder = placeHolder + 2;
		} // end of for

		// calc tax cost
		taxNumber = priceTotal * 0.1;

		// adding tax cost and formatting total
		priceTotal = (priceTotal + taxNumber) / 100;

		tax = new JLabel("Tax: " + taxNumber / 100);
		total = new JLabel("Total: " + priceTotal);

		title.setBounds(175, 40, 100, 40);
		address.setBounds(180, 55, 100, 40);
		teleNumber.setBounds(165, 70, 100, 40);
		date.setBounds(50, 95, 300, 40);
		server.setBounds(275, 95, 300, 40);
		itemsL.setBounds(165, 125, 100, 40);
		priceL.setBounds(295, 125, 50, 40);
		tax.setBounds(295, 125 + placeHolder * (10), 100, 100);
		total.setBounds(295, 140 + placeHolder * (10), 100, 100);

		// Events when buttons are pressed vv
		exitEvent(billF);
		sendOrderEvent();
		// Events when buttons are pressed ^^

		exit.setBounds(315, 515, 105, 20);
		sendOrder.setBounds(315, 490, 105, 20);

		billF.add(exit);
		billF.add(sendOrder);
		billF.add(title);
		billF.add(address);
		billF.add(teleNumber);
		billF.add(date);
		billF.add(server);
		billF.add(itemsL);
		billF.add(priceL);
		billF.add(tax);
		billF.add(total);
		billF.add(billP);// add last
		billP.setLayout(null);
		billF.setSize(450, 580);
		billF.setLocationRelativeTo(null);
		billF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		billF.setVisible(true);
	}// end of buildBillUI

	void addEvent() {// event used to added checked off items into textArea

		addSelected.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < checkBoxes.size(); i++) {
					if (checkBoxes.get(i).isSelected()) {// check if the checkBoxes are selected

						// inserts the textFiles text amount if the check box is checked
						addedItems.insert(menuFile.getDataInfo(i * 4 + 1) + " x " + textBoxes.get(i).getText() + " "
								+ menuFile.getDataInfo(i * 4 + 2) + "\n", 0);

					} // end of if
				} // end of for

			}

		});

	}// end of addEvent

	void removeEvent() {// event for removing items from the textArea

		removeSelected.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int line = Integer.parseInt(lineNumber.getText()) - 1;
				try {// deletes specified line
					int start = addedItems.getLineStartOffset(line);
					int end = addedItems.getLineEndOffset(line);
					addedItems.replaceRange("", start, end);
				} catch (BadLocationException e1) {
						errorUI(foodMenuF,"Invalid Input: " + line);
						throw new WrongInputException(line);	
				} // end of catch

			}

		});

	}// end of removeEvent

	void writeToFile(String x) {// writes to bill file

		billFile.delete();// deletes previous billFile, to maintain receipt generation

		try {
			write = new PrintWriter(billFile);
			write.print(addedItems.getText());// get and print text from the textArea
			write.close();
		} catch (FileNotFoundException e1) {// error handling

			System.out.print("Please check your dir for bill.txt!");
			errorUI(foodMenuF, "Please check your dir for bill.txt!");
			e1.printStackTrace();
		} // end of catch

	}// end of writeToFile

	void writeToOrdersFile() throws Exception {
		orderedItems = new Reader(billFile);// reading billFile to print to orders file

		orderedItems.readFile();// reading the files individual text

		write = new PrintWriter(new FileWriter(ordersFile, true));// creating new printwriter

		write.print("Order#_" + totalOrders + "\n");// Printing this in out of for loop, because its needed but not in
													// billFile

		// for loop for writing to ordersFile
		for (int i = 0; i < orderedItems.getSize() / 2; i++) {
			write.print("Item_" + orderedItems.getDataInfo((i * 2)) + "_");// only want even numbers
			write.print("Amount_" + orderedItems.getDataInfo((i * 2) + 2) + "\n");// only want even numbers
			i = i + 1;// i + 1 is used here because 2 of the items are being printed at a time

		} // end of for

		write.close();// closing printwriter
	}// end of writeToOrdersFile

	void createBillEvent() {// event that creates a bill when pushed

		createBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// clearing things before re-creating things, this is needed to avoid repetition
					numberPrice.clear();
					orderedItemsL.clear();
					// re-building
					buildOrderedItemsL();
					foodMenuF.dispose();
					buildBillUI();
				} catch (Exception e1) {// error handling

					System.out.print("You must input a number in the text box when adding an item!");
					errorUI(foodMenuF, "You must input a number in the text box when adding an item!");

					e1.printStackTrace();
				} // end of catch
			}

		});
	}// end of createBillEvent

	void exitEvent(JFrame isCurrent) {// event that exits current window and goes back to foodmenu UI
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				isCurrent.dispose();// disposing current window

				buildFoodUI();// going back into food ui Menu
			}
		});
	}// end of exitEvent

	void sendOrderEvent() {// things that happen when send order button is pushed

		sendOrder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				billF.dispose();// dispose of bill menu

				JLabel success = new JLabel("Successfully added the order!");// create success message
				JLabel failed = new JLabel("ERROR could not read file!");// create failed message

				exit = new JButton("Exit");

				JFrame Orderframe = new JFrame();

				JPanel panel = new JPanel();
				panel.setLayout(null);

				success.setBounds(10, -10, 300, 300);
				failed.setBounds(10, -10, 300, 300);
				exit.setBounds(40, 75, 100, 50);

				// event for exiting
				exitEvent(Orderframe);

				Orderframe.setSize(200, 200);
				Orderframe.setLocationRelativeTo(null);
				Orderframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Orderframe.setVisible(true);

				try {// do this when successful
					writeToOrdersFile();
					totalOrders++;
					Orderframe.add(success);
					Orderframe.add(exit);
					Orderframe.add(panel);

				} catch (Exception e1) {// do this when error occurs
					Orderframe.add(failed);
					Orderframe.add(exit);
					Orderframe.add(panel);
				} // end of catch

			}
		});
	}

	void buildOrderedItemsL() throws Exception {// adding labels into the orderedItems arrayList

		writeToFile(null);// this is set to null because the parameters are unimportant in this class

		orderedItems = new Reader(billFile);// reading stuff
		orderedItems.readFile();// reading into file

		// for loop to add stuff to the arraylist
		for (int i = 0; i < orderedItems.getSize(); i++) {
			if (orderedItems.getDataInfo(i).equals("x")) {
			} else {
				orderedItemsL.add(new JLabel(orderedItems.getDataInfo(i)));
			}
		} // end of for

	}// end of buildOrderedItemsL

	// errorUI is for creating a errorUI and message when an error occurs
	void errorUI(JFrame isCurrent, String errorMessage) {// takes in a string for making an error message
		// closes current JFrame
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
}// end of Waiter class