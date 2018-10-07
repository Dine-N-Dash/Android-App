package com.dine.dinendash.dinendash.util;


import android.net.Uri;

import java.io.IOException;

import androidx.exifinterface.media.ExifInterface;

public class PictureRotate {
    private static int rotate = 0;

    public static int getRotateOfPicture(String uri) {

        try {
            ExifInterface exifInterface = new ExifInterface(String.valueOf(Uri.parse(uri)));

            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotate;
    }
}
