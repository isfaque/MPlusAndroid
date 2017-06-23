package apps.lnsel.com.messengerplus.views.Dashboard.PrivateChatScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import apps.lnsel.com.messengerplus.R;

/**
 * Created by apps2 on 6/14/2017.
 */
public class PrivateChatActivity extends AppCompatActivity implements PrivateChatActivityView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_private_chat);
    }
}
