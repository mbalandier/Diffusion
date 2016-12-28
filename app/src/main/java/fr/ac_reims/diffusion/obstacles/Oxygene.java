package fr.ac_reims.diffusion.obstacles;

public class Oxygene extends Atome{

    public Oxygene(double[] position, double[] speed) {
        super(position, speed, "red", 140/5, 16);// rayon de van der Waals (en pm) /5
    }

}
