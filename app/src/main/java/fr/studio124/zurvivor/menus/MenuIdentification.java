package fr.studio124.zurvivor.menus;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import fr.studio124.zurvivor.PermissionGps;
import fr.studio124.zurvivor.R;

/**
 * Created by Quentin on 06/02/2015.
 */
public class MenuIdentification extends ActionBarActivity {

    final String loginEdit = "user_login";
    final String passwordEdit = "user_password";

    private Intent intent = null;

    private MediaPlayer mPlayer = null;

    private int mainMusic;

    private boolean pause = false;

    private RelativeLayout identification = null,
                            creation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_identification);

        identification = (RelativeLayout) findViewById(R.id.identification);
        creation = (RelativeLayout) findViewById(R.id.creation);

        playSound(R.raw.accueil, true);

        mainMusic = R.raw.accueil;

        /** Récupère le locationManager qui gère la localisation */
        LocationManager locManager;
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /** Test si le gps est activé ou non */
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            /** on lance notre activity (qui est une dialog) */
            Intent localIntent = new Intent(this, PermissionGps.class);
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(localIntent);
        }

        final EditText menuIdentifiantEditTextLogin = (EditText) findViewById(R.id.menu_identifictaion_editText_identification);
        final EditText menuIdentifiantEditTextPassword = (EditText) findViewById(R.id.menu_identification_editText_motDePasse);

        intent = getIntent();

        if (intent != null) {
            menuIdentifiantEditTextLogin.setText(intent.getStringExtra(loginEdit));
            menuIdentifiantEditTextPassword.setText(intent.getStringExtra(passwordEdit));
        }
    }

    // Bouton Jouer du menu d'identification
    public void jouer(View view) {
        playSound(R.raw.jouer, false);

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), fr.studio124.zurvivor.interfaces.InterfaceCarte.class);
        startActivity(intent);

        // close this activity
        finish();
    }

    // Bouton Créer du menu d'identification
    public void creer(View view) {
        identification.setVisibility(View.INVISIBLE);
        creation.setVisibility(View.VISIBLE);
    }

    // Bouton Retour du menu d'identification
    public void retour(View view) {
        identification.setVisibility(View.VISIBLE);
        creation.setVisibility(View.INVISIBLE);
    }

    private void playSound(int resId, boolean main) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }

        mPlayer = MediaPlayer.create(this, resId);
        if (main)
            mPlayer.setLooping(true);
        mPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.setLooping(false);
            mPlayer.release();
            pause = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pause) {
            mPlayer = MediaPlayer.create(this, mainMusic);
            mPlayer.setLooping(true);
            mPlayer.start();
            pause = false;
        }
    }
}
