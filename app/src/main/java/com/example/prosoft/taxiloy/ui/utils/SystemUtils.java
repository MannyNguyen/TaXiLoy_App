package com.example.prosoft.taxiloy.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by prosoft on 12/18/15.
 */
public class SystemUtils {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    public static void hideKeyboard(View view, Context ctx) {
        InputMethodManager keyboard = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(view, 0);
    }

    public static void hideSoftKeyboardForEditText(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static Bitmap resizeMapIcons(int id, int width, int height, Context ctx) {
        if (ctx.getResources() != null) {
            Bitmap imageBitmap = BitmapFactory.decodeResource(ctx.getResources(), id);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
            return resizedBitmap;
        }
        return null;
    }

    public static String extractStringFromTextView(TextView tv) {
        CharSequence cs = tv.getText();

        if (cs != null) {
            return cs.toString();
        }

        return null;
    }

    public static boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void saveDeviceID(String deviceId, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("deviceId", deviceId);
        editor.commit();
    }

    public static String getDeviceID(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getString("deviceId", null);
    }

    public static void saveIDPassenger(int idPassenger, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("idPassenger", idPassenger);
        editor.commit();
    }

    public static int getIDPassenger(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getInt("idPassenger", 0);
    }

    public static void saveIDCar(long idCar, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("idCar", idCar);
        editor.commit();
    }

    public static long getIDCar(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getLong("idCar", 0);
    }

    public static void saveStatusLogin(boolean isLogin, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
    }

    public static boolean getStatusLogin(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getBoolean("isLogin", false);
    }

    public static void saveUserType(String userType, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userType", userType);
        editor.commit();
    }

    public static String getUserType(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getString("userType", null);
    }

    public static void saveAccessToken(String accessToken, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("accessToken", accessToken);
        editor.commit();
    }

    public static String getAccessToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getString("accessToken", null);
    }

    public static void saveDeviceToken(String accessToken, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("deviceToken", accessToken);
        editor.commit();
    }

    public static String getDeviceToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getString("deviceToken", null);
    }

    public static void saveIDMatchLog(int idMatchLog, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("idMatchLog", idMatchLog);
        editor.commit();
    }

    public static int getIDMatchLog(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getInt("idMatchLog", 0);
    }

    public static void saveLanguage(int idLanguage, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("idLanguage", idLanguage);
        editor.commit();
    }

    public static int getLanguage(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getInt("idLanguage", 1);
    }

    public static String BitMapToString(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public static Bitmap StringToBitMap(String encodedString, int reqWidth, int reqHeight) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);

            InputStream is = new ByteArrayInputStream(encodeByte);

            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            bitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, bitmapFactoryOptions);

            bitmapFactoryOptions.inSampleSize = calculateInSampleSize(bitmapFactoryOptions, reqWidth, reqHeight);

            bitmapFactoryOptions.inJustDecodeBounds = false;

            is = new ByteArrayInputStream(encodeByte);

            Bitmap bitmap = BitmapFactory.decodeStream(is, null, bitmapFactoryOptions);

            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Uri uri, int reqWidth, int reqHeight, Context context) {
        try {
            String path = getRealPathFromURI(uri, context);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);

            return bitmap;
        } catch (Exception ex) {
            return null;
        }
    }

    private static String getRealPathFromURI(Uri contentUri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //Check device have service or not to set function push
    public static boolean checkPlayServices(Activity context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    //Use Phone call
    public static void actionCall(String number, Context context) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        try {
            context.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }

    //Use Phone SMS
    public static void sendSMS(String number, Context context) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", number);
//        smsIntent.putExtra("sms_body", "Test ");

        try {
            context.startActivity(smsIntent);
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getTime() {
        String datetime;
        TimeZone tz = TimeZone.getDefault();
        Calendar calendar = Calendar.getInstance(tz);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            month = "0" + month;
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + day;
        }
        datetime = month + "/" + day + "/" + year;
        return datetime;
    }

    public static void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

}
