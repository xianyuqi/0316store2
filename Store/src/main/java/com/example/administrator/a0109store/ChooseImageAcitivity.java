package com.example.administrator.a0109store;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.File;

public class ChooseImageAcitivity extends Activity {

    Button btn_chooseImage;
    private Button btn_OK;
    private Button btn_takephoto;
    ImageView iv_show;
    public String url = null;
    private String capturePath = null;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 2;
    private static final String SAVED_IMAGE_DIR_PATH = "/storage/sdcard1/DCIM/Camera/";
    private static final String SAVED_IMAGE_DIR_PATH1= "/storage/emulated/0/DCIM/Camera/";

    private static final int REQUEST_CODE = 1;
    private Uri imageUri;
    private File fileU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image_acitivity);
        //init  初始化控件
        init();
        //选择图片

        chooseImage();
        //上传地址到插入数据页面的imagesrc
        upload();
        //拍照
        takePhoto(this);

    }


    /**
     * 工具方法
     * 取得uri的真实路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK) {
                 //   newUri = FileProvider.getUriForFile(this, "com.example.administrator.a20171216takephoto", new File(newUri.getPath()));
                    Uri uri = data.getData();
                   // Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(this, "com.example.administrator.a0109store", new File(uri.getPath()));
                    }
                    //ContentResolver cr=this.getContentResolver();
                    url = getRealFilePath(this, uri);
                   // Bitmap bitmap = BitmapFactory.decodeFile(url);
                   // iv_show.setImageBitmap(bitmap);

                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.drawable.nopicture)
                            .showImageOnFail(R.drawable.nopicture2)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();



                    String imageUrl = ImageDownloader.Scheme.FILE.wrap(url);
                    ImageLoader.getInstance().displayImage(imageUrl,iv_show,options);



                } else {
                    Toast.makeText(ChooseImageAcitivity.this, "图片未找到", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case REQUEST_CODE_CAPTURE_CAMEIA:
                url=capturePath;

                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.nopicture)
                        .showImageOnFail(R.drawable.nopicture2)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();


                //String imagePath = goodses.get(position).getImagesrc();
                String imageUrl = ImageDownloader.Scheme.FILE.wrap(url);
                ImageLoader.getInstance().displayImage(imageUrl,iv_show,options);

                Bitmap bitmap = BitmapFactory.decodeFile(capturePath);
                iv_show.setImageBitmap(bitmap);

                break;
            default:


        }


        super.onActivityResult(requestCode, resultCode, data);


    }

    private void init() {
        btn_chooseImage = (Button) findViewById(R.id.btn_ChooseImage_choose);
        btn_takephoto = (Button) findViewById(R.id.btn_takephoto);
        iv_show = (ImageView) findViewById(R.id.iv_ChooseImage_show);
        btn_OK = (Button) findViewById(R.id.btn_ChooseImage_OK);


    }

    private void chooseImage() {
        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image:/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

            }
        });
    }

    private void upload() {
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("url", url);
                setResult(1, intent);
                finish();
            }
        });
    }
//添加Context
    private void takePhoto(final Context context) {
        btn_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromCamera(context);

            }
        });
    }
//添加Context
    protected void getImageFromCamera(Context context) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                /**
                 * android 6.0以上需要添加条件判断
                 * 请求权限是一个异步任务  不是立即请求就能得到结果 在结果回调中返回
                 */




                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                //打开相机 前置摄像头
                getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                if (Build.VERSION.SDK_INT >= 24) {
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    getImageByCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }




                String out_file_path = SAVED_IMAGE_DIR_PATH1;
                File dir = new File(out_file_path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                capturePath = SAVED_IMAGE_DIR_PATH1 + System.currentTimeMillis() + ".jpg";
                fileU=new File(capturePath);
                imageUri= Uri.fromFile(fileU);
                if (Build.VERSION.SDK_INT >=24) {
                    // TODO: 2017/12/17
                    //imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.administrator.a20171216takephoto", fileUri);
                    imageUri = FileProvider.getUriForFile(context, "com.example.administrator.a0109store", fileU);
                }



               // getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
            } else {
                //打开相机 前置摄像头
                 getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                String out_file_path = SAVED_IMAGE_DIR_PATH1;
                File dir = new File(out_file_path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                capturePath = SAVED_IMAGE_DIR_PATH1 + System.currentTimeMillis() + ".jpg";
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
                getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
            }


        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }
//    protected int getImageFromCamera() {
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
//            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
//        }
//        return 0;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
