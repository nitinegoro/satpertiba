package id.ac.stiepertiba.pertiba;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import id.ac.stiepertiba.pertiba.controllers.DataAdapter;
import id.ac.stiepertiba.pertiba.models.IndexIcon;

/**
 * Created by nitinegoro on 9/17/2017.
 */

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    SharedPreferences sharedpreferences;
    Boolean session;
    String name, npm;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    private final String nama_icons[] = {
            "Jadwal Kuliah",
            "Transkrip Nilai",
            "Hasil Studi",
            "Rencana Studi",
            "Kalender Akademik",
            "Berita Kampus",
            "Info Loker",
            "Lihat Dosen",
            "Akun Saya"
    };

    private final String url_icons[] = {
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_today_black_48dp.png",
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_school_black_48dp.png",
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_assignment_black_48dp.png",
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_description_black_48dp.png",
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_date_range_black_48dp.png",
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_library_books_black_48dp.png",
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_find_in_page_black_48dp.png",
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_supervisor_account_black_48dp.png",
            "http://siakad.stiepertiba.ac.id/assets/mobile/images/icon/ic_account_circle_black_48dp.png"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tampilicon();

        if( getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("");
        }
    }

    /* START ICON */
    private void tampilicon() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rescycleLay);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<IndexIcon> planet = prepareData();
        DataAdapter adapter = new DataAdapter(getApplicationContext(),planet);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<IndexIcon> prepareData() {
        ArrayList<IndexIcon> icons = new ArrayList<>();
        for(int i=0;i<nama_icons.length;i++){
            IndexIcon Icon = new IndexIcon();
            Icon.setNama_icon(nama_icons[i]);
            Icon.setUrl_icon(url_icons[i]);
            icons.add(Icon);
        }
        return icons;
    }
    /* END ICON*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.quit)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Yakin ingin keluar Aplikasi?");

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Iya",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

                            SharedPreferences preferences = getSharedPreferences(my_shared_preferences,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.commit();
                            startActivity(intent);
                            finish();
                            // current activity
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
