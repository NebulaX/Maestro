package in.co.nebulax.maestro;

import in.co.nebulax.maestro.utils.ConnectionDetector;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.PushService;

public class MyActivity extends SherlockActivity implements OnClickListener,
		OnCheckedChangeListener, OnItemSelectedListener {

	public ConnectionDetector cd;
	public Boolean isInternetPresent;

	public ProgressDialog pDialog;

	// Variables related to shared preferances

	SharedPreferences remeberData;
	String radio = "rememberdata";
	SharedPreferences.Editor rememberEditor;
	boolean isRemembered = false;

	public MyActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Check if internet connection is present
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();

		// if (!isInternetPresent) {
		// Toast.makeText(this, "Unable to establish a connection",
		// Toast.LENGTH_LONG).show();
		// }

		// Initialisation of Parse
		Parse.initialize(this, getResources().getString(R.string.APP_ID),
				getResources().getString(R.string.CLIENT_KEY));

		// Initialisation of shared Prefs

		remeberData = getSharedPreferences(radio, 0);
		rememberEditor = remeberData.edit();
		isRemembered = remeberData.getBoolean("remember", false);
		Log.v("isRemembered", "In MyActivity : " + isRemembered);

		ParseUser currUser = ParseUser.getCurrentUser();
		if (currUser != null) {
			PushService.setDefaultPushCallback(this, HomeScreen.class);
		} else {
			PushService.setDefaultPushCallback(this, LoginActivity.class);
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}