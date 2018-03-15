package brawijaya.electricity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    Spinner spKota, spDaya;
    String nKota=null, nDaya=null;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<List> listAlat = new ArrayList<List>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listAlat.add(new List("Televisi", R.drawable.tv));
        listAlat.add(new List("AC", R.drawable.ac));
        listAlat.add(new List("Kipas Angin", R.drawable.kipas));
        listAlat.add(new List("Mesin Cuci", R.drawable.mcuci));
        listAlat.add(new List("Kulkas", R.drawable.kulkas));
        listAlat.add(new List("Rice Cooker", R.drawable.rcooker));

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        adapter = new CustomAdapter(listAlat);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.commonmenus,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                Intent myIntent = new Intent(this, AboutActivity.class);
                startActivity(myIntent);
                break;
            case R.id.action_settings:
                Intent myIntent2 = new Intent(this, SettingsActivity.class);
                startActivity(myIntent2);
                break;
            case R.id.action_update:
                Toast.makeText(getApplicationContext(), "Latest Version Installed!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inputManual(View view){
            Intent myIntent = new Intent(this, Main3Activity.class);
            startActivity(myIntent);
        }
}