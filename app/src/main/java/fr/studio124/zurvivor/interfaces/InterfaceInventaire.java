package fr.studio124.zurvivor.interfaces;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fr.studio124.zurvivor.R;

/**
 * Created by Quentin on 08/02/2015.
 */
public class InterfaceInventaire extends ActionBarActivity {
    private String choix = null,
            titre = null,
            descriptif = null;

    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_inventaire);

    }

    public void armePrincipale (View view) {
        choix = "Arme principale";

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Create and show the dialog.
        SomeDialog newFragment = new SomeDialog ();
        newFragment.show(ft, "dialog");
    }

    public void armeSecondaire (View view) {
        choix = "Arme secondaire";

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Create and show the dialog.
        SomeDialog newFragment = new SomeDialog ();
        newFragment.show(ft, "dialog");
    }

    public void objet1 (View view) {
        choix = "Objet 1";

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Create and show the dialog.
        SomeDialog newFragment = new SomeDialog ();
        newFragment.show(ft, "dialog");
    }

    public void objet2 (View view) {
        choix = "Objet 2";

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Create and show the dialog.
        SomeDialog newFragment = new SomeDialog ();
        newFragment.show(ft, "dialog");
    }

    public class SomeDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            switch (choix) {
                case "Arme principale" :
                    titre = "Tronçonneuse";
                    descriptif = "Utile pour découper facilement ses ennemis."; break;
                case "Arme secondaire" :
                    titre = "Hachette";
                    descriptif = "Idéal pour sectionner un bras."; break;
                case "Objet 1" :
                    titre = "La Villageoise";
                    descriptif = "Dangereux pour la santé, l'abus d'alcool est conseillé contre la contamination."; break;
                case "Objet 2" :
                    titre = "Chaton";
                    descriptif = "Attention : ceci est un leurre. Il éloigne 4 zombies."; break;
            }

            return new AlertDialog.Builder(getActivity())
                    .setTitle(titre)
                    .setMessage(descriptif)
                    .setNeutralButton("Retour", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    }).create();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_inventaire);
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