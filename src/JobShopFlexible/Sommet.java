package JobShopFlexible;

public class Sommet {

    public String id;
    public int nbpredecesseurs;
    public Arc[] predecesseurs;
    public int indicePredecesseurs;


    // CONSTRUCTEUR
    public Sommet (String nom, int nbpredecesseurs) {
        this.id = nom;
        this.nbpredecesseurs = nbpredecesseurs;
        this.predecesseurs = new Arc[nbpredecesseurs];
        this.indicePredecesseurs = nbpredecesseurs-1;
    }

    public Sommet () {}

    /*
     * Méthodes
     */

    public int ajouterPredecesseur(Arc predecesseur){

        if (indicePredecesseurs == -1){
            System.out.println("Impossible d'ajouter un successeur, nombre maximal atteint");
            return -1;
        }

        this.predecesseurs[indicePredecesseurs] = predecesseur;
        indicePredecesseurs--;

        return 0;
    }
}