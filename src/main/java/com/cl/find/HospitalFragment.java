package com.cl.find;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.Poi;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/12 0012.
 */

public class HospitalFragment extends BaseFragment implements OnGetPoiSearchResultListener, SwipeRefreshLayout.OnRefreshListener{

    private View mLayout;
    private RecyclerView lv;
    private List<PoiInfo> list;
    private HospitalAdapter adapter;
    private SharedPreferences sharedprefrence;
    private ProgressDialog loaddialog;
    private PoiSearch poiSearch;
    private  int page=1;
    private SwipeRefreshLayout mRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedprefrence=getActivity().getSharedPreferences("loction",Context.MODE_PRIVATE);
        loaddialog=new ProgressDialog(getActivity());
        loaddialog.setCanceledOnTouchOutside(false);
        loaddialog.setMessage("正在获取附近医院信息");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout=inflater.inflate(R.layout.fragment_hospital,container,false);
        return mLayout;
    }

    @Override
    public void lazyLoad() {
       initView();
        initData();
    }

    private void initView() {
        mRefresh= (SwipeRefreshLayout) mLayout.findViewById(R.id.refresh);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(this);
        lv= (RecyclerView) mLayout.findViewById(R.id.fragment_hospital_lv);
        list=new ArrayList<PoiInfo>();
        adapter=new HospitalAdapter(getActivity(),list);
        lv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        lv.setItemAnimator(new DefaultItemAnimator());
        lv.setAdapter(adapter);


    }

    private void initData(){
        loaddialog.show();
        poiSearch=PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        poiSearch.searchInCity(new PoiCitySearchOption().city(sharedprefrence.getString("city","北京")).keyword("医院").pageNum(page++).pageCapacity(20).isReturnAddr(true));

    }

    public static HospitalFragment getInstance() {
        return new HospitalFragment();
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (loaddialog.isShowing())loaddialog.dismiss();
        if (mRefresh.isRefreshing())mRefresh.setRefreshing(false);
        if (poiResult!=null&&!poiResult.getAllPoi().isEmpty()){
            list.addAll(0,poiResult.getAllPoi());

            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
              if (loaddialog.isShowing())loaddialog.dismiss();
                if (poiDetailResult.error== SearchResult.ERRORNO.NO_ERROR){
                   Intent intent=new Intent(getContext(),HospitalDetailActivity.class);
                   intent.putExtra("url",poiDetailResult.detailUrl);
                    startActivity(intent);
                }else{
                    Snackbar.make(lv,"未查询到详细信息"+poiDetailResult.error,Snackbar.LENGTH_LONG).show();
                }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (poiSearch!=null)
        poiSearch.destroy();

    }

    @Override
    public void onRefresh() {
        poiSearch.searchInCity(new PoiCitySearchOption().city(sharedprefrence.getString("city","北京")).keyword("医院").pageNum(page++).pageCapacity(20).isReturnAddr(true));

    }



    class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.MyHolder>{
        private final List<PoiInfo> data;
        private final Context context;

        HospitalAdapter(Context context , List<PoiInfo> data){
              this.data=data;
            this.context=context;
        }
       @Override
       public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View itemview= LayoutInflater.from(context).inflate(R.layout.hospital_item,parent,false);
           return new MyHolder(itemview);
       }

       @Override
       public void onBindViewHolder(MyHolder holder, final int position) {
           holder.tv_name.setText(data.get(position).name);
           holder.tv_address.setText("地址:"+data.get(position).address);
           holder.tv_phone.setText("急救电话:"+data.get(position).phoneNum);
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (mRefresh.isRefreshing())return;
                 loaddialog.show();
                  poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(data.get(position).uid));
             }
         });
       }

       @Override
        public long getItemId(int position) {
            return position;
        }

       @Override
       public int getItemCount() {
           return data.size();
       }



        class MyHolder extends RecyclerView.ViewHolder{

            private final TextView tv_name;
            private final TextView tv_address;
            private final TextView tv_phone;

            public MyHolder(View itemView) {
                super(itemView);
                tv_name= (TextView) itemView.findViewById(R.id.hospital_item_name);
                 tv_address= (TextView) itemView.findViewById(R.id.hospital_item_address);
                 tv_phone= (TextView) itemView.findViewById(R.id.hospital_item_phone);

            }
        }
    }


}
