package cmpt213.asn4.bank;

/**
 * Abstract class BankAccount represents a bank account with basic functionalities such as deposit,
 * withdrawal, interest calculation, and monthly processing.
 * @Author Irene Luu
 * @Version 01
 */
public abstract class BankAccount {
    protected double balance;
    protected int numDeposit;
    protected int numWithdraw;
    protected double interestRate;
    protected double serviceCharges;

    public BankAccount(double balance, double interestRate) {
        if (balance < 0) {
            throw new IllegalArgumentException("Error: Balance cannot be negative");
        }
        if (interestRate < 0) {
            throw new IllegalArgumentException("Error: Interest rate cannot be negative");
        }
        this.balance = balance;
        this.interestRate = interestRate;
        this.numDeposit = 0;
        this.numWithdraw = 0;
        this.serviceCharges = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Error: Deposit amount has to be a positive number");
        }
        balance += amount;
        numDeposit++;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Error: Withdraw amount has to be a positive number");
        }
        if (balance - amount < 0) {
            throw new IllegalArgumentException("Error: Insufficient funds to withdraw");
        }
        balance -= amount;
        numWithdraw++;
    }

    public void calcInterest() {
        double monthlyInterestRate = interestRate / 12.0;
        double monthlyInterest = balance * monthlyInterestRate;
        balance += monthlyInterest;
    }

    public void monthlyProcess() {
        if (balance < serviceCharges) {
            throw new IllegalArgumentException("Error: Insufficient funds to pay service charges");
        }
        this.balance -= serviceCharges;
        calcInterest();
        this.numDeposit = 0;
        this.numWithdraw = 0;
        this.serviceCharges = 0;
    }

    // Helper testing function
    public double getBalance() {
        return balance;
    }
    public double getInterestRate() {return interestRate;}
    public double getServiceCharges() {
        return serviceCharges;
    }
    public int getNumDeposit() {
        return numDeposit;
    }
    public int getNumWithdraw() {
        return numWithdraw;
    }


}
