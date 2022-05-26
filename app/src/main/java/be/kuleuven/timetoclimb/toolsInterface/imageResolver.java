package be.kuleuven.timetoclimb.toolsInterface;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;

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

    default Bitmap imageViewToBitmap(ImageView imageView){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return bitmap;
    }

    default Bitmap drawbleToBitmap(Drawable drawable){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return bitmap;
    }

    /** simply resizes a given drawable resource to the given width and height */
    @SuppressWarnings("deprecation")
    default Drawable getResizedDrawble(Context context, int resId, int iconWidth, int iconHeight) {

        // load the origial Bitmap
        Bitmap BitmapOrg = BitmapFactory.decodeResource(context.getResources(),resId);

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = iconWidth;
        int newHeight = iconHeight;

        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return new BitmapDrawable(resizedBitmap);

    }

    default void loadImageToImageView(Uri imageUri, ImageView imageView){
        imageView.setImageURI(imageUri);
    }

    default ContentResolver getContentResolver(){
        return getContentResolver();
    }
}
