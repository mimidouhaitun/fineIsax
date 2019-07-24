package isa.mvp.tarena.isax.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import isa.mvp.tarena.isax.R;
import isa.mvp.tarena.isax.entity.User;
import isa.mvp.tarena.isax.fragment.CircleFragment;
import isa.mvp.tarena.isax.fragment.HomeWorkFragment;
import isa.mvp.tarena.isax.fragment.RankFragment;
import isa.mvp.tarena.isax.fragment.ShopFragment;
import isa.mvp.tarena.isax.utils.FastBlurUtils;
import isa.mvp.tarena.isax.view.CircleImageView;

public class MainFragmentActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String[] TITLES = {"朋友圈", "作业", "排行榜", "商店"};

    private static FragmentManager fragManager;
    private FragmentTransaction fragTrans;

    private CircleFragment circleFragment;
    private HomeWorkFragment homeWorkFragment;
    private RankFragment rankFragment;
    private ShopFragment shopFragment;


    private static final String PG1_NAME = "circle";
    private static final String PG2_NAME = "work";
    private static final String PG3_NAME = "rank";
    private static final String PG4_NAME = "shop";


    @BindViews({R.id.rb_tab_item_1, R.id.rb_tab_item_2, R.id.rb_tab_item_3, R.id.rb_tab_item_4})
    List<RadioButton> radioButtonList;

    List<Integer> botPicList = new ArrayList<>();
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

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    CircleImageView cmHead;
    TextView tvNick;
    RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_fragment);
        ButterKnife.bind(this);

        initDrawerUI();
        showBackBtn();
        actionBarLeftBtn.setImageResource(R.drawable.ic_drawer_menu);
        actionBarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }else{
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        botPicList.add(R.drawable.ic_circle_normal);
        botPicList.add(R.drawable.ic_homework_normal);
        botPicList.add(R.drawable.ic_rank_normal);
        botPicList.add(R.drawable.ic_shop_normal);
        updateRadioButtonPicWithIndex(0);

        fragManager = getSupportFragmentManager();
        initFragment();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        for (int i = 0; i < drawer.getChildCount(); i++) {
            Log.i("abc", "onTouchEvent: " + drawer.getChildAt(i));
        }
        return super.onTouchEvent(event);
    }

    private static void popAllExceptTheBottomOne() {
        for (int i = 0, count = fragManager.getBackStackEntryCount() - 1; i < count; i++) {
            fragManager.popBackStack();
        }
    }

    private void initFragment() {

        popAllExceptTheBottomOne();
        circleFragment = new CircleFragment();
        fragTrans = fragManager.beginTransaction();
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragTrans.add(R.id.fragmentRoot, circleFragment, PG1_NAME);
        fragTrans.addToBackStack(PG1_NAME);
        fragTrans.commit();
    }


    @OnClick({R.id.rb_tab_item_1, R.id.rb_tab_item_2, R.id.rb_tab_item_3, R.id.rb_tab_item_4})
    public void onViewClicked(RadioButton view) {
        int position = radioButtonList.indexOf(view);
        updateRadioButtonPicWithIndex(position);

        switch (view.getId()) {
            case R.id.rb_tab_item_1:
                popAllExceptTheBottomOne();
                Log.i("RadioGroup", "rb_tab_item_1");
                break;

            case R.id.rb_tab_item_2:
                if (fragManager.findFragmentByTag(PG2_NAME) != null &&
                        fragManager.findFragmentByTag(PG2_NAME).isVisible()) {
                    return;
                }
                popAllExceptTheBottomOne();
                Log.i("RadioGroup", "rb_tab_item_2");
                homeWorkFragment = new HomeWorkFragment();
                dealWithFragmentTransaction(homeWorkFragment, PG2_NAME);
                break;

            case R.id.rb_tab_item_3:
                if (fragManager.findFragmentByTag(PG3_NAME) != null &&
                        fragManager.findFragmentByTag(PG3_NAME).isVisible()) {
                    return;
                }
                popAllExceptTheBottomOne();
                Log.i("RadioGroup", "rb_tab_item_3");
                rankFragment = new RankFragment();
                dealWithFragmentTransaction(rankFragment, PG3_NAME);
                break;

            case R.id.rb_tab_item_4:
                if (fragManager.findFragmentByTag(PG4_NAME) != null &&
                        fragManager.findFragmentByTag(PG4_NAME).isVisible()) {
                    return;
                }
                popAllExceptTheBottomOne();
                Log.i("RadioGroup", "rb_tab_item_4");
                shopFragment = new ShopFragment();
                dealWithFragmentTransaction(shopFragment, PG4_NAME);
                break;

        }

    }

    private void dealWithFragmentTransaction(Fragment fragment, String fragmentName) {
        fragTrans = fragManager.beginTransaction();
        fragTrans.hide(fragManager.findFragmentByTag(PG1_NAME));
        fragTrans.add(R.id.fragmentRoot, fragment, fragmentName);
        fragTrans.addToBackStack(fragmentName);
        fragTrans.commit();
    }

    private void updateRadioButtonPicWithIndex(int position) {

        for (int i = 0; i < radioButtonList.size(); i++) {
            Drawable drawable = getResources().getDrawable(botPicList.get(i));
            drawable.setTint(i == position ? Color.parseColor("#007AFF") : Color.parseColor("#939393"));
            drawable.setBounds(0, 0, 45, 45);
            radioButtonList.get(i).setCompoundDrawables(null, drawable, null, null);
            radioButtonList.get(i).setTextColor(i == position ? Color.parseColor("#007AFF") : Color.parseColor("#939393"));
            // toolbar.setTitle(TITLES[position]);
            titleTV.setText(TITLES[position]);
        }

    }

    @OnClick(R.id.fab)
    public void onClickFloatActionButton(FloatingActionButton fab) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        tvNick.setText(BmobUser.getCurrentUser(User.class).getNick());

        Picasso.with(MainFragmentActivity.this).load(BmobUser.getCurrentUser(User.class).getHeadPath()).into(cmHead);


        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.bg_welcome);
        bm = FastBlurUtils.doBlur(bm, 156, false);
        Drawable d = new BitmapDrawable(bm);
        navigationView.setBackground(d);
    }

    private void initDrawerUI() {

        View headerView = navigationView.getHeaderView(0);

        cmHead = (CircleImageView) headerView.findViewById(R.id.cm_myhead);
        tvNick = (TextView) headerView.findViewById(R.id.tv_mynick);
        rlRoot = (RelativeLayout) headerView.findViewById(R.id.rl_root);


      /*  toolbar.setTitle(TITLES[0]);
        toolbar.setTitleTextColor(Color.parseColor("#141414"));
        toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);


        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);*/

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            MainFragmentActivity.this.finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        Intent intent = new Intent();
        if (id == R.id.nav_setting) {

            intent.setClass(MainFragmentActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/


        return true;
    }
}
