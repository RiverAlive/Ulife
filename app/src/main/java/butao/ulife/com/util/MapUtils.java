package butao.ulife.com.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butao.ulife.com.base.CApplication;


/**
 * Created by as on 2017/5/22.
 */

public class MapUtils {
    public  static Activity mActivity;
    public  static String address;
    public MapUtils(Activity activity,String address){
        mActivity = activity;
        this.address =address;
    }
    private static PackageManager mPackageManager = CApplication.getIntstance().getPackageManager();
    private static List<String> mPackageNames = new ArrayList<>();
    private static final String GAODE_PACKAGE_NAME = "com.autonavi.minimap";
    private static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";
    private static final String GOOGLE_PACKAGE_NAME = "com.google.android.apps.maps";


    private static void initPackageManager() {

        List<PackageInfo> packageInfos = mPackageManager.getInstalledPackages(0);

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                mPackageNames.add(packageInfos.get(i).packageName);
            }
        }
    }

    public static boolean haveGaodeMap() {
        initPackageManager();
        return mPackageNames.contains(GAODE_PACKAGE_NAME);
    }
    public static boolean haveBaiDuMap() {
        initPackageManager();
        return mPackageNames.contains(BAIDU_PACKAGE_NAME);
    }
    public static boolean haveGoogleMap() {
        initPackageManager();
        return mPackageNames.contains(GOOGLE_PACKAGE_NAME);
    }
    public static void startMap(){
        if(haveBaiDuMap()){
            openBaiDuMapToGuide();
        }else  if(haveGaodeMap()){
            openGaodeMapToGuide();
        }else
        if(haveGoogleMap()){
            openGoogleMapToGuide();
        }else{
            Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mActivity.startActivity(intent);
        }
    }
    //使用本机Googleapp地图
    public static void openGoogleMapToGuide() {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        String url = "https://maps.google.com/maps?q=%"+address+"%&z=17&hl=en";
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        mActivity.startActivity(intent);
    }
    //使用本机高德app地图
    public static void openGaodeMapToGuide() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        String url = "androidamap://route?sourceApplication=amap&slat=" + CApplication.getIntstance().latitude + "&slon=" + CApplication.getIntstance().longitude
//                + "&sname=我的位置&dname=" + address + "&dev=0&t=1";
      String url = "androidamap://keywordNavi?sourceApplication=amap&keyword=%"+address+"%&style=2";
        Uri uri = Uri.parse(url);
        //将功能Scheme以URI的方式传入data
        intent.setData(uri);
        //启动该页面即可
        mActivity.startActivity(intent);
    }



    //使用本机百度app地图
    public static  void openBaiDuMapToGuide() {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        String url ="baidumap://map/direction?origin=latlng:" + CApplication.getIntstance().latitude + ","
//                + CApplication.getIntstance().longitude + "|name:我的位置&destination=name:" + address + "&mode=driving&output=html&src=yourCompanyName|yourAppName";
//        Uri uri = Uri.parse(url);
//        //将功能Scheme以URI的方式传入data
//        intent.setData(uri);
//        //启动该页面即可
//        mActivity.startActivity(intent);

        Intent intent = null;
        try {
            intent = Intent.getIntent("intent://map/line?coordtype=&zoom=&region="+address+"&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            mActivity.startActivity(intent);   //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //调起百度地图客户端（Android）展示上海市"28"路公交车的检索结果


    }
}
