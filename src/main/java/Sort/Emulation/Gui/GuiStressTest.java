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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Sort.Emulation.Helpers.ReadFile;
import Sort.Emulation.Helpers.SwingUtils;
import Sort.Emulation.Model.RootElementLogicModel;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class GuiStressTest {

	private JFrame frame;
	private File file;
	String userDir = System.getProperty("user.home");
	final FileFilter filter = new FileNameExtensionFilter("txt or csv files",new String[] {"txt", "csv"});
	final FileFilter filterSave = new FileNameExtensionFilter("csv file"," ");
	static String file_path_open;
	private JTextField intervalValue;
	public static ArrayList<RootElementLogicModel> barcodes;

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
		OpenFileLabel.setBounds(188, 11, 144, 32);
		panel.add(OpenFileLabel);
		
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
                    
                   
                    
                }
			}
		});
		buttonFile.setBounds(23, 11, 165, 32);
		panel.add(buttonFile);
		
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				barcodes.clear();
				barcodes = ReadFile.readFileToListByLine(file_path_open);
			}
		});
		load.setBounds(342, 11, 88, 32);
		panel.add(load);
		
		JLabel interval = new JLabel("Интервал (ms)");
		interval.setHorizontalAlignment(SwingConstants.CENTER);
		interval.setBounds(23, 55, 100, 18);
		panel.add(interval);
		
		intervalValue = new JTextField();
		intervalValue.setHorizontalAlignment(SwingConstants.CENTER);
		intervalValue.setText("300");
		intervalValue.setBounds(125, 54, 63, 20);
		panel.add(intervalValue);
		intervalValue.setColumns(10);
		
		JButton start = new JButton("СТАРТ");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!intervalValue.getText().matches("^[0-9]*$") || intervalValue.getText().length() <= 0){
					JOptionPane.showMessageDialog(panel, "Некорректный интервал", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
				else {
					
					
				}
			}
		});
		start.setBounds(23, 86, 165, 32);
		panel.add(start);
		
		
	}
}
