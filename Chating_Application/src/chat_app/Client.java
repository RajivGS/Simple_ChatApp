package chat_app;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

public class Client extends JFrame implements ActionListener {
	boolean typing;
	JPanel top_panel;
	JTextField message_box;
	JButton button;
	static JTextArea displayMessage;

	static Socket socket;
	static DataInputStream dataInputStream;
	static DataOutputStream dataOutputStream;

	public Client() {

		top_panel = new JPanel();
		top_panel.setLayout(null);
		top_panel.setBackground(new Color(7, 94, 84));
		top_panel.setBounds(0, 0, 450, 70);

		add(top_panel);

		ImageIcon back_arrow = new ImageIcon(ClassLoader.getSystemResource("chat_app/icons/back_arrow.png"));
		Image image = back_arrow.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon arrow = new ImageIcon(image);
		JLabel arrow_label = new JLabel(arrow);
		arrow_label.setBounds(5, 17, 30, 30);
		top_panel.add(arrow_label);

		arrow_label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});

		ImageIcon user_img = new ImageIcon(ClassLoader.getSystemResource("chat_app/icons/2.png"));
		Image user_image = user_img.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon user_imageeIcon = new ImageIcon(user_image);
		JLabel user_photo_label = new JLabel(user_imageeIcon);
		user_photo_label.setBounds(35, 3, 80, 65);
		top_panel.add(user_photo_label);

		ImageIcon phone_img = new ImageIcon(ClassLoader.getSystemResource("chat_app/icons/phone.png"));
		Image phoneImage = phone_img.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon phoImageIcon = new ImageIcon(phoneImage);
		JLabel phonLabel = new JLabel(phoImageIcon);
		phonLabel.setBounds(300, 20, 30, 30);
		top_panel.add(phonLabel);

		ImageIcon video_img = new ImageIcon(ClassLoader.getSystemResource("chat_app/icons/video.png"));
		Image videoImage = video_img.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon videImageIcon = new ImageIcon(videoImage);
		JLabel videoLabel = new JLabel(videImageIcon);
		videoLabel.setBounds(350, 20, 30, 30);
		top_panel.add(videoLabel);

		ImageIcon dot_img = new ImageIcon(ClassLoader.getSystemResource("chat_app/icons/3icon.png"));
		Image dotImage = dot_img.getImage().getScaledInstance(15, 25, Image.SCALE_DEFAULT);
		ImageIcon dotImageIcon = new ImageIcon(dotImage);
		JLabel dotLabel = new JLabel(dotImageIcon);
		dotLabel.setBounds(400, 21, 15, 25);
		top_panel.add(dotLabel);

		JLabel name_label = new JLabel("Bunty");
		name_label.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
		name_label.setForeground(Color.WHITE);
		name_label.setBounds(110, 15, 100, 18);
		top_panel.add(name_label);

		JLabel active_label = new JLabel("Active Now");
		active_label.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
		active_label.setForeground(Color.WHITE);
		active_label.setBounds(110, 35, 100, 20);
		top_panel.add(active_label);
		Timer t = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!typing) {
					active_label.setText("Active Now");
				}
			}
		});

		t.setInitialDelay(2000);

		displayMessage = new JTextArea();
		displayMessage.setBounds(5, 75, 440, 570);
		displayMessage.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		displayMessage.setEditable(false);
		displayMessage.setLineWrap(true);
		displayMessage.setWrapStyleWord(true);
		add(displayMessage);

		message_box = new JTextField();
		message_box.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
		message_box.setBounds(5, 655, 310, 40);
		add(message_box);
		message_box.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				active_label.setText("typing...");

				t.stop();

				typing = true;
			}

			public void keyReleased(KeyEvent ke) {
				typing = false;

				if (!t.isRunning()) {
					t.start();
				}
			}
		});

		button = new JButton("Send");
		button.setBounds(320, 655, 125, 40);
		button.setBackground(new Color(7, 94, 84));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("SAN-SERIF", Font.BOLD, 16));
		button.addActionListener(this);
		add(button);

		getContentPane().setBackground(Color.white);
		setLayout(null);
		setSize(450, 700);
		setLocation(900, 35);
		setUndecorated(true);
		setVisible(true);

	}
	public static void main(String[] args) {
		new Client().setVisible(true);

		try {
			socket = new Socket("127.0.0.1", 6001);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());

			String message_input = "";

			message_input = dataInputStream.readUTF();
			displayMessage.setText(displayMessage.getText() + "\n" + message_input);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String showText = message_box.getText();
		displayMessage.setText(displayMessage.getText() + "\n\t\t\t" + showText);

		try {

			dataOutputStream.writeUTF(showText);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		message_box.setText("");
	}
}
