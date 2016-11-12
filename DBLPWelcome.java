import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DBLPWelcome extends JFrame {
	private JPanel panel;
	private JLabel label;
	private JButton startButton;
	private JButton exitButton;
	private final int WINDOW_HGT = 300;
	private final int WINDOW_WTH = 400;

	public DBLPWelcome () {
		panel = new JPanel();
		this.setTitle("DBLP Query Engine");
		this.setSize(WINDOW_WTH, WINDOW_HGT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		label = new JLabel("Welcome to the DBLP Query Engine");

		startButton = new JButton("Start the Engine");
		startButton.addActionListener(new OpenEngine());

		exitButton = new JButton("Exit");
		exitButton.addActionListener(new CloseListener());

		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(15, 15, 15, 15);
		panel.add(label, gbc);

		// gbc.fill = GridBagConstraints.NONE;
		// gbc.gridx = 1;
		// gbc.gridy = 1;
		// gbc.insets = new Insets(15, 15, 15, 15);
		// panel.add(messageLabel2, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 0, 0);
		panel.add(startButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 3;
		// gbc.insets = new Insets(3, 3, 3, 3);
		panel.add(exitButton, gbc);

		add(panel);

		this.setVisible(true);
	}

	private class CloseListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
	}

	private class OpenEngine implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			// setVisible(false);
		}
	}

	public static void main(String[] args) {
		new DBLPWelcome();
	}
}