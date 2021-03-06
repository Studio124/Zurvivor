package fr.studio124.zurvivor.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import fr.studio124.zurvivor.R;

/**
 * Created by Quentin on 08/02/2015.
 */
public class InterfaceProfil extends ActionBarActivity {
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_profil);

        final TextView renseignements = (TextView) findViewById(R.id.profil_renseignements);

        renseignements.setText("NAME :\r\nLingals\r\n\r\n" +
                "AGE :\r\n26\r\n\r\n" +
                "COUNTRY :\r\nFrance\r\n\r\n" +
                "TIME TO SURVIVE :\r\n4 days\r\n");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_profil);
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
                case R.id.action_inventaire : {
                    intent = new Intent(getApplicationContext(), InterfaceInventaire.class);
                    startActivity(intent);
                } break;
                case R.id.action_chat : {
                    intent = new Intent(getApplicationContext(), InterfaceChat.class);
                    startActivity(intent);
                } break;
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }

        return true;
    }
}
