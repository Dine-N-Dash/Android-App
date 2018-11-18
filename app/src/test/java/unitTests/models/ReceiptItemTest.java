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


public class ReceiptItemTest {
    private ReceiptItem item;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        item = new ReceiptItem();
    }

    @Test
    public void testReceiptItemConstructors() {
        item = new ReceiptItem();

        String name = item.getName();
        assertThat(name, is(equalTo("")));

        double price = item.getPrice();
        assertThat(price, is(equalTo(0.0)));

        Transaction owner = item.getOwner();
        assertThat(owner, is(equalTo(null)));

        item = new ReceiptItem("Pizza", 12.95);

        name = item.getName();
        assertThat(name, is(equalTo("Pizza")));

        price = item.getPrice();
        assertThat(price, is(equalTo(12.95)));
    }

    @Test
    public void testSetName() {
        item.setName("Sloppy Joe");
        String name  = item.getName();
        assertThat(name, is(equalTo("Sloppy Joe")));
    }

    @Test
    public void testSetPrice() {
        item.setPrice(5.80);
        double price = item.getPrice();
        assertThat(price, is(equalTo(5.80)));
    }

    @Test
    public void testSetOwner() {
        Transaction testOwner = new Transaction();
        item.setOwner(testOwner);
        Transaction owner = item.getOwner();
        assertThat(owner, is(equalTo(testOwner)));
    }
}
