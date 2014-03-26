package in.co.nebulax.maestro;

import in.co.nebulax.maestro.utils.ConnectionDetector;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.parse.Parse;

public class MyActivity extends SherlockActivity implements OnClickListener,
		OnCheckedChangeListener {

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

		if (!isInternetPresent) {
			Toast.makeText(this, "Unable to establish a connection",
					Toast.LENGTH_LONG).show();
		}

		// Initialisation of Parse 
		Parse.initialize(this, getResources().getString(R.string.APP_ID),
				getResources().getString(R.string.CLIENT_KEY));
		
		// Initialisation of shared Prefs
		
		remeberData = getSharedPreferences(radio, 0);
		rememberEditor = remeberData.edit();
		isRemembered = remeberData.getBoolean("remember", false);
		Log.v("isRemembered", "In MyActivity : "+isRemembered);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	}
}