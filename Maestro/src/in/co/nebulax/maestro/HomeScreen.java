package in.co.nebulax.maestro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class HomeScreen extends MyActivity {

	ParseUser currentUser;
	TextView usrName;

	Button logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialiseFields();
		setFields();
		
		usrName.setText(currentUser.getUsername());
	}
	
	public void initialiseFields(){

		usrName = (TextView) findViewById(R.id.tv_usrName);
		logout = (Button) findViewById(R.id.btn_logout);
		currentUser = ParseUser.getCurrentUser();
		
	}
	
	public void setFields(){

		logout.setOnClickListener(this);		
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
