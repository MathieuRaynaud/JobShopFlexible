package JobShopFlexible;

public class Sommet {

    /*************************************
     * Un sommet correspond à une activité
     *************************************/

    public String id;
    public int nbpredecesseurs;
    public Arc[] predecesseurs;
    public int indicePredecesseurs;
    public Activite activite;


    // CONSTRUCTEUR
    public Sommet (String nom, int nbpredecesseurs, Activite activite) {
        this.id = nom;
        this.nbpredecesseurs = nbpredecesseurs;
        this.predecesseurs = new Arc[nbpredecesseurs];
        this.indicePredecesseurs = nbpredecesseurs-1;
        this.activite = activite;
    }

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