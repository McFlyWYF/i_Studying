package com.example.logindemo.navigation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.NaviGuideWidget;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.example.logindemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class SearchMap extends Activity implements AMapNaviListener,AMapNaviViewListener,AMapLocationListener {

    private Button search_map_btn;

    private static final String TAG = SearchMap.class.getName();
    private AMapNaviView aMapNaviView;//3D导航地图对象
    private AMapNavi aMapNavi;//导航对象
    private final List<NaviLatLng> startList = new ArrayList<NaviLatLng>();//起点坐标
    private final List<NaviLatLng> endList = new ArrayList<NaviLatLng>();//终点坐标
    private List<NaviLatLng> mWayPointList = new ArrayList<NaviLatLng>();//途经点坐标
    private AMapLocationClientOption aMapLocationClientOption = null;
    private AMapLocationClient aMapLocationClient = null;

    private NaviGuideWidget naviWay;//导航方式
    private JSONArray naviData;//导航参数
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);

        aMapNaviView = new AMapNaviView(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        aMapNaviView.setLayoutParams(layoutParams);
        setContentView(aMapNaviView);

        //naviWay = (NaviType)getIntent().getSerializableExtra(A);
        try{
            //naviData = new JSONArray(getIntent().getStringExtra(AMapNaviPlugin.NAVI_DATA));
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
            finish();
        }

        intitNaviData();

        try{
            //起点坐标为空，则获取设备当前坐标
            JSONObject start = naviData.getJSONObject(0);
            startList.add(new NaviLatLng(start.optDouble("latitude"),start.optDouble("lpngitude")));
            initNavi();
        }catch (Exception e){
            Log.e(TAG,"起点坐标为空，获取当前位置");
            initLocation();
        }

        aMapNaviView.onCreate(savedInstanceState);
        setAmapNaviViewOptions();
        aMapNaviView.setAMapNaviViewListener(this);
//        aMapNaviView.setAMapNaviViewListener((AMapNaviViewListener) this);
//        aMapNaviView.onCreate(savedInstanceState);
//        //初始化
//        aMapNavi = AMapNavi.getInstance(getApplicationContext());
//        aMapNavi.addAMapNaviListener((AMapNaviListener) this);

    }

    //设置导航参数
    private void setAmapNaviViewOptions() {
        if (aMapNaviView == null){
            return;
        }

        AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
        viewOptions.setReCalculateRouteForYaw(true);//偏离是重新计算
        aMapNaviView.setViewOptions(viewOptions);
    }

    //获取定位坐标
    private void initLocation() {
        aMapLocationClient = new AMapLocationClient(this);
        aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClient.setLocationListener(this);
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setOnceLocation(true);
        aMapLocationClientOption.setOnceLocationLatest(true);
        aMapLocationClientOption.setInterval(2000);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
    }

    //初始化导航
    private void initNavi() {
        aMapNavi = AMapNavi.getInstance(getApplicationContext());
        aMapNavi.addAMapNaviListener(this);

        aMapNavi.setEmulatorNaviSpeed(75);
    }

    //初始化起始点
    private void intitNaviData() {
        JSONObject end = null;
        if (true){
            //步行导航
            end = naviData.optJSONObject(1);
            endList.add(new NaviLatLng(end.optDouble("latitude"),end.optDouble("longitude")));
        }else {
            //驾车方式
            end = naviData.optJSONObject(2);
            endList.add(new NaviLatLng(end.optDouble("latitude"),end.optDouble("longitude")));

            JSONArray wayPosArray = naviData.optJSONArray(1) == null ? new JSONArray() : naviData.optJSONArray(1);
            int length = wayPosArray.length() > 3 ? 3 : wayPosArray.length();
            for(int i = 0;i < length;i++){
                JSONObject waypos = naviData.optJSONObject(i);
                mWayPointList.add(new NaviLatLng(waypos.optDouble("latitude"),waypos.optDouble("longitude")));
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        aMapNaviView.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        try {
            aMapNaviView.onPause();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        try {
            aMapNaviView.onDestroy();
            // since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
            aMapNavi.stopNavi();
            aMapNavi.destroy();
            if (null != aMapLocationClient) {
                /**
                 * 如果AMapLocationClient是在当前Activity实例化的，
                 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
                 */
                aMapLocationClient.onDestroy();
                aMapLocationClient = null;
                aMapLocationClient = null;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this,"init navi Failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess(){
        int strategy = 0;
        try {
            strategy = aMapNavi.strategyConvert(true,false,false,false,false);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(true) {
            aMapNavi.calculateWalkRoute(startList.get(0), endList.get(0)); // 步行导航
        } else {
            aMapNavi.calculateDriveRoute(startList, endList, mWayPointList, strategy);
        }
    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {
        Toast.makeText(SearchMap.this, "onCalculateRouteFailure code : " + i, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    //导航开始
    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        Log.i(TAG,"开始导航");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean naviType = true;
                if (true) {
                    naviType = naviData.optBoolean(1,true);
                }else {
                    naviType = naviData.optBoolean(3,true);
                }
                if (naviType){
                    aMapNavi.startNavi(NaviType.EMULATOR);
                }else aMapNavi.startNavi(NaviType.GPS);
            }
        },3000);
    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null){
            if (aMapLocation.getErrorCode() == 0){
                //定位成功，显示回调信息
                startList.add(new NaviLatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()));
                initNavi();
                onInitNaviSuccess();
            }else {
                Log.e("AmapError","locetion Error,ErrorCode" + aMapLocation.getErrorCode() + ",errInfo" + aMapLocation.getErrorInfo());
                Toast.makeText(SearchMap.this, "location Error, ErrCode:" + aMapLocation.getErrorCode() +
                        ", errInfo:" + aMapLocation.getErrorInfo(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviCancel() {
        Log.i(TAG,"导航结束");
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx","导航页面加载成功");
        Log.d("wlx","会覆盖");
    }
}
