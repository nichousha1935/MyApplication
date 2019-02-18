package activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ind4.myapplication.HotFixActivity;
import com.example.ind4.myapplication.NoScrollViewPager;
import com.example.ind4.myapplication.R;

import fragment.AliFragment;
import fragment.BaiduFragment;
import fragment.TengxunFragment;
import fragment.WangyiFragment;
import fragment.WeiruanFragment;
import utils.FixDexUtils;


public class MainActivity extends AppCompatActivity {
    private TabLayout tab_net;
    private NoScrollViewPager viewpage_net;
    private String[] title=new String[]{"腾讯","微软","网易","百度","阿里"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tab_net =  findViewById(R.id.tab_order);
        viewpage_net =  findViewById(R.id.viewpage_order);
        viewpage_net.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewpage_net.setCurrentItem(0);
        tab_net.setupWithViewPager(viewpage_net);
        for (int i=0;i<tab_net.getTabCount();i++){
            tab_net.getTabAt(i).setCustomView(new PagerAdapter(getSupportFragmentManager()).getTabView(i));
        }
       // init();
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override

        public Fragment getItem(int position) {
            if (position == 0) {
                return new TengxunFragment();
            } else if (position == 1) {
                return new WeiruanFragment();
            } else if (position == 2) {
                return new WangyiFragment();
            } else if (position == 3) {
                return new BaiduFragment();
            } else if (position == 4) {
                return new AliFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return title.length;

        }

        @Override
        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "腾讯";
//                case 1:
//                    return "微软";
//                case 2:
//                    return "网易";
//                case 3:
//                    return "百度";
//                case 4:
//                    return "阿里";
//                default:
//                    return "";
//
//
//            }
            return null;
        }

        public View getTabView(int position){
            View view=LayoutInflater.from(MainActivity.this).inflate(R.layout.item_tab,null);
            TextView textView=view.findViewById(R.id.tv_title);
            textView.setText(title[position]);
//            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            },1000);
            return view;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void init(){
        WindowManager windowManager= (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.type=WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format=PixelFormat.RGBA_8888;
        params.gravity=Gravity.RIGHT|Gravity.CENTER_VERTICAL;
        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        TextView textView=new TextView(MainActivity.this);
        textView.setText("悬浮窗");
        textView.setBackgroundColor(Color.parseColor("#FF3499"));
        windowManager.addView(textView,params);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1){
            if (permissions[0].equals(Manifest.permission.READ_CALENDAR)){
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    FixDexUtils.loadFixedDex(this, Environment.getExternalStorageDirectory());
                    startActivity(new Intent(this,HotFixActivity.class));
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
