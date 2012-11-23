package ca.cs304.client;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.*;
public class LibraryMainView extends JFrame {
	
	Connection connection;

	JPanel panel;
	JButton borrowerButton = new JButton(" Borrower ");
	JButton librarianButton = new JButton("  Librarian  ");
	JButton clerkButton = new JButton("Clerk");
	JButton testButton = new JButton ("Test & Debug");

	//LibraryLibrarianrView librarianView;
	//LibraryClerkView clerkView;
	LibraryBorrowerView borrowerView;

	public LibraryMainView()
	{

		// call the superclass constructor
		super("Library Database");
		buildMenuBar();
		
		panel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(new BorderLayout());
		panel.setLayout(gb);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel welcomeLabel = new JLabel("Welcome to the University Library. Please choose your user view.");

		testButton.addMouseListener(new ViewButtonListener());
		clerkButton.addMouseListener(new ViewButtonListener());
		librarianButton.addMouseListener(new ViewButtonListener());
		borrowerButton.addMouseListener(new ViewButtonListener());
		testButton.setBackground(Color.white);
		clerkButton.setBackground(Color.white);
		borrowerButton.setBackground(Color.white);
		librarianButton.setBackground(Color.white);



		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets (0, 10, 10, 0);
		gb.setConstraints(welcomeLabel, c);
		panel.add(welcomeLabel);


		//c.gridx = 0;
		c.ipady = 20;
		c.ipadx = 50;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weightx = 0.1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 0, 0, 5);
		gb.setConstraints(borrowerButton, c);
		panel.add(borrowerButton);

		//c.gridx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 0, 0, 5);
		gb.setConstraints(clerkButton, c);
		panel.add(clerkButton);

		//c.gridx = 2;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(5, 0, 0, 5);
		gb.setConstraints(librarianButton, c);
		panel.add(librarianButton);

		//c.gridx = 3;
		c.insets = new Insets(5, 0, 0, 5);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(testButton, c);
		panel.add(testButton);

		getContentPane().add(panel, BorderLayout.CENTER);

		this.pack();

		// this line terminates the program when the X button
		// located at the top right hand corner of the
		// window is clicked
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	class ViewButtonListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e){
			JButton source = (JButton) e.getSource();
			if (source == borrowerButton){
				openBorrower();
				panel.setVisible(false);
			}
			else if (source == clerkButton){
				openClerk();
				panel.setVisible(false);
			}
			else if (source == librarianButton){
				openManager();
				panel.setVisible(false);
			}
			else if (source == testButton){
				openTest();
			}
			
		}
	}
	private void buildMenuBar() {
		JMenuBar menuBar;
		JMenu userMenu;
		JMenuItem logoutItem, exitItem, viewItem;


		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		userMenu = new JMenu("User");
		userMenu.setMnemonic(KeyEvent.VK_U);
		menuBar.add(userMenu);
		this.setJMenuBar(menuBar);

		viewItem = new JMenuItem("Change user view", KeyEvent.VK_C);
		userMenu.add(viewItem);
		viewItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!panel.isVisible()){
					//if(librarianView != null) librarianView.mainPanel.setVisible(false);
					//if(clerkView != null) clerkView.mainPanel.setVisible(false);
					if (borrowerView != null) borrowerView.setVisible(false);
					panel.setVisible(true);
					pack();
					Dimension d = getToolkit().getScreenSize();
					Rectangle r = getBounds();
					setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
				}

			}

		});
		userMenu.addSeparator();
		logoutItem = new JMenuItem("Logout", KeyEvent.VK_L);
		userMenu.add(logoutItem);
		logoutItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				try {
//					LibraryLogin.getLogin().getCon().close();
//					mainView.dispose();
//					new LibraryLogin();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}

			}

		});

		exitItem = new JMenuItem("Exit Library", KeyEvent.VK_X);
		userMenu.add(exitItem);
		exitItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}

		});


	}
	public void openTest() {
		TestView testView = new TestView(connection);
		testView.showMenu();
		this.dispose();
	}
	public void openManager() {
//		librarianView = new LibraryLibrarianView();
//		mainView.add(manView.loadManager(), BorderLayout.NORTH);
//		mainView.setSize(mainView.getToolkit().getScreenSize());
//		mainView.setLocation(0, 0);

	}
	public void openClerk() {
//		clerkView = new LibraryClerkView();
//		mainView.add(clerkView.loadClerk(), BorderLayout.NORTH);
//		mainView.setSize(mainView.getToolkit().getScreenSize());
//		mainView.setLocation(0, 0);

	}
	
	public void openBorrower() {
		borrowerView = new LibraryBorrowerView();
		add(borrowerView, BorderLayout.NORTH);
	}
	
	public static void main(String[] args)
	{
		LibraryMainView mainFrame = new LibraryMainView();
		// this line causes the window to be sized to its
		// preferred size (this essentially compresses the
		// window)
		mainFrame.pack();
		// center window on screen
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		// Initially, the JFrame is invisible. This line
		// makes the window visible.
		mainFrame.setVisible(true);
	}
}
