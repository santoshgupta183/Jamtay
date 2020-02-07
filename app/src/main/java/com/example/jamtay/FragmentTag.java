package com.example.jamtay;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        FragmentTag.DASHBOARD_FRAG,
        FragmentTag.SEARCH_FRAG,
        FragmentTag.MY_PROFILE_FRAG,
        FragmentTag.MESSAGE_FRAG,
})
public @interface FragmentTag {
    String DASHBOARD_FRAG = "Dashboard";
    String SEARCH_FRAG = "searchFragment";
    String MY_PROFILE_FRAG = "myProfileFragment";
    String MESSAGE_FRAG = "messageFragment";
}
