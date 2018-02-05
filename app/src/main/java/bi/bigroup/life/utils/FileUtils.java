package bi.bigroup.life.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

//    public static File compressFile(Context context, String photoPath) {
//        Bitmap bitmap = getSmallBitmap(photoPath);
//        File file = null;
//        try {
//            file = FileUtils.createImageFile(context);
//        } catch (IOException ex) {
//            // Error occurred while creating the File
//            ex.printStackTrace();
//        }
//        // Continue only if the File was successfully created
//        if (file != null) {
//
//            OutputStream os;
//            try {
//                os = new FileOutputStream(file);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
//                os.flush();
//                os.close();
//            } catch (Exception e) {
//                Log.e("FileUtils", "Error writing bitmap", e);
//                e.printStackTrace();
//            }
//        }
//
//        return file;
//    }

    public static File compressFile(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = getSmallBitmap(context, uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        if (bitmap == null) {
            return null;
        }

        File file = null;
        try {
            file = createImageFile(context);
        } catch (IOException ex) {
            // Error occurred while creating the File
            ex.printStackTrace();
        }
        // Continue only if the File was successfully created
        if (file != null) {

            OutputStream os;
            try {
                os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e("FileUtils", "Error writing bitmap", e);
                e.printStackTrace();
            }
        }

        return file;
    }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

//    public static Bitmap getSmallBitmap(String filePath) {
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        options.inSampleSize = calculateInSampleSize(options, 480);
//        options.inJustDecodeBounds = false;
////        BitmapFactory.decodeFile(filePath, options);
//
//        Bitmap compressedImage = BitmapFactory.decodeFile(filePath, options);
//
//        return compressedImage;
//    }

    public static Bitmap getSmallBitmap(Context context, Uri uri) throws FileNotFoundException {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = calculateInSampleSize(options, 480);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > reqWidth) {

            final int halfWidth = width / 2;

            while ((halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor == null)
            return null;

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}