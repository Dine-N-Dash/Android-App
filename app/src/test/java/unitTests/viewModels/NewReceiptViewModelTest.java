package unitTests.viewModels;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NewReceiptViewModelTest {
    private NewReceiptViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        viewModel = new NewReceiptViewModel();
    }

    @Test
    public void testNewReceiptViewModelConstructor() {
        viewModel = new NewReceiptViewModel();
        assertThat(viewModel.getProcessed().getValue(), is(equalTo(false)));
    }

    @Test
    public void testSetReceipt() {
        Receipt receipt = new Receipt();
        viewModel.setReceipt(receipt);
        assertThat(viewModel.getReceipt().getValue(), is(equalTo(receipt)));
    }

    @Test
    public void testAddTransaction() {
        viewModel.getReceipt().getValue().getTransactions().setValue(new ArrayList<Transaction>());
        assertThat(viewModel.getReceipt().getValue().getTransactions().getValue().size(), is(equalTo(0)));
        viewModel.addTransaction("Bob", "1234567890");
        assertThat(viewModel.getReceipt().getValue().getTransactions().getValue().size(), is(equalTo(1)));
    }

    @Test
    public void testItemSelected() {
        Transaction transaction = new Transaction("Bill", "1234567890");
        viewModel.setCurrentTransaction(transaction);
        ReceiptItem item = new ReceiptItem("Pizza", 14.92);

        viewModel.itemSelected(item);
        assertThat(item.getOwner(), is(equalTo(transaction)));
        assertThat(viewModel.getCurrentTransaction().getValue().getTotal().getValue(), is(equalTo(14.92)));

        viewModel.itemSelected(item);
        assertThat(item.getOwner(), is(equalTo(null)));
        assertThat(viewModel.getCurrentTransaction().getValue().getTotal().getValue(), is(equalTo(0.0)));
    }

    @Test
    public void testSetCurrentTransaction() {
        Transaction transaction = new Transaction();
        viewModel.setCurrentTransaction(transaction);
        assertThat(viewModel.getCurrentTransaction().getValue(), is(equalTo(transaction)));
    }

    @Test
    public void testSetCurrentTransactionByIndex() {
        viewModel.addTransaction("Jim", "1234567890");
        Transaction transaction = viewModel.getReceipt().getValue().getTransactions().getValue().get(0);
        viewModel.setCurrentTransaction(0);

        assertThat(viewModel.getCurrentTransaction().getValue(), is(equalTo(transaction)));
    }

    @Test
    public void testSetTipPercent() {
        viewModel.setTipPercent(15);
        assertThat(viewModel.getTipPercent().getValue(), is(equalTo(15)));
    }

    @Test
    public void testSetTipPercentDecimal() {
        viewModel.setTipPercentDecimal(5);
        assertThat(viewModel.getTipPercentDecimal().getValue(), is(equalTo(5)));
    }

    @Test
    public void testAddTip() {
        viewModel.reset();

        viewModel.addTransaction("Jim", "1234567890");

        ReceiptItem item = new ReceiptItem("Pizza", 10.00);
        viewModel.getReceipt().getValue().addItem(item);
        viewModel.setCurrentTransaction(0);
        viewModel.itemSelected(item);

        viewModel.setTipPercent(10);
        viewModel.setTipPercentDecimal(0);

        viewModel.addTip();

        assertThat(viewModel.getCurrentTransaction().getValue().getTotal().getValue(), is(equalTo(11.00)));
    }

    @Test
    public void testSetProcessed() {
        viewModel.setProcessed(true);
        assertThat(viewModel.getProcessed().getValue(), is(equalTo(true)));
    }

    @Test
    public void testReset() {
        viewModel.reset();
        assertThat(viewModel.getReceipt().getValue().getItems().getValue().size(), is(equalTo(0)));
        assertThat(viewModel.getCurrentTransaction().getValue().getTotal().getValue(), is(equalTo(0.0)));
        assertThat(viewModel.getProcessed().getValue(), is(equalTo(false)));
    }
}
