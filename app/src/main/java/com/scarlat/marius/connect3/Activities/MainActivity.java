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
import com.scarlat.marius.connect3.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        game.initialize();
        LinearLayout linearLayout  = (LinearLayout) findViewById(R.id.playAgainLayout);
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public void dropIn (View view) {

        if (!game.isActiveGame()) {
            return;
        }

        ImageView counter = (ImageView) view;
        int tagNumber;

        // get current index in the gameState
        try {
            tagNumber = Integer.parseInt(view.getTag().toString());
            Log.d(TAG, "dropIn: Current cell = (" + tagNumber / 3 + ", " + tagNumber % 3 + ")");
        } catch (NumberFormatException e) {
            Log.e(TAG, "dropIn: ", e);
            return;
        }

        // update only unset cells
        if (game.getGameState()[tagNumber] == 2) {
            game.updateGameState(tagNumber, game.getActivePlayer().getId());
            animateCell(counter);
            game.changePlayer();
        }

        String message = game.checkState();
        if (!message.equals("")) {
            displayWinningLayout(message);
            game.setActiveGame(false);
        }
    }

    public void playAgain(View view) {
        initialize();
        clearTable();
    }

    private void animateCell(ImageView counter) {

        float translateY = 1000f, rotationAngle = 360f;
        int animationDuration = 300;

        // Animate the translation and update player
        counter.setTranslationY(-translateY);
        if (game.getActivePlayer().getId() == 0) {
            counter.setImageResource(R.drawable.yellow);
        } else {
            counter.setImageResource(R.drawable.red);
        }
        counter.animate()
                .translationYBy(translateY)
                .rotationBy(rotationAngle)
                .setDuration(animationDuration);
    }

    private void displayWinningLayout(String message) {
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        winnerMessage.setText(message);

        LinearLayout linearLayout  = (LinearLayout) findViewById(R.id.playAgainLayout);
        if (game.getActivePlayer().getId() == 0) {
            linearLayout.setBackgroundColor(Color.rgb(255, 247, 117));
        } else {
            linearLayout.setBackgroundColor(Color.rgb(254, 99, 104));
        }
        linearLayout.setAlpha(0.85f);
        linearLayout.setVisibility(View.VISIBLE);
    }

    private void clearTable() {
        GridLayout layout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < layout.getChildCount(); ++i) {
            View v = layout.getChildAt(i);
            if (v instanceof ImageView) {
                ((ImageView) v).setImageResource(0);
            }
        }
    }

}
