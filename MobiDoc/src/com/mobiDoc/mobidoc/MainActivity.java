package com.mobiDoc.mobidoc;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.mobiDoc.mobidoc.R;
import com.mobiDoc.mobidoc.R.id;
import com.mobiDoc.mobidoc.R.layout;
import com.mobiDoc.mobidoc.R.menu;

import dalvik.system.DexClassLoader;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity<YourActivity> extends ActionBarActivity {
	  final BlockingQueue q1 = new ArrayBlockingQueue<String>(1000);
	  private TextView t;
	@Override
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 t=(TextView)findViewById(R.id.textView2);
		 Class<?>[] params= new Class[]{BlockingQueue.class};
		
		 
		 Toast.makeText(this.getApplicationContext(),"projection is set every 30 sec", 2).show();
		 
		 // inst.init(q1);
		 // Thread qu=new Thread(inst);
		
		  //Toast.makeText(this.getApplicationContext(),"welocme", 1).show();
		 
			 
			
			
			
	        try {
	            final String libPath = Environment.getExternalStorageDirectory() + "/makejar.jar";
	            final File tmpDir = getDir("dex", 0);

	            final DexClassLoader classloader = new DexClassLoader(libPath, tmpDir.getAbsolutePath(), null, this.getClass().getClassLoader());
	            final Class<Object> classToLoad = (Class<Object>) classloader.loadClass("com.example.makejar.test");
	          

	  		

	            final Object myInstance  = classToLoad.newInstance();
	             Method initmeth  = classToLoad.getMethod("init",params);
	             initmeth.setAccessible(true);
	            // final  Thread pp=new Thread((Runnable) myInstance);
	             String res=(String)initmeth.invoke(myInstance,new Object[]{ q1 }); 
	             //Toast.makeText(this.getApplicationContext(),"start the projection Test", 1).show();
	             //t.setText("before executing  : "+res);
	             Method start  = classToLoad.getMethod("start");
	           
	          showToastFromBackground("");
	           start.invoke(myInstance);
	            
	        	
	        	
	        	
	            
	            
	      	    

	      	     

	           // final Method doSomething = classToLoad.getMethod("beepForAnHour");

	           //doSomething.invoke(myInstance);
	           
	        } catch (Exception e) {
	       	 Toast.makeText(this.getApplicationContext(),"error consumer main : "+e.getLocalizedMessage(), 3).show();
	        	
	        
	        }
	      
	    }

	private void showToastFromBackground(final String message) {
		new Thread(new Runnable() {
	    //runOnUiThread

	        @Override
	        public void run() {
	        	
	        	
	        	while(true){
	        		final String s;
					try {
						s = (String) q1.take();
						//System.out.println("takkkkkkkkkkkkkkeee  : "+s);
						t.post(new Runnable() {

							@Override
							public void run() {
							//t.setText("recieve from proj: "+s);
							Toast.makeText(MainActivity.this.getApplicationContext(), "please remember to take your pill", 2).show();
							}
							
							
						});
						
					} catch (InterruptedException e) {
						Toast.makeText(MainActivity.this.getApplicationContext(), "error taking element", 2).show();
						//t.setText("error taking element");
					}
					 
	        	}
				
	        
	           
	        }
	    }).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
