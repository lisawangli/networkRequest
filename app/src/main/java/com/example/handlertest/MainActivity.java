package com.example.handlertest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.handlertest.http.BannerData;
import com.example.handlertest.http.IJsonDataListener;
import com.example.handlertest.http.NetUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int student_size = 2;
    private IStudentManager mRemoteManager;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IStudentManager iStudentManager = IStudentManager.Stub.asInterface(service);
            mRemoteManager = iStudentManager;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteManager = null;
            Log.e("MainActivity","onServiceDisconnected:"+Thread.currentThread().getName());
        }
    };

    String url = "http://t.weather.sojson.com/api/weather/city/101220101";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetUtil.sendJsonrequest(url,"","GET",BannerData.class,new IJsonDataListener<BannerData>(){

            @Override
            public void onSuccess(BannerData o) {
                if (o!=null)
                Log.e("MainActivity",o.getCityInfo().getCity()+"====o="+o.toString());
            }

            @Override
            public void onFailure() {

            }
        });
//        Intent intent = new Intent(this,StudentManagerService.class);
//        bindService(intent,connection,BIND_AUTO_CREATE);
//        findViewById(R.id.txt1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getStudentList();
//            }
//        });
//        findViewById(R.id.txt2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addStudent();
//            }
//        });
    }

    void addStudent(){
        try {
            mRemoteManager.addStudent(new Student(student_size+1,"大话","male"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void getStudentList(){
        Toast.makeText(this,"正在获取学生列表",Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Student> studentList = mRemoteManager.getStudentList();
                    student_size = studentList.size();
                    Log.e("MainActivity","获取到的学生列表："+studentList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
