package unitTests.models;

import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.models.Transaction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransactionTest {
    private Transaction transaction;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        transaction = new Transaction();
    }

    @Test
    public void testTransactionConstructors() {
        transaction = new Transaction();

        String name = transaction.getName().getValue();
        assertThat(name, is(equalTo("")));

        String phoneNumber = transaction.getPhoneNumber().getValue();
        assertThat(phoneNumber, is(equalTo("")));

        Double total = transaction.getTotal().getValue();
        assertThat(total, is(equalTo(0.0)));

        Boolean completed = transaction.getCompleted().getValue();
        assertThat(completed, is(equalTo(false)));

        transaction = new Transaction("Bob", "18001234567");

        name = transaction.getName().getValue();
        assertThat(name, is(equalTo("Bob")));

        phoneNumber = transaction.getPhoneNumber().getValue();
        assertThat(phoneNumber, is(equalTo("18001234567")));
    }

    @Test
    public void testAddItem() {
        transaction.setTotal(0.0);
        transaction.addItem(new ReceiptItem("Pizza", 14.23));
        Double total = transaction.getTotal().getValue();
        assertThat(total, is(equalTo(14.23)));
    }

    @Test
    public void testRemoveItem() {
        transaction.setTotal(23.45);
        transaction.removeItem(new ReceiptItem("Pizza", 23.45));
        Double total = transaction.getTotal().getValue();
        assertThat(total, is(equalTo(0.0)));
    }

    @Test
    public void testAppyTip() {
        transaction.setTotal(10.00);

        transaction.applyTip(0.1);
        Double total = transaction.getTotal().getValue();
        assertThat(total, is(equalTo(11.00)));

        transaction.applyTip(0.25);
        total = transaction.getTotal().getValue();
        assertThat(total, is(equalTo(12.50)));
    }

    @Test
    public void testSetName() {
        transaction.setName("Mr. Man");
        String name = transaction.getName().getValue();
        assertThat(name, is(equalTo("Mr. Man")));
    }

    @Test
    public void testSetPhoneNumber() {
        transaction.setPhoneNumber("1112223333");
        String phoneNumber = transaction.getPhoneNumber().getValue();
        assertThat(phoneNumber, is(equalTo("1112223333")));
    }

    @Test
    public void testSetTotal() {
        transaction.setTotal(25.65);
        Double total = transaction.getTotal().getValue();
        assertThat(total, is(equalTo(25.65)));
    }

    @Test
    public void testToString() {
        transaction.setName("Bob");
        String str = transaction.toString();
        assertThat(str, is(equalTo("Bob")));
    }

    @Test
    public void testSetCompleted() {
        transaction.setCompleted(true);
        Boolean completed = transaction.getCompleted().getValue();
        assertThat(completed, is(equalTo(true)));
    }
}
