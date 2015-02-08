package fr.studio124.zurvivor.menus;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.studio124.zurvivor.R;

/**
 * Created by Quentin on 06/02/2015.
 */
public class MenuCreationCompte extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_creation_compte);

        // Récupération des éléments de la page
        final TextView menuCreationCompteBoutonCréer = (TextView) findViewById(R.id.menu_creation_compte_bouton_creer);

        final EditText loginEdit = (EditText) findViewById(R.id.menu_creation_compte_editText_pseudo);
        final EditText paysEdit = (EditText) findViewById(R.id.menu_creation_compte_editText_pays);
        final EditText mailEdit = (EditText) findViewById(R.id.menu_creation_compte_editText_mail);
        final EditText passwordEdit = (EditText) findViewById(R.id.menu_creation_compte_editText_motDePasse);
        final EditText confirmationEdit = (EditText) findViewById(R.id.menu_creation_compte_editText_confirmation);

        // Quand on clique sur le bouton "Créer"
        menuCreationCompteBoutonCréer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Vérifie si un des champs est vide
                if ( verifChampsVide(loginEdit)
                        || verifChampsVide(paysEdit) || verifChampsVide(mailEdit)
                        || verifChampsVide(passwordEdit) || verifChampsVide(confirmationEdit) ) {

                    Toast.makeText(MenuCreationCompte.this, "Veuillez renseigner tous les champs svp !", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Vérifie si certains champs contiennent un espace
                    if ( verifEspace(loginEdit) || verifEspace(mailEdit) || verifEspace(passwordEdit) ) {

                        Toast.makeText(MenuCreationCompte.this, "Attention, ne pas mettre d'espace dans le pseudo, le mail et le mot de passe svp.", Toast.LENGTH_LONG).show();
                        passwordEdit.setText("");
                        confirmationEdit.setText("");
                    }
                    else {
                        // Vérifie si le mot de passe correspond à la confirmation
                        if (passwordEdit.getText().toString().equals(confirmationEdit.getText().toString())) {

                        }
                        else {
                            Toast.makeText(MenuCreationCompte.this, "Les mots de passes ne correspondent pas.", Toast.LENGTH_LONG).show();
                            passwordEdit.setText("");
                            confirmationEdit.setText("");
                        }
                    }
                }
            }
        });
    }

    // Vérifie si le champs est vide
    private boolean verifChampsVide(EditText edit) {

        boolean champs = true;

        if (edit.getText().toString().trim().equals("")) {
            champs = true;
        }
        else {
            champs = false;
        }

        return champs;
    }

    // Vérifie si le champs contient un espace
    private boolean verifEspace(EditText edit) {

        boolean espace = false;

        if (edit.getText().toString().contains(" ") || edit.getText().toString().contains("	")) {
            espace = true;
        }
        else {
            espace = false;
        }

        return espace;
    }
}
