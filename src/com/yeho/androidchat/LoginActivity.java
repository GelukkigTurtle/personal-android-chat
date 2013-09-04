package com.yeho.androidchat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.yeho.androidchat.database.SQLiteHelper;
import com.yeho.personalmsj.R;


public class LoginActivity extends Activity {

	private static final String TAG = LoginActivity.class.getSimpleName();
	
	EditText userText;
	EditText passWord;
	Activity activity;
	ProgressBar progress;
	View view;
	String uri2;

	private SQLiteDatabase db;
	final Handler mHandler = new Handler();

	// private String[] wsResult = null;
		private JSONObject json;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//EOA
		Bundle bundle = getIntent().getExtras();
		uri2 = bundle.getString("splash");
		Drawable background = null;
		//Se obtiene el splash
		
		
		setContentView(R.layout.login);
		
			
		view = findViewById(android.R.id.content);
		getActionBar().show();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		

		userText = (EditText) findViewById(R.id.etextusuario);
		passWord = (EditText) findViewById(R.id.etextpassword);

		
		if (!isInternet()){
			Toast.makeText(this, "No tienes una conexion activa a internet, intenta de nuevo mas tarde", Toast.LENGTH_LONG).show();
		}
		
		passWord.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					lanzar(view);
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return menuSelecciona(item);
	}

	private boolean menuSelecciona(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		userText.setText("");
		passWord.setText("");
		progress.setVisibility(View.INVISIBLE);
	}

	public void lanzar(View view) {

		Log.v("LOGIN_ACTIVITY", "INICIA METODO lanzar");
		if (userText.getText().toString().equals("") || passWord.getText().toString().equals("")) {
			Toast.makeText(LoginActivity.this,
					"Favor de capturar el usuario y el password",
					Toast.LENGTH_SHORT).show();
			return;
		}
	
		progress.setVisibility(0);

		// //// CALL LOGIN WEB SERVICE //////
//		if (isInternet()) {
			Log.v("LOGIN_ACTIVITY", "VERIFICAR CREDENCIALES A TRAVES DEL WEB SERVICE.");
			callWS();
//		} else {
	
		
				if (true) {
					Log.v("LOGIN_ACTIVITY", "LOGIN OK.");
					//String displayname = getDisplay(UserData.getUser()); //YEHO TODO aqui tengo que poner la consulta a la BD
					Log.v("YEHO", "EL DIsPLAYNAME Q MANDO AL HOME 2: "); //aqui hay q sacarlo de la BD
					String displayname = userText.getText().toString();
					
					startHome(displayname);
				}
	/*	
				Context context = getApplicationContext();
				CharSequence text = "Usuario o contraseña invalido.";
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				return;
	*/	
		}

	

	public String getDisplay(String usu) {
		String name = "";
		SQLiteHelper usdbh = new SQLiteHelper(this, "berrendo.sqlite", null, 1);
		db = usdbh.getWritableDatabase();
		Cursor c = db.rawQuery( "SELECT displayname from usuarios where usuario='" + usu + "'", null);
		// Nos aseguramos de que existe al menos un registro
		int cont = 0;
		String estado = null;
		if (c.moveToFirst()) {
			do {
				name = c.getString(0);
				cont++;
			} while (c.moveToNext());
		}
		db.close();
		return name;
	}
	
	public boolean isInternet() {

		boolean enabled = true;

		ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();

		if ((info == null || !info.isConnected() || !info.isAvailable())) {
			enabled = false;
		}
		return enabled;
	}

	public void borrar(View view) {

		userText.setText("");
		passWord.setText("");

	}

	public void startHome(String displayName) {
		Intent i = new Intent(this, Activity_Home.class);
		i.putExtra("displayName",displayName);
		startActivity(i);
	}
/*
	private boolean auth() { 

		boolean retorno = false;

		SQLiteHelper usdbh = new SQLiteHelper(this, "berrendo.sqlite", null, 1);
		SQLiteDatabase db = usdbh.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT password FROM usuarios WHERE usuario='" + userText.getText().toString() + "' ", null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya mas registros
			do {
				String password = c.getString(0);
				Log.v("LOGIN_ACTIVITY", "PWD FROM DB[" + password + "]");
				Util u = new Util();

				String passwmd5 = u.md5(passWord.getText().toString());
				Log.v("LOGIN_ACTIVITY", "PWD MD5[" + passwmd5 + "]");

   	          if (password.equals(passWord.getText().toString()))
   	          {
   	        	  retorno = true;
   	          }
			//	retorno = passwmd5.equals(password);
			} while (c.moveToNext());
		}

		c.close();
		return retorno;

	}
*/
	protected void callWS() {
		Thread t = new Thread() {
			@Override
			public void run() {

				// wsResult = null;
				json = null;

				ClientWS client = ClientWS.getInstance();

				try {
					//wsResult = client.login(userText.getText().toString(),
					//		passWord.getText().toString());
					String usuario = userText.getText().toString();
					String password = passWord.getText().toString();
					
					json = client.login(usuario, password);

					mHandler.post(stopProgress);

				} catch (SocketTimeoutException socketTimeoutException) {
					mHandler.post(stopProgress);
					Log.e(TAG, "ERROR: SocketTimeoutException::::::");
					// e.printStackTrace();
				} catch (IOException ioException) {

					mHandler.post(stopProgress);
					Log.e(TAG, "ERROR:IOException::[" + ioException.getMessage() + "::");

				} catch (XmlPullParserException xmlPullParserException) {
					mHandler.post(stopProgress);
					Log.e(TAG, "ERROR: XmlPullParserException::::::");
					// e.printStackTrace();
				} catch (NumberFormatException numberFormatException) {
					mHandler.post(stopProgress);
					Log.e(TAG, "ERROR: NumberFormatException::[" + numberFormatException.getMessage() + "::]");
					// e.printStackTrace();
				} catch (JSONException jsonException) {
					Log.e(TAG, "ERROR: JSONException::[" + jsonException.getMessage() + "]::");
					// e.printStackTrace();
				} catch (Exception exception) {
					mHandler.post(stopProgress);
					Log.e(TAG, "ERROR: Exception::[" + exception.getMessage() + "]::");
					// e.printStackTrace();
				}

			}
		};
		t.start();
	}

	final Runnable stopProgress = new Runnable() {

		// @Override
		public void run() {

			progress.setVisibility(4);

			// if (wsResult == null || wsResult.length < 1) {
						if (json == null) {
							Context context = getApplicationContext();
							CharSequence text = "No se pudo validar su usuario con el servidor.";
							int duration = Toast.LENGTH_SHORT;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
							return;
						}

						try { 
							String status;
							if(json.getString("success") != null) 
								status = json.getString("success");
							else 
								status = "0";
							Log.v(TAG, "STATUS LOGIN ["+status+"]");
							
							if (Integer.parseInt(status) == 1) {
								Log.v(TAG, "LOGIN SUCCESSFUL");
								Util u = new Util();

							     JSONObject user = json.getJSONObject("user");
							     
							     String pass = user.getString("password");
							     String passwmd5 = u.md5(pass);
							     Log.v(TAG, "GUARDA MD5_PWD["+passwmd5+"]");
//							     usuario.setId(Integer.valueOf(json.getString("uid")));
	//						     usuario.setUsuario(user.getString("username"));
		//					     usuario.setPassword(passwmd5); // GUARDAR PWD CON MD5.
							     String displayName = user.getString("name");
			//				     usuario.setDisplayName(displayName);
							
						 
					Log.v("YEHO", "EL DIsPLAYNAME Q MANDO AL HOME 1: " + displayName);
					startHome(displayName);
				} else {
					Context context = getApplicationContext();
					CharSequence text = "Usuario o contrasena invalido.";
					int duration = Toast.LENGTH_LONG;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					return;
				}
			} catch (Exception exception) {
				Log.e("LOGIN_ACTIVITY", "ERROR: Exception::[" + exception.getMessage() + "]");
				Context context = getApplicationContext();
				CharSequence text = "No se pudo validar su usuario con el servidor.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

				return;
			}
		}
	};
}

class Util {
	public String md5(String string) {
		if (string == null || string.trim().length() < 1) {
			return null;
		}
		try {
			return getMD5(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private String getMD5(byte[] source) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			StringBuffer result = new StringBuffer();
			for (byte b : md5.digest(source)) {
				result.append(Integer.toHexString((b & 0xf0) >>> 4));
				result.append(Integer.toHexString(b & 0x0f));
			}
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
