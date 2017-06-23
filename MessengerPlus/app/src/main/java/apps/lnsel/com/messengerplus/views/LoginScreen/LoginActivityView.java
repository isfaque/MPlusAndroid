package apps.lnsel.com.messengerplus.views.LoginScreen;

/**
 * Created by apps2 on 6/14/2017.
 */
public interface LoginActivityView {
    void startSignupActivity();
    void startMainActivity();

    void errorInfo(String msg);
    void successInfo(String msg);

    void updateSession(String usrId, String usrUserName, String usrPassword);
}
