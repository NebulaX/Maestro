package in.co.nebulax.maestro;

import in.co.nebulax.maestro.utils.ConnectionDetector;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class PortalStudent extends SherlockFragment implements TabListener,
		OnItemSelectedListener {

	View v;

	ParseUser currUser;
	TextView tv_usrName;
	Button btn_requestTutor, submit;
	LinearLayout requestBuilder;
	Spinner subject_select;

	EditText topic_name;

	ProgressDialog pDialog;
	public ConnectionDetector cd;
	public Boolean isInternetPresent;

	String subject = "English";

	public PortalStudent() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.fragment_student, container, false);

		Parse.initialize(getSherlockActivity(),
				getResources().getString(R.string.APP_ID), getResources()
						.getString(R.string.CLIENT_KEY));

		initialiseFields();
		setFields();

		return v;
	}

	public void initialiseFields() {

		// Initialising fields
		tv_usrName = (TextView) v.findViewById(R.id.fragStudent_tv_name);
		btn_requestTutor = (Button) v
				.findViewById(R.id.fragStudent_btn_tutRequest);

		requestBuilder = (LinearLayout) v
				.findViewById(R.id.fragStudent_ll_tut_request);

		subject_select = (Spinner) v
				.findViewById(R.id.fragStudent_spinner_subjectSelect);

		currUser = ParseUser.getCurrentUser();
		tv_usrName.setText("Hi " + currUser.getString("Name"));

		topic_name = (EditText) v.findViewById(R.id.fragStudent_topic_name);
		submit = (Button) v.findViewById(R.id.fragStudent_btn_submit);
	}

	public void setFields() {

		// set fields
		btn_requestTutor.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				requestBuilder.setVisibility(View.VISIBLE);
			}
		});

		subject_select.setOnItemSelectedListener(this);

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isValidated()) {

					ParseObject request = new ParseObject("RequestLog");
					request.put("StudentId", currUser.getObjectId());
					request.put("Subject", subject);
					request.put("Topic", topic_name.getText().toString());
					request.put("Status", 0);
					request.saveEventually();
					Toast.makeText(getSherlockActivity(), "Request Recieved",
							Toast.LENGTH_SHORT).show();

					// Add a call to cloud function to search for suitable
					// maestro

					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("objectId", currUser.getObjectId());
					ParseCloud.callFunctionInBackground("maestroRequest",
							params, new FunctionCallback<String>() {
								public void done(String result, ParseException e) {
									if (e == null) {
										Log.v("Cloud" , result.toString());										
									}
								}
							});
				}
			}
		});
	}

	public boolean isValidated() {

		return true;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.add(android.R.id.content, this, "Student");
		ft.attach(this);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.detach(this);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long arg3) {

		subject = parent.getItemAtPosition(pos).toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
