package fr.studio124.zurvivor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import fr.studio124.zurvivor.interfaces.InterfaceProfil;

/**
 * Created by Quentin on 09/02/2015.
 */
public class Fouille implements GoogleMap.OnMarkerClickListener {

    private Context sourceContext;

    private String choix;

    public Fouille (Context c) {
        sourceContext = c;
    }

    public void setChoix(String c) {
        choix = c;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.getTitle().contains("Votre position")) {
            getProfil();
        } else {
            getFouille();
        }

        return true;

    }

    private void getFouille() {
        AlertDialog.Builder fouilleBuilder = new AlertDialog.Builder(sourceContext);
        final String accept = "Vous avez un nouvel objet : " + choix;

        fouilleBuilder
                .setMessage("Vous venez de trouver un nouvel objet : " + choix)
                .setCancelable(false)
                .setPositiveButton("Prendre ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                Toast toast = Toast.makeText(sourceContext,  accept, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                );
        fouilleBuilder.setNegativeButton("Laisser ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.cancel();
                    }
                }
        );
        fouilleBuilder.create().show();
    }

    private void getProfil() {
        AlertDialog.Builder profilBuilder = new AlertDialog.Builder(sourceContext);

        ImageView image = new ImageView(sourceContext);
        image.setImageResource(R.drawable.zomb_profil);

        profilBuilder
                .setMessage("Lingals")
                .setIconAttribute(R.drawable.zomb_profil)
                .setCancelable(false)
                .setView(image)
                .setNegativeButton("Fermer ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                paramDialogInterface.cancel();
                            }
                        }
                );
        profilBuilder.create().show();
    }
}
