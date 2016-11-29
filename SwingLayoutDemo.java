import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import org.xml.sax.InputSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

public class SwingLayoutDemo {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel control1Panel;
   private JPanel control2Panel;
   private JPanel controlPanel;
   private JLabel msglabel;
   CardLayout layout;
   GridBagLayout layout2;
   GridBagConstraints constraints;
   private JPanel noQueryPanel;
   private JPanel query1Panel;
   private JPanel query2Panel;
   private JPanel query3Panel;
   private JPanel panel;
   private JScrollPane listComboScrollPane;
   private JButton showButton;
   private JPanel tablePanel;
   private JButton reset1Button;
   private JButton reset2Button;

   private InputSource is;
   private SAXParser saxParser;

   private String[][] map;
   private int q2Count;
   private static JTable table;

   private ArrayList<AuthorNames> authorEntities = new ArrayList<AuthorNames>();


   GetKPublications kPublications;// = new GetKPublications(hashMap,k);

   private String fileName = "input1.xml";

   public SwingLayoutDemo(){
      System.out.println("Please wait while loading...");

      getKPublications(0);
      System.out.println("almost done :)");
      ParseEntityResolution per = new ParseEntityResolution();
      SAXParser saxParser = getSAXParser();

      try {
         saxParser.parse(is,per);
      }
      catch(Exception e) {
         e.printStackTrace();
      }

      authorEntities = per.getEntityAuthors();

      System.out.println("...thank you :)");

      prepareGUI();

      //Query 1 Panel*****************************************
      panelQuery1();
      //Query 2 Panel*****************************************
      panelQuery2();
      //Query 3 Panel*****************************************
      panelQuery3();

      
      panel.add("Select a Query", noQueryPanel);
      panel.add("Query 1", query1Panel);
      panel.add("Query 2", query2Panel);
      panel.add("Query 3", query3Panel);
      

      //ComboBox Panel*****************************************
      panelComboBox();
      //Results Table *****************************************
      panelResultTable();
      //Adding to frame****************************************
      control1Panel.add(listComboScrollPane);
      control1Panel.add(showButton);
      control2Panel.add(panel);

      constraints.fill = GridBagConstraints.VERTICAL;
      constraints.gridwidth = 1;
      constraints.weighty = 0;
      constraints.weightx = 0;
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.anchor = GridBagConstraints.WEST;

      controlPanel.add(control1Panel,constraints);

      constraints.fill = GridBagConstraints.VERTICAL;
      constraints.gridwidth = 1;
      constraints.weighty = 0;
      constraints.weightx = 0;
      constraints.gridx = 0;
      constraints.gridy = 1;
      constraints.anchor = GridBagConstraints.WEST;

      controlPanel.add(control2Panel,constraints);

      constraints.fill = GridBagConstraints.BOTH;
      constraints.gridwidth = 500;
      constraints.gridheight = 0;
      constraints.weighty = 20;
      constraints.weightx = 200;
      constraints.gridx = 1;
      constraints.gridy = 1;
      // constraints.anchor = GridBagConstraints.WEST;
      constraints.anchor = GridBagConstraints.EAST;
      controlPanel.add(tablePanel, constraints);
      mainFrame.setVisible(true);  
   }

   public void prepareGUI() {
      mainFrame = new JFrame("DBLP Query Engine");
      mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      mainFrame.setUndecorated(false);
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setLayout(new GridBagLayout());

      constraints = new GridBagConstraints();
      constraints.insets = new Insets(10, 10, 20 ,40);

      constraints.fill = GridBagConstraints.VERTICAL;
      constraints.gridwidth = GridBagConstraints.REMAINDER;
      constraints.weighty = 0;
      constraints.weightx = 0;
      constraints.gridx = 0;
      constraints.gridy = 0;

      headerLabel = new JLabel("");
      statusLabel = new JLabel("");        

      statusLabel.setSize(350,100);
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
           System.exit(0);
         }        
      });    
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridBagLayout());

      control1Panel = new JPanel();
      control1Panel.setLayout(new FlowLayout());

      control2Panel = new JPanel();
      control2Panel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel, constraints);

      constraints.fill = GridBagConstraints.VERTICAL;
      constraints.gridwidth = 1;
      constraints.weighty = 70;
      constraints.weightx = 50;
      constraints.gridx = 0;
      constraints.gridy = 1;
      constraints.anchor = GridBagConstraints.WEST;

      mainFrame.add(controlPanel,constraints);
      // mainFrame.add(statusLabel);
      // mainFrame.setVisible(true);  

      headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
      headerLabel.setText("DBLP Query Engine"); 
      // headerLabel.setHorizontalAlignment(JLabel.CENTER);  
      // headerLabel.setVerticalAlignment(JLabel.TOP);

      // statusLabel.setText("Divyanshu"); 

      panel = new JPanel();
      panel.setSize(400,400);

      layout = new CardLayout();
      layout.setHgap(10);
      layout.setVgap(10);
      panel.setLayout(layout);

      noQueryPanel = new JPanel();

      constraints = new GridBagConstraints(); 
   }

   public void panelQuery1() {
      query1Panel = new JPanel(new GridLayout(7, 1));
      JPanel query11Panel = new JPanel(new FlowLayout());
      JPanel query12Panel = new JPanel(new FlowLayout());
      JPanel query13Panel = new JPanel(new FlowLayout());
      JPanel query14Panel = new JPanel(new FlowLayout());
      JPanel query15Panel = new JPanel(new FlowLayout());
      JPanel query16Panel = new JPanel(new FlowLayout());

      final DefaultComboBoxModel<String> searchBy = new DefaultComboBoxModel<String>();

      searchBy.addElement("By Author Name");
      searchBy.addElement("By Title Tag");

      final JComboBox<String> comboBox = new JComboBox<String>(searchBy);
      comboBox.setSelectedIndex(0);

      JScrollPane comboScroll = new JScrollPane(comboBox);
      query11Panel.add(comboScroll);//Row 1

      // JButton searchButton = new JButton("Search");
      // query11Panel.add(searchButton);//Row 1

      JLabel nameLabel = new JLabel("Name / Title Tag: ");
      query12Panel.add(nameLabel);

      JTextField nameField = new JTextField(15);
      query12Panel.add(nameField);//Row 2

      JLabel sinceYearLabel = new JLabel("Since Year:");
      query13Panel.add(sinceYearLabel);

      JTextField sinceYearField = new JTextField(5);
      query13Panel.add(sinceYearField);//Row 3

      JLabel customRangeLabel = new JLabel("Custom Range: ");
      query14Panel.add(customRangeLabel);

      JTextField customStartRangeField = new JTextField(5);
      query14Panel.add(customStartRangeField);

      JLabel customRangeHyphenLabel = new JLabel("-");
      query14Panel.add(customRangeHyphenLabel);

      JTextField customEndRangeField = new JTextField(5);
      query14Panel.add(customEndRangeField);//Row 4

      JRadioButton yearSortButton = new JRadioButton("Sort By Year");
      JRadioButton relevanceSortButton = new JRadioButton("Sort By Relevance");

      ButtonGroup bg = new ButtonGroup();
      bg.add(yearSortButton);
      bg.add(relevanceSortButton);

      query16Panel.add(yearSortButton);
      query16Panel.add(relevanceSortButton);//Row 5

      JButton search1Button = new JButton("Search");
      search1Button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

            String searchType = (String)comboBox.getSelectedItem();

            

            if (searchType.equals("By Author Name")) {
               if (yearSortButton.isSelected()) {
                  if (!(sinceYearField.getText().equals("")) && customStartRangeField.getText().equals("") && customEndRangeField.getText().equals("")) {
                     getAuthorYearSorted(nameField.getText(), sinceYearField.getText());
                  }
                  else if (sinceYearField.getText().equals("") && !(customStartRangeField.getText().equals("")) && !(customEndRangeField.getText().equals(""))) {
                     getAuthorYearSorted(nameField.getText(), customStartRangeField.getText(), customEndRangeField.getText());
                  }
                  else {
                     JOptionPane.showMessageDialog(null, "Please enter a valid format.", "Warning", JOptionPane.WARNING_MESSAGE);
                  }
               }
               else if (relevanceSortButton.isSelected()) {
                  if (!(sinceYearField.getText().equals("")) && customStartRangeField.getText().equals("") && customEndRangeField.getText().equals("")) {
                     //SINCE YEAR
                  }
                  else if (sinceYearField.getText().equals("") && !(customStartRangeField.getText().equals("")) && !(customEndRangeField.getText().equals(""))) {
                     //RANGE
                  }
                  else {
                     JOptionPane.showMessageDialog(null, "Please enter a valid format.", "Warning", JOptionPane.WARNING_MESSAGE);
                  }
               }

            }
            else if (searchType.equals("By Title Tag")) {
               if (yearSortButton.isSelected()) {
                  if (!(sinceYearField.getText().equals("")) && customStartRangeField.getText().equals("") && customEndRangeField.getText().equals("")) {
                     getTitleYearSorted(nameField.getText(), sinceYearField.getText());
                  }
                  else if (sinceYearField.getText().equals("") && !(customStartRangeField.getText().equals("")) && !(customEndRangeField.getText().equals(""))) {
                     getTitleYearSorted(nameField.getText(), customStartRangeField.getText(), customEndRangeField.getText());
                  }
                  else {
                     JOptionPane.showMessageDialog(null, "Please enter a valid format.", "Warning", JOptionPane.WARNING_MESSAGE);
                  }
               }
               else if (relevanceSortButton.isSelected()) {
                  if (!(sinceYearField.getText().equals("")) && customStartRangeField.getText().equals("") && customEndRangeField.getText().equals("")) {
                     //SINCE YEAR
                  }
                  else if (sinceYearField.getText().equals("") && !(customStartRangeField.getText().equals("")) && !(customEndRangeField.getText().equals(""))) {
                     //RANGE
                  }
                  else {
                     JOptionPane.showMessageDialog(null, "Please enter a valid format.", "Warning", JOptionPane.WARNING_MESSAGE);
                  }
               }
            }

         }
      });
      query15Panel.add(search1Button);

      reset1Button = new JButton("Reset");
      reset1Button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            nameField.setText("");
            sinceYearField.setText("");
            customStartRangeField.setText("");
            customEndRangeField.setText("");
            bg.clearSelection();
         }
      });
      query15Panel.add(reset1Button);//Row 6

      query1Panel.add(query11Panel);
      query1Panel.add(query12Panel);
      query1Panel.add(query13Panel);
      query1Panel.add(query14Panel);
      query1Panel.add(query16Panel);
      query1Panel.add(query15Panel);//end query1 panels.
   }

   public void panelQuery2() {
      query2Panel = new JPanel(new GridLayout(5, 1));

      JPanel query21Panel = new JPanel(new FlowLayout());
      JPanel query22Panel = new JPanel(new FlowLayout());

      JLabel publicationsLabel = new JLabel("No. of Publications:");
      query21Panel.add(publicationsLabel);

      JTextField publicationsField = new JTextField(15);
      query21Panel.add(publicationsField);//Row 1

      JButton search2Button = new JButton("Search");
      search2Button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) { //ADD POP UP
            try{
               // if(publicationsField.getText() != null && publicationsField.getText().equals("")){
               //    JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Title", JOptionPane.WARNING_MESSAGE);
               // }
               
               map = kPublications.getAuthors(Integer.parseInt(publicationsField.getText()));
               getQ2Table();
            } catch (NumberFormatException num) {
               JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
         }

      });
      query22Panel.add(search2Button);

      reset2Button = new JButton("Reset");
      reset2Button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            publicationsField.setText("");
         }
      });
      query22Panel.add(reset2Button);//Row 2

      query2Panel.add(query21Panel);
      query2Panel.add(query22Panel);//end query2 panels.
   }

   public void panelQuery3() {
      query3Panel = new JPanel(new GridLayout(7, 1));
      JPanel query31Panel = new JPanel(new FlowLayout());
      JPanel query32Panel = new JPanel(new FlowLayout());
      JPanel query33Panel = new JPanel(new FlowLayout());
      JPanel query34Panel = new JPanel(new FlowLayout());
      JPanel query35Panel = new JPanel(new FlowLayout());
      JPanel query36Panel = new JPanel(new FlowLayout());
      JPanel query37Panel = new JPanel(new FlowLayout());

      JLabel yearLabel = new JLabel("Previous Year:");
      query31Panel.add(yearLabel);

      JTextField yearField = new JTextField(5);
      query31Panel.add(yearField);//end Row 1

      JLabel author1Label = new JLabel("Author 1:");
      query32Panel.add(author1Label);

      JTextField author1Field = new JTextField(15);
      query32Panel.add(author1Field);//end Row 2 

      JLabel author2Label = new JLabel("Author 2:");
      query33Panel.add(author2Label);

      JTextField author2Field = new JTextField(15);
      query33Panel.add(author2Field);//end Row 3

      JLabel author3Label = new JLabel("Author 3:");
      query34Panel.add(author3Label);

      JTextField author3Field = new JTextField(15);
      query34Panel.add(author3Field);//end Row 4 

      JLabel author4Label = new JLabel("Author 4:");
      query35Panel.add(author4Label);

      JTextField author4Field = new JTextField(15);
      query35Panel.add(author4Field);//end Row 5

      JLabel author5Label = new JLabel("Author 5:");
      query36Panel.add(author5Label);

      JTextField author5Field = new JTextField(15);
      query36Panel.add(author5Field);//end Row 6

      JLabel predictionLabel = new JLabel("Publication Predictions:");
      query37Panel.add(predictionLabel);

      JTextField predictionField = new JTextField(10);
      query37Panel.add(predictionField);//end Row 7      


      query3Panel.add(query31Panel);
      query3Panel.add(query32Panel);
      query3Panel.add(query33Panel);
      query3Panel.add(query34Panel);
      query3Panel.add(query35Panel);
      query3Panel.add(query36Panel);
      query3Panel.add(query37Panel);//end query3 panels.
  
   }

   public void panelComboBox() {
      final DefaultComboBoxModel<String> panelName = new DefaultComboBoxModel<String>();

      panelName.addElement("Select a Query");
      panelName.addElement("Query 1");
      panelName.addElement("Query 2");
      panelName.addElement("Query 3");
      
      final JComboBox<String> listCombo = new JComboBox<String>(panelName);    
      listCombo.setSelectedIndex(0);

      listComboScrollPane = new JScrollPane(listCombo);    

      showButton = new JButton("Show");

      // listComboScrollPane.addItemListener(new )

      showButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { 
            String data = "";
            if (listCombo.getSelectedIndex() != -1) {  
               CardLayout cardLayout = (CardLayout)(panel.getLayout());
               cardLayout.show(panel, listCombo.getItemAt(listCombo.getSelectedIndex()));                
            }              
            statusLabel.setText(data);
         }
      }); 

   }

   public void panelResultTable() {
      getStartTable();
      table.setRowHeight(26);
      final JScrollPane scrollPane = new JScrollPane(table);
      // scrollPane.setPreferredSize(new Dimension(1500, 1000));
      scrollPane.setPreferredSize(new Dimension(850, 300));
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
      final JButton next = new JButton("next");
      final JButton prev = new JButton("prev");

      ActionListener al = new ActionListener(){
         public void actionPerformed(ActionEvent e) {
            Rectangle rect = scrollPane.getVisibleRect();
            JScrollBar  bar = scrollPane.getVerticalScrollBar();
            int blockIncr = scrollPane.getViewport().getViewRect().height;
            // int blockIncr = 505;
            System.out.println(blockIncr);
            if (e.getSource() == next) {
               bar.setValue(bar.getValue() + blockIncr);
            } 
            else if (e.getSource() == prev) {
               bar.setValue(bar.getValue() - blockIncr);
            }
            scrollPane.scrollRectToVisible(rect);
         }
      };

      next.addActionListener(al);
      prev.addActionListener(al);

      tablePanel = new JPanel(new BorderLayout());
      // tablePanel.setPreferredSize(new Dimension(640, 480));
      JPanel buttonPanel = new JPanel(new FlowLayout());
      buttonPanel.add(prev);
      buttonPanel.add(next);
      tablePanel.add(buttonPanel, BorderLayout.SOUTH);
      tablePanel.add(scrollPane, BorderLayout.CENTER);

   }

   private void getQ2Table() {
        String[] colNames = new String[] {"SNo","Author", "Number of Publications"};
        DefaultTableModel dtm = new DefaultTableModel(map, colNames);
        table.setModel(dtm);
   }

   private void getQ1Table(String[][] arr) { //SNO, AUTHORS, TITLE, PAGES, YEAR, VOLUME, JOURNAL, URL
        String[] colNames = new String[] {"SNo", "Authors", "Title", "Pages", "Year", "Volume", "Journal", "url"};
        DefaultTableModel dtm = new DefaultTableModel(arr, colNames);
        table.setModel(dtm);
   }

   private void getStartTable() {
        String[] colNames =  new String[] {"WELCOME"};
        String[][] startArr = new String [20][1];
        table = new JTable(startArr,colNames);
   }

   public SAXParser getSAXParser() {
      try {


         File inputFile = new File(fileName);
         InputStream inputStream = new FileInputStream(inputFile);
         Reader reader = new InputStreamReader(inputStream);
         is = new InputSource(reader);
            
         is.setEncoding("ISO-8859-1");
         
         System.setProperty("jdk.xml.entityExpansionLimit", "0");
         SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
         
       }
       catch (Exception e) {
         e.printStackTrace();
         // return saxParser;
       }
       
      return saxParser;
   }

   public void getKPublications(int k) {
      try {
         SAXParser saxParser = getSAXParser();

         GetAllAuthors publ = new GetAllAuthors();
         saxParser.parse(is, publ);

         HashMap<String,Integer> hashMap = publ.getHashMap();

         kPublications = new GetKPublications(hashMap,k);
         q2Count = kPublications.getCount();

         System.out.println("second");
         kPublications = parseAgain(kPublications);

         map = kPublications.getAuthors(k);

         

         System.out.println("done");
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   public GetKPublications parseAgain(GetKPublications publ) {
      try {
         File inputFile = new File(fileName);
         InputStream inputStream = new FileInputStream(inputFile);
         Reader reader = new InputStreamReader(inputStream);
         InputSource is = new InputSource(reader);
         
         is.setEncoding("ISO-8859-1");
         
         System.setProperty("jdk.xml.entityExpansionLimit", "0");
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();

         saxParser.parse(is, publ);

         // publ.getAuthors();

         return publ;
      }
      catch (Exception e) {
         e.printStackTrace();
         return publ;

        }
   }

   public void getAuthorYearSorted(String name, String year) {
      try {
         int foo = Integer.parseInt(year);
         SAXParser saxParser = getSAXParser();

         SearchAuthorPublication publ = new SearchAuthorPublication(name, year, authorEntities);

         saxParser.parse(is, publ);

         getQ1Table(publ.getArray());

         publ.printData();
      }
      catch (NumberFormatException num) {
         JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void getAuthorYearSorted (String name, String startYear, String endYear) {
      try {
         int foo = Integer.parseInt(startYear);
         int bar = Integer.parseInt(endYear);

         if (foo >= bar)
            throw new Exception();

         SAXParser saxParser = getSAXParser();

         SearchAuthorPublication publ = new SearchAuthorPublication(name, startYear, endYear, authorEntities);

         saxParser.parse(is, publ);

         getQ1Table(publ.getArray());

         publ.printData();
      }
      catch (NumberFormatException num) {
         JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
      catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Warning", JOptionPane.WARNING_MESSAGE);

         // e.printStackTrace();
      }
   }

   public void getTitleYearSorted(String name, String year) {
      try {
         int foo = Integer.parseInt(year);
         SAXParser saxParser = getSAXParser();

         SearchTitlePublications publ = new SearchTitlePublications(name, year, authorEntities);

         saxParser.parse(is, publ);

         getQ1Table(publ.getArray());

         // publ.printData();
      }
      catch (NumberFormatException num) {
         JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void getTitleYearSorted (String name, String startYear, String endYear) {
      try {
         int foo = Integer.parseInt(startYear);
         int bar = Integer.parseInt(endYear);
         if (foo >= bar)
            throw new Exception();

         SAXParser saxParser = getSAXParser();

         SearchTitlePublications publ = new SearchTitlePublications(name, startYear, endYear, authorEntities);

         saxParser.parse(is, publ);

         getQ1Table(publ.getArray());

         // publ.printData();
      }
      catch (NumberFormatException num) {
         JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
      catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Warning", JOptionPane.WARNING_MESSAGE);

         // e.printStackTrace();
      }
   }


}