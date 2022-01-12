package com.company.wanbei.app.util;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;

//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.FileProvider;

/**
 * 上传头像单独操作类
 *
 * @author YC
 */
public class UploadPersonPhotoUtil {

    public static final String PICTURE_DIR = "/doctor";
    public static final int REQUEST_CODE_TAKE_PHOTO = 1989;
    public static final int REQUEST_CODE_SELECT_PICTURE = 1990;
    public static final int REQUEST_CODE_CROP_PICTURE = 1991;
    private static int OUTPUT_X = 240;
    private static int OUTPUT_Y = 240;

    /**
     * 调用相机
     *
     * @param
     */
    public static String takePhoto(Activity activity) {
        //特殊处理（6.0以上）
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
            return "";
        }
        //特殊处理 （三星手机，读写权限被禁止）
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return "";
        }
        String mPath = "";
        if (isSdCardExist()) {
            mPath = createAvatarPath();
//			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//			intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(mPath)));


            //android 7.0 新增私有文件权限控制
            Uri imageUri = FileProvider.getUriForFile(activity, "com.company.wanbei.app.fileProvider", new File(mPath));//通过FileProvider创建一个content类型的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            try {

                activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
                return mPath;
            } catch (ActivityNotFoundException anf) {
                Toast.makeText(activity, "摄像头尚未准备好", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(activity, "未找到外部存储", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    /**
     * 特殊机型 拍照无法保存时 特殊处理
     *
     * @param context
     * @param data
     * @param file
     */
    public static void getUriAndSave(Context context, Intent data, String file) {
        if (data != null) {
            //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
            Uri mImageCaptureUri = data.getData();
            //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
            if (mImageCaptureUri != null) {
                Bitmap image;
                try {
                    //这个方法是根据Uri获取Bitmap图片的静态方法
                    image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mImageCaptureUri);
                    if (image != null) {
                        savePicToSdcard(image, file);
                        image.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                    Bitmap image = extras.getParcelable("data");
                    if (image != null) {
                        savePicToSdcard(image, file);
                        image.recycle();
                    }
                }
            }
        }
    }

    /**
     * 拍照保存图片
     *
     * @param bitmap
     * @param
     * @param
     * @return
     */
    public static void savePicToSdcard(Bitmap bitmap, String file) {
        if (bitmap != null) {
            File destFile = new File(file);
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                bitmap.compress(CompressFormat.JPEG, 60, os);
                os.flush();
                os.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 调用本地图片
     *
     * @param
     */
    public static void selectImageFromLocal(Activity activity) {

        //特殊处理（6.0以上）
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            return;
        }


        if (isSdCardExist()) {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }

            activity.startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
        } else {
            Toast.makeText(activity, "未找到外部存储", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据选择本地图片返回图片的实际地址
     *
     * @param data
     * @param activity
     * @return
     */
    public static String getPathFromIntent(Intent data, Activity activity) {
        String mPath = "";
        if (data != null) {
            Uri selectedImg = data.getData();
            int s = PhotoBitmapUtils.readPictureDegree(String.valueOf(selectedImg));
            if (selectedImg != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query(
                        selectedImg, filePathColumn, null, null, null);
                if (null == cursor) {
                    mPath = selectedImg.getPath();
                    File file = new File(mPath);
                    if (file.isFile()) {
                        // copyAndCrop(file);
                        return mPath;
                    } else {
                        Toast.makeText(activity, "图片不存在", Toast.LENGTH_SHORT)
                                .show();
                        return null;
                    }
                } else if (!cursor.moveToFirst()) {
                    Toast.makeText(activity, "图片不存在", Toast.LENGTH_SHORT)
                            .show();
                    return null;
                }
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mPath = cursor.getString(columnIndex);
                if (mPath != null) {
                    File file = new File(mPath);
                    if (!file.isFile()) {
                        Toast.makeText(activity, "图片不存在", Toast.LENGTH_SHORT)
                                .show();
                        cursor.close();
                    } else {
//                         copyAndCrop(file);
                        cursor.close();
                        return mPath;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 剪裁图片
     */
    public static void cropRawPhoto(Uri uri, Activity activity, Uri taguri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 是否剪裁
        intent.putExtra("crop", "true");
//		intent.putExtra("circleCrop", "true");
        // aspectX , aspectY : 剪裁 比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 输出的长宽
        intent.putExtra("outputX", OUTPUT_X);
        intent.putExtra("outputY", OUTPUT_Y);
        // 是否返回数据
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, taguri);
        activity.startActivityForResult(intent, REQUEST_CODE_CROP_PICTURE);
    }

    /**
     * 剪裁图片 android 7.0 之后
     */
    public static void cropRawPhoto_NewApi(String uri, Activity activity, String taguri) {


        //android 7.0 新增私有文件权限控制
        Uri outputUri = FileProvider.getUriForFile(activity, "com.company.wanbei.app.fileProvider", new File("uri"));
        Uri imageUri = FileProvider.getUriForFile(activity, "com.company.wanbei.app.fileProvider", new File(taguri));
        //通过FileProvider创建一个content类型的Uri

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        // 是否剪裁
        intent.putExtra("crop", "true");
//		intent.putExtra("circleCrop", "true");
        // aspectX , aspectY : 剪裁 比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 输出的长宽
        intent.putExtra("outputX", OUTPUT_X);
        intent.putExtra("outputY", OUTPUT_Y);
        // 是否返回数据
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        activity.startActivityForResult(intent, REQUEST_CODE_CROP_PICTURE);
    }

    /**
     * 判断是否存在SD卡
     *
     * @return
     */
    private static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建文件路径
     *
     * @return
     */
    public static String createAvatarPath() {
        String dir = Environment.getExternalStorageDirectory() + PICTURE_DIR;
        File destDir = new File(dir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = new File(dir, new DateFormat().format("yyyy_MMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA)) + ".png");
        return file.getAbsolutePath();
    }

    /**
     * 读取Uri 转化成bitMap
     *
     * @param uri
     * @param mContext
     * @return
     */
    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            // 读取uri所在的图片
            return MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Bitmap
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapFromFile(String path, int width, int height) {
        BitmapFactory.Options opts = null;
        if (path != null) {
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, opts);
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);
//                opts.inSampleSize =10;
                        Log.i("getBitmapFromFile", "inSampleSize:" + opts.inSampleSize);
                opts.inJustDecodeBounds = false;
            }
            return BitmapFactory.decodeFile(path, opts);
        } else
            return null;
    }

    /**
     * 缩放图片大小
     *
     * @param bitmap
     * @param sw
     * @param sh
     * @return
     */
    public static Bitmap zoomBitmapForImageView(Bitmap bitmap, int sw, int sh) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) sw / w);
        float scaleHeight = ((float) sh / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    /**
     * 计算放缩比率
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    /**
     * 计算放缩比率
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public interface CopyFileCallback {
        public void copyCallback(Uri uri, String path);
    }

    public static void copyAndCrop(final File file, final Activity context,
                                   final CopyFileCallback callback) {
        if (isSdCardExist()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        final String path = createAvatarPath();
                        final File tempFile = new File(path);
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        byte[] bt = new byte[1024];
                        int c;
                        while ((c = fis.read(bt)) > 0) {
                            fos.write(bt, 0, c);
                        }
                        // 关闭流
                        fos.flush();
                        fis.close();
                        fos.close();
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.copyCallback(Uri.fromFile(tempFile), path);
//								callback.copyCallback(Uri.parse("file://"+path));
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // dialog.dismiss();
                            }
                        });
                    }
                }
            });
            thread.start();
        } else {
            Toast.makeText(context, "未找到外部存储", Toast.LENGTH_SHORT).show();
        }
    }
}
