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
import com.parse.Parse;
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
		setFields();

		Parse.initialize(this, getResources().getString(R.string.APP_ID),
				getResources().getString(R.string.CLIENT_KEY));

		ActionBar actionBar = getSupportActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab;
		if (currentUser.getBoolean("isMaestro")) {
			tab = actionBar.newTab().setText("Maestro Portal")
					.setTabListener(new PortalMaestro())
					.setIcon(R.drawable.ic_launcher);

			actionBar.addTab(tab);
		}

		if (currentUser.getBoolean("isStudent")) {
			tab = actionBar.newTab().setText("Student Portal")
					.setTabListener(new PortalStudent())
					.setIcon(R.drawable.ic_launcher);

			actionBar.addTab(tab);
		}
	}

	public void initialiseFields() {
		currentUser = ParseUser.getCurrentUser();
	}

	public void setFields() {
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
}
