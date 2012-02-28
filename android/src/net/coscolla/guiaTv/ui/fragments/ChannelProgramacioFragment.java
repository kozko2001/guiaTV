package net.coscolla.guiaTv.ui.fragments;

import java.util.Random;

import net.coscolla.guiaTv.R;
import net.coscolla.guiaTv.model.Programa;
import net.coscolla.guiaTv.model.Programacio;
import net.coscolla.guiaTv.ui.RoboFragment;
import net.coscolla.guiaTv.ui.iosched.BlockView;
import net.coscolla.guiaTv.ui.iosched.BlocksLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChannelProgramacioFragment extends RoboFragment {
	private Programacio programacio;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		programacio = (Programacio)getArguments().getParcelable("programacio");
		
		View v = inflater.inflate(R.layout.programa, null);

		BlocksLayout layout = (BlocksLayout) v.findViewById(R.id.blocks);
		
		for(Programa programa : programacio.programes)
		{
			BlockView block = new BlockView(getActivity(),"", programa.programName, programa.hora, programa.hora_fin, false, 1);
			layout.addBlock(block);
		}
		
		return v;
	}
}
