package cmpt213.asn4.bank;

/**
 * Unit tests for the SavingsAccount class.
 * @Author Irene Luu
 * @Version 01
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static  org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {

    SavingsAccount saveAccount;

    @BeforeEach
    void setUp() {
        System.out.println("Run before each test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Run after each test");
    }

    //-----------------------------test constructor----------------------------------

    // Case 1: balance > 0, interest > 0
    @Test
    void validConstructor1() {
        saveAccount = new SavingsAccount(10, 3);
        assertEquals(10, saveAccount.getBalance());
        assertEquals(3, saveAccount.getInterestRate());
    }
    // Case 2: balance = 0, interest = 0
    @Test
    void validConstructor2() {
        saveAccount = new SavingsAccount(0, 0);
        assertEquals(0, saveAccount.getBalance());
        assertEquals(0, saveAccount.getInterestRate());
    }

    // Case 3: balance < 0
    @Test
    void invalidConstructor1() {
        try {
            saveAccount = new SavingsAccount(-10, 3);
            fail();
        }
        catch (IllegalArgumentException e) { // if it has illegalArgument => it is true
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }
    // Case 4: interest < 0
    @Test
    void invalidConstructor2() {
        try {
            saveAccount = new SavingsAccount(10, -3);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    //-----------------------------test deposit----------------------------------

    // Case 1: deposit amount > 0, both when account inactive and account active
    @Test
    void validDeposit() {
        saveAccount = new SavingsAccount(10, 3);
        assertFalse(saveAccount.isActive());

        saveAccount.deposit(10);
        assertEquals(20, saveAccount.getBalance());
        assertFalse(saveAccount.isActive());
        assertEquals(1, saveAccount.getNumDeposit());

        saveAccount.deposit(10);
        assertEquals(30, saveAccount.getBalance());
        assertTrue(saveAccount.isActive());
        assertEquals(2, saveAccount.getNumDeposit());
    }


    // Case 2: deposit amount = 0
    @Test
    void invalidDeposit1() {
        saveAccount = new SavingsAccount(10, 3);
        try {
            saveAccount.deposit(0);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }
    // Case 3: deposit amount < 0
    @Test
    void invalidDeposit2() {
        saveAccount = new SavingsAccount(10, 3);
        try {
            saveAccount.deposit(-5);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    //-----------------------------test withdraw----------------------------------
    // Case 1: amount withdraw > 0, sufficient fund, account active -> active
    @Test
    void validWithdraw1() {
        saveAccount = new SavingsAccount(50, 3);
        assertTrue(saveAccount.isActive());

        saveAccount.withdraw(20);
        assertEquals(30, saveAccount.getBalance());
        assertTrue(saveAccount.isActive());
    }

    // Case 2: amount withdraw > 0, sufficient fund, account active -> inactive
    @Test
    void validWithdraw2() {
        saveAccount = new SavingsAccount(50, 3);
        assertTrue(saveAccount.isActive());

        saveAccount.withdraw(40);
        assertEquals(10, saveAccount.getBalance());
        assertFalse(saveAccount.isActive());
    }

    // Case 3: amount withdraw > 0, account active before withdraw, insufficient fund
    @Test
    void invalidWithdraw1() {
        saveAccount = new SavingsAccount(50, 3);
        assertTrue(saveAccount.isActive());

        try {
            saveAccount.withdraw(60);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch(Exception e) {
            fail();
        }
    }

    // Case 4: amount withdraw > 0, account inactive before withdraw
    @Test
    void invalidWithdraw2() {
        saveAccount = new SavingsAccount(20, 3);
        try {
            saveAccount.withdraw(10);
            fail();
        }
        catch (IllegalStateException e) {
            assertTrue(true);
        }
        catch(IllegalArgumentException e) {
            fail();
        }
    }


    // Case 5: amount withdraw = 0
    @Test
    void invalidWithdraw3() {
        saveAccount = new SavingsAccount(50, 3);
        try {
            saveAccount.withdraw(0);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch(Exception e) {
            fail();
        }
    }

    // Case 6: amount withdraw < 0
    @Test
    void invalidWithdraw4() {
        saveAccount = new SavingsAccount(50, 3);
        try {
            saveAccount.withdraw(-10);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch(Exception e) {
            fail();
        }
    }

    //-----------------------------test calInterest----------------------------------
    @Test
    void validCalInterest() {
        saveAccount = new SavingsAccount(100, 1.2);
        saveAccount.calcInterest();
        assertEquals(110, saveAccount.getBalance());
    }


    //-----------------------------test monthlyProcess----------------------------------
    // Case 1: numWithdraw <= 4 (fee = 0)
    @Test
    void validMonthlyProcess1() {
        saveAccount = new SavingsAccount(140, 1.2);
        for (int i = 1; i <= 4; i++) {
            saveAccount.withdraw(10);
        }
        assertEquals(100, saveAccount.getBalance());
        saveAccount.monthlyProcess();

        assertEquals(110, saveAccount.getBalance());
        assertTrue(saveAccount.isActive());
    }

    // Case 2: numWithdraw > 4, sufficient fund to pay, active -> active
    @Test
    void validMonthlyProcess2() {
        saveAccount = new SavingsAccount(136, 1.2);
        for (int i = 1; i <= 10; i++) {
            saveAccount.withdraw(10);
        }
        assertEquals(36, saveAccount.getBalance());         // withdraw 10 times -> remain 36
        saveAccount.monthlyProcess();                               // service charges = 10 - 4 = 6 -> remain 30; interest month = 0.1 -> interest amount = 3 => balance = 33
        assertEquals(33, saveAccount.getBalance());
        assertTrue(saveAccount.isActive());
    }

    // Case 3: numWithdraw > 4, sufficient fund to pay, active -> inactive
    @Test
    void validMonthlyProcess3() {
        saveAccount = new SavingsAccount(137, 1.2);
        for (int i = 1; i <= 11; i++) {
            saveAccount.withdraw(10);
        }
        assertEquals(27, saveAccount.getBalance());         // withdraw 110 -> remain 27
        assertEquals(11, saveAccount.getNumWithdraw());     // service charges = 11 - 4 = 7

        saveAccount.monthlyProcess();
        assertEquals(22, saveAccount.getBalance());         // balance = 27 - 7 = 20; interest = 0.1 -> interest amount = 2 => balance = 22
        assertFalse(saveAccount.isActive());
        assertEquals(0, saveAccount.getServiceCharges());   // return service charges to 0
    }

    // Case 4: numWithdraw > 4, insufficient fund to pay
    @Test
    void invalidMonthlyProcess1() {
        saveAccount = new SavingsAccount(137, 1.2);
        for (int i = 1; i <= 11; i++) {
            saveAccount.withdraw(10);
        }
        assertTrue(saveAccount.isActive());                         // balance = 137 - 11 * 10 = 27
        saveAccount.withdraw(20);
        assertEquals(7, saveAccount.getBalance());          // still active; withdraw 20 -> remain 7
        assertEquals(12, saveAccount.getNumWithdraw());     // withdraw 12 times -> service charge = 8
        try {
            saveAccount.monthlyProcess();
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

}
