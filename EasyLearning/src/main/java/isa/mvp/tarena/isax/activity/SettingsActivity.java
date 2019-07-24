package isa.mvp.tarena.isax.activity;

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
import isa.mvp.tarena.isax.R;

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
}
