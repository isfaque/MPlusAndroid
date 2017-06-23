package apps.lnsel.com.messengerplus.utils;

import android.content.Context;
import android.content.Intent;

import apps.lnsel.com.messengerplus.views.Dashboard.MainScreen.MainActivity;
import apps.lnsel.com.messengerplus.views.LoginScreen.LoginActivity;
import apps.lnsel.com.messengerplus.views.SignupScreen.SignupActivity;

/**
 * Created by apps2 on 6/14/2017.
 */
public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startSignupActivity() {
        Intent intent = new Intent(context, SignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
