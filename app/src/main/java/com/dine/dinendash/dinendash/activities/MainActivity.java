package com.dine.dinendash.dinendash.activities;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.dine.dinendash.dinendash.R;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Delete unneeded files
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (storageDir != null) {
            File[] files = storageDir.listFiles();

            for (File f: files) {
                if (!f.delete()) {
                    Log.e("PHOTO-DELETE", "Unable to delete: " + f.getAbsolutePath());
                }
            }
        }

        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }
}
