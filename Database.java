


import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*Object used to store Transaction objects in an arraylist type structure.*/
public class Database{
    /*Variable used to indicate the logical size of the mTransactions array.*/
    protected int mSize;
    /*Array used to hold the transaction objects. The primary part of the Database.*/
    protected Transaction[] mTransactions;
    /*A String object that stores the file name for the database(dbase.txt).*/
    protected String mFileName;
    /*This variable holds the physical size of mTransactions*/
    protected int sSizeIncrement;
    /*Constructor for a database object,which stores transactions objects in an array of transactions named 'mTransactions'.
      The default physical size of the array-sSizeIncrement-is set to twenty, then is expanded as the logical size-mSize-increases.
      Parameters:fileName-a String containing the path to the file that the database reads and writes to.*/
    public Database(String fileName){
        mFileName=fileName;
        sSizeIncrement=20;
        mSize=0;
        mTransactions=new Transaction[sSizeIncrement];
            try{
                Scanner inputReader=new Scanner(new File(mFileName));
                /*Each line of the file is read in(if it exists).*/
                while(inputReader.hasNextLine()){
                    String trans=inputReader.nextLine();
                    /*Removes the square braces from the String trans.*/
                    trans=trans.substring(1,trans.length()-1);
                    String[] inputItems=trans.split(":");
                    int memID=Integer.valueOf(inputItems[0]);
                    int memAccount =Integer.valueOf(inputItems[1]);
                    float memAmount=Float.valueOf(inputItems[2].substring(1));
                    /*This is to determine if the note instance variable is blank.*/
                    if(inputItems.length==3){
                        addTransaction(new Transaction(memID,memAccount,memAmount,""));
                    }else{
                        addTransaction(new Transaction(memID,memAccount,memAmount,inputItems[3]));
                    }
                }
            }catch(FileNotFoundException | NumberFormatException e){}
    }
    /*Adds a transaction to the mTransactions array. If the Transaction exceed the array capacity, then it is expanded and added.*/
    public void addTransaction(Transaction t){
        /*This applies of the logical size of mTransactions is less than the physical size.*/
        if(mSize<sSizeIncrement){
            mTransactions[mSize]=t;
            mSize++;
        }
        /*This applies if mTransactions needs to be expanded in size*/
        else{
            Transaction[] temp=mTransactions.clone();
            sSizeIncrement++;
            mTransactions=new Transaction[sSizeIncrement];
            System.arraycopy(temp, 0, mTransactions, 0, mSize);
            mTransactions[mSize]=t;
            mSize++;
        }
    }
    /*Sorts the transactions based on the field specified by the user.
      This is a standard bubble sort, so it is fairly inefficent unless the list is near sorted.*/
    public void sortTransactions(int field){
        boolean sorted=false;
        while(!sorted){
            sorted=true;
            for(int i=0;i<mSize-1;i++){
                if(!mTransactions[i].compare(mTransactions[i+1],field)){
                    sorted=false;
                    Transaction temp=mTransactions[i];
                    mTransactions[i]=mTransactions[i+1];
                    mTransactions[i+1]=temp;
                }
            }
        }
    }
    /*Removes a transaction from the mTransaction array. The method will do nothing if the array is less than size 0 or the id given doesn`t exist.*/
    public void removeTransaction(int idNum){
        if(mSize>=0){
            int pos=-1;
            /*Searches mTransactions to see if the ID value exists*/
            for(int i=0;i<mSize;i++){
                if(mTransactions[i].getMemberID()==idNum){
                    pos=i;
                    break;
                }
            }
            /*If the value exists,then the element is removed and every element after is moved down an index by one.*/
            if(pos>=0){
                for(int i=pos;i<mSize-1;i++){
                    mTransactions[i]=mTransactions[i+1];
                }
                mSize--;
                mTransactions[mSize]=null;
            }
        }
    } 
    /*Writes the Transactions to dbase.txt to be loaded into the Database when opened next time.*/
    public void saveDatabase() throws FileNotFoundException{
        PrintWriter fileWriter=new PrintWriter(mFileName);
        for(int i=0;i<mSize;i++)
            fileWriter.println(mTransactions[i]);
        fileWriter.close();
    }
    /*Reurns the logical size of the mTransactions array.*/
    public int getTransactionSize(){
        return mSize;
    }
    /*Returns a specified piece of data from a specific Transaction based on the value of the enum sortBy.*/
    public String getTransactionData(int index,SortState sortBy){
        if(sortBy==SortState.Identification)
            return String.valueOf(mTransactions[index].getMemberID());
        else if(sortBy==SortState.Amount)
            return String.valueOf(mTransactions[index].getMemberAmount());
        else if(sortBy==SortState.Account)
            return String.valueOf(mTransactions[index].getMemberAccountNum());
        else if(sortBy==SortState.Notes)
            return mTransactions[index].getMemberNotes();
        return "";
    }
}
