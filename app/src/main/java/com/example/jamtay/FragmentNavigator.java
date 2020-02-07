package com.example.jamtay;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.jamtay.ui.dashboard.DashboardFragment;

public class FragmentNavigator {
    private Activity mActivity;
    FragmentManager mFragmentManager;

    public FragmentNavigator(Activity mActivity) {
        this.mActivity = mActivity;
        this.mFragmentManager = ((MainActivity)mActivity).getSupportFragmentManager();
    }

    public void switchFragmentByTag(String fragTag){
        mFragmentManager.beginTransaction()
        .replace(R.id.nav_host_fragment, findFragmetByTag(fragTag), fragTag)
        .commit();
    }

    private Fragment findFragmetByTag(String fragTag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(fragTag);
        if(fragment==null){
            switch (fragTag){
                case FragmentTag.DASHBOARD_FRAG:
                    return new DashboardFragment();
                case FragmentTag.MY_PROFILE_FRAG:
                    break;
                case FragmentTag.SEARCH_FRAG:
                    break;
                case FragmentTag.MESSAGE_FRAG:
                    break;
            }
        }
        return null;
    }
}
