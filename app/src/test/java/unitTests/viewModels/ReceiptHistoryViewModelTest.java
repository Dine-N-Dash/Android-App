// Written by: Shelby Heffron

package unitTests.viewModels;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.viewModels.ReceiptHistoryViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReceiptHistoryViewModelTest {
    private ReceiptHistoryViewModel receiptHistoryViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        receiptHistoryViewModel = new ReceiptHistoryViewModel();
    }

    @Test
    public void testReceiptHistoryViewModelConstructor() {
        assertThat(receiptHistoryViewModel.getReceipts().getValue().isEmpty(), is(equalTo(true)));
    }

    @Test
    public void testAddReceipt() {
        Receipt receipt = new Receipt();
        receiptHistoryViewModel.addReceipt(receipt);
        assertThat(receiptHistoryViewModel.getReceipts().getValue().get(0), is(equalTo(receipt)));
    }

    @Test
    public void testGetReceipts() {
        MutableLiveData<List<Receipt>> receipts = new MutableLiveData<>();
        ArrayList<Receipt> receiptList = new ArrayList<>();
        receiptList.add(new Receipt());
        receipts.setValue(receiptList);

        receiptHistoryViewModel.getReceipts().setValue(receiptList);

        assertThat(receiptHistoryViewModel.getReceipts().getValue(), is(equalTo((List<Receipt>)receiptList)));
        assertThat(receiptHistoryViewModel.getReceipts().getValue().size(), is(equalTo(1)));
    }

    @Test
    public void testSetReceipts() {
        MutableLiveData<List<Receipt>> receipts = new MutableLiveData<>();
        ArrayList<Receipt> receiptList = new ArrayList<>();
        receiptList.add(new Receipt());
        receipts.setValue(receiptList);

        receiptHistoryViewModel.setReceipts(receiptList);

        assertThat(receiptHistoryViewModel.getReceipts().getValue(), is(equalTo((List<Receipt>)receiptList)));
    }
}
