package JobShopFlexible;

public class Sommet {

    public String id;
    public int nbsuccesseurs;
    public Arc[] successeurs;


    // CONSTRUCTEUR
    public Sommet (String nom, int nbsuccesseurs) {
        this.id = nom;
        this.nbsuccesseurs = nbsuccesseurs;
        this.successeurs = new Arc[nbsuccesseurs];
    }

    public Sommet () {}

}