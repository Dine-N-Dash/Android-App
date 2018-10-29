package unitTests.models;

import com.dine.dinendash.dinendash.models.Receipt;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReceiptTest {
    private Receipt receipt;

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

    }

    @Test
    public void testSetItems() {

    }

    @Test
    public void testSetSubTotal() {

    }

    @Test
    public void testSetTotal() {

    }

    @Test
    public void testSetTotalTax() {

    }
}
