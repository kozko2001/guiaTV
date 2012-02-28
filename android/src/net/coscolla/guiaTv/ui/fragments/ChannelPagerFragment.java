package net.coscolla.guiaTv.ui.fragments;

import java.lang.reflect.Array;
import java.util.ArrayList;

import net.coscolla.android.utils.HttpHelper;
import net.coscolla.guiaTv.Constants;
import net.coscolla.guiaTv.R;
import net.coscolla.guiaTv.R.layout;
import net.coscolla.guiaTv.model.Channel;
import net.coscolla.guiaTv.model.Programacio;
import net.coscolla.guiaTv.model.parser.ChannelsParser;
import net.coscolla.guiaTv.model.parser.ProgramacioParser;
import net.coscolla.guiaTv.ui.ISendIntent;
import net.coscolla.guiaTv.ui.RoboFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChannelPagerFragment extends RoboFragment implements ISendIntent {
	private Channel channel;
	private Programacio programacio;
	private ProgramacioDiaAdapter pagerAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		updateData(getArguments());
		pagerAdapter = new ProgramacioDiaAdapter(getSupportFragmentManager(), programacio);
		
		View v = inflater.inflate(R.layout.pager, null);
		ViewPager pager = (ViewPager) v.findViewById(R.id.pager);
		pager.setAdapter(pagerAdapter);
		
		return v;
	}

	
	
	 public class ProgramacioDiaAdapter extends FragmentStatePagerAdapter {

		 	private ArrayList<Programacio> programacio;
	        public ProgramacioDiaAdapter(FragmentManager fm, Programacio programacio) {
	            super(fm);
	            this.programacio  = Programacio.programacioPerDies(programacio);
	        }

	        @Override
	        public int getCount() {
	            return programacio.size();
	        }

	        @Override
	        public Fragment getItem(int position) {
	            Fragment f = new ChannelProgramacioFragment();
	            Bundle b = new Bundle();
	            b.putParcelable("programacio", programacio.get(position));
	            f.setArguments(b);
	            
	            return f;
	        }
	        
	        public void setProgramacio(Programacio p)
	        {
	        	this.programacio = Programacio.programacioPerDies(p);
	        	this.notifyDataSetChanged();
	        }
	        
	        @Override
	        public int getItemPosition(Object object) {
	        	return POSITION_NONE; // HACK!
	        }
	    }

	@Override
	public void sendSignalIntent(Intent i) {
		updateData(i.getExtras());
		pagerAdapter.setProgramacio(programacio);
	}
	
	private void updateData(Bundle b) {
		channel = (Channel)b.getParcelable("channel");
		
		String xml = HttpHelper.doGet(Constants.getServerUrl() + "channels/" + channel.channelId);
		programacio =ProgramacioParser.parse(xml);
	}
}
