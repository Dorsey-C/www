package com.example.cient;

import com.example.service.ImusicService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button mButton;
	private ImusicService mservice;
	private ServiceConnection conn = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			 Log.i("ServiceConnection", "onServiceConnected() called"); 
			mservice = ImusicService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i("ServiceConnection", "onServiceDisconnected() called");
		
			mservice = null;
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mButton = (Button) findViewById(R.id.button1);
		
		Intent intent = new Intent();
        intent.setAction("android.intent.action.MUSIC_PLAYER");
        bindService(intent, conn, BIND_AUTO_CREATE); 
	
		
		mButton.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                
                try {
					mservice.playMusic();
				} catch (RemoteException e) {
					e.printStackTrace();
				} 
                  
            }  
        });  
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(conn != null){
			unbindService(conn);
		}
	}
}
