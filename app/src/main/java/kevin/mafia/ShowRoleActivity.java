package kevin.mafia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static kevin.mafia.MainActivity.game;

// figure out toolbar shit later

public class ShowRoleActivity extends AppCompatActivity {

    Person currentPerson;
    Button showRoleBtn;
    Spinner spinner;
    TextView textViewShowRole;

    public boolean pressedOnce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_role);
        currentPerson = game.getCurrentPerson();
        pressedOnce = false;
        getGuiItems();
        if (MainActivity.game.currentDay == 0) {
            preGameReveal();
        }
        else roleActivity();
        addButtonListener();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    public void onDestroy(Bundle savedInstanceState) {
        MainActivity.game = null;
    }

    public void launchBufferActivity()
    {
        Intent intent = new Intent(this, BufferActivity.class);
        startActivity(intent);
        finish();
    }

    public void getGuiItems()
    {
        spinner = (Spinner) findViewById(R.id.roleSpinner);
        showRoleBtn = (Button) findViewById(R.id.showRoleBtn);
        textViewShowRole = (TextView) findViewById(R.id.textViewShowRole);
    }

    public void preGameReveal()
    {
        textViewShowRole.setText("You are the " + currentPerson.getRoleName() +
                ".  Press OK when you're good.");
    }

    public void roleActivity()
    {
        if (currentPerson.isDead) {
            textViewShowRole.setText("You are dead. RIP.\n" + MainActivity.game.summary());
        }
        else textViewShowRole.setText("You are the " + currentPerson.getRoleName() + ".  " + currentPerson.getRoleString());
        spinnerPopulation();
    }

    public void spinnerPopulation()
    {
        spinner.setVisibility(View.VISIBLE);

        if (currentPerson instanceof Vigilante)
            if (((Vigilante) currentPerson).hasUsedShot)
                spinner.setVisibility(View.INVISIBLE);

        if (currentPerson instanceof TownsPerson)
            spinner.setVisibility(View.INVISIBLE);

        if (currentPerson.isDead)
            spinner.setVisibility(View.INVISIBLE);

        ArrayList<String> liveNames = MainActivity.game.getLivePeople();
        if (currentPerson instanceof Vigilante)
            liveNames.add(0, "No one.");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, liveNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void addButtonListener()
    {
        showRoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pressedOnce && MainActivity.game.currentDay != 0 && !(currentPerson instanceof TownsPerson)) {
                    pressedOnce = true;
                    if (MainActivity.game.currentDay == 0) {
                    } else {

                        Game game = MainActivity.game;
                        int spinnerPerson = game.getIndexFromName(spinner.getSelectedItem().toString());
                        textViewShowRole.setText(game.currentPersonTurn(spinnerPerson) + "\nPress OK again to continue.");
                        spinner.setVisibility(View.INVISIBLE);
                        pressedOnce = true;
                    }
                }
                else {
                    pressedOnce = false;
                    if (!MainActivity.game.advanceCurrentPerson())
                        launchBufferActivity();
                    else launchDayActivity();
                }
            }
        });
    }

    public void launchDayActivity()
    {
        Intent intent = new Intent(this, DayActivity.class);
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
