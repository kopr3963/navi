package com.example.mac.navi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mac.service.NaviService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;



public class MainActivity extends Activity {

    String TAG = "MainActivity";

    public boolean service_State = true;
    public int time = 15;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      티맵 패키지명 : com.skt.skaf.l001mtm091
//      베가 티맵 패키지명 : com.skt.tmap.ku

        //NaviService  start
        final Intent service = new Intent(MainActivity.this, NaviService.class);
        startService(service);
        NaviService naviService = new NaviService();


        Button btn_Settings = (Button) findViewById(R.id.btn_settings);

        btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

//        /data/data/com.androidhuman.app/files/filename.ext
//        File storeDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "setting.txt");



        File ff = new File("");


//        File file = new File("/data/data/com.example.mac.navi/cache/");
        File file = new File ("/data/data/com.example.mac.navi/lib");
        String[] array = file.list();
        if ( array.length >0 ) {
            for (String obj : array) {
                Log.i(TAG, "result : " + obj);
            }
        }
        Log.i(TAG, "is File ? "+file.isFile());
        Log.i(TAG, "file.list.length : " +array.length );
//        Log.i(TAG, "array[0]"+array[0]);

//        Log.i(TAG, "file.list() " + file.list());
        MainActivity activity = new MainActivity();
        Log.i(TAG, "File Can Read ? : "+ file.canRead());


//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//                Log.i(TAG, "bufferedReader : " + line);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null)
//                try {
//                    br.close();
//                } catch (IOException e) {
//                }
//        }
        //갤럭시 win
//        Intent intent = getPackageManager().getLaunchIntentForPackage("com.skt.skaf.l001mtm091");
//        startActivity(intent);



        //부기형 티맵 패키지명
        //Intent intent = getPackageManager().getLaunchIntentForPackage("com.skt.tmap.ku");
        //startActivity(intent);

        //loot access check code
        //Intent intent = getPackageManager().getLau

        TextView rooting_text = (TextView) findViewById(R.id.text_rootingCheck);
        try {
            Runtime.getRuntime().exec("su");
            rooting_text.setText("루팅 됨");
        } catch (Exception e) {
            rooting_text.setText("루팅 안됨");
        }

//        try{
//            Log.i(TAG, "onCreate: $$$$$$$$$$$$$$$$");
//            Runtime.getRuntime().exec("ls");
//            Log.i(TAG, "onCreate: $$$$$$$$$$$$$$$$");
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        TextView timeout = (TextView) findViewById(R.id.timeout);
        ApplicationInfo  info ;

        PackageManager pm = this.getPackageManager();
        List<ApplicationInfo> packs = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        List<PackageInfo> packs2 = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

//        ApplicationInfo test = packs.get(0);

        ApplicationInfo id = new ApplicationInfo();
        id = packs.get(0);

//        Log.i(TAG, "state : "+ service_State);
//        Log.i(TAG, "onCreate: "+ id.loadLabel(pm));
//        Log.i(TAG, "onCreate: " + packs.get(0));
//        Log.i(TAG, "onCreate: " + test.loadLabel(pm));

//        Log.i(TAG, "getApplicationonLabel() : " + getPackageManager().getApplicationLabel(test));

//        Drawable dr = null;



        ImageView iv = (ImageView)findViewById(R.id.imageView);
//        iv.setImageIcon(android.graphics.drawable.BitmapDrawable@41d6d878);
//        iv.setImageURI(android.graphics.drawable.BitmapDrawable@41d6d878);
//        iv.setImageDrawable(packs.get(0).loadIcon(pm));
//        iv.setVisibility(View.VISIBLE);


        final TextView text_state = (TextView) findViewById(R.id.service_state);

        text_state.setText(getState() ? "시작":"중단");

        final Button btn_state = (Button) findViewById(R.id.btn_state);
        if (getState()){
            btn_state.setText("종료");
//            stopService(service);
        }else {
            btn_state.setText("시작");
//            startService(service);
        }
        btn_state.setOnClickListener(new View.OnClickListener() { //이벤트
            @Override
            public void onClick(View view) {
//                stopService(service);
                if( getState()){ // 시작중이면
                    Toast.makeText(MainActivity.this, "종료하겠습니다.", Toast.LENGTH_SHORT).show();
                    setState(false);
                    btn_state.setText("시작");
                    text_state.setText("중단");
                    stopService(service);

                }else{ //종료면
                    Toast.makeText(MainActivity.this, "시작하겠습니다", Toast.LENGTH_SHORT).show();
                    setState(true);
                    btn_state.setText("종료");
                    text_state.setText("시작");
                    startService(service);
                }
            }
        });
//      ApplicationInfo asdf = packs;



//        Drawable dr = packs.get(10).loadIcon(pm);
//        Log.i(TAG, "onCreate: "+dr);


        for ( ApplicationInfo app : packs){
//            Log.i(TAG, "TEST : "+ app.loadLabel(pm).toString() + "icon : ");
        }



        int size = 0;
        for (int i = 0; i < packs.size(); i++) {
            if (packs.get(i).className != null) {
//                Log.i(TAG, "list " + packs.get(i).className);
            }

        }
//        Log.i(TAG, "size : " + size);

//        String name = packs.get(0).className;
//        packs.get(0).manageSpaceActivityName;


//        String name = getPackageManager().getInstallerPackageName("com.skt.skaf.l001mtm091");
//        Log.i(TAG, "application name : " + name);


        ;





        /*
            패키지 확인할 때 쓰는 코드   저장된 패키지명을 전부 뿌려줌
         */
//        activity = this;
        //PackageManager packageName = activity.getPackageManager();
        //List<PackageInfo> installList = packageName.getInstalledPackages(0);
        //for(int i = 0 ; i < installList.size() ; i++ ) {
        //    Log.d("PackageManager", installList.get(i).packageName);
        //}
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    //    private void getPackageList() {
//        PackageManager pm = this.getPackageManager(PackageInfo.GET_META_DATA);
//        List<PackageInfo> packs =  getPackageManager().getInstalledPackages(PackageManager.PERMISSION_GRANTED);
//        for (PackageInfo pack : packs) {
//            Log.d("TAG", "| name    : " + pack.packageName);
//            Log.d("TAG", "| package : " + pack.packageName);
//            Log.d("TAG", "| version : " + pack.versionName);
//        }
//    }


    public void setState (boolean service_State){
        this.service_State= service_State;
    }
    public boolean getState(){
        return this.service_State;
    }

    public void setTimeout ( int time){
        this.time = time;
    }
    public int getTimeout (){
        return this.time;
    }
}
