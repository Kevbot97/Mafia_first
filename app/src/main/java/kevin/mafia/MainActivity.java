package kevin.mafia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static Game game = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (game == null)
            game = new Game();
        addButtonListener();
    }

    public void onDestroy(Bundle savedInstanceState) {
        game = null;
    }

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
        if (id == R.id.action_settings) {
            launchSettingsActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchSettingsActivity()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    public void addButtonListener()
    {
        Button startBtn = (Button) findViewById(R.id.mainStartBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                launchBufferActivity();
                //launchSettingsActivity();
            }
        });
    }

    public void launchBufferActivity()
    {
        Intent intent = new Intent(this, BufferActivity.class);
        game.start();
        startActivity(intent);
        finish();
    }
}
