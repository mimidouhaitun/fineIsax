package isa.mvp.tarena.isax.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import isa.mvp.tarena.isax.R;
import isa.mvp.tarena.isax.entity.User;

public class RegistActivity extends AppCompatActivity {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_mid)
    TextView tvMid;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.tv_line1)
    TextView tvLine1;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_line2)
    TextView tvLine2;
    @BindView(R.id.btn_regist)
    Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.iv_left)
    public void back(View view){
        RegistActivity.this.finish();

    }

    @OnClick(R.id.btn_regist)
    public void regist(View view){
        if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())  ){
            Toast.makeText(RegistActivity.this,"用户名密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setUsername(etUsername.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.signUp(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {

                if(e==null){
                    Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    RegistActivity.this.finish();
                }else{
                    Toast.makeText(RegistActivity.this,"注册失败"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
