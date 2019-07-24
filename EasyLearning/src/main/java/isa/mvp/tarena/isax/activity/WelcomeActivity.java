package isa.mvp.tarena.isax.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import isa.mvp.tarena.isax.R;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    @BindView(R.id.activity_welcome)
    RelativeLayout activityWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);


        BmobUser currentUser = BmobUser.getCurrentUser();
        if(currentUser != null){
//           Intent intent = new Intent(WelcomeActivity.this,
//                   MainFragmentActivity.class);
//            startActivity(intent);
//            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//            WelcomeActivity.this.finish();

        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
        }

    }

    @OnClick(R.id.btn_login)
    public void login(View view){
        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(intent);

        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    @OnClick(R.id.btn_regist)
    public void regist(View view){
        Intent intent = new Intent(WelcomeActivity.this,RegistActivity.class);
        startActivity(intent);

        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
