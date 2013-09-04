package com.yeho.androidchat;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


public class Splash extends Activity {

	
	int elementos = 0;
	int cont = 0;
	private ImageView mMain;
	
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

	 
	 private int id;
	 SQLiteDatabase db = null;
	 String uri2;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

            	if (isInternet()) {
            	}
                lanzar(uri2);

            }
        }, 3000);
	}
	
	
	
	 public void lanzar(String uri2) {
	        Intent i = new Intent(this, LoginActivity.class);
	        i.putExtra("splash", uri2);
	        startActivity(i);
	    }
		
	
	public boolean isInternet() {

		boolean enabled = true;

		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();

		if ((info == null || !info.isConnected() || !info.isAvailable())) {
			enabled = false;
		}
		return enabled;
	}
	
	
	

}


