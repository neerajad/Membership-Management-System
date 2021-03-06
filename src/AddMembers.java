import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

public class AddMembers {

	private JFrame frame;
	private JTextField firstNameField;
	private JLabel lblLastName;
	private JLabel lblApartmentNumber;
	private JLabel lblStreetName;
	private JLabel lblCity;
	private JLabel lblState;
	private JTextField lastNameField;
	private JTextField apartment;
	private JTextField street;
	private JTextField state;
	private JFormattedTextField mobile;
	private JTextField emailId;
	private JTextField userName;
	private JTextField password;
	private JButton btnAddMember;
	private JButton btnReset;
	private JRadioButton rdbtnAdministrator;
	private JRadioButton rdbtnMember;
	private JLabel lblMobile;
	private JTextField city;
	private int userGroup;
	private JLabel lblEmailId;
	private JLabel lblUserName;
	private JLabel lblPassword;
	private JLabel lblRole;
	private int memberId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMembers window = new AddMembers();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddMembers() {
		initialize();
	}
	
	public AddMembers(int userGroup, int memberId) {
		this.userGroup = userGroup;
		this.memberId = memberId;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1400, 740);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new JLabel(new ImageIcon("E:\\NJIT\\Sem2\\Java\\Workspace\\OnlineMembership\\images\\background.png")));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setPreferredSize(new Dimension(640, 70));
		frame.getContentPane().add(panel, BorderLayout.PAGE_START);
		
		JLabel lblMembershipManagementSystem = new JLabel("Membership Management System");
		lblMembershipManagementSystem.setForeground(Color.BLACK);
		lblMembershipManagementSystem.setFont(new Font("Algerian", Font.BOLD, 36));
		panel.add(lblMembershipManagementSystem);
		
		CommonUtits.createMenu(frame, userGroup, memberId);
		
		JPanel addMemberForm = new JPanel();
		addMemberForm.setBackground(new Color(0,0,0,50));
		UIManager.getDefaults().put( "TitledBorder.font", new javax.swing.plaf.FontUIResource( new Font( "Arial", Font.BOLD, 20 ) ) ) ;
		frame.getContentPane().add(addMemberForm, BorderLayout.CENTER);
		addMemberForm.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), "Add New Member", TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0))));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 53, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -24, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		addMemberForm.setLayout(gridBagLayout);
		
		lblRole = new JLabel("Role");
		GridBagConstraints gbc_lblRole = new GridBagConstraints();
		gbc_lblRole.insets = new Insets(0, 0, 5, 5);
		gbc_lblRole.gridx = 13;
		gbc_lblRole.gridy = 4;
		addMemberForm.add(lblRole, gbc_lblRole);
		
		rdbtnAdministrator = new JRadioButton("Administrator");
		rdbtnAdministrator.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_rdbtnAdministrator = new GridBagConstraints();
		gbc_rdbtnAdministrator.insets = new Insets(10, 10, 10, 10);
		gbc_rdbtnAdministrator.gridx = 14;
		gbc_rdbtnAdministrator.gridy = 4;
		rdbtnAdministrator.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	rdbtnMember.setSelected(false);
	        }
	    });
		addMemberForm.add(rdbtnAdministrator, gbc_rdbtnAdministrator);
		
		rdbtnMember = new JRadioButton("Member");
		rdbtnMember.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_rdbtnMember = new GridBagConstraints();
		gbc_rdbtnMember.insets =new Insets(10, 10, 10, 10);
		gbc_rdbtnMember.gridx = 16;
		gbc_rdbtnMember.gridy = 4;
		rdbtnMember.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	rdbtnAdministrator.setSelected(false);
	        }
	    });
		
		addMemberForm.add(rdbtnMember, gbc_rdbtnMember);
		
		JLabel lblFirstName = new JLabel("First Name :");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets =new Insets(10, 10, 10, 10);
		gbc_lblFirstName.gridx = 13;
		gbc_lblFirstName.gridy = 5;
		addMemberForm.add(lblFirstName, gbc_lblFirstName);
		
		firstNameField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets =new Insets(10, 10, 10, 10);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 17;
		gbc_textField.gridy = 5;
		addMemberForm.add(firstNameField, gbc_textField);
		firstNameField.setColumns(15);
		
		lblLastName = new JLabel("Last Name :");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.WEST;
		gbc_lblLastName.insets =new Insets(10, 10, 10, 10);
		gbc_lblLastName.gridx = 13;
		gbc_lblLastName.gridy = 6;
		addMemberForm.add(lblLastName, gbc_lblLastName);
		
		lastNameField = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets =new Insets(10, 10, 10, 10);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 17;
		gbc_textField_1.gridy = 6;
		addMemberForm.add(lastNameField, gbc_textField_1);
		lastNameField.setColumns(10);
		
		lblApartmentNumber = new JLabel("Apartment :");
		GridBagConstraints gbc_lblApartmentNumber = new GridBagConstraints();
		gbc_lblApartmentNumber.anchor = GridBagConstraints.WEST;
		gbc_lblApartmentNumber.insets =new Insets(10, 10, 10, 10);
		gbc_lblApartmentNumber.gridx = 13;
		gbc_lblApartmentNumber.gridy = 7;
		addMemberForm.add(lblApartmentNumber, gbc_lblApartmentNumber);
		
		apartment = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets =new Insets(10, 10, 10, 10);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 17;
		gbc_textField_2.gridy = 7;
		addMemberForm.add(apartment, gbc_textField_2);
		apartment.setColumns(10);
		
		lblStreetName = new JLabel("Street :");
		GridBagConstraints gbc_lblStreetName = new GridBagConstraints();
		gbc_lblStreetName.anchor = GridBagConstraints.WEST;
		gbc_lblStreetName.insets =new Insets(10, 10, 10, 10);
		gbc_lblStreetName.gridx = 13;
		gbc_lblStreetName.gridy = 8;
		addMemberForm.add(lblStreetName, gbc_lblStreetName);
		
		street = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets =new Insets(10, 10, 10, 10);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 17;
		gbc_textField_3.gridy = 8;
		addMemberForm.add(street, gbc_textField_3);
		street.setColumns(10);
		
		lblCity = new JLabel("City :");
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.anchor = GridBagConstraints.WEST;
		gbc_lblCity.insets =new Insets(10, 10, 10, 10);
		gbc_lblCity.gridx = 13;
		gbc_lblCity.gridy = 9;
		addMemberForm.add(lblCity, gbc_lblCity);
		
		city = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets =new Insets(10, 10, 10, 10);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 17;
		gbc_textField_4.gridy = 9;
		addMemberForm.add(city, gbc_textField_4);
		city.setColumns(10);
		
		lblState = new JLabel("State :");
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.anchor = GridBagConstraints.WEST;
		gbc_lblState.insets =new Insets(10, 10, 10, 10);
		gbc_lblState.gridx = 13;
		gbc_lblState.gridy = 10;
		addMemberForm.add(lblState, gbc_lblState);
		
		state = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.anchor = GridBagConstraints.NORTH;
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.insets =new Insets(10, 10, 10, 10);
		gbc_textField_5.gridx = 17;
		gbc_textField_5.gridy = 10;
		addMemberForm.add(state, gbc_textField_5);
		state.setColumns(10);
		
		
		lblMobile = new JLabel("Mobile");
		GridBagConstraints gbc_lblMobile = new GridBagConstraints();
		gbc_lblMobile.anchor = GridBagConstraints.WEST;
		gbc_lblMobile.insets =new Insets(10, 10, 10, 10);
		gbc_lblMobile.gridx = 13;
		gbc_lblMobile.gridy = 11;
		addMemberForm.add(lblMobile, gbc_lblMobile);
		
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    
		mobile = new JFormattedTextField(formatter);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets =new Insets(10, 10, 10, 10);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 17;
		gbc_textField_6.gridy = 11;
		addMemberForm.add(mobile, gbc_textField_6);
		mobile.setColumns(10);
		
		lblEmailId = new JLabel("Email Id");
		GridBagConstraints gbc_lblEmailId = new GridBagConstraints();
		gbc_lblEmailId.anchor = GridBagConstraints.WEST;
		gbc_lblEmailId.insets =new Insets(10, 10, 10, 10);
		gbc_lblEmailId.gridx = 13;
		gbc_lblEmailId.gridy = 12;
		addMemberForm.add(lblEmailId, gbc_lblEmailId);
		
		emailId = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets =new Insets(10, 10, 10, 10);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 17;
		gbc_textField_7.gridy = 12;
		addMemberForm.add(emailId, gbc_textField_7);
		emailId.setColumns(10);
		
		lblUserName = new JLabel("User Name");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.WEST;
		gbc_lblUserName.insets =new Insets(10, 10, 10, 10);
		gbc_lblUserName.gridx = 13;
		gbc_lblUserName.gridy = 13;
		addMemberForm.add(lblUserName, gbc_lblUserName);
		
		userName = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.insets =new Insets(10, 10, 10, 10);
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.gridx = 17;
		gbc_textField_8.gridy = 13;
		addMemberForm.add(userName, gbc_textField_8);
		userName.setColumns(10);
		
		lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets =new Insets(10, 10, 10, 10);
		gbc_lblPassword.gridx = 13;
		gbc_lblPassword.gridy = 14;
		addMemberForm.add(lblPassword, gbc_lblPassword);
		
		password = new JTextField();
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets =new Insets(10, 10, 10, 10);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 17;
		gbc_textField_9.gridy = 14;
		addMemberForm.add(password, gbc_textField_9);
		password.setColumns(10);
		
		btnAddMember = new JButton("Add Member");
		GridBagConstraints gbc_btnAddMember = new GridBagConstraints();
		gbc_btnAddMember.anchor = GridBagConstraints.NORTH;
		gbc_btnAddMember.insets =new Insets(10, 10, 10, 10);
		gbc_btnAddMember.gridx = 14;
		gbc_btnAddMember.gridy = 16;
		btnAddMember.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (validatefields()) {
	        		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to add?");
		        	if (confirm == 0) {
		        		AddMember();
		        	}
	        	} else {
	        		JOptionPane.showMessageDialog(null, "Please enter all values before saving");
	        	}
	        }
	    });
		addMemberForm.add(btnAddMember, gbc_btnAddMember);
		
		btnReset = new JButton("Reset");
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.NORTH;
		gbc_btnReset.insets =new Insets(10, 10, 10, 10);
		gbc_btnReset.gridx = 16;
		gbc_btnReset.gridy = 16;
		btnReset.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	reset();
	        }
	    });
		addMemberForm.add(btnReset, gbc_btnReset);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void AddMember() {
		DataObject myObject = new DataObject();
		
		//Select the role based on radio button selected
		int role = 0;
		if (rdbtnAdministrator.isSelected()) {
			role = 1;
		} else if (rdbtnMember.isSelected()) {
			role = 2;
		}
		
		//Create the message to sent to server
		String querySring = firstNameField.getText() + "~" + lastNameField.getText() + "~"
				+ apartment.getText() + ", " + street.getText() + ", " + city.getText() + ", " + state.getText() + "~" + mobile.getText() + "~" + role + "~" +
				emailId.getText() + "~" +
				userName.getText() + "~" +
				password.getText();
		
		myObject.setMessage("add" + "~" + querySring);
	
		myObject = CommonUtits.connectToServer(myObject);
		String status = myObject.getMessage();
		
		if ("success".equals(status)) {
			JOptionPane.showMessageDialog(null, "Added Successfully");
			reset();
		} else if("duplicate".equals(status)) {
			JOptionPane.showMessageDialog(null, "User Name already in use. Please use a different one");
		}
		else {
			JOptionPane.showMessageDialog(null, "An error occurred. Please try after sometime");
		}
		SwingUtilities.updateComponentTreeUI(frame);
	}
	
	/**
	 * This method reset all fields
	 */
	private void reset() {
		firstNameField.setText("");
		lastNameField.setText("");
		apartment.setText("");
		street.setText("");
		city.setText("");
		state.setText("");
		mobile.setText("");
		emailId.setText("");
		userName.setText("");
		password.setText("");
		rdbtnAdministrator.setSelected(false);
		rdbtnMember.setSelected(false);
	}
	
	/**
	 * Validate if all input fields are filled
	 * @return boolean
	 */
	private boolean validatefields() {
		if (firstNameField.getText().length() == 0 || 
			lastNameField.getText().length() == 0 ||
			apartment.getText().length() == 0 ||
			street.getText().length() == 0 ||
			city.getText().length() == 0 ||
			state.getText().length() == 0 ||
			mobile.getText().length() == 0 ||
			emailId.getText().length() == 0 ||
			userName.getText().length() == 0 ||
			password.getText().length() == 0 ||
			(!rdbtnAdministrator.isSelected() && !rdbtnMember.isSelected())) {
			return false;
		}
		return true;
	}
}

