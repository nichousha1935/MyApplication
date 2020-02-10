package com.example.wangke.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.MovingPointOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener,
        AMap.OnMarkerClickListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener,
        Inputtips.InputtipsListener {
    private MapView mapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;//小蓝点
    private boolean isCreateMarker = false;
    private GeocodeSearch geocoderSearch;//地址描述数据
    private PoiSearch.Query query;//poi 检索
    private PoiSearch poiSearch;
    private EditText et_search;
    private TextView tv_search;
    private Marker locationMarker;
    private String current_cityCode;
    private String current_cityName;
    private AMapLocation currentaMapLocation;
    private int currentZoom = 19;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private ImageView img_address_search, img_list_search, img_homing, img_shrink, img_expand, img_reveal;
    public LatLng currentLatLng;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
//                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                    aMapLocation.getLatitude();//获取纬度
//                    aMapLocation.getLongitude();//获取经度
//                    aMapLocation.getAccuracy();//获取精度信息
//                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    aMapLocation.getCountry();//国家信息
//                    aMapLocation.getProvince();//省信息
//                    aMapLocation.getCity();//城市信息
//                    aMapLocation.getDistrict();//城区信息
//                    aMapLocation.getStreet();//街道信息
//                    aMapLocation.getStreetNum();//街道门牌号信息
//                    aMapLocation.getCityCode();//城市编码
//                    aMapLocation.getAdCode();//地区编码
//                    aMapLocation.getAoiName();//获取当前定位点的AOI信息
//                    aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//                    aMapLocation.getFloor();//获取当前室内定位的楼层
//                    aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                    current_cityCode = aMapLocation.getCityCode();
                    current_cityName = aMapLocation.getCity();
                    currentLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    currentaMapLocation = aMapLocation;
                    if (!isCreateMarker) {
                        showGatherMarker(aMapLocation);
                        isCreateMarker = true;
                    }

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。

                }
            }
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_map);
        getToolBar().setTitle("高德地图");
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(currentZoom));
        aMap.showIndoorMap(true);  //true：显示室内地图；false：不显示；
        aMap.setOnMyLocationChangeListener(this::onMyLocationChange);
        aMap.setOnMarkerClickListener(this::onMarkerClick);


        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //方法自5.1.0版本后支持
        myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location));//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
//        myLocationStyle.anchor((float) 0.0, (float) 1.0);//设置定位蓝点图标的锚点方法。
//        myLocationStyle.strokeColor(Color.parseColor("#000000"));//设置定位蓝点精度圆圈的边框颜色的方法。
//        myLocationStyle.radiusFillColor(Color.parseColor("#000000"));//设置定位蓝点精度圆圈的填充颜色的方法。
//        myLocationStyle.strokeWidth(10);//设置定位蓝点精度圈的边框宽度的方法。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //  aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        allocation();
    }


    @Override
    public void initView() {
        et_search = findViewById(R.id.et_search);
        tv_search = findViewById(R.id.tv_search);
        img_address_search = findViewById(R.id.img_address_search);
        img_list_search = findViewById(R.id.img_list_search);
        img_homing = findViewById(R.id.img_homing);
        img_shrink = findViewById(R.id.img_shrink);
        img_expand = findViewById(R.id.img_expand);
        img_reveal = findViewById(R.id.img_reveal);


//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
//        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
    }

    @Override
    public void initBind() {
        tv_search.setOnClickListener(this::widgetClick);
        img_address_search.setOnClickListener(this::widgetClick);
        img_list_search.setOnClickListener(this::widgetClick);
        img_homing.setOnClickListener(this::widgetClick);
        img_shrink.setOnClickListener(this::widgetClick);
        img_expand.setOnClickListener(this::widgetClick);
        img_reveal.setOnClickListener(this::widgetClick);
    }

    @Override
    public void initData() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                poiSearch(et_search.getText().toString(), "", current_cityCode);
                break;
            case R.id.img_address_search:
                startActivityForResult(new Intent(MapActivity.this, GaoDeSearchActivity.class).putExtra("LatLng", currentLatLng).putExtra("cityName",current_cityName), 1000);
                break;
            case R.id.img_list_search:
                startActivity(new Intent(MapActivity.this, DataListActivity.class));
                break;
            case R.id.img_homing:
                homing(currentLatLng);
                break;
            case R.id.img_shrink:
                if (currentZoom > 3) {
                    aMap.moveCamera(CameraUpdateFactory.zoomOut());//缩放地图到当前缩放级别的下一级
                    currentZoom = currentZoom - 1;
                } else {
                    ToastUtil.showToast(MapActivity.this, "缩放已经达到最小级别");
                }

                break;
            case R.id.img_expand:
                if (currentZoom < 19) {
                    aMap.moveCamera(CameraUpdateFactory.zoomIn());//缩放地图到当前缩放级别的上一级
                    currentZoom = currentZoom + 1;
                } else {
                    ToastUtil.showToast(MapActivity.this, "缩放已经达到最大级别");
                }
                break;
            case R.id.img_reveal:
                startActivity(new Intent(MapActivity.this, RevealSelectActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    //当前地理位置回调
    @Override
    public void onMyLocationChange(Location location) {
//        current_cityCode = ((rq) location).getCityCode();
//        current_cityName = ((rq) location).getCity();
//        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//        showGatherMarker(location);
    }

    public void setMapType(int type) {
        aMap.setMapType(type);
    }

    //调整采集位置的绘制点
    public void showGatherMarker(Location location) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_map_marker, null);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
       // markerOption.position(latLng);
        markerOption.title("定位点").snippet("采集位置调整");
        markerOption.visible(true);
//        markerOption.infoWindowEnable(true);
            markerOption.anchor((float) 0.5,(float) 1);//点标记的锚点
        markerOption.draggable(false);//设置Marker可拖动
//            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(), R.mipmap.location)));
        markerOption.icon(BitmapDescriptorFactory.fromView(view));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        locationMarker = aMap.addMarker(markerOption);
       // LatLng latLng1 = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);//地理位置坐标转屏幕坐标
        locationMarker.setPositionByPixels(mapView.getWidth()/2,mapView.getHeight()/2);//地图移动绘制点固定
       // locationMarker.setPositionByPixels(screenPosition.x,screenPosition.y);
    }

    //绘制点点击回调
    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLonPoint latLonPoint = new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 10, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
        return true;
    }

    //地理坐标转地址
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            ToastUtil.showToast(this, regeocodeAddress.getFormatAddress());
            startActivity(new Intent(MapActivity.this, AddInfoSelectActivity.class));
        }
    }


    //地理位置搜索
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    //地理位置搜索
    @Override
    public void onPoiSearched(PoiResult poiResult, int errorCode) {
        //解析result获取POI信息
        if (errorCode == 1000) {
            LatLonPoint latLonPoint = poiResult.getPois().get(0).getLatLonPoint();
            LatLng endLatLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
            moveMarker(currentLatLng, endLatLng);
        }
    }

    //地理位置搜索
    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public void poiSearch(String keyWord, String poiType, String cityCode) {
        query = new PoiSearch.Query(keyWord, poiType, cityCode);//keyWord表示搜索字符串，第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    //绘制点移动
    public void moveMarker(LatLng startLatLng, LatLng endLatLng) {
        // 获取轨迹坐标点
        List<LatLng> points = new ArrayList<>();
        points.add(startLatLng);
        points.add(endLatLng);


//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(points.get(0));
//        builder.include(points.get(points.size() - 2));
        //使用传入的西南角坐标和东北角坐标创建一个矩形区域。
//        LatLngBounds bounds = new LatLngBounds(points.get(0),points.get(1));
//        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));//设置显示在规定屏幕范围内的地图经纬度范围。

        // 设置滑动的图标
        MovingPointOverlay movingPointOverlay = new MovingPointOverlay(aMap, locationMarker);


        LatLng drivePoint = points.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());

        // 设置滑动的轨迹左边点
        movingPointOverlay.setPoints(subList);
        // 设置滑动的总时间
        movingPointOverlay.setTotalDuration(4);
        // 开始滑动
        movingPointOverlay.startSmoothMove();
    }

    //搜索智能提示
    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == 1000) {

        }
    }
//        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式，aMap是地图控制器对象。
//        aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图，aMap是地图控制器对象。
//        aMap.setTrafficEnabled(true);//显示实时路况图层，aMap是地图控制器对象。
//        aMap.setMapType(AMap.MAP_TYPE_NAVI);// 设置导航地图模式，aMap是地图控制器对象。
//        aMap.setMapType(AMap.MAP_TYPE_NORMAL);//设置白昼地图模式，aMap是地图控制器对象。


    /**
     * 设置地图底图语言，目前支持中文底图和英文底图
     *
     * @param language AMap.CHINESE 表示中文，即"zh_cn", AMap.ENGLISH 表示英文，即"en"
     * @since 5.5.0
     */
//    public void setMapLanguage(String language)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
//            moveMarker(currentLatLng, data.getParcelableExtra("location"));
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    aMap.moveCamera(CameraUpdateFactory.newLatLng(data.getParcelableExtra("location")));
//                }
//            }, 2000);
            homing(data.getParcelableExtra("location"));
        }

    }

    //归位方法
    public void homing(LatLng latLng) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, currentZoom));//设置地图中心点
        locationMarker.remove();
        showGatherMarker(currentaMapLocation);
    }

    public void allocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
            //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
            //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);

            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(false);

            //获取最近3s内精度最高的一次定位结果：
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
            // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            mLocationOption.setOnceLocationLatest(false);

            //SDK默认采用连续定位模式，时间间隔2000ms。如果您需要自定义调用间隔：
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            mLocationOption.setInterval(2000);

            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);

            //设置是否允许模拟位置,默认为true，允许模拟位置
            mLocationOption.setMockEnable(true);

            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(20000);

            //缓存机制默认开启，可以通过以下接口进行关闭。
            //当开启定位缓存功能，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存，
            // 不区分单次定位还是连续定位。GPS定位结果不会被缓存
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);

            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();

        }
    }
}
