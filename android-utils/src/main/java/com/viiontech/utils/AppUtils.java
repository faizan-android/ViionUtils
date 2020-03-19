package com.viiontech.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MalformedJsonException;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class AppUtils {
    public static final int MY_PERMISSIONS_REQUEST_CHECK_LOCATION = 123;
    public static final int REQUEST_PERMISSION_TYPE_FRAGMENT = 2;
    public static final int REQUEST_PERMISSION_TYPE_ACTIVITY = 1;
    public static final int MY_PERMISSIONS_REQUEST_CHECK_PHONE_STATE = 456;

   /* public static boolean isFingerPrintScannerAvailable(Context context){
        BiometricManager manager = BiometricManager.from(context);
        return manager.canAuthenticate() != BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE && manager.canAuthenticate() != BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE;
    }*/
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static CircularProgressDrawable getImageLoader(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(25f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    public static boolean checkPermissionLocation(final Context context,final int TYPE,@Nullable Fragment fragment,int dialogStyle) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, dialogStyle);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Location permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                            fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CHECK_LOCATION);
                        }else{
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CHECK_LOCATION);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                        fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CHECK_LOCATION);
                    }else{
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CHECK_LOCATION);
                    }

                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean checkPermissionWrite(final Context context, final int PERMISSION_CODE,final int TYPE,@Nullable Fragment fragment,int dialogStyle) {//TYPE -> fragment = 0 or activity = 1
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, dialogStyle);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("STORAGE permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                            fragment.requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                        }else{
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                        fragment.requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    }else{
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean checkPermissionRead(final Context context, final int PERMISSION_CODE,final int TYPE,@Nullable Fragment fragment,int dialogStyle) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, dialogStyle);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("STORAGE permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                            fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
                        }else{
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
                        }

                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                        fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    }else{
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    public static boolean checkPermissionCamera(final Context context, final int PERMISSION_CODE,final int TYPE,@Nullable Fragment fragment,int dialogStyle) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context,dialogStyle);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                            fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CODE);
                        }else{
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, PERMISSION_CODE);
                        }

                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                        fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CODE);
                    }else{
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, PERMISSION_CODE);
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPhoneStatePermission(final Context context,final int TYPE,@Nullable Fragment fragment,int dialogStyle) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_PHONE_STATE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, dialogStyle);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Read phone state permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                            fragment.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CHECK_PHONE_STATE);
                        }else{
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CHECK_PHONE_STATE);
                        }

                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    if(TYPE == REQUEST_PERMISSION_TYPE_FRAGMENT && fragment != null){
                        fragment.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CHECK_PHONE_STATE);
                    }else{
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CHECK_PHONE_STATE);
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    private  static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);

    private static SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static String showDateFormatter(Date date){
        return dateFormatter.format(date);
    }
    public static String changeDateToShowDateFormat(String dateValue){

        try {
            Date tempDate = dbDate.parse(dateValue);
            return dateFormatter.format(tempDate);
        } catch (ParseException e) {
            Log.e("AppUtils", "changeDateToShowDateFormat: ",e );
            return dateValue;
        }
    }
    public static Date getDateFromServerFormat(String dateValue){
        try {
            return dbDate.parse(dateValue);
        } catch (ParseException e) {
            Log.e("AppUtils", "changeDateToShowDateFormat: ",e );
            return Calendar.getInstance().getTime();
        }
    }
    public static String dbDateFormatter(Date date){
        return dbDate.format(date);
    }
    public static String getFormattedDateNotification(String customDate) {
        // SimpleDateFormat sdfServer = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //     SimpleDateFormat sdfLocal = new SimpleDateFormat("EEEE dd MMMM, yyyy");
        SimpleDateFormat sdfLocal = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdfServer.parse(customDate);
            String dateString = sdfLocal.format(date);
            customDate = dateString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return customDate;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showProgressBar(ProgressBar pb) {
        if (pb.getVisibility() == View.GONE) {
            pb.setVisibility(View.VISIBLE);
        }
    }

    public static void hideProgressBar(ProgressBar pb) {
        if (pb.getVisibility() == View.VISIBLE) {
            pb.setVisibility(View.GONE);
        }
    }


    public static boolean compareStringsSimilarity(String s1, String s2) {
        //s1->Search Query / Primary String
        //s2->News attribute with which we are comparing the search query.
        // Returns true if the strings are similar to a particular degree/threshold.
        final double THRESHOLD = 0.99999; // Value between 0 and 1
        /*
        Increasing the threshold value strickens the comparison. Decreasing it relaxes the comparison.
        If you desire that the comparison be extremely accurate increase the value to near 1.0;
        1.000 is the similarity between "" and ""
        0.100 is the similarity between "1234567890" and "1"
        0.300 is the similarity between "1234567890" and "123"
        0.700 is the similarity between "1234567890" and "1234567"
        1.000 is the similarity between "1234567890" and "1234567890"
        0.800 is the similarity between "1234567890" and "1234567980"
        0.857 is the similarity between "47/2010" and "472010"
        0.714 is the similarity between "47/2010" and "472011"
        0.000 is the similarity between "47/2010" and "AB.CDEF"
        0.125 is the similarity between "47/2010" and "4B.CDEFG"
        0.000 is the similarity between "47/2010" and "AB.CDEFG"
        0.700 is the similarity between "The quick fox jumped" and "The fox jumped"
        0.350 is the similarity between "The quick fox jumped" and "The fox"
        0.571 is the similarity between "kitten" and "sitting"
         */
        double similarityIndex;

        String[] s1Array = s1.split(" "); // Splits the search query into words for better recognition.
        String[] s2Array = s2.split(" "); // Splits the internship attribute string into seperate word for better recognition.

        for (String s1word : s1Array) {
            for (String s2word : s2Array) {
                similarityIndex = similarity(s1word, s2word);
                //Log.d("Similarity Index",Double.toString(similarityIndex));
                if (similarityIndex > THRESHOLD) {
                    return true;
                }
            }
        }
        return false;
    }

    private static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
    /* // If you have StringUtils, you can use it to calculate the edit distance:
    return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
                               (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    private static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
    public static String dateToString(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return  format.format(date);
    }
    public  static Date stringToDate(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return Calendar.getInstance().getTime();
        }

    }
    public static String getTime(String timeStamp)
    {
        Calendar timeStampCal = Calendar.getInstance();
        timeStampCal.setTime(stringToDate(timeStamp));
        String timeStr = "";
        SimpleDateFormat format = new SimpleDateFormat("hh:ss a", Locale.US);
        try {
            timeStr = format.format(timeStampCal.getTime());
        }catch (Exception e)
        {
            e.printStackTrace();
            timeStr = timeStamp;
        }
        return timeStr;
    }

    public static int setYearSpinnerSelection(Spinner spinner, String date) {
        if(date == null)
            return  0;
        for (int i = 0; spinner.getAdapter() != null && i <spinner.getAdapter().getCount() ; i++) {
            if(spinner.getAdapter().getItem(i).toString().equalsIgnoreCase(date))
                return i;
        }
        return 0;
    }
    public static void setSpinnerYearsList(FragmentActivity activity, Spinner spinnerYearsList, String startValue)
    {
        /*List<String> yearsList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,1970);
        while (calendar.getTime().before(Calendar.getInstance().getTime()))
        {
            yearsList.add(0,calendar.get(Calendar.YEAR)+"");
            calendar.add(Calendar.YEAR,1);
        }
        if(!startValue.isEmpty())
            yearsList.add(0,startValue);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, R.layout.spinner_items, yearsList);
        spinnerYearsList.setAdapter(adapter);*/
    }
    //test code
    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
   /* public static Snackbar noCountrySnackBar(View view)
    {
        return Snackbar.make(view,"Unable to get Countries List. Please Refresh", BaseTransientBottomBar.LENGTH_INDEFINITE);
    }*/

    public static void loadStringSpinner(Context context, Spinner spinner, List<String> list, String defaultValue)
    {
        /*ArrayAdapter<String> activityAdapter = new ArrayAdapter<>(context, R.layout.spinner_items, list);
        // Specify the layout to use when the list of choices appears
        activityAdapter.setDropDownViewResource(R.layout.spinner_items);
        spinner.setAdapter(activityAdapter);
        spinner.setSelection(activityAdapter.getPosition(defaultValue));*/
    }
    public static void createUpdateAppDialog(Context context, String message,int dialogStyle)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context, dialogStyle).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) ->{
            dialog.dismiss();
        });
        alertDialog.show();
    }
    public static void createNetworkError(FragmentActivity context,int dialogStyle)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context, dialogStyle).create();
        alertDialog.setTitle("No Internet Connection");
        alertDialog.setMessage("Please Check your Internet Connectivity");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) ->{
            dialog.dismiss();
        });
        alertDialog.show();
    }
    public static AlertDialog showUpdateMessage(Context context, String message,int dialogStyle){
        AlertDialog alertDialog = new AlertDialog.Builder(context, dialogStyle).create();
        alertDialog.setTitle("Update Message");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) ->{
            Uri uri = Uri.parse("market://details?id="+context.getPackageName());
            Log.e("appLink",uri.toString());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                context.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="+context.getPackageName())));
            }
        });
        return alertDialog;
    }
    public static Bitmap screenShot(View view,  ProgressBar pb) {
        pb.setVisibility(View.VISIBLE);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    //////// this method share your image
    public static void  shareBitmap (Context context,Bitmap bitmap,String fileName, ProgressBar pb) {
        try {
            pb.setVisibility(View.VISIBLE);
            File file = new File(context.getCacheDir(), fileName + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(     Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            pb.setVisibility(View.GONE);
            context.startActivity(intent);
        } catch (Exception e) {
            pb.setVisibility(View.GONE);
            e.printStackTrace();
        }

    }
    public static void createFailedDialog(Context context, String message,int dialogStyle) {

        AlertDialog alertDialog = new AlertDialog.Builder(context, dialogStyle).create();
        alertDialog.setTitle("Failed");
        alertDialog.setMessage(message);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.show();
    }
    public static void createFailedDialog(Context context, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Failed");
        alertDialog.setMessage(message);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.show();
    }
    public static void handleResponseFailedRetrofit(Context context,Throwable t,String apiName){
        t.printStackTrace();
        if(t instanceof SocketTimeoutException){
            createFailedDialog(context,"Slow Internet Connection");
        }else if(t instanceof MalformedJsonException){
            createFailedDialog(context,"Invalid Server Response");
        }else{
            createFailedDialog(context,"Server Error");
        }
    }

    public static String getMonthName(String eventDate) {
        String[] months = new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEPT","OCT","NOV","DEC"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(AppUtils.getDateFromServerFormat(eventDate));
        return months[calendar.get(Calendar.MONTH)];
    }

    public static String getDateDay(String eventDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(AppUtils.getDateFromServerFormat(eventDate));
        return calendar.get(Calendar.DAY_OF_MONTH)+"";
    }
}