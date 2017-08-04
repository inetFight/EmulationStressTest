package Sort.Emulation.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import Sort.Emulation.Helpers.ReadFile;
import Sort.Emulation.Helpers.SwingUtils;
import Sort.Emulation.Model.RootElementLogicModel;
import Sort.Emulation.SendMessages.HEARTBEAT;
import Sort.Emulation.SendMessages.SORTREQ;
import Sort.Emulation.Service.Receiver;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.LineBorder;

public class GuiStressTest {

	private JFrame frame;
	private File file;
	String userDir = System.getProperty("user.home");
	final FileFilter filter = new FileNameExtensionFilter("txt or csv files",new String[] {"txt", "csv"});
	final FileFilter filterSave = new FileNameExtensionFilter("csv file"," ");
	static String file_path_open;
	private JTextField intervalValue;
	public static ArrayList<RootElementLogicModel> barcodes;
	public static HashMap<String, RootElementLogicModel> data = new HashMap<String, RootElementLogicModel>();
	public static ArrayList<String> toSend;
	public static JTable table;
	public static DefaultTableModel dtm;
	Thread t;
	public static JLabel go;
	public static JLabel end;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiStressTest window = new GuiStressTest();
					window.frame.setVisible(true);
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
	 */
	public GuiStressTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Emulation");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height - 45);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		final JLabel OpenFileLabel = new JLabel("<html><i>Название файла</i></html>");
		OpenFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		OpenFileLabel.setBounds(214, 11, 165, 32);
		panel.add(OpenFileLabel);
		
		JLabel interval = new JLabel("Интервал (ms)");
		interval.setHorizontalAlignment(SwingConstants.CENTER);
		interval.setBounds(23, 55, 100, 18);
		panel.add(interval);
		
		final JButton stop = new JButton("СТОП");
		
		
		intervalValue = new JTextField();
		intervalValue.setHorizontalAlignment(SwingConstants.CENTER);
		intervalValue.setText("300");
		intervalValue.setBounds(125, 54, 63, 20);
		panel.add(intervalValue);
		intervalValue.setColumns(10);
		
		final JButton buttonFile = new JButton("Выбрать файл");
		buttonFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser(userDir + "/Desktop");
				fileopen.setAcceptAllFileFilterUsed(false);
				fileopen.addChoosableFileFilter(filter);
				AbstractButton button = SwingUtils.getDescendantOfType(AbstractButton.class,
						fileopen, "Icon", UIManager.getIcon("FileChooser.detailsViewIcon"));
		        	button.doClick();
                int ret = fileopen.showDialog(null, "Выбрать файл");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file = fileopen.getSelectedFile();
                    OpenFileLabel.setText(file.getName());
                    
                    file_path_open = file.getAbsolutePath();
                    barcodes = ReadFile.readFileToListByLine(file_path_open);
                   
                    
                }
			}
		});
		buttonFile.setBounds(23, 11, 165, 32);
		panel.add(buttonFile);
		
		
		
		final JButton start = new JButton("СТАРТ");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!intervalValue.getText().matches("^[0-9]*$") || intervalValue.getText().length() <= 0){
					JOptionPane.showMessageDialog(panel, "Некорректный интервал", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
				else {
					t = new Thread() {
						public void run() {
							start.setEnabled(false);
							stop.setEnabled(true);
							int cnt = 0;
					for (final RootElementLogicModel element : barcodes) {
						toSend = new ArrayList<String>();
						if(element.getBarcode1() != null) toSend.add(element.getBarcode1());
						if(element.getBarcode2() != null) toSend.add(element.getBarcode2());
						if(element.getBarcode3() != null) toSend.add(element.getBarcode3());
						if(element.getBarcode4() != null) toSend.add(element.getBarcode4());
						if(element.getBarcode5() != null) toSend.add(element.getBarcode5());
						
						try {
							SORTREQ.sendSortreq(toSend, element);
							cnt+=1;
							Thread.sleep(Long.valueOf(intervalValue.getText()));
						} catch (JAXBException e) {						
							e.printStackTrace();
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
													
								
						toSend.clear();
						go.setText("" + cnt);
						}
						start.setEnabled(true);
						
					}};
					t.start();
				}
			}
		});
		start.setBounds(23, 86, 165, 32);
		panel.add(start);
		
		JPanel tablepanel = new JPanel();
		tablepanel.setBackground(Color.WHITE);
		tablepanel.setBounds(0, 142, 1350, 542);
		panel.add(tablepanel);
		
		Object[] headers = {
				"HPIC", 
				"Barcode1", 
				"Barcode2", 
				"Barcode3", 
				"Barcode4", 
				"Barcode5", 
				"SORTREQ_TIME", 
				"SORTRPL_TIME", 
				"REQ_RPL_TIME", 
				"SORTRPT_TIME", 
				"SORTRPL_TIME", 
				"RPT_RPL_TIME"
			};

		table = new JTable();
		dtm = new DefaultTableModel(0, 0);
		dtm.setColumnIdentifiers(headers);
		table.setModel(dtm);
		table.setGridColor(Color.BLACK);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		table.setPreferredScrollableViewportSize(new Dimension(1330, 500));
		JScrollPane scrollPane = new JScrollPane(table);
		
		scrollPane.setBackground(Color.WHITE);

		tablepanel.add(scrollPane);
		
		stop.setEnabled(false);
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			t.stop();
			start.setEnabled(true);
			stop.setEnabled(false);
	
			}
		});
		stop.setBounds(214, 86, 165, 32);
		panel.add(stop);
		
		go = new JLabel("нет данных");
		go.setHorizontalAlignment(SwingConstants.CENTER);
		go.setBounds(393, 24, 165, 32);
		panel.add(go);
		
		end = new JLabel("нет данных");
		end.setHorizontalAlignment(SwingConstants.CENTER);
		end.setBounds(393, 72, 165, 32);
		panel.add(end);
		
		JLabel label = new JLabel("из");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(452, 57, 46, 14);
		panel.add(label);
		
		JButton button = new JButton("Очистить");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dtm.setRowCount(0);
			}
		});
		button.setBounds(1251, 108, 89, 23);
		panel.add(button);
		
		
	}
}
