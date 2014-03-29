package in.co.nebulax.maestro;

import in.co.nebulax.maestro.utils.ConnectionDetector;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.parse.ParseUser;

public class HomeScreen extends SherlockFragmentActivity implements
		OnClickListener {

	ParseUser currentUser;

	public ConnectionDetector cd;
	public Boolean isInternetPresent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		initialiseFields();

//		try{
//		Parse.initialize(this, getResources().getString(R.string.APP_ID),
//				getResources().getString(R.string.CLIENT_KEY));
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		ActionBar actionBar = getSupportActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab;
		if (currentUser.getBoolean("isMaestro")) {
			tab = actionBar.newTab().setText("Maestro Console")
					.setTabListener(new PortalMaestro())
					.setIcon(R.drawable.ic_launcher);

			actionBar.addTab(tab);
		}

		if (currentUser.getBoolean("isStudent")) {
			tab = actionBar.newTab().setText("Student Console")
					.setTabListener(new PortalStudent())
					.setIcon(R.drawable.ic_launcher);

			actionBar.addTab(tab);
		}
	}

	public void initialiseFields() {
		currentUser = ParseUser.getCurrentUser();
	}

	@Override
	public void onClick(View v) {
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			ParseUser.logOut();
			finish();
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
		} else {
			Toast.makeText(this, "Internet Connection not available",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
//		case R.id.action_change_profile :
//			break;
		case R.id.action_logout :
			ParseUser.logOut();
			finish();
			Intent i = new Intent(this , LoginActivity.class);
			startActivity(i);
		}		
		return super.onOptionsItemSelected(item);
	}
}