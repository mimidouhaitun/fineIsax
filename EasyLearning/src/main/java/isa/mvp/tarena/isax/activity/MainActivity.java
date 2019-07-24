package isa.mvp.tarena.isax.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import isa.mvp.tarena.isax.R;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.fragmentRoot)
    LinearLayout fragmentRoot;
    @BindView(R.id.rb_tab_item_1)
    RadioButton rbTabItem1;
    @BindView(R.id.rb_tab_item_2)
    RadioButton rbTabItem2;
    @BindView(R.id.rb_tab_item_3)
    RadioButton rbTabItem3;
    @BindView(R.id.rb_tab_item_4)
    RadioButton rbTabItem4;
    @BindView(R.id.rg_tab_menu)
    RadioGroup rgTabMenu;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @BindViews({R.id.rb_tab_item_1, R.id.rb_tab_item_2, R.id.rb_tab_item_3, R.id.rb_tab_item_4})
    List<RadioButton> radioButtonList;

    List<Integer> botPicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        botPicList.add(R.drawable.ic_circle_normal);
        botPicList.add(R.drawable.ic_homework_normal);
        botPicList.add(R.drawable.ic_rank_normal);
        botPicList.add(R.drawable.ic_shop_normal);
        updateRadioButtonPicWithIndex(0);
    }






    @OnClick({R.id.rb_tab_item_1, R.id.rb_tab_item_2, R.id.rb_tab_item_3, R.id.rb_tab_item_4})
    public void onViewClicked(RadioButton view) {
        int position = radioButtonList.indexOf(view);

        updateRadioButtonPicWithIndex(position);


    }

    private void updateRadioButtonPicWithIndex(int position) {

        for (int i = 0; i < radioButtonList.size(); i++) {
            Drawable drawable = getResources().getDrawable(botPicList.get(i));
            drawable.setTint(i == position ? Color.parseColor("#007AFF") : Color.parseColor("#939393"));
            drawable.setBounds(0, 0, 45, 45);
            radioButtonList.get(i).setCompoundDrawables(null, drawable, null, null);
            radioButtonList.get(i).setTextColor(i == position ? Color.parseColor("#007AFF") : Color.parseColor("#939393"));
        }

    }


}
