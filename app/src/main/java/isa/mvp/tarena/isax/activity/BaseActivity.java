package isa.mvp.tarena.isax.activity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import isa.mvp.tarena.isax.R;

/**
 * Created by tarena on 2017/8/4.
 */

public class BaseActivity extends AppCompatActivity {

    public TextView titleTV;
    public ImageButton actionBarLeftBtn;
    public ImageButton actionBarRightBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//得到系统actinbar
        ActionBar ab = getSupportActionBar();

        RelativeLayout actionbarLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.actionbar_layout, null);

        titleTV = (TextView) actionbarLayout.findViewById(R.id.titleView);
        actionBarLeftBtn = (ImageButton) actionbarLayout.findViewById(R.id.actionBarLeftBtn);
        actionBarRightBtn = (ImageButton) actionbarLayout.findViewById(R.id.actionRightBtn);

        actionBarLeftBtn.setBackgroundColor(Color.TRANSPARENT);
        actionBarRightBtn.setBackgroundColor(Color.TRANSPARENT);

        //让actionbar显示自定义的布局
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(actionbarLayout);
    }



    public void showBackBtn(){

        actionBarLeftBtn.setVisibility(View.VISIBLE);
        actionBarLeftBtn.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_top_back));
        actionBarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //出发系统actionbar的返回按钮
                onBackPressed();
            }
        });

    }

}
