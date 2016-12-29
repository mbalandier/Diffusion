package fr.ac_reims.diffusion;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import fr.ac_reims.diffusion.dessin.Dessinateur;

/**
 * Responsabilité de la classe : Mesurer les dimensions de la vue et en dessiner l'intérieur.
 * Dès l'objet créé, il crée à son tour un objet "Dessinateur" à qui il délègue la partie dessin.
 */
public class VuePrincipale extends View implements Runnable{
    private final Dessinateur dessinateur = new Dessinateur();

    public VuePrincipale(Context context) {
        super(context);
    }

    /**
     * Méthode appelée lorsque cette Vue doit afficher quelquechose à l'écran.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        long delai;

        do {
            delai = dessinateur.dessiner(canvas);
        } while (delai<0); //pas d'attente et ni d'affichage en cas de délai périmé (délai<0).

        postDelayed(this, delai); // l'image programmée sera affichée une fois le délai écoulé.
    }

    /**
     * Méthode appelée une fois le délai écoulé pour réactualiser l'affichage
     * Cela relance aussi la méthode onDraw(...)
     */
    @Override
    public void run(){
        invalidate();   // Méthode qui réactualise l'affichage du Canvas et de tout ce qu'il contient.
    }
}
