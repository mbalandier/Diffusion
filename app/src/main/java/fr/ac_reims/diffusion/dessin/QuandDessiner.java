package fr.ac_reims.diffusion.dessin;

/**
 * Cette classe s'occupe du temps et du délai d'affichage.
 */
import android.os.Handler;
import android.util.Log;

import java.util.Calendar;

class QuandDessiner extends Handler{
    private static final String TAG = "WhenToDraw";
    private long t0; // date initiale de la première image affichée.

    long getDelai(long numeroImage){
        Calendar calendar = Calendar.getInstance();
        long t; // durée écoulée depuis la première image.
        long persistence = 30; // durée (en ms) théorique entre deux images successives.
        long delai; // temps à attendre avant d'afficher la prochaine image.

        if (numeroImage == 1){
            t0 = calendar.getTimeInMillis();
            delai = persistence;
        }else{
            t = calendar.getTimeInMillis() - t0;
            delai = numeroImage* persistence - t;
        }

        if(delai<0){
            Log.w(TAG, ">> Image non affichée");
        }
        return delai;
    }
}
