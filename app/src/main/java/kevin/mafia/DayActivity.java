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


public class DayActivity extends AppCompatActivity {

    Game game;
    Button dayBtn;
    TextView textDay;
    Spinner daySpinner;
    String gameOver;
    String murders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        game = MainActivity.game;
        murders = game.dayTime();
        gameOver = game.gameOver();

        dayBtn = (Button) findViewById(R.id.dayBtn);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        textDay = (TextView) findViewById(R.id.textDay);

        if (game.currentDay == 1 && gameOver.equals("The game is still in progress."))
            textDay.setText("Day 1.  Who will be killed today?");
        else if (gameOver.equals("The game is still in progress"))
            textDay.setText("Day " + game.currentDay + "\n" + gameOver + "\n" + murders + "\nWho will be killed today?");
        else textDay.setText("Day " + game.currentDay + "\n" + gameOver + "\n" + murders);

        ArrayList<String> liveNames = MainActivity.game.getLivePeople();
            liveNames.add(0, "No one.");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, liveNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dataAdapter);

        if (!gameOver.equals("The game is still in progress."))
            daySpinner.setVisibility(View.INVISIBLE);
        addButtonListener();
    }

    public void onDestroy(Bundle savedInstanceState) {
        MainActivity.game = null;
    }

    public void addButtonListener()
    {
        dayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.resetFlags();
                if (!gameOver.equals("The game is still in progress.")) {
                    game.reset();
                    launchMainActivity();
                }
                else {
                    int spinnerPerson = game.getIndexFromName(daySpinner.getSelectedItem().toString());
                    game.kill(spinnerPerson);
                    if (!game.gameOver().equals("The game is still in progress."))
                        launchDayActivity();
                    else launchBufferActivity();
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

    public void launchMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void launchBufferActivity()
    {
        Intent intent = new Intent(this, BufferActivity.class);
        startActivity(intent);
        finish();
    }
}
