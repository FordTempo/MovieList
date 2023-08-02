package sg.edu.rp.c346.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MovieList extends AppCompatActivity {
    ListView lvMovies;
    Button btnReturn, btnRating;
    Spinner spnRating;

    CustomAdapter adapterCustom; // Custom adapter for the ListView
    ArrayList<Movie> customList; // ArrayList to hold the movies for the custom adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lvMovies = findViewById(R.id.lvMovies);
        btnReturn = findViewById(R.id.btnBack);
        btnRating = findViewById(R.id.btnFilterByRating);

        spnRating = findViewById(R.id.spnRating);

        DBHelper db = new DBHelper(MovieList.this);

        ArrayList<String> distinctRatings = db.getDistinctRating();
        distinctRatings.add(0, "All movies"); // Add "All" option at the beginning
        ArrayAdapter<String> ratingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, distinctRatings);
        ratingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRating.setAdapter(ratingsAdapter);

        ArrayList<Movie> movies = db.getMovies();
        db.close();

        customList = new ArrayList<>(movies);

        adapterCustom = new CustomAdapter(this, R.layout.row, customList);
        lvMovies.setAdapter(adapterCustom);

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie data = customList.get(position); // Retrieve the selected Movie object
                Intent i = new Intent(MovieList.this, MovieAction.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedRating = spnRating.getSelectedItem().toString();

                if (selectedRating.equals("All movies")) {
                    // Display all movies
                    customList.clear();
                    customList.addAll(movies);
                } else {
                    // Filter movies by the selected rating
                    customList.clear();
                    for (Movie movie : movies) {
                        if (selectedRating.equals(movie.getRating())) {
                            customList.add(movie);
                        }
                    }
                }
                adapterCustom.notifyDataSetChanged();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieList.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}