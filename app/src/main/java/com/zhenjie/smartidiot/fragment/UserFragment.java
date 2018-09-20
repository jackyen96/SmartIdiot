package com.zhenjie.smartidiot.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.activities.LoginActivity;
import com.zhenjie.smartidiot.entity.MyUser;
import com.zhenjie.smartidiot.utils.L;
import com.zhenjie.smartidiot.utils.ShareUtils;
import com.zhenjie.smartidiot.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 文件名: UserFragment
 * 创建者: Jack Yan
 * 创建日期: 2018/8/31 20:58
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：TODO
 */
public class UserFragment extends Fragment implements View.OnClickListener {


    private Button buttonLogout, buttonConfirmEdit, buttonCamera, buttonPicLibrary, buttonCancel;
    private TextView tvEditUser;
    private EditText etName, etGender, etAge, etDescription;
    private CircleImageView profile_image;
    private CustomDialog dialog;
    public static final String PIC_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 1002;
    public static final int IMAGE_LIB_REQUEST_CODE = 1004;
    public static final int CROP_REQUEST_CODE = 1005;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 184;
    public static final String CROP_FILE_NAME = "cropImg.jpg";
    private Uri imageUri = null;
    private Uri cropImageUri = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, null);
        findView(v);
        String imgString = ShareUtils.getString(getActivity(), "image_profile", "");
        if (!imgString.equals("")){
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            profile_image.setImageBitmap(bitmap);
        }
        return v;
    }

    private void findView(View v) {

        buttonLogout = v.findViewById(R.id.bt_logout);
        buttonLogout.setOnClickListener(this);
        tvEditUser = v.findViewById(R.id.tv_edit_user);
        tvEditUser.setOnClickListener(this);
        buttonConfirmEdit = v.findViewById(R.id.bt_confirm_edit);
        buttonConfirmEdit.setOnClickListener(this);
        etName = v.findViewById(R.id.et_edit_user_name);
        etGender = v.findViewById(R.id.et_gender);
        etAge = v.findViewById(R.id.et_user_age);
        etDescription = v.findViewById(R.id.et_description);

        profile_image = v.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);


        dialog = new CustomDialog(getActivity(), WindowManager.LayoutParams.MATCH_PARENT
                , WindowManager.LayoutParams.MATCH_PARENT, R.layout.dialog_photo
                , R.style.Theme_dialog, Gravity.BOTTOM, R.style.pop_anim_style);

        dialog.setCancelable(false);

        buttonCamera = dialog.findViewById(R.id.bt_camera);
        buttonCamera.setOnClickListener(this);
        buttonPicLibrary = dialog.findViewById(R.id.bt_picture);
        buttonPicLibrary.setOnClickListener(this);
        buttonCancel = dialog.findViewById(R.id.bt_cancel);
        buttonCancel.setOnClickListener(this);


        //默认不可以编辑

        etName.setEnabled(false);
        etGender.setEnabled(false);
        etAge.setEnabled(false);
        etDescription.setEnabled(false);
        //设置默认值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        etName.setText(userInfo.getUsername());
        etGender.setText(userInfo.isGender() ? getString(R.string.male) : getString(R.string.female));
        etAge.setText(userInfo.getAge() + "");
        etDescription.setText(userInfo.getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_logout:
                MyUser.logOut();
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.tv_edit_user:
                setEnable(true);
                buttonConfirmEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_confirm_edit:
                String userName = etName.getText().toString();
                String userAge = etAge.getText().toString();
                String userGender = etGender.getText().toString();
                String description = etDescription.getText().toString();

                if (!TextUtils.isEmpty(userName) & !TextUtils.isEmpty(userAge) & !TextUtils.isEmpty(userGender)) {
                    MyUser user = new MyUser();
                    user.setUsername(userName);
                    user.setAge(Integer.parseInt(userAge));
                    if (userGender.equals("男")) {
                        user.setGender(true);
                    } else {
                        user.setGender(false);
                    }
                    if (!TextUtils.isEmpty(description)) {
                        user.setDescription(description);
                    } else {
                        user.setDescription("这个人很懒，什么都没有留下");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //修改成功
                                setEnable(false);
                                buttonConfirmEdit.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //修改不成功
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.bt_camera:
                toCamera();
                break;
            case R.id.bt_picture:
                toPictureLib();
                break;
            case R.id.bt_cancel:
                dialog.dismiss();
                break;
        }
    }

    private void setEnable(boolean is) {
        etName.setEnabled(is);
        etGender.setEnabled(is);
        etAge.setEnabled(is);
        etDescription.setEnabled(is);
    }


    //跳转到相册
    private void toPictureLib() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_LIB_REQUEST_CODE);
        dialog.dismiss();
    }


    //跳转到相机
    private void toCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}
                    , CAMERA_PERMISSION_REQUEST_CODE);
        } else {


            File outputImage = new File(getActivity().getExternalCacheDir(), PIC_FILE_NAME);
            if (outputImage.exists()) {
                outputImage.delete();
            }
            try {
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(getActivity(), "com.zhenjie.smartidiot.fileprovider"
                        , outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
            dialog.dismiss();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != 0) {
            switch (requestCode) {
                case IMAGE_LIB_REQUEST_CODE:
                    cropPic(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    cropPic(imageUri);
                    break;
                case CROP_REQUEST_CODE:
                    L.d("case CROP_REQUEST_CODE:");
                    if (data != null) {
                        L.d(data.toString());
                        setIconToView();
                    }
                    break;
            }
        }
    }

    private void cropPic(Uri uri) {
        if (uri == null) {
            return;
        }
        File cropPhoto = new File(getActivity().getExternalCacheDir(), CROP_FILE_NAME);
        if (cropPhoto.exists()) {
            cropPhoto.delete();
        }
        try {
            cropPhoto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropImageUri = Uri.fromFile(cropPhoto);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("noFaceDetection", true);

        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    private void setIconToView() {
        L.d(cropImageUri.toString());
        String path = cropImageUri.getPath();
        profile_image.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存头像
        BitmapDrawable drawable = (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        String mString = Base64.encodeToString(byStream.toByteArray(),Base64.DEFAULT);
        ShareUtils.putString(getActivity(),"image_profile",mString);
    }
}
