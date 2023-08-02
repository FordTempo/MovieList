package sg.edu.rp.c346.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle;
    EditText etTitle;

    TextView tvGenre;
    EditText etGenre;

    TextView tvYear;
    EditText etYear;

    Spinner Rating;

    Button btnInsert;
    Button btnShowList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.textTitle);
        etTitle = findViewById(R.id.editTitle);

        tvGenre = findViewById(R.id.textGenre);
        etGenre = findViewById(R.id.editGenre);

        tvYear = findViewById(R.id.textYear);
        etYear = findViewById(R.id.editYear);

        Rating = findViewById(R.id.rating);

        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        ArrayAdapter<CharSequence> ratingAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        ratingAdapter.add("G");
        ratingAdapter.add("PG");
        ratingAdapter.add("PG-13");
        ratingAdapter.add("NC-16");
        ratingAdapter.add("M-18");
        ratingAdapter.add("R-21");

        // Set the layout resource for the dropdown menu
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach the adapter to the Spinner
        Rating.setAdapter(ratingAdapter);

        // Set an item selected listener for the Spinner
        Rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRating = parent.getItemAtPosition(position).toString();
                // You can use the selectedRating here or store it in a variable for later use.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());

                String rating = Rating.getSelectedItem().toString();

                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                db.insertMovie(title, genre, year, rating);

                // Toast Message: To confirm Insertion of movie
                Toast.makeText(MainActivity.this, "Movie Inserted \nMovie Title: " + title + "\nGenre: " + genre +
                        "\nYear: " + year + "\nMovie Rating: " + rating, Toast.LENGTH_LONG).show();

                // Clear the Fields
                etTitle.setText("");
                etGenre.setText("");
                etYear.setText("");
                Rating.setSelection(0);
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MovieList.class);
                startActivity(intent);
            }
        });


    }
}