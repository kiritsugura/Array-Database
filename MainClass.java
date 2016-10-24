

/*A ton of imports*/
import java.io.IOException;
import java.awt.Container;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;
/*The main class that runs the database program.*/
public class MainClass{
    /*The Window that contains everything in the program.*/
    protected static JFrame frame;
    /*Primarily used here for background color.*/
    protected static JPanel content;
    /*The database containing all of the transaction objects.*/
    protected static Database transactions;
    /*The Menubar that hold all of the Menus and MenuItems.*/
    protected static JMenuBar theMenu;
    /*Menus used to organize the sort and account balance functions*/
    protected static JMenu sortBy,findAccountBalance;
    /*MenuItems for each menu that have a ActionListener object associated with each of them.*/
    protected static JMenuItem sortByID,sortByAccount,sortByAmount,sortByNoteLength,findBalance;
    /*The scrollbar used for the JListt transactionList.*/
    protected static JScrollPane scrollPane;
    /*A Lists that contains and displays all of the transactions based on the current sorted preference.*/
    protected static JList transactionList;
    /**JLabels used to display text so the user better understands how the program works.*/
    protected static JLabel idLabel,accountLabel,amountLabel,noteLabel,enterAccountLabel,enterAmountLabel,enterNoteLabel,addTransLabel,transactionLabel;
    /*Textfields used for entering a new transaction object.*/
    protected static JTextField accountField,amountField,noteField;
    /*JButtons used to remove or add a transaction via an ActionListener.*/
    protected static JButton addTransaction,removeTransaction;
    /*An enum used to determine the current sorting preference of the program.*/
    protected static SortState sortByItem;
    /*Main method of the program. Configures the JFrame options then calls methods to set everything else up.*/
    public static void main(String[] args){
        frame=new JFrame("Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowListener(new ClosingAction());  
        miscSetup();
        menuSetup();
        listSetup();
        addSetup();
        setToPane(frame.getContentPane());
        
    }
    /*Input parameter is the JFrame contentpane. 
      This method sets all of the objects to the JFrame contentpane to display them.*/
    private static void setToPane(Container pane){
        pane.setLayout(null);
        pane.add(scrollPane);
        pane.add(addTransaction);
        pane.add(addTransLabel);
        pane.add(removeTransaction);
        pane.add(theMenu);
        pane.add(idLabel);
        pane.add(accountLabel);
        pane.add(amountLabel);
        pane.add(noteLabel);
        pane.add(enterAccountLabel);
        pane.add(enterAmountLabel);
        pane.add(enterNoteLabel);
        pane.add(accountField);
        pane.add(amountField);
        pane.add(noteField);
        pane.add(transactionLabel);
        pane.add(content);       
        frame.setSize(600,600);
        frame.setResizable(false);
        frame.repaint();        
    }
    /*Method that instantiates and sets up items related to the menu.*/
    private static void menuSetup(){
        theMenu=new JMenuBar();
        theMenu.setBounds(0,0,196,40);
        findAccountBalance=new JMenu("Find Account Balance");
        findAccountBalance.setBounds(56,0,150,40);
        findBalance=new JMenuItem("Input Account Number");
        findBalance.addActionListener(new totalBalanceListener());
        sortBy=new JMenu("Sort by:");
        sortByID=new JMenuItem("ID number");
        sortByAccount=new JMenuItem("Account number");
        sortByAmount=new JMenuItem("Transaction Amount");
        sortByNoteLength=new JMenuItem("Note Length");
        sortByID.addActionListener(new IdSort());
        sortByAccount.addActionListener(new AccountSort());
        sortByAmount.addActionListener(new AmountSort());
        sortByNoteLength.addActionListener(new NoteLengthSort());
        sortBy.add(sortByID);
        sortBy.add(sortByAccount);
        sortBy.add(sortByAmount);
        sortBy.add(sortByNoteLength); 
        findAccountBalance.add(findBalance);
        theMenu.setLayout(null);
        sortBy.setBounds(0,0,56,40);
        theMenu.add(sortBy);   
        theMenu.add(findAccountBalance);
    }
    /*Method that instantiates and sets up the list(and items related to it) used to display the Transactions.*/
    private static void listSetup(){
        String[] trans=new String[transactions.getTransactionSize()];
        for(int i=0;i<transactions.getTransactionSize();i++){
            String modifier;
            if(sortByItem==SortState.Identification)
                modifier="ID Number:";
            else if(sortByItem==SortState.Amount)
                modifier="Amount:";
            else if(sortByItem==SortState.Account)
                modifier="Account Number:";
            else
                modifier="Notes:";
            trans[i]=modifier+transactions.getTransactionData(i,sortByItem);
        }     
        transactionList=new JList(trans);
        transactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionList.addListSelectionListener(new listListener());
        scrollPane=new JScrollPane(transactionList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(130,200,300,100);    
    }
    /*Method that instantiates and sets up objects that aren`t part of a grouping.*/
    private static void miscSetup(){
        transactionLabel=new JLabel("Transaction List");
        transactionLabel.setFont(new Font("Title Lable",Font.ROMAN_BASELINE,30));
        sortByItem=SortState.Identification;
        content=new JPanel();      
        content.setBounds(0,0,600,600);
        content.setBackground(new Color(50,133,200));
        transactions=new Database("dbase.txt");         
        removeTransaction=new JButton("Remove Transaction");
        removeTransaction.addActionListener(new RemoveTransAction());
        removeTransaction.setBounds(270,300,160,30);
        transactionLabel.setBounds(168,150,250,40);
    }
    /*Method that instantiates and sets up items related to adding a new Transaction.*/
    private static void addSetup(){
        addTransaction=new JButton("Add Transaction");
        idLabel=new JLabel();
        accountLabel=new JLabel();
        amountLabel=new JLabel();
        noteLabel=new JLabel();
        addTransLabel=new JLabel("Fields to Add a Transaction:");
        enterAccountLabel=new JLabel("Enter Account Number:");
        enterAmountLabel=new JLabel("Enter Amount:");
        enterNoteLabel=new JLabel("Enter Notes:");
        accountField=new JTextField("");
        amountField=new JTextField("");
        noteField=new JTextField("");
        idLabel.setBounds(130,330,50,30);
        amountLabel.setBounds(180,330,100,30);
        accountLabel.setBounds(280,330,150,30);
        noteLabel.setBounds(130,360,300,30);
        enterAccountLabel.setBounds(130,430,136,40);
        enterAmountLabel.setBounds(130,470,80,40);
        enterNoteLabel.setBounds(130,510,80,40);
        addTransLabel.setBounds(190,390,200,40);        
        accountField.setBounds(300,430,100,40);
        amountField.setBounds(300,470,100,40);
        noteField.setBounds(300,510,100,40);
        addTransaction.setBounds(130,300,140,30);
        addTransaction.addActionListener(new AddTransAction());        
    }
    /*Method that updates the list transactionsList based on the current sorting preference or if an item is added or removed*/
    private static void updateList(){
        String[] trans=new String[transactions.getTransactionSize()];
        for(int i=0;i<transactions.getTransactionSize();i++){
            String modifier;
            if(sortByItem==SortState.Identification)
                modifier="ID Number:";
            else if(sortByItem==SortState.Amount)
                modifier="Amount:";
            else if(sortByItem==SortState.Account)
                modifier="Account Number:";
            else
                modifier="Notes:";            
            trans[i]=modifier+transactions.getTransactionData(i,sortByItem);
        }     
        transactionList.setListData(trans);
        /*Saves the changes to dbase.txt*/
        try{
            transactions.saveDatabase();
        }catch(IOException e){}
    }
    /*A Listener that is called when an item in the list is Selected.
      Displays more information than the sorted information via JLabels.*/
    private static class listListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e){
            if(!transactionList.isSelectionEmpty()){
                idLabel.setText("ID: "+transactions.getTransactionData(transactionList.getSelectedIndex(),SortState.Identification));
                accountLabel.setText("Account Number: "+transactions.getTransactionData(transactionList.getSelectedIndex(),SortState.Account));
                amountLabel.setText("Amount: "+transactions.getTransactionData(transactionList.getSelectedIndex(),SortState.Amount));
                noteLabel.setText("Notes: "+transactions.getTransactionData(transactionList.getSelectedIndex(),SortState.Notes));
            }
        }
    }
    /*Sorts the transactionsList based on the lowest ID value.*/
    private static class IdSort implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            transactions.sortTransactions(1);
            sortByItem=SortState.Identification;
            updateList();
        }
    }
    /*Sorts the transactionsList based on the lowest account number.*/
    private static class AccountSort implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            transactions.sortTransactions(2);
            sortByItem=SortState.Account;
            updateList();
        }
    }    
    /*Sorts the transactionsList based on the lowest amount processed.*/
    private static class AmountSort implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            transactions.sortTransactions(3);
            sortByItem=SortState.Amount;
            updateList();
        }
    }
    /*Sorts the transactionsList based on the lowest Note length.*/
    private static class NoteLengthSort implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            transactions.sortTransactions(4);
            sortByItem=SortState.Notes;
            updateList();
        }
    }       
    /*Removes an item from the List transactionList.*/
   private static class RemoveTransAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!transactionList.isSelectionEmpty()){
                transactions.removeTransaction(Integer.valueOf(idLabel.getText().substring(4)));
                updateList();
            }
        }
    }
   /*Adds an item to the List transactionsList.
     An Exception is thrown if any of the imput fields are blank.
     Once a transaction is entered, the textfields are cleared.*/
   private static class AddTransAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!(accountField.getText().equals(""))&& !(amountField.getText().equals(""))&& !(noteField.getText().equals(""))){
                transactions.addTransaction(new Transaction(Integer.valueOf(accountField.getText()),Float.valueOf(amountField.getText()),noteField.getText()));
                accountField.setText("");
                amountField.setText("");
                noteField.setText("");
                updateList();
            }else{
                throw new NumberFormatException();
            }
        }
    }  
   /*Class used to find the total account balance.
     When the user input is not a number,an exception is thrown*/
   private static class totalBalanceListener implements ActionListener{
       @Override
       public void actionPerformed(ActionEvent e) throws NumberFormatException{
           String accountNumber=JOptionPane.showInputDialog("Enter the Account Number");
           /*Used to prevent a nullPointerException.*/
           if(!(accountNumber==null) && !(accountNumber.equals(""))){
                float totalBalance=0;
                for(int i=0;i<transactions.getTransactionSize();i++){
                    if(Float.parseFloat(transactions.getTransactionData(i, SortState.Account))==Float.parseFloat(accountNumber))
                        totalBalance+=Float.parseFloat(transactions.getTransactionData(i,SortState.Amount));
                    if(i==transactions.getTransactionSize()-1 && totalBalance==0.0)
                        JOptionPane.showMessageDialog(null,"The Account Number doesn`t exist.");

                }
                /*If the account number was not found.*/
                if(!(totalBalance==0.0))
                    JOptionPane.showMessageDialog(null,"The Account Amount is $"+totalBalance);
                
           }else{
               JOptionPane.showMessageDialog(null,"Data Input Error,please try again.");
           }
       }
   }
   /*Called when the JFrame close is requested.
     Saves the transactions then closes the window.*/
   private static class ClosingAction extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent event){
            try{
                transactions.saveDatabase();
            }catch(IOException e){}
            frame.dispose();
        }
    }
}
