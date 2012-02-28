package net.coscolla.guiaTv.ui.mobile.activities;

import android.support.v4.app.Fragment;
import net.coscolla.guiaTv.ui.BaseSinglePaneActivity;
import net.coscolla.guiaTv.ui.fragments.ChannelPagerFragment;

public class InfoChannelMobile extends BaseSinglePaneActivity {

	@Override
	protected Fragment onCreatePane() {
		return new ChannelPagerFragment();
	}

}
