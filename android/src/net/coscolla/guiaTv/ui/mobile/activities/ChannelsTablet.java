package net.coscolla.guiaTv.ui.mobile.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import net.coscolla.guiaTv.R;
import net.coscolla.guiaTv.ui.BaseMultiPaneActivity;
import net.coscolla.guiaTv.ui.fragments.ChannelPagerFragment;
import net.coscolla.guiaTv.ui.fragments.ChannelListFragment;


public class ChannelsTablet extends BaseMultiPaneActivity {

	private FragmentManager fm;
	public static String CHANNEL_LIST_TAG = "channelList";
	public static String CHANNEL_DETAIL_TAG = "channelDetail";
	
	@Override
	protected void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		
		setContentView(R.layout.tablet);
		fm = this.getSupportFragmentManager();
		if( fm.findFragmentByTag(CHANNEL_LIST_TAG) == null)
			fm.beginTransaction().add(R.id.list,new ChannelListFragment(), CHANNEL_LIST_TAG).commit();
	}
	
	@Override
	protected FragmentReplaceInfo onSubstituteFragmentForActivityLaunch(String activityClassName) {
		
		if (InfoChannelMobile.class.getName().equals(activityClassName)) {
			 fm.popBackStack();
			 findViewById(R.id.detail).setBackgroundColor(0);
	          return new FragmentReplaceInfo(ChannelPagerFragment.class, CHANNEL_DETAIL_TAG, R.id.detail);
		}
		
		return null;
	}
}
