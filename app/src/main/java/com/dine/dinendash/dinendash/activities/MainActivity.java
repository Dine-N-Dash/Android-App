package com.dine.dinendash.dinendash.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dine.dinendash.dinendash.R;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }
}
