package net.coscolla.guiaTv.ui;

import roboguice.RoboGuice;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

public class RoboListFragment extends ListFragment{



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectViewMembers(this);
    }
    
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	    RoboGuice.getInjector(getActivity()).injectViewMembers(this);
	}
}
