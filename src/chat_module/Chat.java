import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Chat extends JFrame{

	private JPanel contentPane;
	private JTextField inputField;
	private JTextArea chatArea;
	private JTextField nameField;
	String serverIP;
	int serverPort;
	String name;
    String message;
    private JTextField serverName;
    private JTextField port;
    Socket client;
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
		inputField.setBounds(466, 402, 209, 25);
		contentPane.add(inputField);
		inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatArea.append(nameField.getText());	
			}
		});
		inputField.setColumns(10);
		
		chatArea = new JTextArea();
		chatArea.setLineWrap(true);
		chatArea.setBounds(466, 51, 209, 340);
		chatArea.setEditable(false);
		contentPane.add(chatArea);
		
		nameField = new JTextField();
		nameField.setBounds(466, 15, 209, 25);
		nameField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				name = nameField.getText();
				nameField.setText(name);
				nameField.setEditable(false);
			}
		});
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chatArea.append(nameField.getText());	
			}
		});
		enter.setBounds(586, 438, 89, 23);
		contentPane.add(enter);
		
		serverName = new JTextField();
		serverName.setBounds(80, 17, 146, 20);
		serverName.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				serverIP = serverName.getText();
				serverName.setText(serverIP);
				serverName.setEditable(false);
				port.setEditable(true);
			}
		});
		contentPane.add(serverName);
		serverName.setColumns(10);
		
		port = new JTextField();
		port.setBounds(236, 17, 86, 20);
		port.setEditable(false);
		port.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				serverPort = Integer.parseInt(port.getText());
				port.setText(""+serverPort);
				chatArea.append("Connecting to " + serverIP + " on port " + serverPort + "\n");
			}
		});
		contentPane.add(port);
		port.setColumns(10);
		
		
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(432, 20, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblIpport = new JLabel("IP:port");
		lblIpport.setBounds(38, 20, 46, 14);
		contentPane.add(lblIpport);
		
		JLabel label = new JLabel(":");
		label.setBounds(229, 20, 46, 14);
		contentPane.add(label);
		
		JButton socketButton = new JButton("Connect");
		socketButton.setBounds(174, 48, 86, 23);
		socketButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					client = new Socket(serverIP, serverPort);
					socketButton.setText("Connected");
					socketButton.setEnabled(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(socketButton);
	}
}
