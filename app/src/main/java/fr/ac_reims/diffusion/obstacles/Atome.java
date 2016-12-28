package fr.ac_reims.diffusion.obstacles;

/**
 * Rôle : Contenir les champs qui définissent un atome.
 */
public abstract class Atome {
    private double[] position = {0,0};
    private double[] vitesse = {20,10};
    private final double rayon;
    private final double masse;
    private String color;

    public double getMasse() {
        return masse;
    }

    Atome(double[] position, double[] vitesse, String color, double rayon, double masse) {
        this.position = position;
        this.vitesse = vitesse;
        this.color = color;
        this.rayon = rayon;
        this.masse = masse;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getVitesse() {
        return vitesse;
    }

    public void setVitesse(double[] speed) {
        this.vitesse = speed;
    }

    public double getRayon() {
        return rayon;
    }

    public String getColor() {
        return color;
    }

}
