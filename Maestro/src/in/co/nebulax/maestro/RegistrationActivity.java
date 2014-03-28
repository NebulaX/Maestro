package in.co.nebulax.maestro;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseUser;

public class RegistrationActivity extends MyActivity implements LocationListener {

	EditText name, email, pass, confirmpass, mobile;
	CheckBox student, maestro;
	Button register, btn_location;
	TextView redirect_login, tv_location;
	boolean isRegSuccess = true;

	int studentClass = 0;

	Spinner spinner_class, spinner_from, spinner_to, spinner_subject;

	boolean isStudent = false, isMaestro = false;

	LinearLayout stuClass;
	LinearLayout maestroDetails;

	String subject = "";
	int from = 0, to = 0;
	
	LatLng latLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		initialiseFields();
		setFields();
	}

	public void initialiseFields() {

		name = (EditText) findViewById(R.id.reg_name);
		email = (EditText) findViewById(R.id.reg_email);
		pass = (EditText) findViewById(R.id.reg_password);
		confirmpass = (EditText) findViewById(R.id.reg_password_confirm);
		student = (CheckBox) findViewById(R.id.check_student);
		maestro = (CheckBox) findViewById(R.id.check_maestro);
		register = (Button) findViewById(R.id.btn_register);
		redirect_login = (TextView) findViewById(R.id.tv_login_redirect);
		stuClass = (LinearLayout) findViewById(R.id.layout_student_class);
		maestroDetails = (LinearLayout) findViewById(R.id.layout_maestro_details);
		btn_location = (Button) findViewById(R.id.btn_detectLocation);
		tv_location = (TextView) findViewById(R.id.tv_location);
		spinner_class = (Spinner) findViewById(R.id.spinner_student_class);
		spinner_from = (Spinner) findViewById(R.id.spinner_maestro_from);
		spinner_to = (Spinner) findViewById(R.id.spinner_maestro_to);
		spinner_subject = (Spinner) findViewById(R.id.spinner_maestro_subject);
		mobile = (EditText) findViewById(R.id.reg_mobile);
	}

	private void setFields() {
		register.setOnClickListener(this);
		redirect_login.setOnClickListener(this);
		btn_location.setOnClickListener(this);

		student.setOnCheckedChangeListener(this);
		maestro.setOnCheckedChangeListener(this);

		spinner_class.setOnItemSelectedListener(this);
		spinner_from.setOnItemSelectedListener(this);
		spinner_to.setOnItemSelectedListener(this);
		spinner_subject.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View v) {

		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			switch (v.getId()) {
			case R.id.btn_register:
				// Send request to register
				if (isValidated())
					new RegistrationHandler().execute();
				break;

			case R.id.tv_login_redirect:
				showLoginActivity();
				break;

			case R.id.btn_detectLocation:
				getLocation();
				break;
			}
		} else {
			Toast.makeText(this, "Internet Connection not available",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		switch (buttonView.getId()) {
		case R.id.check_student:
			if (isChecked) {
				isStudent = true;
				stuClass.setVisibility(View.VISIBLE);
			} else {
				isStudent = false;
				stuClass.setVisibility(View.GONE);
			}
			break;
		case R.id.check_maestro:
			if (isChecked) {
				isMaestro = true;
				maestroDetails.setVisibility(View.VISIBLE);
			} else {
				isMaestro = false;
				maestroDetails.setVisibility(View.GONE);
			}
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long l) {

		switch (parent.getId()) {

		case R.id.spinner_student_class:
			studentClass = pos + 1;
			break;
		case R.id.spinner_maestro_subject:
			subject = parent.getItemAtPosition(pos).toString();
			break;
		case R.id.spinner_maestro_from:
			from = pos + 1;
			break;
		case R.id.spinner_maestro_to:
			to = pos + 1;
			break;
		}
	}

	public boolean isValidated() {

		String sname = name.getText().toString();
		String sEmail = email.getText().toString();
		String sPass = pass.getText().toString();
		String sCPass = confirmpass.getText().toString();
		String sMobile = mobile.getText().toString();

		if (sname.equalsIgnoreCase("") || !sname.matches("^[A-Za-z ]+$")) {
			Toast.makeText(RegistrationActivity.this,
					"Please enter a valid name", Toast.LENGTH_SHORT).show();
			return false;
		} else if (sEmail.equalsIgnoreCase("")
				|| !sEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
			Toast.makeText(RegistrationActivity.this,
					"Please enter valid email", Toast.LENGTH_SHORT).show();
			return false;
		} else if (sPass.equalsIgnoreCase("")) {
			Toast.makeText(RegistrationActivity.this,
					"Password should not be left blank", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (!sPass.equals(sCPass)) {
			Toast.makeText(RegistrationActivity.this, "Password Mismatch",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (sMobile.equalsIgnoreCase("")
				|| !sMobile
						.matches("^((\\+){0,1}91(\\s){0,1}(\\-){0,1}(\\s){0,1}){0,1}9[0-9](\\s){0,1}(\\-){0,1}(\\s){0,1}[1-9]{1}[0-9]{7}$")) {
			Toast.makeText(RegistrationActivity.this,
					"Enter proper mobile number", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}

	}

	// Async Task class for making HTTP call
	private class RegistrationHandler extends AsyncTask<Integer, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(RegistrationActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Integer... arg0) {

			// Validate the entries first...
			ParseUser user = new ParseUser();
			user.setUsername(email.getText().toString());
			user.setPassword(pass.getText().toString());
			user.setEmail(email.getText().toString());

			// other fields can be set just like with ParseObject
			user.put("Name", name.getText().toString());
			user.put("isStudent", isStudent);
			user.put("isMaestro", isMaestro);
			user.put("studentClass", studentClass);
			user.put("Subject", subject);
			user.put("Class_from", from);
			user.put("Class_to", to);
			user.put("Mobile", mobile.getText().toString());
			user.put("Location", "");

			try {
				user.signUp();
			} catch (ParseException e) {
				e.printStackTrace();
				isRegSuccess = false;
			} catch (Exception e) {
				isRegSuccess = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			if (isRegSuccess) {
				Toast.makeText(RegistrationActivity.this,
						"Successfully Registered !!", Toast.LENGTH_SHORT)
						.show();
				showLoginActivity();
			} else {
				Toast.makeText(RegistrationActivity.this,
						"Something Wicked Happenned", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private void showLoginActivity() {
		finish();
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
	}

	public void getLocation() {

		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		if (status != ConnectionResult.SUCCESS) {

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();
		}else {
			
			 // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
 
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
 
            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
            
            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);
 
            if(location!=null){
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		
		// Getting latitude of the current location
        double latitude = location.getLatitude();
 
        // Getting longitude of the current location
        double longitude = location.getLongitude();
 
        // Creating a LatLng object for the current location
        latLng = new LatLng(latitude, longitude);
        
        tv_location.setText("Latitude : "+latitude + " Longitude : "+longitude);
        
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

}