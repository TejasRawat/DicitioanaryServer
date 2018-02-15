package co.dictionary.client.ui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import co.dictionary.client.ClientServerInteraction;
import co.dictionary.client.ConnectServerArgsDTO;
import co.dictionary.client.RequestModel;
import co.dictionary.client.ResponseModel;
import java.awt.Font;
/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class DictionaryClient extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1642959404374814396L;
	private JPanel readWordPanel;

	private static ClientServerInteraction client = new ClientServerInteraction();
	private JTextField txtFieldReadWord;
	private JTextField txtFieldReadMeaning;
	private JTextField txtFieldAddWord;
	private JTextField txtFieldAddMeaning;
	private JTextField textFieldDeleteWord;
	private static ConnectServerArgsDTO connectServerArgsDTO = new ConnectServerArgsDTO();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length==2 && (args[0]!= null && !args[0].isEmpty())
				&&  (args[1]!= null && !args[1].isEmpty()) ) {
			try {
				connectServerArgsDTO.setHostName(args[0]);
				connectServerArgsDTO.setPort(Integer.parseInt(args[1]));
				client.setConnectArgsDTO(connectServerArgsDTO);
			
				boolean isConnected = client.ping(connectServerArgsDTO);
				
				if(isConnected) {
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							DictionaryClient frame = new DictionaryClient();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				}else {
					System.err.println("Unable to Connect to HOST");
					System.exit(1);
				}
				
				
			}catch(Exception exception) {
				System.err.println("Invlaid Command parameter provided to run DictionaryClient.java"
						+ "Paramter should be <servre> <port>");	
			}
		}else {
			System.err.println("Invlaid Command parameter provided to run DictionaryClient.java"
					+ "Paramter should be <servre> <port>");
			System.exit(1);
		}
	}

	/**
	 * Create the frame.
	 */
	public DictionaryClient() {
		setFont(new Font("Dialog", Font.BOLD, 80));
		setTitle("Dictionary");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 2;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);
		
		
		JPanel readWordPanel = new JPanel();
		tabbedPane.addTab("Read", null, readWordPanel, "Enter the word to get the meaning");
		GridBagLayout gbl_readWordPanel = new GridBagLayout();
		gbl_readWordPanel.columnWidths = new int[]{204, 68, 130, 0};
		gbl_readWordPanel.rowHeights = new int[]{26, 0, 0, 0, 0, 0};
		gbl_readWordPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_readWordPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		readWordPanel.setLayout(gbl_readWordPanel);
		//readWordPanel.setVisible(false);
		//tabbedPane.setEnabledAt(0, false);

		
		JLabel lblReadWord = new JLabel("Enter Word");
		GridBagConstraints gbc_lblReadWord = new GridBagConstraints();
		gbc_lblReadWord.anchor = GridBagConstraints.WEST;
		gbc_lblReadWord.insets = new Insets(0, 0, 5, 5);
		gbc_lblReadWord.gridx = 1;
		gbc_lblReadWord.gridy = 0;
		readWordPanel.add(lblReadWord, gbc_lblReadWord);
		
		txtFieldReadWord = new JTextField();
		GridBagConstraints gbc_txtFieldReadWord = new GridBagConstraints();
		gbc_txtFieldReadWord.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldReadWord.insets = new Insets(0, 0, 5, 0);
		gbc_txtFieldReadWord.anchor = GridBagConstraints.NORTH;
		gbc_txtFieldReadWord.gridx = 2;
		gbc_txtFieldReadWord.gridy = 0;
		readWordPanel.add(txtFieldReadWord, gbc_txtFieldReadWord);
		txtFieldReadWord.setColumns(10);
		
		JLabel lblReadMeaning = new JLabel("Meaning");
		GridBagConstraints gbc_lblReadMeaning = new GridBagConstraints();
		gbc_lblReadMeaning.anchor = GridBagConstraints.EAST;
		gbc_lblReadMeaning.insets = new Insets(0, 0, 5, 5);
		gbc_lblReadMeaning.gridx = 1;
		gbc_lblReadMeaning.gridy = 1;
		readWordPanel.add(lblReadMeaning, gbc_lblReadMeaning);
		
		txtFieldReadMeaning = new JTextField();
		GridBagConstraints gbc_txtFieldReadMeaning = new GridBagConstraints();
		gbc_txtFieldReadMeaning.insets = new Insets(0, 0, 5, 0);
		gbc_txtFieldReadMeaning.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldReadMeaning.gridx = 2;
		gbc_txtFieldReadMeaning.gridy = 1;
		readWordPanel.add(txtFieldReadMeaning, gbc_txtFieldReadMeaning);
		txtFieldReadMeaning.setColumns(10);
		
		txtFieldReadMeaning.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				txtFieldReadMeaning.setText(null);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				txtFieldReadMeaning.setText(null);
			}
		});
		
		JButton buttonFindRead = new JButton("Find");
		buttonFindRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RequestModel requestModel = new RequestModel();
				requestModel.setOperationType("GET");
				requestModel.setWord(txtFieldReadWord.getText());
				
				ResponseModel  responseModel  = null;
				
				try {
					  responseModel = client.read(requestModel);
				} catch (ClassNotFoundException e1) {

					 JOptionPane.showMessageDialog(null, "Error Searching Meaning of Word", 
 							"Error Finding meaning" , JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				}
				
				if(responseModel.isStatus()) {
					
					StringBuilder builder = new StringBuilder();
					
					ArrayList<String>list  = responseModel.getMeaningList();
					
				   Iterator<String> itr =list.iterator();
					
					while(itr.hasNext()) {
						String meaning = itr.next();
						builder.append(meaning);
						builder.append(",");
					}
					
					builder.deleteCharAt(builder.length()-1);
					
					txtFieldReadMeaning.setText(builder.toString());
					
				}else {
					JOptionPane.showMessageDialog(null, "Unable to find meaning of Word", 
 							"Unable to Find Meaning" , JOptionPane.INFORMATION_MESSAGE);				}
			}
		});
		GridBagConstraints gbc_buttonFindRead = new GridBagConstraints();
		gbc_buttonFindRead.insets = new Insets(0, 0, 5, 0);
		gbc_buttonFindRead.gridx = 2;
		gbc_buttonFindRead.gridy = 2;
		readWordPanel.add(buttonFindRead, gbc_buttonFindRead);
		
		JPanel addWordPanel = new JPanel();
		addWordPanel.setToolTipText("Add multiple meanings by comma separate");
		tabbedPane.addTab("Add", null, addWordPanel, "For multiple meaning write with comma separate E.g. joy,fun,happy");
		GridBagLayout gbl_addWordPanel = new GridBagLayout();
		gbl_addWordPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_addWordPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_addWordPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_addWordPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		addWordPanel.setLayout(gbl_addWordPanel);
		//tabbedPane.setEnabledAt(1, false);

		
		JLabel lblAddWord = new JLabel("Word");
		GridBagConstraints gbc_lblAddWord = new GridBagConstraints();
		gbc_lblAddWord.anchor = GridBagConstraints.EAST;
		gbc_lblAddWord.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddWord.gridx = 6;
		gbc_lblAddWord.gridy = 1;
		addWordPanel.add(lblAddWord, gbc_lblAddWord);
		
		txtFieldAddWord = new JTextField();
		GridBagConstraints gbc_txtFieldAddWord = new GridBagConstraints();
		gbc_txtFieldAddWord.insets = new Insets(0, 0, 5, 0);
		gbc_txtFieldAddWord.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldAddWord.gridx = 7;
		gbc_txtFieldAddWord.gridy = 1;
		addWordPanel.add(txtFieldAddWord, gbc_txtFieldAddWord);
		txtFieldAddWord.setColumns(10);
		
		JLabel lblMeaningWord = new JLabel("Meaning");
		GridBagConstraints gbc_lblMeaningWord = new GridBagConstraints();
		gbc_lblMeaningWord.anchor = GridBagConstraints.EAST;
		gbc_lblMeaningWord.insets = new Insets(0, 0, 5, 5);
		gbc_lblMeaningWord.gridx = 6;
		gbc_lblMeaningWord.gridy = 2;
		addWordPanel.add(lblMeaningWord, gbc_lblMeaningWord);
		
		txtFieldAddMeaning = new JTextField();
		GridBagConstraints gbc_txtFieldAddMeaning = new GridBagConstraints();
		gbc_txtFieldAddMeaning.insets = new Insets(0, 0, 5, 0);
		gbc_txtFieldAddMeaning.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldAddMeaning.gridx = 7;
		gbc_txtFieldAddMeaning.gridy = 2;
		addWordPanel.add(txtFieldAddMeaning, gbc_txtFieldAddMeaning);
		txtFieldAddMeaning.setColumns(10);
		
		JButton btnAddWord = new JButton("Add");
		GridBagConstraints gbc_btnAddWord = new GridBagConstraints();
		gbc_btnAddWord.gridx = 7;
		gbc_btnAddWord.gridy = 3;
		addWordPanel.add(btnAddWord, gbc_btnAddWord);
		
		btnAddWord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String meanings = txtFieldAddMeaning.getText();
				ArrayList<String> listMeaning = new ArrayList<String>();
				StringTokenizer stringTokenizer = new StringTokenizer(meanings, ",");
				while(stringTokenizer.hasMoreTokens()) {
					String word = stringTokenizer.nextToken();
					listMeaning.add(word);
				}
				
				RequestModel requestModel = new RequestModel();
				requestModel.setOperationType("POST");
				requestModel.setWord(txtFieldAddWord.getText());
				requestModel.setListMeaning(listMeaning);
				
				ResponseModel responseModel = client.add(requestModel);
			
				if(responseModel.isStatus()) {
					JOptionPane.showMessageDialog(null, "Word Added Successfully", 
 							"Added" , JOptionPane.INFORMATION_MESSAGE);	
				}else {
					
					JOptionPane.showMessageDialog(null, responseModel.getErrorList().get(0), 
 							"Unable to Add" , JOptionPane.INFORMATION_MESSAGE);	
				}

			}
		});
		
		JPanel deleteWordPanel = new JPanel();
		tabbedPane.addTab("Delete", null, deleteWordPanel, "Enter the word to delete from server");
		GridBagLayout gbl_deleteWordPanel = new GridBagLayout();
		gbl_deleteWordPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_deleteWordPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_deleteWordPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_deleteWordPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		deleteWordPanel.setLayout(gbl_deleteWordPanel);
		//tabbedPane.setEnabledAt(2, false);

		
		JLabel lblWordDelete = new JLabel("Word");
		GridBagConstraints gbc_lblWordDelete = new GridBagConstraints();
		gbc_lblWordDelete.insets = new Insets(0, 0, 5, 5);
		gbc_lblWordDelete.anchor = GridBagConstraints.EAST;
		gbc_lblWordDelete.gridx = 5;
		gbc_lblWordDelete.gridy = 1;
		deleteWordPanel.add(lblWordDelete, gbc_lblWordDelete);
		
		textFieldDeleteWord = new JTextField();
		GridBagConstraints gbc_textFieldDeleteWord = new GridBagConstraints();
		gbc_textFieldDeleteWord.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDeleteWord.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDeleteWord.gridx = 6;
		gbc_textFieldDeleteWord.gridy = 1;
		deleteWordPanel.add(textFieldDeleteWord, gbc_textFieldDeleteWord);
		textFieldDeleteWord.setColumns(10);
		
		JButton btnWordDelete = new JButton("Delete");
		GridBagConstraints gbc_btnWordDelete = new GridBagConstraints();
		gbc_btnWordDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnWordDelete.gridx = 6;
		gbc_btnWordDelete.gridy = 2;
		deleteWordPanel.add(btnWordDelete, gbc_btnWordDelete);

		btnWordDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String wordToDelete = textFieldDeleteWord.getText();		
				
				RequestModel requestModel = new RequestModel();
				requestModel.setOperationType("DELETE");
				requestModel.setWord(wordToDelete);
				
				ResponseModel responseModel = client.delete(requestModel);
				
				if(responseModel.isStatus()) {
					JOptionPane.showMessageDialog(null, "Deleted Successfully", 
 							"Deleted" , JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Unable to delete", 
 							"Unable to Delete" , JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {

				
				txtFieldAddWord.setText(null);
				txtFieldAddMeaning.setText(null);
				txtFieldReadWord.setText(null);
				txtFieldReadMeaning.setText(null);
				textFieldDeleteWord.setText(null);
				
			}
		});
		
	}

}
