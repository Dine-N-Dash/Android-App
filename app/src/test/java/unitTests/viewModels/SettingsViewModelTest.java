package unitTests.viewModels;

import com.dine.dinendash.dinendash.viewModels.SettingsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SettingsViewModelTest {
    private SettingsViewModel settingsViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        settingsViewModel = new SettingsViewModel();
    }

    @Test
    public void testSettingsViewModelConstructor() {
        settingsViewModel = new SettingsViewModel();
        assertThat(settingsViewModel.getUsername().getValue(), is(equalTo(null)));
    }

    @Test
    public void testGetUsername() {
        settingsViewModel.getUsername().setValue("Janet");
        assertThat(settingsViewModel.getUsername().getValue(), is(equalTo("Janet")));
    }

    @Test
    public void testSetUsername() {
        settingsViewModel.setUsername("Janet");
        assertThat(settingsViewModel.getUsername().getValue(), is(equalTo("Janet")));
    }
}
