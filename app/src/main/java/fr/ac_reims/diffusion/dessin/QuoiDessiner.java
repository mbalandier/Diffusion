package fr.ac_reims.diffusion.dessin;

import android.graphics.Rect;
import android.util.SparseArray;

import fr.ac_reims.diffusion.obstacles.Atome;
import fr.ac_reims.diffusion.obstacles.Azote;
import fr.ac_reims.diffusion.obstacles.Oxygene;

/**
 * Responsabilité de la classe : Créer les atomes.
 */
class QuoiDessiner {
    private static SparseArray<Atome> atomes = new SparseArray<>();
    private final int vMax = 10;

    QuoiDessiner(Rect limites) {
        int nbColonnes = 4;
        int nbLignes = 10;

        for(int i=0 ; i<nbColonnes ; i++){
            for(int j=0 ; j<nbLignes ; j++){
                double[] position = {(i+1)*limites.right/(nbColonnes+1),  (j+1)*limites.bottom/(nbLignes+1)};
                int indice = i*nbLignes+j;
                double [] vitesse = {nbAleatoire(vMax), nbAleatoire(vMax)};

                if(j>nbLignes/2 - 1){
                    atomes.append(indice, new Oxygene(position, vitesse));
                }else{
                    atomes.append(indice, new Azote(position, vitesse));
                }
            }
        }
    }

    static SparseArray <Atome> getAtomes(){
        return atomes;
    }

    /**
     * Cette méthode génère un nombre décimal aléatoire dans l'intervalle : ]-max ; +max[
     */
    private static double nbAleatoire(int max){
        return Math.random() * (2*max) - max;
    }
}
