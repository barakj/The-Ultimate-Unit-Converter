import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * A class that creates GUI and functionality of the converter
 */
public class MyFrame extends JFrame {
	//Will only be used if there exists an addition in the conversion formula
	private double formulaAddition;
	//number to multiply (factor of conversion)
	private double factor;
	private JTextField textFrom;
	private JTextField textTo;
	private JLabel from;
	private JLabel to;
	ButtonGroup group;
	private JRadioButton cmToInch;
	private JRadioButton kmToMile;
	private JRadioButton kgToLbs;
	private JRadioButton kgToOz;
	private JRadioButton cTof;
	private JRadioButton cTok;
	private JRadioButton cadToUsd;
	private JRadioButton cadToNis;
	private JButton switchType;
	private JButton submit;
	private JComboBox<String> type;
	//used for border purposes
	private JPanel titlePanel;
	private JPanel step1Panel;
	private JPanel step2Panel;
	private JPanel step3Panel;
	
	/**
     * Constructs a frame/GUI to be displayed when program starts
     */
	public MyFrame(){
		createTitleLabel();
		//used for the border(to indicate step number)
		//Initialize step 1 panel and buttons
		initializeStep1Buttons();
		createStep1Panel();
		//initializing step 2 panel (wont be seen), and buttons
		group = new ButtonGroup();
		initializeStep2Buttons();
		step2Panel = new JPanel();
		//initializing step 3 buttons and panel
		initializeStep3Buttons();
		createStep3Panel();
		//Making sure step2 and step3 panels are hidden
		step2Panel.setVisible(false);
		step3Panel.setVisible(false);
		//creates the main panel and adding it to the frame
		createMainPanel();
	}
	
	/**
     * Creates the label that will contain the main title of the program
     */
	private void createTitleLabel(){
		JLabel title = new JLabel("Welcome to The Ultimate Unit Converter!");
		title.setFont(new Font("Serif", Font.PLAIN, 24));
		titlePanel = new JPanel();
		titlePanel.add(title);
	}
	
	/**
     * Initializes comboBox button for step 1 - categories
     */
	private void initializeStep1Buttons(){
		type = new JComboBox<String>();
		type.addItem("");
		type.addItem("Distance");
		type.addItem("Temperature");
		type.addItem("Weight");
		type.addItem("Currency");
		type.addActionListener(new ComboBoxListener());
	}
	
	/**
     * Creates the panel that will contain step 1 (the category choice)
     */
	private void createStep1Panel(){
		step1Panel = new JPanel();
        JLabel choice = new JLabel("Choose Type of Conversion: ");
        step1Panel.add(choice);
		step1Panel.add(type);
		TitledBorder titled = BorderFactory.createTitledBorder("Step 1");
		step1Panel.setBorder(titled);
	}
	
	/**
     * Initializes step 3 buttons (convert and switch)
     */
	private void initializeStep3Buttons(){
		from = new JLabel("");
		to = new JLabel("");
		submit = new JButton("Convert !");
		submit.addActionListener(new SubmitListener());
		switchType = new JButton("Switch");
		switchType.addActionListener(new SwitchListener());
	}
	
	/**
     * Creates the panel that will contain step 3 (the conversion step)
     */
	private void createStep3Panel(){
		step3Panel = new JPanel();
		step3Panel.setLayout(new GridLayout(3,1));
		JPanel labelPanel = new JPanel();
		JPanel labelPanel2 = new JPanel();
		JPanel labelPanel3 = new JPanel();
		labelPanel3.add(switchType);
		labelPanel3.add(submit);
		textFrom = new JTextField(8);
		textTo = new JTextField(8);
		textTo.setEditable(false);
		
		labelPanel.add(from);
		labelPanel.add(textFrom);
		labelPanel2.add(to);
		labelPanel2.add(textTo);
		step3Panel.add(labelPanel);
		step3Panel.add(labelPanel3);
		step3Panel.add(labelPanel2);
		TitledBorder titled = BorderFactory.createTitledBorder("Step 3");
		step3Panel.setBorder(titled);
	}
	
	/**
     * Creates the main panel of the frame and adds it to the frame
     */
	private void createMainPanel(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4,1));
		mainPanel.add(titlePanel);
		mainPanel.add(step1Panel);
		mainPanel.add(step2Panel);
		mainPanel.add(step3Panel);
		this.add(mainPanel);
	}
	/**
     * Listener class for the submit button (step 3)
     * Validates the user's input and makes the actual conversion
     */
	class SubmitListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			try{
				//round the numbers to 3 decimal places
				DecimalFormat df = new DecimalFormat("#.####");
				df.setRoundingMode(RoundingMode.HALF_UP);
				double originalNum = Double.parseDouble(textFrom.getText());
				originalNum *= factor;
				originalNum += formulaAddition;
				textTo.setText(df.format(originalNum));
			}
			catch(NumberFormatException ex){
				textTo.setText("Numbers only!");
			}
		}
	}
	/**
     * Listener class for the switch button (step 3)
	 * exchanges the from and to labels and use the reciprocal of factor
     */
	class SwitchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//Changing the from label to the to label and vice versa
			String newTo = from.getText();
			String newFrom = to.getText();
			from.setText(newFrom);
			to.setText(newTo);
			textTo.setText("");
			//reciprocal since switching direction
			factor = 1/factor;
			//reciprocal of the addition in the formula
			formulaAddition = -(formulaAddition*factor);
		}
		
	}
	
	/**
     * Listener class for the radio buttons (step 2)
	 * sets up step 3 labels and the factor of conversion according to user's input
     */
	class RadioButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			//Setting up the Step3 text and the factor based on the radioButton chosen
			textFrom.setText("");
			textTo.setText("");
			factor = 0;
			formulaAddition = 0;
			if(cmToInch.isSelected()){
		 		from.setText("Cm: ");
				to.setText("Inch: ");
				factor = Constants.CM_TO_INCH;
			}
			else if(kmToMile.isSelected()){
				from.setText("Km: ");
				to.setText("Mile: ");
				factor = Constants.KM_TO_MILE;
			}
			else if(kgToLbs.isSelected()){
				from.setText("Kg: ");
				to.setText("Lbs: ");
				factor = Constants.KG_TO_LBS;
			}
			else if(kgToOz.isSelected()){
				from.setText("Kg: ");
				to.setText("Oz: ");
				factor = Constants.KG_TO_OZ;
			}
			else if(cTof.isSelected()){
				from.setText("Celsius: ");
				to.setText("Fahrenheit: ");
				factor = Constants.C_TO_F;
				formulaAddition = Constants.C_TO_F_ADDITION;
			}
			else if(cTok.isSelected()){
				from.setText("Celsius: ");
				to.setText("Kelvin: ");
				factor = Constants.C_TO_K;
				formulaAddition = Constants.C_TO_K_ADDITION;
			}
			else if(cadToUsd.isSelected()){
				from.setText("CAD: ");
				to.setText("USD: ");
				factor = Constants.CAD_TO_USD;
			}
			else if(cadToNis.isSelected()){
				from.setText("CAD: ");
				to.setText("NIS: ");
				factor = Constants.CAD_TO_NIS;
			}
			step3Panel.setVisible(true);
			
		}
	}
	/**
     * Listener class for the JComboBox (step 1)
	 * Creates panel for step 2 according to user's input
     */
	class ComboBoxListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//make sure all buttons are not selected
			group.clearSelection();
			from.setText("");
			to.setText("");
			String choice = (String) type.getSelectedItem();
			//make sure empty option is not a valid option
			if(choice.equals(""))
				return;
			//create step2 radio buttons based on type choice
			if(choice.equals("Distance")){
				step2Panel = createDistancestep2Panel();
			}
			else if(choice.equals("Temperature")){
				step2Panel = createTempstep2Panel();
			}
			else if(choice.equals("Weight")){
				step2Panel = createWeightstep2Panel();
			}
			else if(choice.equals("Currency")){
				step2Panel = createCurrencystep2Panel();
			}
			//"repainting"
			updateFrame();
			step2Panel.setVisible(true);
		}
		
	}

	/**
	 * Creates panel for step 2 for distance type conversions
     */
	private JPanel createDistancestep2Panel() {
		cmToInch.setText("Cm/Inch");
		cmToInch.addActionListener(new RadioButtonListener());
		kmToMile.setText("Km/Mile");
		kmToMile.addActionListener(new RadioButtonListener());
		
		JPanel panel = new JPanel();
		panel.add(cmToInch);
		JPanel panel2 = new JPanel();
		panel2.add(kmToMile);
		JPanel manager = new JPanel(new GridLayout(2,1));
		manager.add(panel);
		manager.add(panel2);
		TitledBorder titled = BorderFactory.createTitledBorder("Step 2");
		manager.setBorder(titled);
		return manager;
	}
	 
	/**
	 * Creates panel for step 2 for temperature type conversions
     */
	private JPanel createTempstep2Panel(){
		cTof.setText("Celsius/Fahrenheit");
		cTof.addActionListener(new RadioButtonListener());
		cTok.setText("Celsius/Kelvin");
		cTok.addActionListener(new RadioButtonListener());
		
		JPanel panel = new JPanel();
		panel.add(cTof);
		JPanel panel2 = new JPanel();
		panel2.add(cTok);
		JPanel manager = new JPanel(new GridLayout(2,1));
		manager.add(panel);
		manager.add(panel2);
		TitledBorder titled = BorderFactory.createTitledBorder("Step 2");
		manager.setBorder(titled);
		return manager;
	}
	
	/**
	 * Creates panel for step 2 for weight type conversions
     */
	private JPanel createWeightstep2Panel(){
		kgToLbs.setText("Kg/Lbs");
		kgToLbs.addActionListener(new RadioButtonListener());
		kgToOz.setText("Kg/Oz");
		kgToOz.addActionListener(new RadioButtonListener());
		
		JPanel panel = new JPanel();
		panel.add(kgToLbs);
		JPanel panel2 = new JPanel();
		panel2.add(kgToOz);
		JPanel manager = new JPanel(new GridLayout(2,1));
		manager.add(panel);
		manager.add(panel2);
		TitledBorder titled = BorderFactory.createTitledBorder("Step 2");
		manager.setBorder(titled);
		return manager;
	}
	
	/**
	 * Creates panel for step 2 for currency type conversions
     */
	private JPanel createCurrencystep2Panel(){
		TitledBorder titled;
		titled = BorderFactory.createTitledBorder("Step 2");
		cadToUsd.setText("CAD/USD");
		cadToUsd.addActionListener(new RadioButtonListener());
		cadToNis.setText("CAD/NIS");
		cadToNis.addActionListener(new RadioButtonListener());
		
		JPanel panel = new JPanel();
		panel.add(cadToUsd);
		JPanel panel2 = new JPanel();
		panel2.add(cadToNis);
		JPanel manager = new JPanel(new GridLayout(2,1));
		manager.add(panel);
		manager.add(panel2);
		manager.setBorder(titled);
		return manager;
	}
	
	/**
	 * Deletes the current frame and repaints it
     */
	private void updateFrame(){
		this.getContentPane().removeAll();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4,1));
		
		mainPanel.add(titlePanel);
		mainPanel.add(step1Panel);
		step2Panel.setVisible(false);
		step3Panel.setVisible(false);
		mainPanel.add(step2Panel);
		mainPanel.add(step3Panel);
		
		this.add(mainPanel);

	}
	
	/**
	 * Initializes all step 2 radio buttons and groups them together
     */
	private void initializeStep2Buttons(){
		cmToInch = new JRadioButton();
		kmToMile = new JRadioButton();
		kgToLbs = new JRadioButton();
		kgToOz = new JRadioButton();
		cTof = new JRadioButton();
		cTok = new JRadioButton();
		cadToUsd = new JRadioButton();
		cadToNis = new JRadioButton();
		//Grouping all buttons together, only one can be selected
		group.add(cmToInch);
		group.add(kgToLbs);
		group.add(kgToOz);
		group.add(kmToMile);
		group.add(cTof);
		group.add(cTok);
		group.add(cadToUsd);
		group.add(cadToNis);
	}
	
	
}

