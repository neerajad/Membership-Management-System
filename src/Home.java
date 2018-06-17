import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

/**
 * This class is the home page of the application
 * @author neera
 *
 */
public class Home {

	private JFrame frame;
	private int userGroup;
	private int memberId;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Home() {
		initialize();
	}
	
	/**
	 * Create the application.
	 */
	public Home(int userGroup, int memberId) {
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
		
		JLabel welcomeMsg = new JLabel("Welcome To Membership Management System. Please use the menu for navigation");
		welcomeMsg.setFont(new Font("Gabriola", Font.BOLD | Font.ITALIC, 22));
		welcomeMsg.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(welcomeMsg, BorderLayout.CENTER);
		
		CommonUtits.createMenu(frame, userGroup, memberId);
	    
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}