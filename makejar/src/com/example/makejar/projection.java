package com.example.makejar;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import static java.util.concurrent.TimeUnit.*;
public abstract class projection {

	
	  private BlockingQueue<String> _q;
	  private ProjectionType Type;
	  private Vector<Calendar> calanders;
	  
	  public enum ProjectionType {
		    Cyclic, Monitor, Question, Recommendation
		 
		}

	public projection(ProjectionType type)
	{
		Type=type;
		calanders=new Vector<Calendar>();
		Calendar c=Calendar.getInstance();
		calanders.add(c);
		
	}
	
	
	public  Calendar getTimer()
	{
		return calanders.get(0);
		
	}
	
	
	public  void SetTime(int year,int month,int day,int hour,int minute,int sec)
	{
		calanders.get(0).set(year, month, day, hour, minute, sec);
		
	}
	
	
	
	public abstract void setTimer();
	
	public  void SendMsg(String msg)
	{
		Runnable msgSender=generateMsgSenderRunnable(msg);
		
		Thread sender=new Thread(msgSender);
		
		sender.start();
	}
	
	
	
	
	
	
	private   Runnable generateMsgSenderRunnable(final String msg)
	{
		Runnable msgSender = new Runnable() {
		       public void run() { 
		    	   
				try {
					Thread.sleep(500);
					_q.put(msg);
				} catch (InterruptedException e) {
				System.out.println("error while sending a msg to the queue");
				}
		      };};
		      
		      
		    return msgSender;
	}

	
	
	
	
	
	
	
}
