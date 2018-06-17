import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.google.gson.Gson;

/**
 * This class search member details. On page load, all member details are loaded by default
 * @author neera
 *
 */
public class SearchMembers extends JFrame{

	private JFrame frame;
	private JTextField memberId1;
	private JTextField firstName;
	private JTextField lastName;
	private JComboBox comboBox;
	private int userGroup;
	JPanel dataPanel = new JPanel();
	private String filterString = "";
	private int memberId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchMembers window = new SearchMembers();
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
	public SearchMembers() {
		initialize();
	}

	public SearchMembers(int userGroup, int memberId) {
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
		panel.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Search Members", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		panel.setBackground(new Color(0, 191, 255));
		panel.setPreferredSize(new Dimension(640, 70));
		frame.getContentPane().add(panel, BorderLayout.PAGE_START);
		
		JLabel lblMemberId = new JLabel("Member Id");
		panel.add(lblMemberId);
		
		memberId1 = new JTextField();
		panel.add(memberId1);
		memberId1.setColumns(5);
		
		JLabel lblFirstName = new JLabel("First Name");
		panel.add(lblFirstName);
		
		firstName = new JTextField();
		panel.add(firstName);
		firstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		panel.add(lblLastName);
		
		lastName = new JTextField();
		panel.add(lastName);
		lastName.setColumns(10);
		
		String[] role = {"--Select--", "Admin", "Member"};
		
		JLabel lblRole = new JLabel("Role");
		panel.add(lblRole);
		comboBox = new JComboBox(role);
		panel.add(comboBox);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	// Create filter string as per filter criteria selected
            	filterString = checkFilterCriteria();
            	loadMembers();
		    }
		});
		panel.add(btnSearch);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	reset();
		    }
		});
		panel.add(btnReset);
		
		CommonUtits.createMenu(frame, userGroup, memberId);
		
		// Member details displayed in this panel
		dataPanel.setBackground(new Color(0,0,0,50));
		frame.getContentPane().add(dataPanel, BorderLayout.CENTER);
		
		loadMembers();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void loadMembers() {
		
		dataPanel.removeAll();
		
		DataObject myObject = new DataObject();
		
		String message = "search";
		if (filterString.length() > 0) {
			message = "filteredSearch~" + filterString;
		}
		myObject.setMessage(message);

		myObject = CommonUtits.connectToServer(myObject);
		String resultStr = myObject.getMessage();
		
		Gson gson = new Gson();
		Object[][] rowData = gson.fromJson(resultStr, Object[][].class);
		
		Object columnNames[] = { "Member Id", "First Name", "Last Name", "Address", "Contact Number", "Email Id", "User Name", "Role"};
		JTable table = new JTable(rowData, columnNames);
		table.setEnabled(false);
	    
		JScrollPane scrollPane = new JScrollPane(table);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(400);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(200);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);
		table.getColumnModel().getColumn(7).setPreferredWidth(200);
		table.setRowHeight(40);
		
		scrollPane.setPreferredSize(new Dimension(1000,500));
		
		dataPanel.add(scrollPane);
		SwingUtilities.updateComponentTreeUI(frame);
	}
	
	/**
	 * This method creates the filter string to append to query if any filter criteria is selected
	 * @return
	 */
	private String checkFilterCriteria() {
		String filterString = "";
		
		if (memberId1.getText().trim().length() > 0) {
			filterString = "memberid = " + memberId1.getText().trim();
		}
		if (firstName.getText().trim().length() > 0) {
			if (filterString.length() > 0) {
				filterString = filterString + " and ";
			}
			filterString = filterString + " upper(MEMBERFNAME) = upper('" + firstName.getText().trim() + "')";
		}
		if (lastName.getText().trim().length() > 0) {
			if (filterString.length() > 0) {
				filterString = filterString + " and ";
			}
			filterString = filterString + " upper(MEMBERLNAME) = upper('" + lastName.getText().trim() + "')";
		}
		if (!"--Select--".equals(comboBox.getSelectedItem())) {
			int groupId = -1;
			if (filterString.length() > 0) {
				filterString = filterString + " and ";
			}
			if ("Admin".equals(comboBox.getSelectedItem())) {
				groupId = 1;
			} else {
				groupId = 2;
			}
			filterString = filterString + " GROUPID = " + groupId;
		}
		return filterString;
	}

	private void reset() {
		memberId1.setText("");
		firstName.setText("");
		lastName.setText("");
		comboBox.setSelectedIndex(0);
	}
}
