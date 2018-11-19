package com.dine.dinendash.dinendash.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentOptionsBinding;
import com.dine.dinendash.dinendash.util.Statics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import static android.app.Activity.RESULT_OK;

public class Options extends Fragment {

    private String currentPhotoPath;

    public Options() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show the Action bar if it was hidden by the splash screen
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().show();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentOptionsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_options, container, false);
        View view = binding.getRoot();

        // Bind fragment to View
        binding.setFragment(this);

        return view;
    }

    public void takePhotoPressed() {
        // Launch an Intent to take a picture
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(getActivity() != null && getActivity().getPackageManager() != null) {
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("ImageCreationError", ex.getMessage());
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, Statics.REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    public void uploadPhotoPressed() {
        // Launch an Intent to select an image from the device library
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Statics.REQUEST_GET_SINGLE_FILE);
    }

    public void historyPressed() {
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_options_to_history, null);
        }
    }

    public void settingsPressed() {
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_options_to_settings, null);
        }
    }

    private File createImageFile() throws IOException {
        if (getActivity() != null) {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";

            File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }

        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle;

        if (resultCode == RESULT_OK) {
            if(requestCode == Statics.REQUEST_GET_SINGLE_FILE) {
                currentPhotoPath = data.getDataString();
            }

            bundle = new Bundle();
            bundle.putString("photoPath", currentPhotoPath);


            // If we successfully got a photo, navigate to the receipt items view and send the photo path as an argument
            if(getView()!=null) {
                Navigation.findNavController(getView()).navigate(R.id.action_options_to_receiptItems, bundle);
            }
        }
    }
}
