import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login {

	private JFrame frame;
	private JTextField userName;
	private JPasswordField password;
	private int userGroup;
	private int memberId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1400, 740);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel(new ImageIcon("E:\\NJIT\\Sem2\\Java\\Workspace\\OnlineMembership\\images\\background.png"));
		label.setFont(new Font("Script MT Bold", Font.BOLD | Font.ITALIC, 11));
		label.setForeground(Color.BLACK);
		frame.setContentPane(label);
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
		
		JLabel message = new JLabel("Login to Membership Management System");
		message.setFont(new Font("Lucida Calligraphy", Font.BOLD, 20));
		message.setForeground(SystemColor.window);
		message.setBounds(502, 172, 524, 30);
		frame.getContentPane().add(message);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Lucida Calligraphy", Font.BOLD, 12));
		lblUserName.setBounds(502, 272, 124, 30);
		frame.getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Lucida Calligraphy", Font.BOLD, 12));
		lblPassword.setBounds(502, 319, 70, 30);
		frame.getContentPane().add(lblPassword);
		
		userName = new JTextField();
		userName.setBounds(676, 269, 124, 25);
		frame.getContentPane().add(userName);
		userName.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(676, 316, 124, 25);
		frame.getContentPane().add(password);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(582, 372, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		lblMembershipManagementSystem = new JLabel(" ");
		lblMembershipManagementSystem.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		lblMembershipManagementSystem.setBounds(202, 49, 360, 23);
		frame.getContentPane().add(lblMembershipManagementSystem);
		
		btnLogin.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	callServer(userName.getText(), String.valueOf(password.getPassword()));
		    }
		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * This method makes call to the server running on AFS
	 * @param user
	 * @param password
	 */
	private void callServer(String user, String password) {
		try {
			DataObject myObject = new DataObject();
			myObject.setMessage("login~" + user + "~" + password);

			myObject = CommonUtits.connectToServer(myObject);
			String userDetails = myObject.getMessage();
			
			if (userDetails.length() > 0) {
				String[] userArr = userDetails.split("~");
				userGroup = Integer.parseInt(userArr[0]);
				memberId = Integer.parseInt(userArr[1]);
				
				if (userGroup != -1) {
					frame.dispose();
		        	new Home(userGroup, memberId);
		        } else {
		        	JOptionPane.showMessageDialog(null, "Invalid Login : User Name or Password incorrect");
		        }
			} else {
	        	JOptionPane.showMessageDialog(null, "Invalid Login : User Name or Password incorrect");
	        }
		} catch(Exception e){
			System.out.println("Exception in callServer " + e);
		}
	}
}
