package com.yeho.androidchat;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeho.androidchat.utility.ImageLoader;

public class Activity_Home extends Activity {

	
	private GridView gridView;
	public String[] desc= null;
	public String[] imagenes= null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new ProductAdapter(this, desc));

//		imagenes = llenagridcontactos();
				
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				try{
					Log.v("YEHO", "Aqui se inicia el chat: "+ position);
			
				}catch(Exception e)
				{
					Log.v("YEHO", "excepcion al dar clic en contacto: "+ position);
				}
			}
});
		
	}



	/*public String[] llenagridcontactos( ){
		SQLiteHelper usdbh = new SQLiteHelper(getActivity(), "androidchat.sqlite", null, 1);
		db = usdbh.getWritableDatabase();
		Cursor c = db.rawQuery("select imagen, nombre from contactos",	null);
		// Nos aseguramos de que existe al menos un registro
		int cont =0;
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya mas registros
			do {					
				cont++;
			} while (c.moveToNext());;
		}
		String[] imagenes = new String[cont];
		desc = new String[cont];
		cont = 0;
		if (c.moveToFirst()) {
			do {
				
				imagenes[cont] = c.getString(0);
				desc[cont] = c.getString(1);
				cont++;
			} while (c.moveToNext());
		}
		
		
		db.close();
		return imagenes;
	}	
	
	*/
	
	public class ProductAdapter extends BaseAdapter {     
	   	
	     final int NumberOfItem = 23;  
	   	 private Context context;  
	   	 private LayoutInflater layoutInflater;
	    private  String[] products;
	   	// private ArrayList<HashMap<String, Object>> products;
	   	 
	     public ImageLoader imageLoader; 

	   	public ProductAdapter(Context context, String[] countries) {
			this.context = context;
			this.products = countries;
			imageLoader=new ImageLoader(context);
		}
	 
	   	 public long getItemId(int position) {   
	   		 return position;  
	   	 }
	    
	   	 public View getView(int position, View convertView, ViewGroup parent) {   
	    		 // TODO Auto-generated method stub  
	    		 View grid = null;
	    		 try{
	    		 if(convertView==null){    
	    			 grid = new View(context);     
	    			 LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    			 grid = vi.inflate(R.layout.item, null);
	    		 }else{
	    			 grid = (View)convertView;    
	    		 }       
	    		 
	    		 TextView name = (TextView)grid.findViewById(R.id.contactName);
	    		 name.setText(products[position].toString());
	    		 ImageView imageView = (ImageView)grid.findViewById(R.id.contact_image);
	    		 imageLoader.DisplayImageThumbnailV2(imagenes[position], imageView);
	    		 }catch(Exception ex){
	    			 Log.v("YEHO displayImage", "excepcion: "+ ex.getMessage());
	    		 }catch(Error err){
	    			 Log.v("YEHO", "Error: "+ err.getMessage());
	    		 }
	    		 return grid;  
	   	 }
	
	   	 public int getCount() {
				// TODO Auto-generated method stub
	   		 	if(products == null)
	   				 return 0;
	   		 	else
	   				// return products.size();
	   		 	 return products.length;
	   	 }
	
	   	 public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
	   	 }
	   	 
}//END ProductAdapter
	

}
