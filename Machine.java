package enigma;
import  javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;

public class Machine {

	public Machine() {
		
		//Rotor Controller for all your purposes!
		RotaryController rotors = new RotaryController(1);
		
		JFrame window = new JFrame("Anorak's Enigma");
		window.setSize(620,690);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		window.setBackground(Color.black);
		window.getContentPane().setBackground(Color.black);
		
		//panel settings for input text box
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(15,5,575,300);
		inputPanel.setLayout(new GridLayout(1,1));
		inputPanel.setBorder(BorderFactory.createLineBorder(Color.white));
		JTextArea input = new JTextArea();
		input.setFont(new Font("Cascadia Code", Font.PLAIN, 14));
		input.setBackground(Color.black);
		input.setForeground(Color.white);
		input.setLineWrap(true);
		input.setWrapStyleWord(true);
		inputPanel.add(input);
		window.add(inputPanel);
		
		//Panel settings for rotor adjustment
		JPanel rotorPanel = new JPanel();
		GridLayout rotorLayout = new GridLayout(1,6);
		rotorLayout.setHgap(15);
		rotorPanel.setLayout(rotorLayout);
		rotorPanel.setBounds(15,315,575,30);
		String[] rotorOptions = {"IC", "IIC", "IIIC", "I", "II", "III", "UKW", "EKW", "KBD"};
		JComboBox<String> rotor1 = new JComboBox<String>(rotorOptions);
		JComboBox<String> rotor2 = new JComboBox<String>(rotorOptions);
		JComboBox<String> rotor3 = new JComboBox<String>(rotorOptions);
		rotor1.setEditable(false);
		rotor2.setEditable(false);
		rotor2.setSelectedIndex(1);
		rotor3.setEditable(false);
		rotor3.setSelectedIndex(2);
		rotor1.setBackground(Color.black);
		rotor1.setForeground(Color.white);
		rotor2.setBackground(Color.black);
		rotor2.setForeground(Color.white);
		rotor3.setBackground(Color.black);
		rotor3.setForeground(Color.white);
		rotorPanel.setBackground(Color.black);
		JLabel r1Label = new JLabel("Rotor 1:");
		r1Label.setForeground(Color.white);
		JLabel r2Label = new JLabel("Rotor 2:");
		r2Label.setForeground(Color.white);
		JLabel r3Label = new JLabel("Rotor 3:");
		r3Label.setForeground(Color.white);
		rotorPanel.add(r1Label);
		rotorPanel.add(rotor1);
		rotorPanel.add(r2Label);
		rotorPanel.add(rotor2);
		rotorPanel.add(r3Label);
		rotorPanel.add(rotor3);
		window.add(rotorPanel);
		
		//Panel settings for starting offset settings
		JPanel offsetPanel = new JPanel();
		GridLayout offsetLayout = new GridLayout(1,8);
		offsetPanel.setBackground(Color.black);
		offsetLayout.setHgap(7);
		offsetPanel.setLayout(offsetLayout);
		offsetPanel.setBounds(15,355,575,30);
		String[] offsetOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", 
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24", "25"};
		JComboBox<String> offset1 = new JComboBox<String>(offsetOptions);
		JComboBox<String> offset2 = new JComboBox<String>(offsetOptions);
		JComboBox<String> offset3 = new JComboBox<String>(offsetOptions);
		JComboBox<String> increment = new JComboBox<String>(offsetOptions);
		offset1.setEditable(false);
		offset2.setEditable(false);
		offset3.setEditable(false);
		increment.setEditable(false);
		offset1.setBackground(Color.black);
		offset1.setForeground(Color.white);
		offset2.setBackground(Color.black);
		offset2.setForeground(Color.white);
		offset3.setBackground(Color.black);
		offset3.setForeground(Color.white);
		increment.setBackground(Color.black);
		increment.setForeground(Color.white);
		
		JLabel o1Label = new JLabel("Offset 1:");
		o1Label.setForeground(Color.white);
		JLabel o2Label = new JLabel("Offset 2:");
		o2Label.setForeground(Color.white);
		JLabel o3Label = new JLabel("Offset 3:");
		o3Label.setForeground(Color.white);
		JLabel iLabel = new JLabel("Increment:");
		iLabel.setForeground(Color.white);
		offsetPanel.add(o1Label);
		offsetPanel.add(offset1);
		offsetPanel.add(o2Label);
		offsetPanel.add(offset2);
		offsetPanel.add(o3Label);
		offsetPanel.add(offset3);
		offsetPanel.add(iLabel);
		offsetPanel.add(increment);
		window.add(offsetPanel);
		
		//panel settings for the reflector
		JPanel reflectPanel = new JPanel();
		reflectPanel.setBackground(Color.black);
		GridLayout reflectLayout = new GridLayout(1,8);
		reflectLayout.setHgap(5);
		reflectPanel.setLayout(reflectLayout);
		reflectPanel.setBounds(15,395,575,30);
		JCheckBox reflector = new JCheckBox();
		reflector.setBackground(Color.black);
		reflector.setForeground(Color.white);
		reflectPanel.add(new JLabel(""));
		reflectPanel.add(new JLabel(""));
		reflectPanel.add(new JLabel(""));
		JLabel rLabel = new JLabel("Reflector?");
		rLabel.setForeground(Color.white);
		reflectPanel.add(rLabel);
		reflectPanel.add(reflector);
		reflectPanel.add(new JLabel(""));
		reflectPanel.add(new JLabel(""));
		reflectPanel.add(new JLabel(""));
		window.add(reflectPanel);
		
		//panel settings for the encode/decode buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);
		GridLayout buttonLayout = new GridLayout(1,2);
		buttonLayout.setHgap(30);
		buttonPanel.setLayout(buttonLayout);
		buttonPanel.setBounds(15,435,575,30);
		JButton encodeButton = new JButton("Encode");
		encodeButton.setBackground(Color.black);
		encodeButton.setForeground(Color.white);
		JButton decodeButton = new JButton("Decode");
		decodeButton.setBackground(Color.black);
		decodeButton.setForeground(Color.white);
		buttonPanel.add(encodeButton);
		buttonPanel.add(decodeButton);
		window.add(buttonPanel);
		
		//action listener for everything
		ActionListener act = e -> {
			//Encode Button
			if(e.getSource() == encodeButton) {
				String encMessage = rotors.encode(input.getText(), reflector.isSelected());
				input.setText(encMessage);
				
				//reset the rotors
				rotors.setOffset(0, Integer.parseInt(offset1.getItemAt(offset1.getSelectedIndex())));
				rotors.setOffset(1, Integer.parseInt(offset2.getItemAt(offset2.getSelectedIndex())));
				rotors.setOffset(2, Integer.parseInt(offset3.getItemAt(offset3.getSelectedIndex())));
			}
			
			//Decode Button
			if(e.getSource() == decodeButton) {
				String decMessage = rotors.decode(input.getText(), reflector.isSelected());
				input.setText(decMessage);
				
				//reset the rotors
				rotors.setOffset(0, Integer.parseInt(offset1.getItemAt(offset1.getSelectedIndex())));
				rotors.setOffset(1, Integer.parseInt(offset2.getItemAt(offset2.getSelectedIndex())));
				rotors.setOffset(2, Integer.parseInt(offset3.getItemAt(offset3.getSelectedIndex())));
			}
			
			//Change Encoding on rotor 1
			if(e.getSource() == rotor1) {
				rotors.setWiring(0, rotor1.getItemAt(rotor1.getSelectedIndex()));
			}
			
			//Change Encoding on rotor 2
			if(e.getSource() == rotor2) {
				rotors.setWiring(1, rotor2.getItemAt(rotor2.getSelectedIndex()));
			}
			
			//Change Encoding on rotor 3
			if(e.getSource() == rotor3) {
				rotors.setWiring(2, rotor3.getItemAt(rotor3.getSelectedIndex()));
			}
			
			//Change Offset on rotor 1
			if(e.getSource() == offset1) {
				rotors.setOffset(0, Integer.parseInt(offset1.getItemAt(offset1.getSelectedIndex())));
			}
			
			//Change Offset on rotor 2
			if(e.getSource() == offset2) {
				rotors.setOffset(1, Integer.parseInt(offset2.getItemAt(offset2.getSelectedIndex())));
			}
			
			//Change Offset on rotor 3
			if(e.getSource() == offset3) {
				rotors.setOffset(2, Integer.parseInt(offset3.getItemAt(offset3.getSelectedIndex())));
			}
		};
		
		//Add the listener to all the buttons
		encodeButton.addActionListener(act);
		decodeButton.addActionListener(act);
		rotor1.addActionListener(act);
		offset1.addActionListener(act);
		rotor2.addActionListener(act);
		offset2.addActionListener(act);
		rotor3.addActionListener(act);
		offset3.addActionListener(act);
		
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Machine();
		
	}

}