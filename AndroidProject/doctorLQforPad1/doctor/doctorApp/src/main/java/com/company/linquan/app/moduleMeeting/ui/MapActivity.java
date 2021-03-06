package com.company.linquan.app.moduleMeeting.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.moduleMeeting.MeetingInterface;
import com.company.linquan.app.moduleMeeting.impl.MapPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.DividerItemDecoration;
import com.company.linquan.app.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 2018/6/6.
 */

public class MapActivity extends BaseActivity implements MeetingInterface.MapInterface,View.OnClickListener
, GeocodeSearch.OnGeocodeSearchListener,PoiSearch.OnPoiSearchListener,Inputtips.InputtipsListener {

    private Dialog myDialog;
    private MapView mapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private String lon,lat,address,room;
    private DelEditText searchBar;

    //??????AMapLocationClient?????????
    public AMapLocationClient mLocationClient = null;
    //??????AMapLocationClientOption??????
    public AMapLocationClientOption mLocationOption = null;
    Bundle bundle;

    private GeocodeSearch search;
    private GeocodeQuery query;
    private PoiSearch poiSearch;
    private PoiSearch.Query poiQuery;
    private InputtipsQuery inputtipsQuery;

    private String city;
    private BitmapDescriptor markBitmap;
    private Marker marker;

    private RecyclerView recyclerView;
    private ArrayList<Tip> addresses;
    private RecordListAdapter adapter;
    private MapPresenterImp presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        ExitApp.getInstance().addActivity(this);		// add Activity ????????????
        requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????????????????????
        setContentView(R.layout.activity_map);
        initHead();
        initView();
        judgePermission();
        setListener();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
        titleTV.setText("??????");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){

        presenter = new MapPresenterImp(this);

        searchBar = (DelEditText)findViewById(R.id.map_search);
        searchBar.setHint("??????????????????????????????");
        searchBar.setSingleLine();
        findViewById(R.id.map_btn).setOnClickListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.map_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        addresses = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),addresses);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getDrawable(this,R.drawable.shape_list_line_style)));
    }

    private void judgePermission(){
        initMap(bundle);
    }


    private void initMap(Bundle savedInstanceState) {
        //????????????????????????
        mapView = (MapView) findViewById(R.id.map_view);
        //???activity??????onCreate?????????mMapView.onCreate(savedInstanceState)???????????????
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        startLocation();
        initSearch();
    }

    private void startLocation(){
        //???????????????
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //?????????AMapLocationClientOption??????
        mLocationOption = new AMapLocationClientOption();
        //?????????????????????AMapLocationMode.Hight_Accuracy?????????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //???????????????????????????
        //??????????????????false???
        mLocationOption.setOnceLocation(true);
        //????????????????????????????????????????????????????????????
        mLocationOption.setNeedAddress(true);
        //??????????????????
        mLocationOption.setLocationCacheEnable(false);
        //????????????????????????
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation.getErrorCode() == 0) {
                    //??????????????????amapLocation?????????????????????
                    lon = aMapLocation.getLongitude()+"";
                    lat = aMapLocation.getLatitude()+"";
                    Log.i("setLocationListener","getLongitude="+lon+"   getLatitude="+lat);
                    city = aMapLocation.getCity();//????????????
//                    aMapLocation.getProvince();//?????????
//                    aMapLocation.getCity();//????????????
//                    aMapLocation.getDistrict();//????????????
//                    aMapLocation.getStreet();//????????????
//                    aMapLocation.getStreetNum();//?????????????????????
                    address = aMapLocation.getProvince()+aMapLocation.getCity()+aMapLocation.getDistrict()
                            +aMapLocation.getStreet()+aMapLocation.getStreetNum();
                    searchBar.setText(address);
                    searchBar.setLastFocus();
                    initMarker(address,new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()));
                    moveMap(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()));
                }
            }
        });

        //??????????????????????????????????????????
        mLocationClient.setLocationOption(mLocationOption);
        //????????????
        mLocationClient.startLocation();
    }

    private void moveMap(LatLng point){
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(point,15,0,0));
        aMap.animateCamera(mCameraUpdate);
    }

    private void initSearch(){
        //??????1??????????????????
//        search = new GeocodeSearch(this);
//        search.setOnGeocodeSearchListener(this);
        Log.i("initSearch","keyWords="+searchBar.getText()+"   city="+city);
        //??????2???poi ???????????????
//        poiQuery = new PoiSearch.Query(searchBar.getText(), "", city);
//        poiQuery.setPageNum(10);
//        poiQuery.setPageSize(1);
//        poiSearch = new PoiSearch(this,poiQuery);
//        poiSearch.setOnPoiSearchListener(this);
//        poiSearch.searchPOIAsyn();

        //??????3???????????????
        inputtipsQuery = new InputtipsQuery(searchBar.getText(),city);
        inputtipsQuery.setCityLimit(false);//?????????????????????
        Inputtips inputTips = new Inputtips(MapActivity.this, inputtipsQuery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    private void initMarker(String address, LatLng latLng){
        if (null == marker){
            markBitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.drawable.img_location_mark));
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.title(address).snippet(address);
            markerOption.position(latLng);
            markerOption.draggable(false);//??????Marker?????????
            markerOption.icon(markBitmap);
            marker = aMap.addMarker(markerOption);
        }
        marker.setTitle(address);
        marker.setPosition(latLng);
    }

    private void setListener() {

        searchBar.setOnEditTextDataChanged(new DelEditText.OnEditTextDataChanged() {
            @Override
            public void onTextIsEmpty() {
            }

            @Override
            public void onTextChanged() {
                if ("".equals(searchBar.getText())){
                    showToast("???????????????????????????");
                    return;
                }

                if (address.equals(searchBar.getText())){
                    return;
                }

                Log.i("onClick",searchBar.getText());
                initSearch();
            }
        });


        aMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if (null != marker.getPosition()){
                    RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(marker.getPosition().latitude
                    ,marker.getPosition().longitude), 200,GeocodeSearch.AMAP);
                    search.getFromLocationAsyn(query);
                }
            }
        });


        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
//                PoiItem address = addresses.get(position);
//                Log.i("onGeocodeSearched","getBusinessArea="+address.getBusinessArea()+",getDirection="+address.getDirection()+",getDistance="+address.getDistance()+",getTitle="+address.getTitle()
//                        +",getSnippet="+address.getSnippet()+",getTypeDes="+address.getTypeDes()+",getIndoorData="+address.getIndoorData()
//                        );
//                MapActivity.this.address = address.getBusinessArea();
//                searchBar.setText(address.getTitle());
//                lat = address.getLatLonPoint().getLatitude()+"";
//                lon = address.getLatLonPoint().getLongitude()+"";
//                initMarker(address.getBusinessArea()
//                        ,new LatLng(address.getLatLonPoint().getLatitude(),address.getLatLonPoint().getLongitude()));
//                moveMap(new LatLng(address.getLatLonPoint().getLatitude()
//                        ,address.getLatLonPoint().getLongitude()));


                Tip address = addresses.get(position);
                Log.i("onGeocodeSearched","getAddress="+address.getAddress()+",getDistrict="+address.getDistrict()+",getName="+address.getName()
                );
                if (null == address.getPoint()){
                    showToast("??????????????????");
                    return;
                }
                MapActivity.this.address = address.getName();
                searchBar.setText(address.getName());
                room = address.getAddress();
                lat = address.getPoint().getLatitude()+"";
                lon = address.getPoint().getLongitude()+"";
                Log.i("setOnItemClickListener","getLongitude="+lon+"   getLatitude="+lat);
                initMarker(address.getName()
                        ,new LatLng(address.getPoint().getLatitude(),address.getPoint().getLongitude()));
                moveMap(new LatLng(address.getPoint().getLatitude()
                        ,address.getPoint().getLongitude()));
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map_btn){
            Intent intent = new Intent();
            intent.putExtra("address",address);
            intent.putExtra("lon",lon);
            intent.putExtra("lat",lat);
            setResult(RESULT_OK,intent);
            finish();
//            presenter.addAddress(address,room,lat,lon);
        }
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null){
            mLocationClient.stopLocation();
            mLocationClient = null;
        }
        super.onDestroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()???????????????
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView.onResume ()???????????????????????????
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView.onPause ()????????????????????????
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //???activity??????onSaveInstanceState?????????mMapView.onSaveInstanceState (outState)??????????????????????????????
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000 && null != regeocodeResult){
            RegeocodeAddress address =  regeocodeResult.getRegeocodeAddress();
            List<PoiItem> poiItems = address.getPois();
            if (poiItems.size() > 0){
                PoiItem item = poiItems.get(0);
                Log.i("onRegeocodeSearched","Title="+item.getTitle()+",Distance="+item.getDistance()+",AdName="+item.getAdName()+",Tel"+item.getTel()
                        +",BusinessArea="+item.getBusinessArea()+",Snippet="+item.getSnippet()+",Direction"+item.getDirection()
                        +",TypeDes"+item.getTypeDes());
                this.address = item.getSnippet()+item.getTitle();
                searchBar.setText(this.address+"??????");
                searchBar.setLastFocus();
                lat = poiItems.get(0).getLatLonPoint().getLatitude()+"";
                lon = poiItems.get(0).getLatLonPoint().getLongitude()+"";
           }else{
                showToast("????????????????????????");
           }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if (i == 1000 && null != geocodeResult ){
            List<GeocodeAddress> list =  geocodeResult.getGeocodeAddressList();
            int size = list.size();
            Log.i("onGeocodeSearched","size="+size);
            if (size > 0){
                recyclerView.setVisibility(View.VISIBLE);
            }else{
                recyclerView.setVisibility(View.GONE);
                showToast("????????????"+searchBar.getText()+"????????????!");
            }
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
//        if (i == 1000 && null != poiResult){
//            ArrayList<PoiItem> poiItems = poiResult.getPois();
//            Log.i("onPoiSearched","size="+poiItems.size());
//            if (poiItems.size()>0){
//                recyclerView.setVisibility(View.VISIBLE);
//                addresses = poiItems;
//                adapter.setList(addresses);
//            }else{
//                recyclerView.setVisibility(View.GONE);
//            }
//        }else{
//            recyclerView.setVisibility(View.GONE);
//            Log.i("onPoiSearched","onPoiSearched="+i);
//        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == 1000){
            Log.i("onGetInputtips","size="+list.size());
            if (list.size()>0){
                recyclerView.setVisibility(View.VISIBLE);
                addresses = (ArrayList<Tip>) list;
                adapter.setList(addresses);
            }else{
                recyclerView.setVisibility(View.GONE);
            }
        }else{
            recyclerView.setVisibility(View.GONE);
            Log.i("onPoiSearched","onPoiSearched="+i);
        }
    }

    /**
     * recycler ?????????
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<Tip> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<Tip> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<Tip> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_select_data,parent,false);
            return new RecordViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof RecordViewHolder){
                initView((RecordViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(RecordViewHolder handler, Tip bean){
            if (bean == null) return;
            handler.titleTV.setText(bean.getName());
            handler.contentTV.setText(bean.getAddress());
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * ?????????
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView titleTV;
        public MyTextView contentTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            titleTV = (MyTextView) view.findViewById(R.id.list_item_data);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
            contentTV.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
        }
    }

    @Override
    public void finishActivity() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDialog() {
        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(this);
    }
    @Override
    public void dismissDialog() {
        if (myDialog != null) myDialog.dismiss();
    }

    @Override
    public void showToast(String txt) {
        MyToast.showToast(this,txt, Toast.LENGTH_SHORT);
    }
}
