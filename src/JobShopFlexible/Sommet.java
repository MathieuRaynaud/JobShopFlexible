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

    public int ajouterPredecesseur(Sommet predecesseur, int duree){

        if (indicePredecesseurs == -1){
            System.out.println("Impossible d'ajouter un successeur, nombre maximal atteint");
            return -1;
        }

        this.predecesseurs[indicePredecesseurs] = new Arc(predecesseur, duree);
        indicePredecesseurs--;

        return 0;
    }

    /***
     * TODO : Fonction de mise à jour du cout de l'arc
     */
    public Integer modifierArc(Sommet depart, Integer nouveauCout){
        return 0;
    }

    /***
     * TODO : Fonction d'ajout d'un arc
     */
    public Integer ajouterArc(Sommet depart, Integer cout){
        return 0;
    }
}