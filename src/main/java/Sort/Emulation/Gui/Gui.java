package Sort.Emulation.Gui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.xml.bind.JAXBException;

import Sort.Emulation.SendMessages.HEARTBEAT;
import Sort.Emulation.SendMessages.SORTREQ;
import Sort.Emulation.Service.Receiver;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Rectangle;

public class Gui {

	private JFrame frm;
	private JTextField barcode1;
	private JTextField barcode2;
	private JTextField barcode3;
	private JTextField barcode4;
	private JTextField barcode5;
	public static JTextArea sendsSortackLog = null;
	public static JTextArea sendsLog = null;
	public static JTextArea receiverLog = null;
	Timer timer;
	public static JLabel labelToTimeText;
	public static JLabel sendConnectStatus;
	public static JLabel receiverConnectStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Gui window = new Gui();
					window.frm.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
				Receiver receiver = new Receiver();
				// receiver.receiveMessage("MQ.NP.MFCHOST.01");
				receiver.receiveMessage("MQ.NP.HOSTMFC.01");
			}
		});

	}

	/**
	 * Create the application.
	 * 
	 */
	public Gui() {

		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final ArrayList<String> barcodes = new ArrayList<String>();

		frm = new JFrame();
		frm.setTitle("Emulation");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frm.setSize(screenSize.width, screenSize.height - 45);

		// frm.setResizable(false);

		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frm.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		barcode1 = new JTextField();
		barcode1.setBounds(113, 11, 112, 20);
		panel.add(barcode1);
		barcode1.setColumns(10);

		JButton SendButton = new JButton("В ПУТЬ!");
		SendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (barcode1.getText().isEmpty() 
					&& barcode2.getText().isEmpty() 
					&& barcode3.getText().isEmpty()
					&& barcode4.getText().isEmpty() 
					&& barcode5.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frm, "Введите хоть один штрихкод", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					barcodes.clear();
					if (!barcode1.getText().isEmpty()) {
						barcodes.add(barcode1.getText());
					}
					if (!barcode2.getText().isEmpty()) {
						barcodes.add(barcode2.getText());
					}
					if (!barcode3.getText().isEmpty()) {
						barcodes.add(barcode3.getText());
					}
					if (!barcode4.getText().isEmpty()) {
						barcodes.add(barcode4.getText());
					}
					if (!barcode5.getText().isEmpty()) {
						barcodes.add(barcode5.getText());
					}
					try {
						SORTREQ.sendSortreq(barcodes);
					} catch (JAXBException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		SendButton.setBounds(245, 103, 130, 51);
		panel.add(SendButton);

		barcode2 = new JTextField();
		barcode2.setColumns(10);
		barcode2.setBounds(113, 41, 112, 20);
		panel.add(barcode2);

		barcode3 = new JTextField();
		barcode3.setColumns(10);
		barcode3.setBounds(113, 72, 112, 20);
		panel.add(barcode3);

		barcode4 = new JTextField();
		barcode4.setColumns(10);
		barcode4.setBounds(113, 103, 112, 20);
		panel.add(barcode4);

		barcode5 = new JTextField();
		barcode5.setColumns(10);
		barcode5.setBounds(113, 134, 112, 20);
		panel.add(barcode5);

		sendsSortackLog = new JTextArea();
		sendsSortackLog.setEditable(false);
		JScrollPane scroll = new JScrollPane(sendsSortackLog);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(10, 236, 365, 448);
		JScrollBar vertical = scroll.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
		DefaultCaret caret = (DefaultCaret) sendsSortackLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		panel.add(scroll);

		sendsLog = new JTextArea();
		sendsLog.setEditable(false);
		JScrollPane scrollsendsLog = new JScrollPane(sendsLog);
		scrollsendsLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollsendsLog.setBounds(398, 82, 460, 602);
		JScrollBar verticalS = scrollsendsLog.getVerticalScrollBar();
		verticalS.setValue(verticalS.getMaximum());
		DefaultCaret caretsendsLog = (DefaultCaret) sendsLog.getCaret();
		caretsendsLog.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		panel.add(scrollsendsLog);

		receiverLog = new JTextArea();
		receiverLog.setEditable(false);
		JScrollPane scrollreceiverLog = new JScrollPane(receiverLog);
		scrollreceiverLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollreceiverLog.setBounds(890, 82, 460, 602);
		JScrollBar verticalR = scrollreceiverLog.getVerticalScrollBar();
		verticalR.setValue(verticalR.getMaximum());
		DefaultCaret caretReceiverLog = (DefaultCaret) receiverLog.getCaret();
		caretReceiverLog.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		panel.add(scrollreceiverLog);

		JLabel barcode1Label = new JLabel("Штрихкод 1");
		barcode1Label.setHorizontalAlignment(SwingConstants.CENTER);
		barcode1Label.setBounds(10, 11, 93, 20);
		panel.add(barcode1Label);

		JLabel barcode2Label = new JLabel("Штрихкод 2");
		barcode2Label.setHorizontalAlignment(SwingConstants.CENTER);
		barcode2Label.setBounds(10, 41, 93, 20);
		panel.add(barcode2Label);

		JLabel barcode3Label = new JLabel("Штрихкод 3");
		barcode3Label.setHorizontalAlignment(SwingConstants.CENTER);
		barcode3Label.setBounds(10, 75, 93, 17);
		panel.add(barcode3Label);

		JLabel barcode4Label = new JLabel("Штрихкод 4");
		barcode4Label.setHorizontalAlignment(SwingConstants.CENTER);
		barcode4Label.setBounds(10, 103, 93, 20);
		panel.add(barcode4Label);

		JLabel barcode5Label = new JLabel("Штрихкод 5");
		barcode5Label.setHorizontalAlignment(SwingConstants.CENTER);
		barcode5Label.setBounds(10, 134, 93, 20);
		panel.add(barcode5Label);

		final JButton StartSortack = new JButton("<html>Старт отправки<br><p align=\"center\"> HEARTBEAT</html>");
		final JButton StopSortack = new JButton("<html>Стоп  отправки<br><p align=\"center\">  HEARTBEAT</html>");
		StopSortack.setEnabled(false);
		StartSortack.setBounds(10, 165, 172, 37);
		StopSortack.setBounds(203, 165, 172, 37);
		panel.add(StartSortack);
		panel.add(StopSortack);

		StopSortack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer.cancel();
				timer.purge();
				StopSortack.setEnabled(false);
				StartSortack.setEnabled(true);
			}
		});
		StartSortack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer = new Timer();
				timer.schedule(new HEARTBEAT(), 0, 30000);
				StopSortack.setEnabled(true);
				StartSortack.setEnabled(false);

			}
		});

		JLabel SortackLog = new JLabel("Лог отправки  HEARTBEAT");
		SortackLog.setHorizontalAlignment(SwingConstants.CENTER);
		SortackLog.setBounds(10, 216, 260, 20);
		panel.add(SortackLog);

		JLabel label = new JLabel("Лог отправленных сообщений");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(398, 44, 350, 27);
		panel.add(label);

		JLabel label_1 = new JLabel("Лог принятых сообщений");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(890, 44, 350, 27);
		panel.add(label_1);

		JButton ClearSendLog = new JButton("Очистить");
		ClearSendLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendsLog.setText(null);
			}
		});
		ClearSendLog.setBounds(756, 43, 100, 28);
		panel.add(ClearSendLog);

		JButton ClearReceiverLog = new JButton("Очистить");
		ClearReceiverLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				receiverLog.setText(null);
			}
		});
		ClearReceiverLog.setBounds(1250, 43, 100, 28);
		panel.add(ClearReceiverLog);

		JButton ClearSortackLog = new JButton("Очистить");
		ClearSortackLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendsSortackLog.setText(null);
			}
		});
		ClearSortackLog.setBounds(275, 206, 100, 27);
		panel.add(ClearSortackLog);

		JLabel timeLabelTitle = new JLabel("<html>Время получения <br><center>канала сброса</center></html>");
		timeLabelTitle.setBounds(263, 10, 112, 37);
		panel.add(timeLabelTitle);

		labelToTimeText = new JLabel("<html><center>Нет данных</center></html>");
		labelToTimeText.setHorizontalAlignment(SwingConstants.CENTER);
		labelToTimeText.setBounds(245, 44, 130, 48);
		panel.add(labelToTimeText);

		JLabel label_2 = new JLabel("Соединение для отправки сообщений");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(400, 14, 348, 27);
		panel.add(label_2);

		JLabel label_3 = new JLabel("Соединение для получения сообщений");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(892, 14, 348, 27);
		panel.add(label_3);

		sendConnectStatus = new JLabel("<html><font color=\"Orange\"><b>Неизвестно</b></<font></html>");
		sendConnectStatus.setHorizontalAlignment(SwingConstants.CENTER);
		sendConnectStatus.setBounds(756, 17, 102, 20);
		panel.add(sendConnectStatus);

		receiverConnectStatus = new JLabel("<html><font color=\"Orange\"><b>Неизвестно</b></<font></html>");
		receiverConnectStatus.setHorizontalAlignment(SwingConstants.CENTER);
		receiverConnectStatus.setBounds(1250, 17, 102, 20);
		panel.add(receiverConnectStatus);
	}
}
