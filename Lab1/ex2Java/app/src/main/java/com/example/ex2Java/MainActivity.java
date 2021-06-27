package com.example.ex2Java;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex2Java.R;

import java.util.HashMap;
import java.util.Map;

import static com.example.ex2Java.Figure.*;

/*
 other version of using enum
enum  Figure {

    rock, paper, scissors;

    private static final Map<Integer, Figure> BY_LABEL = new HashMap<>();

    static {
        int i = 0;
        for (Figure f : values()) {
            BY_LABEL.put(i, f);
            i++;
        }
    }

    // ... fields, constructor, methods

    public static Figure valueOfIndex(Integer index) {
        return BY_LABEL.get(index);
    }
}
*/
enum  Figure {
    rock, paper, scissors;
}
public class MainActivity extends AppCompatActivity {
    private int score;
    private int random;
    private Figure[] figures;
    private Figure computerInput;
    //initialize TextView
    private TextView scoreText;
    private TextView playerChoice;
    private ImageView computersChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreText = (TextView)findViewById(R.id.score);
        playerChoice =  (TextView)findViewById(R.id.playersChoice);
        computersChoice = (ImageView)findViewById(R.id.computersChoice);
        figures = Figure.values();
        score = 0;
        random = 0;
        //Min + (int)(Math.random() * ((Max - Min) + 1))
    }
    private  void roll(){
        scoreText.setText("Score:\n"+score);
        playerChoice.setTextColor(Color.RED);
        playerChoice.setTextSize(TypedValue.COMPLEX_UNIT_SP,24F);
    }

    public void rockButtonHandler(View view){
        random = (int)(Math.random() * 3);
        computerInput = figures[random];
        switch(computerInput){
            case rock:
                computersChoice.setImageResource(R.drawable.rock);
                break;

            case paper:
                computersChoice.setImageResource(R.drawable.paper);
                score--;
                break;
            case scissors:
                score++;

        }
        roll();
        playerChoice.setText("Your choice:\n"+"Rock");
    }
    public void paperButtonHandler(View view){
        random = (int)(Math.random() * 3);
        computerInput = figures[random];
        switch (computerInput){
            case rock:
                computersChoice.setImageResource(R.drawable.rock);
                score++;
                break;
            case paper:
                computersChoice.setImageResource(R.drawable.paper);
                break;

            case scissors:
                computersChoice.setImageResource(R.drawable.scissors);
                score--;
                break;
        }
        roll();
        playerChoice.setText("Your choice:\n"+"Paper");
    }

    public void scissorsButtonHandler(View view){
        random = (int)(Math.random() * 3);
        computerInput = figures[random];
        switch(computerInput){
            case rock:
                computersChoice.setImageResource(R.drawable.rock);
                score--;
                break;
            case paper:
                computersChoice.setImageResource(R.drawable.paper);
                score++;
                break;
            case scissors:
                computersChoice.setImageResource(R.drawable.scissors);
                break;
        }
        roll();
        playerChoice.setText("Your choice:\n"+"Scissors");
    }
}