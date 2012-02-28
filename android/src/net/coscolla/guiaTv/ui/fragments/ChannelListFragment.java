package net.coscolla.guiaTv.ui.fragments;

import java.util.ArrayList;

import net.coscolla.android.utils.HttpHelper;
import net.coscolla.guiaTv.Constants;
import net.coscolla.guiaTv.model.Channel;
import net.coscolla.guiaTv.model.parser.ChannelsParser;
import net.coscolla.guiaTv.ui.BaseActivity;
import net.coscolla.guiaTv.ui.ISendIntent;
import net.coscolla.guiaTv.ui.RoboListFragment;
import net.coscolla.guiaTv.ui.mobile.activities.ChannelsTablet;
import net.coscolla.guiaTv.ui.mobile.activities.InfoChannelMobile;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChannelListFragment extends RoboListFragment{

	    
	private ChannelsArrayAdapter dataProvider;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		String xml = HttpHelper.doGet(Constants.getServerUrl() + "channels");
		ArrayList<Channel> channels = ChannelsParser.parse(xml);
		
		dataProvider = new ChannelsArrayAdapter(getActivity(), channels);
		setListAdapter(dataProvider);
	
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Channel onClickChannel = dataProvider.getItem(position);
		Fragment pagerFragment = getSupportFragmentManager().findFragmentByTag(ChannelsTablet.CHANNEL_DETAIL_TAG);
		if(  pagerFragment != null )
			sendPagerFragmentSignal(onClickChannel, pagerFragment);
		else
			openPagerFragment(onClickChannel);
	}

	private void sendPagerFragmentSignal(Channel channel, Fragment pagerFragment) {
		Intent i = new Intent();
		i.setClass(getActivity().getApplicationContext(), InfoChannelMobile.class);
		i.putExtra("channel", channel);
		((ISendIntent)pagerFragment).sendSignalIntent(i);
	}

	private void openPagerFragment(Channel channel) {
		BaseActivity act = (BaseActivity)getActivity();
		Intent i = new Intent();
		i.setClass(getActivity().getApplicationContext(), InfoChannelMobile.class);
		i.putExtra("channel", channel);
		act.openActivityOrFragment(i);
	}
	
	
	private class ChannelsArrayAdapter extends ArrayAdapter<Channel>
	{
		
		public ChannelsArrayAdapter(Context ctx, ArrayList<Channel> channels)
		{
			super(ctx,android.R.layout.simple_list_item_1, channels.toArray( new Channel[]{}));
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
			TextView tv = (TextView) v.findViewById(android.R.id.text1);
			tv.setText( this.getItem(position).channelDesc);
			
			return v;
		}	
		
	}
	
	public static final String[] Channels = 
    {
            "Henry IV (1)",   
            "Henry V",
            "Henry VIII",       
            "Richard II",
            "Richard III",
            "Merchant of Venice",  
            "Othello",
            "King Lear"
    };
}
