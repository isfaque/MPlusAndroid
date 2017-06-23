package apps.lnsel.com.messengerplus.views.SplashScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import apps.lnsel.com.messengerplus.R;
import apps.lnsel.com.messengerplus.utils.ActivityUtil;
import apps.lnsel.com.messengerplus.utils.SharedManagerUtil;

/**
 * Created by apps2 on 6/14/2017.
 */
public class SplashActivity extends AppCompatActivity implements SplashActivityView {
    SharedManagerUtil session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Session Manager
        session = new SharedManagerUtil(getApplicationContext());

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2600);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    if(session.isLoggedIn()){
                        startMainActivity();
                    }else{
                        startLoginActivity();
                    }
                }
            }
        };
        timerThread.start();
    }

    @Override
    public void startLoginActivity() {
        new ActivityUtil(this).startLoginActivity();
    }

    public void startMainActivity(){
        new ActivityUtil(this).startMainActivity();
    }
}
