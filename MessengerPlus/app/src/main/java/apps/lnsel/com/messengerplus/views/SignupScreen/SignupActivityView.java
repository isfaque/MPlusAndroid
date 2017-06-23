package apps.lnsel.com.messengerplus.views.SignupScreen;

/**
 * Created by apps2 on 6/14/2017.
 */
public interface SignupActivityView {
    void startLoginActivity();
    void startMainActivity();

    void errorInfo(String msg);
    void successInfo(String msg);
}
