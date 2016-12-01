package com.example.studenthub;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.*;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Scheduler extends Activity
        implements EasyPermissions.PermissionCallbacks {
    GoogleAccountCredential mCredential;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final long SEC_DAYS = 1000*60*60*24;
    private static final long SEC_HOURS = 1000*60*60;
    private static final long SEC_MIN = 1000*60;
    private static final String BUTTON_TEXT = "Get Calendar Events";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR /* This was CALENDAR_READONLY*/ };
    private static List<mEvent> allEvents = new ArrayList<>();
    private long start, end;
    private int startPixel, pixelLength;
    private RelativeLayout whichDay;
    private LinearLayout.LayoutParams params;
    private TextView newEvent;
    private DateTime now = new DateTime(System.currentTimeMillis());
    private RelativeLayout today;

    public static List<mEvent> getEvents(){
        return(allEvents);
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Calling Google Calendar API ...");

        setContentView(R.layout.activity_scheduler); //TODO changed this. not sure if it'll mess with google calendar

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        getResultsFromApi();
           // getDataFromApi();
        setUpCalendar();
            //displayEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setUpCalendar(){
        TextView month = (TextView) findViewById(R.id.currentMonthTextView);
        TextView year = (TextView) findViewById(R.id.currentYearTextView);

        String stNow = now.toString();
        month.setText(getMonth(stNow.substring(5,7)));
        TextView day1 = (TextView) findViewById(R.id.day1TextView);
        //TextView date1 = (TextView) findViewById(R.id.day1DateTextView);
        day1.setText(getDayOfWeek(now.getValue()));
        //date1.setText(stNow.substring(8,10));
        TextView day2 = (TextView) findViewById(R.id.day2TextView);
        day2.setText(getDayOfWeek(now.getValue() + SEC_DAYS));
        TextView day3 = (TextView) findViewById(R.id.day3TextView);
        day3.setText(getDayOfWeek(now.getValue()+ 2*SEC_DAYS));
        TextView day4 = (TextView) findViewById(R.id.day4TextView);
        day4.setText(getDayOfWeek(now.getValue()+ 3*SEC_DAYS));
        TextView day5 = (TextView) findViewById(R.id.day5TextView);
        day5.setText(getDayOfWeek(now.getValue()+ 4*SEC_DAYS));
        TextView day6 = (TextView) findViewById(R.id.day6TextView);
        day6.setText(getDayOfWeek(now.getValue()+ 5*SEC_DAYS));
        TextView day7 = (TextView) findViewById(R.id.day7TextView);
        day7.setText(getDayOfWeek(now.getValue()+ 6*SEC_DAYS));


    }

    private String getDayOfWeek(long nowMili){
        // January 1 1970 was a Thursday

        // get in terms of days
        nowMili = nowMili / SEC_DAYS;
        // Get remainter when divided by 7 and we are that many days from Thursday
        nowMili = nowMili % 7;
        switch((int)nowMili){
            case 0:
                return "Thu";
            case 1:
                return "Fri";
            case 2:
                return "Sat";
            case 3:
                return "Sun";
            case 4:
                return "Mon";
            case 5:
                return "Tue";
            case 6:
                return "Wed";
        }
        return "";
    }

    private String getMonth(String input){
        switch(input){
            case "01":
                return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07":
                return "Jul";
            case "08":
                return "Aug";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
        }
        return"";
    }

    private RelativeLayout getDay(long time){
            long diff =  time - now.getValue();
        int days = (int) (diff / (SEC_DAYS));
        switch(days) {
            case 0:
                return (RelativeLayout) findViewById(R.id.day1RelativeLayout);
            case 1:
                return (RelativeLayout) findViewById(R.id.day2RelativeLayout);
            case 2:
                return (RelativeLayout) findViewById(R.id.day3RelativeLayout);
            case 3:
                return (RelativeLayout) findViewById(R.id.day4RelativeLayout);
            case 4:
                return (RelativeLayout) findViewById(R.id.day5RelativeLayout);
            case 5:
                return (RelativeLayout) findViewById(R.id.day6RelativeLayout);
            case 6:
                return (RelativeLayout) findViewById(R.id.day7RelativeLayout);
            default:
                return (RelativeLayout) findViewById(R.id.day1HeaderRelativeLayout);
        }
    }

    private int getPixel(long start){
        String theDate = getDate(start, "hh:mm");
        int hours = Integer.valueOf(theDate.substring(0,2));
        int min = Integer.valueOf(theDate.substring(3,5));
        return (220 * hours) + (220* min /60);


    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private int getDayPixel(long start){
        String theDate = getDate(start, "hh:mm");
        int hours = Integer.valueOf(theDate.substring(0,2));
        int mins = Integer.valueOf(theDate.substring(3,5));
        return (60 * hours) +  mins;
    }

    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
        } else {
            new MakeRequestTask(mCredential).execute();
        }
    }

    private void displayEvents() {
        for(mEvent m: allEvents){
            start = m.getStart().getValue();
            end = m.getEnd().getValue();
            whichDay = getDay(start);
            if(whichDay.equals((RelativeLayout) findViewById(R.id.day1HeaderRelativeLayout))){
                continue;
            }
            startPixel = getPixel(start);
            pixelLength = getPixel(end) - startPixel;
            if(whichDay.equals((RelativeLayout) findViewById(R.id.day1RelativeLayout))){ //add to today view
                today = (RelativeLayout) findViewById(R.id.dayCalendar);
                TextView newPost = new TextView(this);
                newPost.setText(m.getTitle());
                newPost.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                newPost.setBackgroundColor(Color.BLACK);
                newPost.setTextColor(Color.WHITE);
                params = new LinearLayout.LayoutParams(getDayPixel(end) - getDayPixel(start), ViewGroup.LayoutParams.MATCH_PARENT);//needs to be the hour and min value
                params.setMargins(getDayPixel(start),105,0,0);//600 needs to be start time
                newPost.setLayoutParams(params);
                today.addView(newPost);

            }


            newEvent = new TextView(this);
            newEvent.setText(m.getTitle());
            newEvent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            newEvent.setBackgroundColor(Color.BLACK); //TODO vary colors?
            newEvent.setTextColor(Color.WHITE);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixelLength);//needs to be the hour and min value
            params.setMargins(0,startPixel,0,0); //600 needs to be start time
            newEvent.setLayoutParams(params);
            whichDay.addView(newEvent);
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                Scheduler.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }



    /**
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */

    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
       @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */

        public List<String> getDataFromApi() throws IOException { //List<String>
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            DateTime future = new DateTime(now.getValue() + 604800000);

            allEvents.clear();
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setTimeMax(future)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for(Event item : items){
                if(item.getStart().getDateTime() == null){
                    continue;
                }
                mEvent tmpEvent = new mEvent();
                tmpEvent.setTitle(item.getSummary());
                tmpEvent.setEnd(item.getEnd().getDateTime());
                tmpEvent.setLocation(item.getLocation());
                tmpEvent.setStart(item.getStart().getDateTime());
                allEvents.add(tmpEvent);
            }
           //runOnUiThread(displayEvents());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    displayEvents();
                }
            });
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                DateTime end = event.getEnd().getDateTime();
                String location = event.getLocation();
                if (start == null) {
                    // All-day events don't have start times, so skip them
                    // start = event.getStart().getDate();
                    continue;
                }
                eventStrings.add(
                        String.format("%s (%s) (%s) (%s)", event.getSummary(), start, end, location));
            }

            return eventStrings;
        }


        @Override
        protected void onPreExecute() {
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
            } else {
                output.add(0, "Data retrieved using the Google Calendar API:");
                //mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            Scheduler.REQUEST_AUTHORIZATION);
                } else {
                }
            } else {
            }
        }
    }
}