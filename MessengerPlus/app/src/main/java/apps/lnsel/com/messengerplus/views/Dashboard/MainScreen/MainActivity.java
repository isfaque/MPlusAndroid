package apps.lnsel.com.messengerplus.views.Dashboard.MainScreen;

import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import apps.lnsel.com.messengerplus.R;
import apps.lnsel.com.messengerplus.models.UsersModel;
import apps.lnsel.com.messengerplus.presenters.MainPresenter;
import apps.lnsel.com.messengerplus.presenters.SignupPresenter;
import apps.lnsel.com.messengerplus.utils.ActivityUtil;
import apps.lnsel.com.messengerplus.utils.SharedManagerUtil;
import apps.lnsel.com.messengerplus.utils.UrlUtil;

/**
 * Created by apps2 on 6/14/2017.
 */
public class MainActivity extends AppCompatActivity implements MainActivityView {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    SharedManagerUtil session;
    UsersModel mydb;
    ListView listview;

    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new MainPresenter(this);

        updateList();
        startGetUsers();


    }

    public void startGetUsers(){
        if(isNetworkAvailable()){
            presenter.getUsersService(UrlUtil.GET_USERS_URL+"?usrId="+session.getUserID().toString());
        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }
    }

    public void addUser(String usrUserName){
        mydb.insertContactNotExist(usrUserName);
    }

    public void errorInfo(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void updateList(){
        mydb = new UsersModel(this);
        ArrayList array_list = mydb.getAllCotacts();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(arrayAdapter);
    }

    /** function for options menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_menu, menu);

        return true;

    }

    /** function for item selected in options menu **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            switch (item.getItemId()) {
                // action with ID action_refresh was selected
                case R.id.logout:
                    //Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show();
                    logoutDialog();
                    break;
                case R.id.change_password:
                    //Toast.makeText(this, "change_password", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.sync:
                    startGetUsers();
                    break;

                default:
                    break;
            }

        return super.onOptionsItemSelected(item);
    }

    private void logoutDialog(){

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Do you want to Logout from app")
                .setTitle("Logout")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        logoutApp();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();

    }

    public void logoutApp(){
        session.logoutUser();
        startLoginActivity();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void startLoginActivity(){
        new ActivityUtil(this).startLoginActivity();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}
