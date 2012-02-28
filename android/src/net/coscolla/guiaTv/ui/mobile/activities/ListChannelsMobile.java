package net.coscolla.guiaTv.ui.mobile.activities;

import java.util.List;

import net.coscolla.guiaTv.ui.BaseActivity;
import net.coscolla.guiaTv.ui.BaseSinglePaneActivity;
import net.coscolla.guiaTv.ui.fragments.ChannelListFragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class ListChannelsMobile extends BaseSinglePaneActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

	@Override
	protected Fragment onCreatePane() {
		return new ChannelListFragment();
	}
    
}