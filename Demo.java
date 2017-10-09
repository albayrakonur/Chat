import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Demo {

	private JFrame frame;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JLabel lblId;
	private JLabel lblPw;
	private JLabel info;
	private JButton btnLogin;
	private String username = "";
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JTextField textField;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private DBConnect connect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo window = new Demo();
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
	public Demo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		connect = new DBConnect();
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(300, 200, 675, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblId = new JLabel("id");
		lblId.setBounds(138, 79, 70, 15);
		frame.getContentPane().add(lblId);

		lblPw = new JLabel("password");
		lblPw.setBounds(138, 106, 70, 15);
		frame.getContentPane().add(lblPw);

		textField_1 = new JTextField();
		textField_1.setBounds(264, 77, 114, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(264, 104, 114, 19);
		frame.getContentPane().add(passwordField);

		info = new JLabel("");
		info.setBounds(138, 135, 240, 15);
		frame.getContentPane().add(info);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(12, 147, 480, 176);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setVisible(false);
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);

		textField = new JTextField();
		textField.setVisible(false);
		textField.setBounds(12, 338, 480, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("Send");
		btnNewButton.setVisible(false);
		btnNewButton.setBounds(12, 369, 88, 25);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String chat = textArea.getText();
				chat += "\n" + username + ": " + textField.getText();
				textArea.setText(chat);
				connect.addMessage(username, textField.getText());
				textField.setText("");

			}
		});

		btnLogin = new JButton("Login");
		btnLogin.setBounds(138, 163, 117, 25);
		frame.getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					if (connect.logIn(textField_1.getText(), passwordField.getText())) {
						info.setText("Giris Yapildi.");
						username = textField_1.getText();
						chat();
					} else {
						info.setText("Giris Yapilamadi.");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("login error.");
					System.exit(0);
				}
			}
		});

	}

	public void chat() {
		lblId.setVisible(false);
		lblPw.setVisible(false);
		textField_1.setVisible(false);
		passwordField.setVisible(false);
		info.setVisible(false);
		btnLogin.setVisible(false);

		scrollPane.setVisible(true);
		textArea.setVisible(true);
		textField.setVisible(true);
		btnNewButton.setVisible(true);
		Chat t1 = new Chat();
		t1.start();
	}

	class Chat extends Thread {

		private String history;

		public Chat() {

		}

		public void run() {
			while (true) {
				try {
					history = connect.getMessage();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				textArea.setText(history);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
