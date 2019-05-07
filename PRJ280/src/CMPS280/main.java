package CMPS280;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class main {

	static JFrame pathF;
	static JPanel pathP;
	
	static JLabel pathL;
	static JLabel invalidPath;
	
	static JTextField pathField;
	
	static JButton select;
	
	static String path;
	
	static Reader read;
	
	public static void main(String[] args) throws Exception {

		buildPathInputUI();

	}
	
	static void buildPathInputUI() {//building a path for inputting the path for files
		
		pathF = new JFrame("Change Path");
		
		pathP = new JPanel();
		
		invalidPath = new JLabel("");
		pathL = new JLabel("Input Path for Files");
		
		pathField = new JTextField(Reader.filePath);
		
		select = new JButton("Select");
		
		pathL.setBounds(85,50,200,20);
		
		pathField.setBounds(115,75,150,25);
		
		select.setBounds(10, 75, 100,25);
		
		invalidPath.setBounds(85,100,185,25);
		
		
		pathF.add(pathL);
		pathF.add(pathField);
		pathF.add(select);
		pathF.add(invalidPath);
		pathF.add(pathP);
		
		selectEvent();
		
		pathF.setSize(300, 200);
		pathF.setLocationRelativeTo(null);
		pathF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pathF.setVisible(true);
	}
	
	static void selectEvent() {// This is what happens when the select button is pushed
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			path = pathField.getText();
			Reader.filePath = path + "\\";

			try {
				UI loginUI = new UI("employees.txt");
				pathF.dispose();
			} catch (Exception e1) {
				invalidPath.setText("Invalid Path was Selected");
				e1.printStackTrace();
			}
			}
		});
	}// end of selectEvent
	
}
