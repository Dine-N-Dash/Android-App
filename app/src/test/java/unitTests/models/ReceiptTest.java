package unitTests.models;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.models.Transaction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReceiptTest {
    private Receipt receipt;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        receipt = new Receipt();
    }

    @Test
    public void testAddItem() {
        receipt.setItems(new ArrayList<ReceiptItem>());
        int size = receipt.getItems().getValue().size();
        assertThat(size, is(equalTo(0)));

        receipt.addItem(new ReceiptItem("Pizza", 14.23));
        size = receipt.getItems().getValue().size();
        assertThat(size, is(equalTo(1)));
    }

    @Test
    public void testAddTransaction() {
        receipt.setTransactions(new ArrayList<Transaction>());
        int size = receipt.getTransactions().getValue().size();
        assertThat(size, is(equalTo(0)));

        receipt.addTransaction(new Transaction("Bob", "1234567890"));
        size = receipt.getTransactions().getValue().size();
        assertThat(size, is(equalTo(1)));
    }

    @Test
    public void testSetItems() {
        ArrayList<ReceiptItem> testItems = new ArrayList<>();
        testItems.add(new ReceiptItem("Pizza", 14.23));
        receipt.setItems(testItems);

        List<ReceiptItem> items = receipt.getItems().getValue();

        assertThat(testItems, is(equalTo(items)));
    }

    @Test
    public void testSetTransactions() {
        ArrayList<Transaction> testTransactions = new ArrayList<>();
        testTransactions.add(new Transaction("Bob", "1234567890"));
        receipt.setTransactions(testTransactions);

        List<Transaction> transactions = receipt.getTransactions().getValue();

        assertThat(testTransactions, is(equalTo(transactions)));
    }

    @Test
    public void testSetName() {
        receipt.setName("Dinner");
        assertThat(receipt.getName().getValue(), is(equalTo("Dinner")));
    }
}
