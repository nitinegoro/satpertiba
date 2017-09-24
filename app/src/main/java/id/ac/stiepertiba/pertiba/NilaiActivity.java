package id.ac.stiepertiba.pertiba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import id.ac.stiepertiba.pertiba.config.Server;
import id.ac.stiepertiba.pertiba.models.NilaiAdapter;

/**
 * Created by nitinegoro on 9/21/2017.
 */

public class NilaiActivity extends AppCompatActivity
{
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;

    public final static String TAG_NPM = "npm";
    public final static String TAG_NAME = "name";

    SharedPreferences sharedpreferences;
    Boolean session;
    String name, npm;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    private String url = Server.get_nilai;
    private RecyclerView rescycleLayNilai;
    private ProgressBar SetProgressBarGetNilai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);
        Intent intent = getIntent();
        String NamaIcon = intent.getStringExtra("NamaIcon");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SetProgressBarGetNilai = (ProgressBar)findViewById(R.id.progressBar);

        if( getSupportActionBar() != null)
        {
            toolbar.setTitleTextColor(android.graphics.Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(NamaIcon);
        }

        /* SESSION */
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, true);
        name = sharedpreferences.getString(TAG_NAME, null);
        npm = sharedpreferences.getString(TAG_NPM, null);

        final TextView tipk = (TextView)findViewById(R.id.tipk);
        final TextView tsksk = (TextView)findViewById(R.id.tsks);

        //Log.e("Test :", npm);
        /* Rescycle*/
        rescycleLayNilai = (RecyclerView) findViewById(R.id.rescycleLayNilai);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rescycleLayNilai.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(NilaiActivity.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url + npm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray nilaiMhs = jsonObject.getJSONArray("nilaiMhs");

                    String getipk = jsonObject.getString("ipk");
                    String getsks = jsonObject.getString("total_sks");

                    tipk.setText(getipk);
                    tsksk.setText(getsks);

                    System.out.println(getipk);

                    for (int a = 0; a < nilaiMhs.length(); a++) {
                        JSONObject json = nilaiMhs.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("course_code", json.getString("course_code"));
                        map.put("course_name", json.getString("course_name"));
                        map.put("sks", json.getString("sks"));
                        map.put("point", json.getString("point"));
                        map.put("grade", json.getString("grade"));
                        list_data.add(map);
                        NilaiAdapter adapter = new NilaiAdapter(NilaiActivity.this, list_data);
                        rescycleLayNilai.setAdapter(adapter);
                    }

                    SetProgressBarGetNilai.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NilaiActivity.this, "Server could't response!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    /* BACK To Home */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}

