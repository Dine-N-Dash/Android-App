// Written by: Shelby Heffron

package unitTests.viewModels;

import com.dine.dinendash.dinendash.viewModels.FirstTimeLoginViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FirstTimeLoginViewModelTest {
    private FirstTimeLoginViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        viewModel = new FirstTimeLoginViewModel();
    }

    @Test
    public void testFirstTimeLoginViewModelConstructor() {
        viewModel = new FirstTimeLoginViewModel();
        assertThat(viewModel.getUsername().getValue(), is(equalTo("")));
    }

    @Test
    public void testSetUsername() {
        viewModel.setUsername("Bob");
        assertThat(viewModel.getUsername().getValue(), is(equalTo("Bob")));
    }
}
