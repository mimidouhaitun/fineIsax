package isa.mvp.tarena.isax.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import isa.mvp.tarena.isax.R;
import isa.mvp.tarena.isax.entity.User;
import isa.mvp.tarena.isax.utils.Utils;

public class ChangeMyNickAndHeadActivity extends AppCompatActivity {

    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.tv_in)
    TextView tvIn;
    @BindView(R.id.iv_headicon)
    ImageView ivHeadicon;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_unsign)
    Button btnUnsign;
    @BindView(R.id.iv_left)
    ImageView ivLeft;

    private AlertDialog exitDialog;
    private static final int IMAGE_REQUEST_CODE = 101;
    public static final int REQUEST_CODE = 200;
    private String headIconPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_mysetting);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.iv_left)
    public void back(View view){
        ChangeMyNickAndHeadActivity.this.finish();
    }

    @OnClick(R.id.iv_headicon)
    public void goChoosePhoto() {
        checkPermission();
    }

    @OnClick(R.id.btn_submit)
    public void submit() {
        final User user = new User();

        user.setNick(etNickname.getText().toString());

        //判断是否修改了图片
        if (headIconPath != null) {
            final BmobFile uploadHead = new BmobFile(new File(headIconPath));

            uploadHead.uploadblock(new UploadFileListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {

                        // toast("上传文件成功:" + bmobFile.getFileUrl());
                        Utils.showToast(ChangeMyNickAndHeadActivity.this, "上传头像成功");

                        user.setHeadPath(uploadHead.getFileUrl());
                        user.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ChangeMyNickAndHeadActivity.this, "更新成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ChangeMyNickAndHeadActivity.this, "更新失败" + e, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Utils.showToast(ChangeMyNickAndHeadActivity.this, "上传头像失败" + e);
                    }

                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                    Utils.showToast(ChangeMyNickAndHeadActivity.this, "上传进度:" + value + "%");
                }
            });
        } else {
            //没有选择图片 只保存昵称
            user.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(ChangeMyNickAndHeadActivity.this, "更新成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChangeMyNickAndHeadActivity.this, "更新失败" + e, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(ChangeMyNickAndHeadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ChangeMyNickAndHeadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            choosePhoto();

        }
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();

        //得到当前登录的用户
        User currentUser = BmobUser.getCurrentUser(User.class);
        if (currentUser != null) {
            etNickname.setText(currentUser.getNick());
        }
        //获取头像信息并显示
        String currentHead = currentUser.getHeadPath();
        if (null != currentHead) {
            //图片异步加载的框架
            Picasso.with(ChangeMyNickAndHeadActivity.this).load(currentHead).into(ivHeadicon);

        }
    }


    private void showImage(final String path) {

        Log.i("aaa", "showImage: " + Thread.currentThread());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                File file = new File(path);

                if (file.exists()) {
                    Log.i("aaa", "showImageInner: " + Thread.currentThread());
                    headIconPath = path;
//                    Bitmap bitmap = BitmapFactory.decodeFile(path);
//                    mIvHeadIcon.setImageBitmap(bitmap);
                    Picasso.with(ChangeMyNickAndHeadActivity.this).load(file).into(ivHeadicon);
                } else {
                    Toast.makeText(ChangeMyNickAndHeadActivity.this, "选择头像失败", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

            Uri uri = data.getData();
            Toast.makeText(ChangeMyNickAndHeadActivity.this, "选择头像成功,uri:" + uri, Toast.LENGTH_LONG).show();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(uri, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            Toast.makeText(ChangeMyNickAndHeadActivity.this, "path:" + imagePath, Toast.LENGTH_LONG).show();
            showImage(imagePath);
            c.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                choosePhoto();

            } else {
                // Permission Denied
                Toast.makeText(ChangeMyNickAndHeadActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
