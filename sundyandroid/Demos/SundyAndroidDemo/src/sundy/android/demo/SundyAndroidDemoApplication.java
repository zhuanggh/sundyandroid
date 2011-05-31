/**
 * Application of current instance management  . 
 */
package sundy.android.demo;

import sundy.android.demo.configration.CommonConstants;
import android.app.Application;
import android.util.Log;

/**
 * @author sundy
 *
 */
public class SundyAndroidDemoApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(CommonConstants.LOGCAT_APP_NAME, "Created")  ;
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		Log.i(CommonConstants.LOGCAT_APP_NAME, "LowMemory")  ;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.i(CommonConstants.LOGCAT_APP_NAME,"Terminated")  ;
	}

}
