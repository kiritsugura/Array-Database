

/*The Transaction class stores information about a bank Transaction.*/
public class Transaction {
    /*The mID variable holds the transaction number,while the mAccount holds the account number.*/
    protected int mID,mAccount;
    /*totalTrans holds the number of transaction objects created.*/
    protected static int totalTrans;
    /*mAmount holds the amount of money processed in the Transaction.*/
    protected float mAmount;
    /*mNotes holds any notes about the transaction.*/
    protected String mNotes;
    /*A Transaction Constructor used to instantiate the Transactions in dbase.txt.*/
    public Transaction(int memID,int memAccount,float memAmount,String memNotes){
        mID=memID;
        mAccount=memAccount;
        mAmount=memAmount;
        mNotes=memNotes;
        totalTrans++;
    }
    /*A Transaction Constcutor used to instantiate new Transactions entered by the user.*/
    public Transaction(int memAccount,float memAmount,String memNotes){
        mID=totalTrans;
        mAccount=memAccount;
        mAmount=memAmount;
        mNotes=memNotes;
        totalTrans++;
    }
    /*Returns the Transaction number of the object.*/
    public int getMemberID(){
        return mID;
    }
    /*Returns the account number of the Transaction.*/
    public int getMemberAccountNum(){
        return mAccount;
    }
    /*Returns the amount processed in this Transaction.*/
    public float getMemberAmount(){
        return mAmount;
    }
    /*Returns the Notes associated with this Transaction.*/
    public String getMemberNotes(){
        return mNotes;
    }    
    /*Compares the values of the instance variables to those of another Transaction object.
      An Exception is thrown if the index is not 1,2,3 or 4.
      Returns true only if the value of the this Transaction object specified by index is smaller than the coresponding varible in Transaction other.
      Returns false only if the value of the this Transaction object is larger than that of the coresponding varible in Transaction other.*/
    public boolean compare(Transaction other,int index) throws NumberFormatException{
        if(index<1||index>4)
           throw new NumberFormatException();
        else if(index==1 && mID<=other.getMemberID()){
            return true;
        }else if(index==2 && mAccount<=other.getMemberAccountNum()){
            return true;
        }else if(index==3 && mAmount<=other.getMemberAmount()){
            return true;
        }else if(index==4 && mNotes.length()<=other.getMemberNotes().length()){
            return true;
        }
        return false;
    }
    /*Custom String return for a Transaction object.
      Overrides toString() located in object superclass.*/
    @Override
    public String toString(){
        return "["+mID+":"+mAccount+":$"+mAmount+":"+mNotes+"]";
    }
}
