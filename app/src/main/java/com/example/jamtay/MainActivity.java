package com.example.jamtay;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    FragmentNavigator mFragmentNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.side_nav_bar));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();
        mFragmentNavigator = new FragmentNavigator(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void prepareMenuData() {
        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray menu = json.getJSONArray("menu");
            for (int i = 0;i < menu.length();i++){
                JSONObject menuObj = menu.getJSONObject(i);
                MenuModel menuModel = new MenuModel(menuObj.getString("name"), menuObj.getBoolean("isGroup"),
                        menuObj.getBoolean("hasChildren"), getDrawableFromString(menuObj.getString("icon")));
                headerList.add(menuModel);

                if(!menuObj.getBoolean("hasChildren")){
                    childList.put(menuModel, null);
                }else{
                    JSONArray subMenu = menuObj.getJSONArray("subMenu");
                    List<MenuModel> childModelsList = new ArrayList<>();
                    for(int j = 0; j< subMenu.length(); j++){
                        MenuModel childModel = new MenuModel(subMenu.getString(j), false, false, null);
                        childModelsList.add(childModel);
                    }
                    childList.put(menuModel, childModelsList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Drawable getDrawableFromString(String name){
        int id  = getResources().getIdentifier(name, "drawable", getPackageName());
        return getResources().getDrawable(id);
    }

    public String loadJSONFromAsset() {
        String json = null;
        InputStream is = null;
        try {
            is = getAssets().open("menu.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return "{}";
        }
        return json;

    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        Log.e(Constansts.TAG, headerList.get(groupPosition).menuName);
                        mFragmentNavigator.switchFragmentByTag(headerList.get(groupPosition).menuName);
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

//                if (childList.get(headerList.get(groupPosition)) != null) {
//                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
//                    if (model.url.length() > 0) {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(model.url);
//                        onBackPressed();
//                    }
//                }

                return false;
            }
        });
    }
}
