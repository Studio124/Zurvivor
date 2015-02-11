package fr.studio124.zurvivor.interfaces;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import fr.studio124.zurvivor.R;

/**
 * Created by Quentin on 06/02/2015.
 */
public class InterfaceChat extends ActionBarActivity {

    private Intent intent = null;

    private MediaPlayer mPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_chat);

        playSound(R.raw.chat);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_chat);
        item.setEnabled(false);
        this.invalidateOptionsMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id != 0) {
            switch (id) {
                case R.id.action_carte : {
                    intent = new Intent(getApplicationContext(), InterfaceCarte.class);
                    startActivity(intent);
                } break;
                case R.id.action_profil : {
                    intent = new Intent(getApplicationContext(), InterfaceProfil.class);
                    startActivity(intent);
                } break;
                case R.id.action_inventaire : {
                    intent = new Intent(getApplicationContext(), InterfaceInventaire.class);
                    startActivity(intent);
                } break;
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer = null;
    }
}
