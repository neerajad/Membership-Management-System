import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class CommonUtits {

	public static DataObject connectToServer(DataObject dataObject) {
		
		try {
			Socket socketToServer = new Socket("afsconnect1.njit.edu", 9999);
			
			ObjectOutputStream myOutputStream = new ObjectOutputStream(socketToServer.getOutputStream());
			ObjectInputStream myInputStream = new ObjectInputStream(socketToServer.getInputStream());

			myOutputStream.writeObject(dataObject);
			dataObject = (DataObject)myInputStream.readObject();
			
			myOutputStream.close();
			myInputStream.close();
	        socketToServer.close();
	        
		} catch(ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException in connectToServer " + cnfe);
		} catch(IOException ioe) {
			System.out.println("IOException in connectToServer " + ioe);
		}
		return dataObject;
	}
	
	public static JFrame createMenu(JFrame frame, int userGroup, int memberId) {
		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(0, 0, 255), Color.BLUE, Color.GRAY, Color.GRAY));
		menuPanel.setBackground(new Color(0,0,0,50));
		menuPanel.setPreferredSize(new Dimension(170, 70));
		frame.getContentPane().add(menuPanel, BorderLayout.WEST);
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
		
		JMenuBar menuBar = new VerticalMenuBar();
		menuBar.setToolTipText("Select a menu");
		menuBar.setAlignmentY(0.10f);
		menuBar.setAlignmentX(0.0f);
		menuPanel.add(menuBar);
		
		JMenuItem mnNewMenu = new JMenuItem(new AbstractAction("Home") {
		    public void actionPerformed(ActionEvent e) {
		    	frame.dispose();
		    	new Home(userGroup, memberId);
		    }
		});
		mnNewMenu.setIcon(new ImageIcon("E:\\NJIT\\Sem2\\Java\\Workspace\\OnlineMembership\\images\\home.png"));
		mnNewMenu.setFont(new Font("Algerian", Font.PLAIN, 13));
		mnNewMenu.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu.setBackground(Color.LIGHT_GRAY);
		mnNewMenu.addMouseListener(new MouseAdapter() {
              public void mouseEntered(MouseEvent arg0) {
            	  mnNewMenu.setBackground(Color.cyan);
              }      
              
              public void mouseExited(MouseEvent arg0) {
            	  mnNewMenu.setBackground(Color.LIGHT_GRAY);
              }                                          
        });
		menuBar.add(mnNewMenu);
		
		JMenuItem mnNewMenu_1 = new JMenuItem(new AbstractAction("Search Members") {
		    public void actionPerformed(ActionEvent e) {
		    	frame.dispose();
		    	new SearchMembers(userGroup, memberId);
		    }
		});
		mnNewMenu_1.setIcon(new ImageIcon("E:\\NJIT\\Sem2\\Java\\Workspace\\OnlineMembership\\images\\search.jpg"));
		mnNewMenu_1.setFont(new Font("Algerian", Font.PLAIN, 12));
		mnNewMenu_1.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu_1.setBackground(Color.LIGHT_GRAY);
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
              public void mouseEntered(MouseEvent arg0) {
            	  mnNewMenu_1.setBackground(Color.cyan);
              }      
              
              public void mouseExited(MouseEvent arg0) {
            	  mnNewMenu_1.setBackground(Color.LIGHT_GRAY);
              }                                          
        });
		menuBar.add(mnNewMenu_1);
		
		if (userGroup == 1) {
			JMenuItem addMenu = new JMenuItem(new AbstractAction("Add Members") {
			    public void actionPerformed(ActionEvent e) {
			    	frame.dispose();
			    	new AddMembers(userGroup, memberId);
			    }
			});
			addMenu.setHorizontalAlignment(SwingConstants.LEFT);
			addMenu.setIcon(new ImageIcon("E:\\NJIT\\Sem2\\Java\\Workspace\\OnlineMembership\\images\\add.png"));
			addMenu.setFont(new Font("Algerian", Font.PLAIN, 12));
			addMenu.setBackground(Color.LIGHT_GRAY);
			addMenu.addMouseListener(new MouseAdapter() {
	              public void mouseEntered(MouseEvent arg0) {
	            	  addMenu.setBackground(Color.cyan);
	              }      
	              
	              public void mouseExited(MouseEvent arg0) {
	            	  addMenu.setBackground(Color.LIGHT_GRAY);
	              }                                          
	        });
			menuBar.add(addMenu);
			
			JMenuItem updateMembers = new JMenuItem(new AbstractAction("Update Members") {
			    public void actionPerformed(ActionEvent e) {
			    	frame.dispose();
			    	new UpdateMembers(userGroup, memberId);
			    }
			});
			updateMembers.setHorizontalAlignment(SwingConstants.LEFT);
			updateMembers.setIcon(new ImageIcon("E:\\NJIT\\Sem2\\Java\\Workspace\\OnlineMembership\\images\\update.png"));
			updateMembers.setFont(new Font("Algerian", Font.PLAIN, 12));
			updateMembers.setBackground(Color.LIGHT_GRAY);
			updateMembers.addMouseListener(new MouseAdapter() {
	              public void mouseEntered(MouseEvent arg0) {
	            	  updateMembers.setBackground(Color.cyan);
	              }      
	              
	              public void mouseExited(MouseEvent arg0) {
	            	  updateMembers.setBackground(Color.LIGHT_GRAY);
	              }                                          
	        });
			menuBar.add(updateMembers);
		}
		
		JMenuItem myAccount = new JMenuItem(new AbstractAction("My Account") {
		    public void actionPerformed(ActionEvent e) {
		    	frame.dispose();
		    	new MyAccount(userGroup, memberId);
		    }
		});
		myAccount.setHorizontalAlignment(SwingConstants.LEFT);
		myAccount.setIcon(new ImageIcon("E:\\NJIT\\Sem2\\Java\\Workspace\\OnlineMembership\\images\\myAcc.jpg"));
		myAccount.setFont(new Font("Algerian", Font.PLAIN, 12));
		myAccount.setBackground(Color.LIGHT_GRAY);
		myAccount.addMouseListener(new MouseAdapter() {
              public void mouseEntered(MouseEvent arg0) {
            	  myAccount.setBackground(Color.cyan);
              }      
              
              public void mouseExited(MouseEvent arg0) {
            	  myAccount.setBackground(Color.LIGHT_GRAY);
              }                                          
        });
		menuBar.add(myAccount);
		
		JMenuItem mnLogout = new JMenuItem(new AbstractAction("Logout") {
		    public void actionPerformed(ActionEvent e) {
		    	frame.dispose();
		    	new Login();
		    }
		});
		mnLogout.setHorizontalAlignment(SwingConstants.LEFT);
		mnLogout.setIcon(new ImageIcon("E:\\NJIT\\Sem2\\Java\\Workspace\\OnlineMembership\\images\\logout.jpg"));
		mnLogout.setFont(new Font("Algerian", Font.PLAIN, 12));
		mnLogout.setBackground(Color.LIGHT_GRAY);
		mnLogout.addMouseListener(new MouseAdapter() {
              public void mouseEntered(MouseEvent arg0) {
            	  mnLogout.setBackground(Color.cyan);
              }      
              
              public void mouseExited(MouseEvent arg0) {
            	  mnLogout.setBackground(Color.LIGHT_GRAY);
              }                                          
        });
		menuBar.add(mnLogout);
	
	return frame;
	}
}

class VerticalMenuBar extends JMenuBar {

	private static final long serialVersionUID = -5178067337384586884L;
	private static final LayoutManager grid = new GridLayout(0, 1);

	public VerticalMenuBar() {
		setLayout(grid);
	}
}