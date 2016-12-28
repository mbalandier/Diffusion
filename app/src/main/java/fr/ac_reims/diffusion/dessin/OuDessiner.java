package fr.ac_reims.diffusion.dessin;

import android.graphics.Rect;
import android.util.Log;

import fr.ac_reims.diffusion.obstacles.Atome;

/**
 * Cette classe s'occupe de positionner ce qui sera dessiné.
 * Elle permet de prédire la position de chaque atome, après d'éventuelles collisions.
 */
class OuDessiner {
    private static final String TAG = "WhereToDraw";

    private double right;
    private double bottom;

    public OuDessiner(Rect limites) {
        right = limites.right;
        bottom = limites.bottom;
    }

    /**
     * Rôle de la méthode : placer les atomes pour le prochain affichage.
     *
     * Pour prédire les trajectoires, le mouvement de chaque atome est fractionné.
     * Tous les atomes ont un mouvement rectiligne uniforme pendant la durée tMin :
     * temps écoulé avant une collision entre deux obstacles (atome-atome ou atome-bordure).
     *
     * Cette méthode cumule ces déplacements jusqu'à ce que la durée totale = 1 unité.
     */
    void cumulerDeplacement(){
        double dureeCumulee=0;
        do {
            double tMin = plusProche();
            if (dureeCumulee + tMin > 1){
                tMin = 1-dureeCumulee;
            }
            dureeCumulee += tMin;
            deplacerAtomes(tMin);
        } while (dureeCumulee<1);
    }

    private void deplacerAtomes(double tMin){
        int length = QuoiDessiner.getAtomes().size();
        for (int i=0 ; i<length ; i++) {
            Atome atome = QuoiDessiner.getAtomes().get(i);
            double[] P = atome.getPosition();
            double[] V = atome.getVitesse();
            double[] distance = {V[0]*tMin, V[1]*tMin};// distance avant impact.
            P[0] += distance[0];
            P[1] += distance[1];
            atome.setPosition(P);
        }
    }

    private double plusProche(){
        double tMin = 1;
        int length = QuoiDessiner.getAtomes().size();
        for (int i=0 ; i<length ; i++) {
            Atome atome1 = QuoiDessiner.getAtomes().get(i);
            double t1 = prochaineCollisionBordure(atome1);
            if(t1 < tMin){
                tMin = t1;
            }
            for (int j=0 ; j<length-i ; j++) {
                Atome atome2 = QuoiDessiner.getAtomes().get(i+j);
                double t2 = prochaineCollisionEntreAtomes(atome1, atome2);
                if (t2  < tMin) {
                    Log.d(TAG, ">> Collision entre atomes à t = " + t2);
                    tMin = t2;
                }
            }

        }
        return tMin;
    }




    private double prochaineCollisionBordure(Atome atome1){
        double[] P = atome1.getPosition();
        double[] V = atome1.getVitesse();
        double R = atome1.getRayon();
        double time = -1;
        if (V[0] != 0){
            time = V[0] > 0 ? (right - P[0] - R) / V[0] : (R - P[0]) / V[0];
            if (time == 0){
                V[0] *= -1;
                atome1.setVitesse(V);
            }
        }
        if (V[1] != 0){
            double otherTime = V[1] > 0 ? (bottom - P[1] - R) / V[1] : (R - P[1]) / V[1];
            if (otherTime <= 0.00000000001){
                V[1] *= -1;
                atome1.setVitesse(V);
            }
            if (otherTime < time) {
                time = otherTime;
            }
        }
        return time;
    }


    private static double prochaineCollisionEntreAtomes(Atome atome1, Atome atome2){
        double time = 1;

        double[] V1 = atome1.getVitesse();
        double[] V2 = atome2.getVitesse();
        double[] V = {V2[0] - V1[0],V2[1] - V1[1]};

        // Si les atomes n'ont pas de vitesse relative, pas de collision
        if (V[0] != 0 || V[1] != 0) {

            double[] P1 = atome1.getPosition();
            double[] P2 = atome2.getPosition();
            double[] P = {P2[0] - P1[0], P2[1] - P1[1]};

            double R = atome1.getRayon() + atome2.getRayon();

            // Il y a collision lorsque le segment P1'P2' = R
            // On obtient une équation du second degré at² + 2bt + c =0
            double a = V[0]*V[0] + V[1]*V[1];
            double b = (V[0]*P[0] + V[1]*P[1]);
            double c = P[0]*P[0] + P[1]*P[1] - R*R;

            // discriminant réduit : delta = b²-ac
            double delta = b*b -a*c;

            if (delta >= 0) {
                double t1 = (-b - Math.sqrt(delta))/a;
                if (b < 0){
                    time = t1;
                }
                if (time <= 0.00000000001){
                    double M1 = atome1.getMasse();
                    double M2 = atome2.getMasse();
                    double cte = 2*b/(R*R*(M1+M2));
                    double[] V1prime = {V1[0]+M2*cte*P[0], V1[1]+M2*cte*P[1]};
                    double[] V2prime = {V2[0]-M1*cte*P[0], V2[1]-M1*cte*P[1]};
                    atome1.setVitesse(V1prime);
                    atome2.setVitesse(V2prime);
                    Log.d(TAG, ">> V1 = {" + V1[0] + " ; " + V1[1] + "} ; V2 = {" + V2[0] + " ; " + V2[1] + "}");
                    Log.d(TAG, ">> V1' = {" + V1prime[0] + " ; " + V1prime[1] + "} ; V2' = {" + V2prime[0] + " ; " + V2prime[1] + "}");
                }
            }
        }
        return time;
    }

}