package mbaas.com.nifcloud.androidautologinapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.provider.Settings.Secure;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.LoginCallback;
import com.nifcloud.mbaas.core.NCMB;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView _txtMessage;
    TextView _txtLogin;
    private static final String TAG = "MainActivity";
    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //**************** APIキーの設定 **************
        NCMB.initialize(this.getApplicationContext(),"YOUR_APPLICATION_KEY",
                "YOUR_CLIENT_KEY");

        // UUIDを取得します
        uuid = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);
        Log.d(TAG, uuid);

        setContentView(R.layout.activity_main);
        _txtMessage = (TextView) findViewById(R.id.txtMessage);
        _txtLogin = (TextView) findViewById(R.id.txtLogin);

        //Login
        try {
            NCMBUser.loginInBackground(uuid, uuid, new LoginCallback() {
                @Override
                public void done(NCMBUser login_user, NCMBException e) {
                    if (e != null) {
                        //エラー時の処理
                        Log.d(TAG, e.getCode());
                        if (e.getCode().equals( "E401002") ) {

                            //NCMBUserのインスタンスを作成
                            NCMBUser user = new NCMBUser();
                            //ユーザ名を設定
                            user.setUserName(uuid);
                            //パスワードを設定
                            user.setPassword(uuid);
                            //設定したユーザ名とパスワードで会員登録を行う
                            user.signUpInBackground(new DoneCallback() {
                                @Override
                                public void done(NCMBException er) {
                                    if (er != null) {
                                        //会員登録時にエラーが発生した場合の処理
                                        Log.d(TAG, "Signup error" + er);
                                    }
                                    else {
                                        _txtMessage.setText("はじめまして");
                                        _txtLogin.setText("１回目ログイン、ありがとうございます。");
                                        //lastLoginを更新します（ラストログインのタイミングを取得するために）
                                        try {
                                            NCMBUser curUser = NCMBUser.getCurrentUser();
                                            Date now = new Date();
                                            curUser.put("lastLoginDate", now);
                                            curUser.save();
                                        } catch (NCMBException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            });
                        };
                    } else {
                        NCMBUser curUser = NCMBUser.getCurrentUser();

                        Date lastLogin = curUser.getDate("lastLoginDate");
                        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String strDt = simpleDate.format(lastLogin);
                        _txtMessage.setText("お帰りなさい！");
                        _txtLogin.setText("最終ログインは：" + strDt);

                        //lastLoginを更新します（ラストログインのタイミングを取得するために）
                        try {
                            Date now = new Date();
                            curUser.put("lastLoginDate", now);
                            curUser.save();
                        } catch (NCMBException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        } catch (NCMBException e) {
            e.printStackTrace();
        }

    }
}
