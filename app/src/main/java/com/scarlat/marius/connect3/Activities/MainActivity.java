package com.scarlat.marius.connect3.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scarlat.marius.connect3.GameDesign.Game;
import com.scarlat.marius.connect3.GameDesign.Player;
import com.scarlat.marius.connect3.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GridLayout gridLayout;
    private LinearLayout linearLayout;
    private TextView winnerMessage;
    private ImageView currentCell;

    private boolean firstContact;
    Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        firstContact = true;
        initializeActivity();
    }

    private void initializeActivity() {

        if (firstContact) {
            gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            winnerMessage = (TextView) findViewById(R.id.winnerMessage);
            linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
            firstContact = false;
        }

        game.initialize();
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public void dropIn (View view) {

        if (!game.isActiveGame()) {
            return;
        }
        currentCell = (ImageView) view;

        // get current index in the game table
        int tagNumber;
        try {
            tagNumber = Integer.parseInt(view.getTag().toString());
            Log.d(TAG, "dropIn: Current cell = (" + tagNumber / 3 + ", " + tagNumber % 3 + ")");
        } catch (NumberFormatException e) {
            Log.e(TAG, "dropIn: ", e);
            return;
        }

        // update only unset cells
        if (game.getCell()[tagNumber] == 2) {
            updateCellContent(tagNumber);
        }
    }

    public void playAgain(View view) {
        initializeActivity();
        clearTable();
    }

    private void updateCellContent(int tagNumber) {
        game.updateGameState(tagNumber, game.getActivePlayer().getId());
        animateCell();

        String message = game.getGameState();

        if (!message.equals("")) {  /* Winning or DRAW */
            displayWinningLayout(message);
            game.setActiveGame(false);
        } else {
            game.changePlayer();
        }
    }

    private void animateCell() {

        float translateY = 1000f, rotationAngle = 360f;
        int animationDuration = 300;

        // Animate the cell and update player
        currentCell.setTranslationY(-translateY);
        if (game.getActivePlayer().getId() == 0) {
            currentCell.setImageResource(R.drawable.yellow);
        } else {
            currentCell.setImageResource(R.drawable.red);
        }
        currentCell.animate()
                .translationYBy(translateY)
                .rotationBy(rotationAngle)
                .setDuration(animationDuration);
    }

    private void displayWinningLayout(String message) {
        winnerMessage.setText(message);

        int color = Color.rgb(145, 221, 237);
        Player winner = game.getWinner();

        if (winner != null) {
            color = winner.getColor();
        }

        linearLayout.setBackgroundColor(color);
        linearLayout.setAlpha(0.85f);
        linearLayout.setVisibility(View.VISIBLE);
    }

    private void clearTable() {
        for (int i = 0; i < gridLayout.getChildCount(); ++i) {
            View v = gridLayout.getChildAt(i);
            if (v instanceof ImageView) {
                ((ImageView) v).setImageResource(0);
            }
        }
    }

}
