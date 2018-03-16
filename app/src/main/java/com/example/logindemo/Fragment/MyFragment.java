package com.example.logindemo.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logindemo.R;
import com.example.logindemo.activity.ShowAboutActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 王宇飞 on 2018/3/16/016.
 */

public class MyFragment extends Fragment{

    private static final int ALBUM_REQUEST_CODE = 1;//相册请求码
    private static final int CAMERA_REQUEST_CODE = 2;//相机请求码
    private static final int CROP_REQUEST_CODE = 3;//裁剪请求码
    private File tempFile;//调用相机返回图片文件
    private ImageView myPhotoview;
    private TextView collectionview;
    private TextView settingview;
    private TextView logview;
    private TextView aboutview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.my_fragment,container,false);

        myPhotoview = (ImageView) view.findViewById(R.id.icon_image);
        TextView changeview = (TextView) view.findViewById(R.id.change_image);
        changeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicture();
            }
        });

        collectionview = (TextView)view.findViewById(R.id.collection);
        logview = (TextView) view.findViewById(R.id.logg);
        settingview = (TextView)view.findViewById(R.id.setting);
        aboutview = (TextView) view.findViewById(R.id.show_about);

        aboutview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowAboutActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //从相册获取图片
    private void getPicture() {
        Intent photo = new Intent(Intent.ACTION_PICK);
        photo.setType("image/*");
        startActivityForResult(photo, ALBUM_REQUEST_CODE);
    }

    //裁剪图片
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case ALBUM_REQUEST_CODE://调用相机后返回
                if (requestCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE://调用裁剪后返回
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Bitmap image = bundle.getParcelable("data");//获取裁剪后的bitmap对象，课用于上传
                    myPhotoview.setImageBitmap(image);//设置到imageview上
                }
                break;
        }
    }

    public String saveImage(String name, Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String filename = name + ".jpg";
        File file = new File(appDir, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
