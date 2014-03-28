package in.co.nebulax.maestro;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragment;

public class PortalMaestro extends SherlockFragment implements TabListener{

	public PortalMaestro() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_maestro, container,
				false);
		return v;
	}
	
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        ft.add(android.R.id.content, this,"Maestro");
        ft.attach(this);
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
         ft.detach(this);
    }
 
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

}
