package kevin.mafia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static kevin.mafia.MainActivity.game;

// figure out toolbar shit later

public class BufferActivity extends AppCompatActivity {

    Person currentPerson;
    Button bufferBtn;
    TextView textViewBuffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);
        currentPerson = game.getCurrentPerson();
        bufferBtn = (Button) findViewById(R.id.bufferBtn);
        textViewBuffer = (TextView) findViewById(R.id.textViewBuffer);
        textViewBuffer.setText("Hi " + currentPerson.toString() + "!  Press Start when ready.");
        addBufferButtonListener();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    public void onDestroy(Bundle savedInstanceState) {
        MainActivity.game = null;
    }

    public void addBufferButtonListener()
    {
        bufferBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                launchShowRoleActivity();
                //launchSettingsActivity();
            }
        });
    }

    public void launchShowRoleActivity()
    {
        Intent intent = new Intent(this, ShowRoleActivity.class);
        startActivity(intent);
        finish();
    }



/*
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
*/
}
