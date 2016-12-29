package fr.ac_reims.diffusion.dessin;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;

/**
 * Responsabilité de la classe : Etablir une procédure pour dessiner l'image.
 */
public class Dessinateur extends Handler{

    private QuoiDessiner quoiDessiner;
    private OuDessiner ouDessiner;
    private final CommentDessiner commentDessiner = new CommentDessiner();
    private final QuandDessiner quandDessiner = new QuandDessiner();
    private long numeroImage;

    /**
     * Procédure pour dessiner l'image
     */
    public long dessiner(Canvas canvas){
        Rect limites = canvas.getClipBounds();

        if(quoiDessiner == null) {                // 1) Que dois-je dessiner ? (Quoi dessiner ?)
            quoiDessiner = new QuoiDessiner(limites);
            ouDessiner = new OuDessiner(limites);
        }

        ouDessiner.cumulerDeplacement();          // 2) Où dois-je dessiner ces éléments ?
        commentDessiner.afficher(canvas);         // 3) Comment dois-je les dessiner ? (couleurs ?)

        numeroImage++;
        return quandDessiner.getDelai(numeroImage); // 4) Quand dois-je dessiner ?
    }
}
