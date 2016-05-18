package io.handicraft.bottomtabappsample;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Global Application class
 */
public class GlobalApplication extends Application {
	private static volatile GlobalApplication _instance        = null;
	private static volatile Activity          _currentActivity = null;


	public static Activity getCurrentActivity() {
		//Logger.d("++ _currentActivity : " + (_currentActivity != null ? _currentActivity.getClass().getSimpleName() : ""));
		return _currentActivity;
	}

	public static void setCurrentActivity(Activity currentActivity) {
		GlobalApplication._currentActivity = currentActivity;
	}

	/**
	 * singleton 애플리케이션 객체를 얻는다.
	 *
	 * @return singleton 애플리케이션 객체
	 */
	public static GlobalApplication getGlobalApplicationContext() {
		if (_instance == null)
			throw new IllegalStateException("this application does not inherit com.qjubu.app.GlobalApplication");
		return _instance;
	}

	/**
	 * Application 초기화를 수행한다.
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		//Fabric.with(this, new Crashlytics());

		_instance = this;

		// 화면 세로 고정
		registerActivityLifecycleCallbacks(new ActivityLifecycleAdapter() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		});

		initLogger();
	}

	/**
	 * Logger 초기화.
	 */
	private void initLogger() {
		if (BuildConfig.DEBUG) {
			Logger.init("QuickJubu").logLevel(LogLevel.FULL);
		} else {
			Logger.init("QuickJubu").logLevel(LogLevel.NONE);
		}
	}

	/**
	 * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
		_instance = null;
	}
}
