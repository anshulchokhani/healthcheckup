package com.example.saksham.speedcode;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SignIn extends Activity {
    Button b;
    EditText uname;
    EditText pass;
    Boolean correct_login;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        prefs = getSharedPreferences("Healthbuddy",MODE_PRIVATE);
        b = (Button)findViewById(R.id.btnSubmit);
        uname = (EditText)findViewById(R.id.txtUname);
        pass = (EditText)findViewById(R.id.txtPass);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadWebpageTask(new AsyncResult() {
                    @Override
                    public void onResult(JSONObject object) {
                        correct_login = validate_login(object, uname.getText().toString(), pass.getText().toString());
                        if (correct_login) {
                            Toast.makeText(SignIn.this, "Correct Login", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("Created",uname.getText().toString());
                            editor.commit();
                            //Insert shared preference code here
                            //Intent i = new Intent(getApplicationContext(), new_req1.class).putExtra(Intent.EXTRA_TEXT, uname);
                            //startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid username/password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, getApplicationContext()).execute("https://spreadsheets.google.com/tq?key=1TejfFsRoT0Wmni8CUG8IPIy2OtmJCQt0FXpLlDSUurI");

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validate_login(JSONObject object, String uname, String pass) {
        try {
            JSONArray rows = object.getJSONArray("rows");
            int len = rows.length();
            String uname_iter, pass_iter;
            for (int r = 0; r < len; ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                uname_iter = columns.getJSONObject(1).getString("v");
                if (uname_iter.equals(uname)) {
                    pass_iter = columns.getJSONObject(2).getString("v");
                    //forgotpass.setText(uname_iter);
                    if (pass.equals(pass_iter)) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
