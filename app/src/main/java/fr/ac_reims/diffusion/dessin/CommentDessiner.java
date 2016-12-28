package fr.ac_reims.diffusion.dessin;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import fr.ac_reims.diffusion.obstacles.Atome;

/**
 * Responsabilité de la classe : Afficher les couleurs et les formes.
 */
class CommentDessiner {

    private final Paint fond = new Paint();
    private final Paint atomePaint = new Paint();

    CommentDessiner() {
        fond.setColor(Color.WHITE);
        atomePaint.setStyle(Style.FILL);
    }

    void afficher(Canvas canvas){
        canvas.drawPaint(fond);

        int length = QuoiDessiner.getAtomes().size();
        for (int i=0 ; i<length ; i++) {
            Atome atome = QuoiDessiner.getAtomes().get(i);
            String c = atome.getColor();
            int color = Color.parseColor(c);
            atomePaint.setColor(color);
            double[] P = atome.getPosition();
            double R = atome.getRayon();
            canvas.drawCircle((float)P[0], (float)P[1], (float)R, atomePaint);
        }

    }

}
