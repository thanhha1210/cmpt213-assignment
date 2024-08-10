package cmpt213.asn4.bank;
/**
 * SavingsAccount class extends BankAccount and adds additional functionality specific to a savings account.
 * A savings account can become inactive if the balance falls below a certain threshold.
 * @Author Irene Luu
 * @Version 01
 */
public class SavingsAccount extends BankAccount{
    private boolean isActive = false;

    public SavingsAccount(double balance, double interestRate) {
        super(balance, interestRate);
        if (balance >= 25)
            isActive = true;
    }
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        if (balance >= 25) {
            isActive = true;
        }
    }

    @Override
    public void withdraw(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Error: Can not withdraw because this account is not active.");
        }
        else {
            super.withdraw(amount); // throw Illegal if withdraw false
        }
        if (balance < 25) {
           isActive = false;
        }
    }

    @Override
    public void monthlyProcess() {
        if (numWithdraw > 4) {
            serviceCharges += (numWithdraw - 4);
        }
        super.monthlyProcess();
        if (balance < 25) {
            isActive = false;
        }
    }

    // Testing function
    public boolean isActive() {
        return isActive;
    }


}
