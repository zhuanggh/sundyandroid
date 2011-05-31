/**
 * Analyse Handler of Android
 */
package sundy.android.demo.processthread;

import sundy.android.demo.R;
import sundy.android.demo.configration.CommonConstants;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * @author sundy
 *
 */
public class HandlerConceptActivity extends Activity {

	final String HANDLER_KEY = "sundy.android.demo.HandlerKey"  ;
	Handler mainHandler1 ;
	Handler mainHandler2 ;
	
	//Caculate the process .
	long ProcessCaculate()
	{
		long returnResult = 0;
		long j = 98 , i = 99 , k = 100 ; 
		for(int m=0 ;m<10000;m++)
		{
			returnResult = j*i*k*m  ;
		}
		return returnResult ;
	}
	
	//Define a common Message , I will invoke it in different labs . 
	Message defineNewMessage(String messageContent)
	{
		Message returnMsg = new Message() ;
		Bundle data = new Bundle() ;
		data.putString(HANDLER_KEY, messageContent)  ;
		returnMsg.setData(data)  ;
		return returnMsg ;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_handlerconcept) ;
		
		//init handler in main thread .
		mainHandler2 = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Toast.makeText(HandlerConceptActivity.this, "[Main Thread]Handler2 Get the message: "+msg.getData().getString(HANDLER_KEY), 5000).show()  ;
			}
			
		} ;
		mainHandler1 = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Toast.makeText(HandlerConceptActivity.this, "[Main Thread]Handler1 Get the message: "+msg.getData().getString(HANDLER_KEY), 5000).show()  ;
			}
			
		} ;

		//Handler Lab1
		findViewById(R.id.buttonHandlerLab1).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ProcessCaculate() ;
						mainHandler1.sendMessage(defineNewMessage("Lab1")) ;
						//mainHandler2.sendMessage(defineNewMessage("Lab1")) ;
						
					}
					
				}).start() ;
			}
			
		}) ;
		
		//Handler Lab2
		findViewById(R.id.buttonHandlerLab2).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ProcessCaculate() ;
						Handler lab2Handler = new Handler()  ;
						lab2Handler.sendMessage(defineNewMessage("Lab2")) ;
					}
					
				}).start() ;*/
				
				//using handler thread to make handler (message queue)
	
				Runnable _runnable = new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ProcessCaculate() ;
					}
				} ;
				
				HandlerThread handlerThread = new HandlerThread("threadone");  
		        handlerThread.start();  
		        //Handler handler22 =  new Handler(handlerThread.getLooper());  
		        //handler22.post(_runnable);
		        new Handler(handlerThread.getLooper()){

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						Log.i(CommonConstants.LOGCAT_TAG_NAME, "Get the message by Handler Thread")  ;
					}
		        	
		        }.post(_runnable)  ;

			}
			
		}) ;
		
		//Handler Lab3
		findViewById(R.id.buttonHandlerLab3).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable(){
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Handler lab3Handler  ;
						lab3Handler = new Handler(Looper.getMainLooper())
						{

							@Override
							public void handleMessage(Message msg) {
								// TODO Auto-generated method stub
								super.handleMessage(msg);
								Log.i(CommonConstants.LOGCAT_TAG_NAME, "Get the message: "+msg.getData().getString(HANDLER_KEY)+" by Child Thread Handler")  ;
							}
							
						};
						lab3Handler.sendMessage(defineNewMessage("Lab3")) ;
						
						
						/*ProcessCaculate() ;
						Looper.prepare()  ;
						lab3Handler = new Handler(){

							@Override
							public void handleMessage(Message msg) {
								// TODO Auto-generated method stub
								super.handleMessage(msg);
								Log.i(CommonConstants.LOGCAT_TAG_NAME, "Get the message from "+msg.getData().getString(HANDLER_KEY)+" by Child Thread Handler")  ;
							}
						} ;
						
						lab3Handler.sendMessage(defineNewMessage("Lab3")) ;
						Looper.loop() ;*/
					}
					
				}).start() ;
			}
			
		}) ;
	}

}
