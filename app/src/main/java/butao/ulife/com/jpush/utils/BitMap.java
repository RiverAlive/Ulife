package butao.ulife.com.jpush.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import cn.jpush.im.android.api.JMessageClient;

/**
 * 创建时间 ：2017/7/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class BitMap {
    public static void thredMap(final String url){
        new Thread(new Runnable(){
            @Override
            public void run() {
                returnBitMap(url);
            }
        }).start();
    }
    /**
     * return a bitmap from service
     * @param url
     * @return bitmap type
     */
    public final static Bitmap returnBitMap(final String url) {
         Bitmap bitmap = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }


    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static  File saveFile1(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() +"/revoeye/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;

    }
    /**
     * 将Bitmap转换成文件
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String fileName) throws IOException {
        String PATH = Environment.getExternalStorageDirectory().getPath() +File.separator+ "revoeye";
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        String path = getSDPath() +"/revoeye/";
        File dirFile = new File(PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(PATH , fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
    public static File SaveImage(Bitmap finalBitmap,String filename) {

        String PATH = Environment.getExternalStorageDirectory()+ JMessageClient.getMyInfo().getAppKey();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File myDir = new File(PATH);
        myDir.mkdirs();

        String fname = filename;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    public static File saveBitmap(Bitmap bitmap, String bitName) throws IOException
    {
        File file = new File(Environment.getExternalStorageDirectory()+bitName);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }
}
