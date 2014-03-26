package in.co.nebulax.maestro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class HomeScreen extends MyActivity {

	TextView usrName;
	
	Button logout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		usrName = (TextView) findViewById(R.id.tv_usrName);
		logout = (Button) findViewById(R.id.btn_logout);
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		usrName.setText(currentUser.getUsername());
		
		logout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		ParseUser.logOut();
		finish();
		Intent i = new Intent(this , LoginActivity.class);
		startActivity(i);
	}
}
