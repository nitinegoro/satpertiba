package id.ac.stiepertiba.pertiba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.ac.stiepertiba.pertiba.config.Server;
import id.ac.stiepertiba.pertiba.controllers.AppController;

/**
 * Created by nitinegoro on 9/24/2017.
 */

public class AccountActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    Button btnsavacccount;
    EditText tEmail, tpassbaru, tpasslama;

    private String url = Server.put_password;

    ConnectivityManager conMgr;

    String email, pass_baru, pass_lama, name, npm, emailSession;
    int success;

    SharedPreferences sharedpreferences;
    Boolean session;

    private static final String TAG = AccountActivity.class.getSimpleName();

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent intent = getIntent();
        String NamaIcon = intent.getStringExtra("NamaIcon");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if( getSupportActionBar() != null)
        {
            toolbar.setTitleTextColor(android.graphics.Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(NamaIcon);
        }

        btnsavacccount = (Button) findViewById(R.id.btnsavacccount);
        tEmail = (EditText) findViewById(R.id.tEmail);
        tpassbaru = (EditText) findViewById(R.id.tpassbaru);
        tpasslama = (EditText) findViewById(R.id.tpasslama);

        /* SESSION */
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, true);
        name = sharedpreferences.getString("name", null);
        npm = sharedpreferences.getString("npm", null);
        emailSession = sharedpreferences.getString("email", null);

        tEmail.setText(emailSession);

        btnsavacccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = tEmail.getText().toString();
                String pass_baru = tpassbaru.getText().toString();
                String pass_lama = tpasslama.getText().toString();

                if (email.trim().length() > 0 && pass_lama.trim().length() > 0)
                {
                    saveAccount(email, pass_baru, pass_lama);
                } else if(email.isEmpty()) {
                    Toast.makeText(getApplicationContext() ,"E-Mail ini sangat diperlukan!", Toast.LENGTH_LONG).show();
                } else if( pass_baru.trim().length() < 6 && !pass_baru.isEmpty() ) {
                    Toast.makeText(getApplicationContext() ,"Password baru minimal 6 karakter!", Toast.LENGTH_LONG).show();
                } else if( pass_lama.isEmpty() ) {
                    Toast.makeText(getApplicationContext() ,"Password lama sangat diperlukan!", Toast.LENGTH_LONG).show();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveAccount(final String email, final String pass_baru, final String pass_lama)
    {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Menyimpan data ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt("success");

                    // Check for error node in json
                    if (success == 1) {

                        String email = jObj.getString("email");

                        Log.e("Response success!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString("message"), Toast.LENGTH_LONG).show();

                        // Ubah session email
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("email", email);
                        editor.commit();

                        tEmail.setText( jObj.getString("email") );

                        hideKeyboard(AccountActivity.this);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("npm", npm);
                params.put("pass_baru", pass_baru);
                params.put("email", email);
                params.put("password", pass_lama);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
