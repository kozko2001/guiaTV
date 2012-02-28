package net.coscolla.guiaTv;

import android.content.Intent;
import android.os.Bundle;
import net.coscolla.guiaTv.ui.UIUtils;
import net.coscolla.guiaTv.ui.mobile.activities.ChannelsTablet;
import net.coscolla.guiaTv.ui.mobile.activities.ListChannelsMobile;
import roboguice.activity.RoboSplashActivity;

public class Splash extends RoboSplashActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.minDisplayMs = 0;
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void startNextActivity() {
		Intent i = new Intent();
		//i.setClass(getApplicationContext(), ListChannelsMobile.class);
		i.setClass(getApplicationContext(), ChannelsTablet.class);
		startActivity(i);
	}

}
