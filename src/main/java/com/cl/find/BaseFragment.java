package com.cl.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * Created by sks on 2017/3/8.
 */

public abstract class BaseFragment extends Fragment {

    boolean oncreatedView=false;
    private boolean isLoaded=false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        oncreatedView=true;
        if (!isHidden()&&!isLoaded){
            Log.i("onViewCreated","lazyLoad");
            lazyLoad();
        }

    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden&&oncreatedView&&!isLoaded){
            Log.i("onHiddenChanged","lazyLoad");
            lazyLoad();
            isLoaded=true;
        }

    }

 public abstract  void lazyLoad();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        oncreatedView=false;
    }



}
