package unitTests.models;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;

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
    public void testReceiptConstructor() {
        receipt = new Receipt();

        double subTotal = receipt.getSubTotal();
        assertThat(subTotal, is(equalTo(0.0)));

        double totalTax = receipt.getTotalTax();
        assertThat(totalTax, is(equalTo(0.0)));

        double total = receipt.getTotal();
        assertThat(total, is(equalTo(0.0)));
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
    public void testSetItems() {
        ArrayList<ReceiptItem> testItems = new ArrayList<>();
        testItems.add(new ReceiptItem("Pizza", 14.23));
        receipt.setItems(testItems);

        List<ReceiptItem> items = receipt.getItems().getValue();

        assertThat(testItems, is(equalTo(items)));
    }

    @Test
    public void testSetSubTotal() {
        receipt.setSubTotal(35.95);
        double subTotal = receipt.getSubTotal();
        assertThat(subTotal, is(equalTo(35.95)));
    }

    @Test
    public void testSetTotal() {
        receipt.setTotal(38.75);
        double total = receipt.getTotal();
        assertThat(total, is(equalTo(38.75)));
    }

    @Test
    public void testSetTotalTax() {
        receipt.setTotalTax(2.80);
        double totalTax = receipt.getTotalTax();
        assertThat(totalTax, is(equalTo(2.80)));
    }
}
