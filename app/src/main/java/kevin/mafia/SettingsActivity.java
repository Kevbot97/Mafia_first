package kevin.mafia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import static kevin.mafia.MainActivity.game;


public class SettingsActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button removeBtn, editBtn, newBtn, submitBtn;
    private EditText numberOfMafiaEditText;
    private Context context = this;
    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        addNamesToSpinner();
        getButtons();
        addNewButtonListener();
        addRemoveButtonListener();
        addSubmitButtonListener();
        if (MainActivity.game == null)
            MainActivity.game = new Game();
        else
        {
            ArrayList<String> tempNames = MainActivity.game.names;
            MainActivity.game = new Game(tempNames);
        }
    }

    public void onDestroy(Bundle savedInstanceState) {
        MainActivity.game = null;
    }

    public void addNamesToSpinner()
    {
        names = game.names;
        spinner = (Spinner) findViewById(R.id.spinner);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void getButtons()
    {
        removeBtn = (Button) findViewById(R.id.removeBtn);
        editBtn = (Button) findViewById(R.id.editBtn);
        newBtn = (Button) findViewById(R.id.newBtn);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        numberOfMafiaEditText = (EditText) findViewById(R.id.editTextMafia);
    }

    public void addNewButtonListener() {
        newBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder alert;
                alert = new AlertDialog.Builder(context);

                alert.setTitle("Add New Townsperson");
                alert.setMessage("Please enter the name of your new townsperson: ");

                // Set an EditText view to get user input
                final EditText input = new EditText(context);
                alert.setView(input);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        names.add(input.getText().toString());
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }

        });
    }

    public void addRemoveButtonListener() {
        removeBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder alert;
                alert = new AlertDialog.Builder(context);

                alert.setTitle("Remove Townsperson");
                alert.setMessage("Remove " + names.get(spinner.getSelectedItemPosition()) + "?");

                alert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        names.remove(spinner.getSelectedItemPosition());
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }

        });
    }

    public void addEditButtonListener()
    {
        // implement this later.
    }

    public void addSubmitButtonListener()
    {
        submitBtn.setOnClickListener(new OnClickListener() {

            // when submit button is clicked, number of mafia is processed,
            // and app goes back to MainActivity.
            public void onClick(View v) {
                try {
                    int temp = Integer.parseInt(numberOfMafiaEditText.getText().toString());
                    if (temp >= 1)
                        game.numMafia = temp;
                }
                catch (Exception e) {
                    game.numMafia = 1;
                }
                launchMainActivity();
            }
        });
    }

    public void launchMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
