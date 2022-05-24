package be.kuleuven.timetoclimb.subActivity;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface imageResolver {
    default String onRetrieveSuccess(String b64String){return b64String;}
    default String uriToString(Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //compress bitmap into JPEG and output to byte array with quality 100%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String b64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return b64String;
    }
    default Bitmap uriToBitmap(Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        return bitmap;
    }
    default String bitmapToString(Bitmap bitmap) throws IOException {
        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //compress bitmap into JPEG and output to byte array with quality 100%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String b64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return b64String;
    }
    default Bitmap StringToBitmap(String b64String){
        //converting base64 string to image when b64String is not null
        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
        Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
        return bitmap;
    }

    default Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = ((float) newWidth) / width;

        // We create a matrix to transform the image
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create the new bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    default void loadImageToImageView(Uri imageUri, ImageView imageView){
        imageView.setImageURI(imageUri);
    }

    default ContentResolver getContentResolver(){
        return getContentResolver();
    }
}