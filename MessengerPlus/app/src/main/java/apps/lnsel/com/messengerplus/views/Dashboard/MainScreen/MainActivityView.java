package apps.lnsel.com.messengerplus.views.Dashboard.MainScreen;

/**
 * Created by apps2 on 6/14/2017.
 */
public interface MainActivityView {
    void errorInfo(String msg);
    void addUser(String msg);

    void updateList();
    void startGetUsers();
}
