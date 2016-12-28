package fr.ac_reims.diffusion.obstacles;

public class Azote extends Atome{

    public Azote(double[] position, double[] speed) {
        super(position, speed, "blue", 150/5, 14);// rayon de van der Waals (en pm) /5
    }
}
