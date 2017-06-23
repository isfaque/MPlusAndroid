package apps.lnsel.com.messengerplus.helpers.CustomAdapter.MainAdapter;

/**
 * Created by apps2 on 6/14/2017.
 */
public class MainSetterGetter {
    private String usrId;
    private String usrUserName;
    private String usrPassword;
    private String usrStatus;


    public MainSetterGetter(String usrId, String usrUserName, String usrPassword, String usrStatus) {
        this.usrId = usrId;
        this.usrUserName = usrUserName;
        this.usrPassword = usrPassword;
        this.usrStatus = usrStatus;
    }

    public String getUsrId() {
        return this.usrId;
    }

    public String getUsrUserName() {
        return this.usrUserName;
    }

    public String getUsrPassword() {
        return this.usrPassword;
    }

    public String getUsrStatus() {
        return this.usrStatus;
    }

}
