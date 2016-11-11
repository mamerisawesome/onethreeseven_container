import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Chat extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField inputField;
	private JTextArea chatArea;
	private JTextField nameField;
	String name;
    String message;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Chat frame = new Chat();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Chat() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		inputField = new JTextField();
		inputField.setBounds(488, 402, 187, 25);
		contentPane.add(inputField);
		inputField.addActionListener(this);
		inputField.setColumns(10);
		
		chatArea = new JTextArea();
		chatArea.setLineWrap(true);
		chatArea.setBounds(488, 51, 187, 340);
		chatArea.setEditable(false);
		contentPane.add(chatArea);
		
		nameField = new JTextField();
		nameField.setBounds(488, 15, 187, 25);
		nameField.addActionListener(this);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JButton enter = new JButton("Enter");
		enter.setBounds(586, 438, 89, 23);
		contentPane.add(enter);
	}

	public JTextField getInputField() {
		return inputField;
	}

	public void setInputField(JTextField inputField) {
		this.inputField = inputField;
	}

	public JTextArea getChatArea() {
		return chatArea;
	}

	public void setChatArea(JTextArea chatArea) {
		this.chatArea = chatArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			name = nameField.getText();
	}
}
