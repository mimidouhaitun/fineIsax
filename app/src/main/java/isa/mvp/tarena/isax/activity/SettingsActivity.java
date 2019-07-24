package isa.mvp.tarena.isax.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import isa.mvp.tarena.isax.R;
import isa.mvp.tarena.isax.entity.User;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rl_clearcache)
    RelativeLayout rlClearcache;
    @BindView(R.id.rl_changepassword)
    RelativeLayout rlChangepassword;
    @BindView(R.id.rl_changenickandhead)
    RelativeLayout rlChangenickandhead;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.activity_settings)
    RelativeLayout activitySettings;

    AlertDialog exitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.iv_left)
    public void back(View view){
        SettingsActivity.this.finish();
    }

    @OnClick(R.id.rl_changenickandhead)
    public void setNickAndHead(){
        Intent intent = new Intent(SettingsActivity.this,ChangeMyNickAndHeadActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.rl_logout)
    public void logout(View view){
        getExitDialog().show();
    }

    private AlertDialog getExitDialog() {
        if (exitDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("您确定要退出吗？");
            builder.setCancelable(false);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    BmobUser.logOut();
                    BmobUser currentUser = BmobUser.getCurrentUser(User.class);
                    Intent intent = new Intent(SettingsActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    SettingsActivity.this.finish();
                }
            });
            builder.setNegativeButton("取消", null);

            exitDialog = builder.create();
        }

        return exitDialog;
    }
}
