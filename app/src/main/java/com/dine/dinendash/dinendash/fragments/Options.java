package com.dine.dinendash.dinendash.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentOptionsBinding;
import com.dine.dinendash.dinendash.util.Statics;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.navigation.Navigation;

import static android.app.Activity.RESULT_OK;

public class Options extends Fragment {

    private String currentPhotoPath;

    public Options() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }catch (Exception e){}
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentOptionsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_options, container, false);
        View view = binding.getRoot();
        binding.setFragment(this);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void takePhotoPressed() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Statics.REQUEST_IMAGE_CAPTURE);
            }
        }    }

    public void uploadPhotoPressed() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Statics.REQUEST_GET_SINGLE_FILE);
    }

    public void historyPressed() {
        Log.d("PIZZA", "history");
    }

    public void settingsPressed() {
        Log.d("PIZZA", "settings");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        Bundle bundle = null;

        if (resultCode == RESULT_OK) {
            if(requestCode == Statics.REQUEST_GET_SINGLE_FILE) {
                currentPhotoPath = data.getDataString();
            }

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(currentPhotoPath));
            } catch (Exception e){
                Log.e("PHOTOERROR", e.toString());
            }

            if (bitmap != null) {
                bundle = new Bundle();
                bundle.putParcelable("bitmap", bitmap);
            }

            if(getView()!=null) {
                Navigation.findNavController(getView()).navigate(R.id.action_options_to_receiptItems, bundle);
            }
        }
    }
}
