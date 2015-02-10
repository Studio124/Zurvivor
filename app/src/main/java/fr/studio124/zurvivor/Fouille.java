package fr.studio124.zurvivor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Quentin on 09/02/2015.
 */
public class Fouille implements GoogleMap.OnMarkerClickListener {

    private Context sourceContext;

    private String choix;

    private Marker userMarker = null,
                    placeMarker = null;

    public Fouille (Context c) {
        sourceContext = c;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        placeMarker = marker;

        if (marker.getTitle().contains("Vous")) {
            getProfil();
        } else {
            if (getDistance()) {
                getFouille();
            } else {
                tropLoin();
            }
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

    private void tropLoin() {
        AlertDialog.Builder distanceBuilder = new AlertDialog.Builder(sourceContext);

        distanceBuilder
                .setMessage("Vous êtes trop loin pour pouvoir fouiller.")
                .setIconAttribute(R.drawable.zomb_profil)
                .setCancelable(false)
                .setTitle("Et non petit tricheur !")
                .setNegativeButton("Fermer ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                paramDialogInterface.cancel();
                            }
                        }
                );
        distanceBuilder.create().show();
    }

    private boolean getDistance() {
        float[] distance = new float[1];
        boolean result = false;

        Location.distanceBetween(userMarker.getPosition().latitude, userMarker.getPosition().longitude,
                placeMarker.getPosition().latitude, placeMarker.getPosition().longitude, distance);

        if (distance[0] <= 800) {
            result = true;
        }

        return result;
    }

    public void setChoix(String c) {
        choix = c;
    }

    public void setUserPosition (Marker m) {
        userMarker = m;
    }
}
