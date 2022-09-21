package com.example.androidlearning.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amap.api.maps.CameraUpdateFactory;
import com.example.androidlearning.R;

import com.amap.api.maps.MapView; // 引入地图控件
import com.amap.api.maps.AMap; // 地图控制器对象
import com.amap.api.maps.model.MyLocationStyle; // 定位样式
import com.amap.api.maps.MapsInitializer;

import com.amap.api.location.AMapLocation; // 定位
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

// 继承  AMapLocationListener（多继承 以逗号分隔）
public class MapActivity extends AppCompatActivity implements AMapLocationListener {
    private MapView mMapView; // 地图控件（显示地图画面）
    private AMap aMap = null; // 地图控制器对象（显示定位蓝点）

    public AMapLocationClient mlocationClient; // 定位客户端对象（定位详细参数信息-经纬度，省市区）
    public AMapLocationClientOption mLocationOption = null;

    public LocationManager systemLocationManager; // 系统定位 管理器
    public SystemLocationListener systemLocationListener; // 系统定位 监听器

    public Activity theActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        theActivity = this;

        // 先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口
        MapsInitializer.updatePrivacyAgree(this, true);
        MapsInitializer.updatePrivacyShow(this, true, true);

        /** 地图（通过高德api） **/
        createMapViewByGaoDe(savedInstanceState);

        /** 定位（通过高德api） **/
        getLocationByGaoDe();

        /** 定位（通过安卓系统api） **/
        getLocationBySystem();
    }

    // 创建地图（通过高德api）
    public void createMapViewByGaoDe(Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        // 设置定位样式
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.interval(10000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true); // 设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15)); // 指定地图显示级别为15级
        // 定位回调监听器：获得经纬度信息
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
//                高德地图回调经纬度: createMapViewByGaoDe: 纬度：34.196758 | 经度：108.887565
//                Log.d("高德地图回调经纬度", "createMapViewByGaoDe: 纬度：" + lat + " | 经度：" + lng);
            }
        });
    }

    // 获取定位信息（通过高德api）
    public void getLocationByGaoDe() {
        try {
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener((AMapLocationListener) this);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(10000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

            //启动定位
            mlocationClient.startLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取定位信息（通过安卓系统api）
    public void getLocationBySystem() {
        systemLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<String> list = systemLocationManager.getProviders(true);
        if (list != null) {
            for (String x : list) {
                /*
                * getLocationBySystem -: x:passive
                * getLocationBySystem -: x:gps
                * getLocationBySystem -: x:network
                *
                * */
//                Log.d("getLocationBySystem -", "x:" + x);
            }
        }

        LocationProvider lpGps = systemLocationManager.getProvider(LocationManager.GPS_PROVIDER);
        LocationProvider lpNet = systemLocationManager.getProvider(LocationManager.NETWORK_PROVIDER);
        LocationProvider lpPsv = systemLocationManager.getProvider(LocationManager.PASSIVE_PROVIDER);

        Criteria criteria = new Criteria();
        // Criteria是一组筛选条件
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置定位精准度
        criteria.setAltitudeRequired(false);
        //是否要求海拔
        criteria.setBearingRequired(true);
        //是否要求方向
        criteria.setCostAllowed(true);
        //是否要求收费
        criteria.setSpeedRequired(true);
        //是否要求速度
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        //设置电池耗电要求
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        //设置方向精确度
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        //设置速度精确度
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        //设置水平方向精确度
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        //设置垂直方向精确度

        //返回满足条件的当前设备可用的provider，第二个参数为false时返回当前设备所有provider中最符合条件的那个provider，但是不一定可用
        String mProvider = systemLocationManager.getBestProvider(criteria, true);
        if (mProvider != null) {
            // getLocationBySystem -: mProvider:gps
//            Log.d("getLocationBySystem - ", "mProvider:" + mProvider);
        }

        // 获取位置信息，调用监听方法
        systemLocationListener = new SystemLocationListener();
        if (systemLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            systemLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 10, systemLocationListener);
        }
    }
    // 注册一个位置监听器来接受结果
    private final class SystemLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            // 系统定位回调经纬度: 纬度：34.19817548417356 | 经度：108.88292359880214
            Log.d("getLocationBySystem - ", "系统定位回调经纬度: 纬度：" + latitude + " | 经度：" + longitude);

            // 反地理编码
            Geocoder geocoder = new Geocoder(theActivity);
            boolean flag = Geocoder.isPresent();
            if (flag) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String sAddress;//地址

                        Locale locale = address.getLocale(); // zh_CN
                        String featureName = address.getFeatureName(); // 老潼关肉夹馍(都市之门店)都市之门C座
                        String adminArea = address.getAdminArea(); // 陕西省
                        String subAdminArea = address.getSubAdminArea(); // 丈八沟街道
                        String locality = address.getLocality(); // 西安市
                        String subLocality = address.getSubLocality(); // 雁塔区
                        String thoroughfare = address.getThoroughfare(); // 锦业路
                        String subThoroughfare = address.getSubThoroughfare(); // 1号
                        String countryCode = address.getCountryCode(); // CN
                        String countryName = address.getCountryName(); // 中国
                        double latitude2 = address.getLatitude(); // 34.197178
                        double longitude2 = address.getLongitude(); // 108.883653

                        if (!TextUtils.isEmpty(address.getLocality())) {
                            if (!TextUtils.isEmpty(address.getFeatureName())) {
                                //市和周边地址
                                sAddress = adminArea + locality + subLocality + thoroughfare + subThoroughfare + featureName; // 西安市 老潼关肉夹馍(都市之门店)都市之门C座
                            } else {
                                sAddress = address.getLocality(); // 西安市
                            }
                        } else {
                            sAddress = "定位失败";
                        }
                        /*
                         定位信息为：
                         纬度：34.197178，经度：108.883653，地点：老潼关肉夹馍(都市之门店)都市之门C座，
                         国家：中国，省：陕西省，市：西安市，区：雁塔区，街区：丈八街道，街道：锦业路，街道门牌号：1号，
                         完整地址：陕西省西安市雁塔区锦业路1号老潼关肉夹馍(都市之门店)都市之门C座，
                         国家编码：CN
                         * */
                        Log.d("getLocationBySystem - ", "定位信息为：" +
                                "纬度：" + latitude2 + "，经度：" + longitude2 + "，地点：" + featureName +
                                "，国家：" + countryName + "，省：" + adminArea + "，市：" + locality + "，区：" + subLocality + "，街区：" + subAdminArea + "，街道：" + thoroughfare + "，街道门牌号：" + subThoroughfare +
                                "，完整地址：" + sAddress +
                                "，国家编码：" + countryCode);
                    }
                } catch (IOException e) {

                }
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("SystemLocationListener-", "onStatusChanged:" + status);
        }

        public void onProviderEnabled(String provider) {
            Log.d("SystemLocationListener-", "onProviderEnabled:");
        }

        public void onProviderDisabled(String provider) {
            Log.d("SystemLocationListener-", "onProviderDisabled:");
        }

    }
    // 实现AMapLocationListener的方法 定位信息回调
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                float accuracy = amapLocation.getAccuracy();//获取精度信息
                String address = amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                String country = amapLocation.getCountry();//国家信息
                String province = amapLocation.getProvince();//省信息
                String city = amapLocation.getCity();//城市信息
                String district = amapLocation.getDistrict();//城区信息
                String street = amapLocation.getStreet();//街道信息
                String streetNum = amapLocation.getStreetNum();//街道门牌号信息
                String cityCode = amapLocation.getCityCode();//城市编码
                String adCode = amapLocation.getAdCode();//地区编码
                String aoiName = amapLocation.getAoiName();//获取当前定位点的AOI信息
                /*
                getLocationByGaoDe -: 定位信息为：纬度：34.19651241578378，经度：108.88750188303054，精度信息：32.75574，地址：陕西省西安市雁塔区丈八东路1号靠近绿地都市之门，
                国家：中国，省：陕西省，市：西安市，区：雁塔区，街道：丈八东路，街道门牌号：1号，
                城市编码：029，地区编码：610113，当前定位点的AOI信息：都市之门C座
                * */
                Log.d("getLocationByGaoDe - ", "定位信息为：" +
                        "纬度：" + latitude + "，经度：" + longitude + "，精度信息：" + accuracy + "，地址：" + address +
                        "，国家：" + country + "，省：" + province + "，市：" + city + "，区：" + district + "，街道：" + street + "，街道门牌号：" + streetNum +
                        "，城市编码：" + cityCode + "，地区编码：" + adCode + "，当前定位点的AOI信息：" + aoiName);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        // 解注册 系统定位监听器
        systemLocationManager.removeUpdates(systemLocationListener);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}