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

    /***
     * Fonction de mise à jour du cout de l'arc
     ***/
    public Integer modifierArc(Sommet depart, Integer nouveauCout){
        Integer res = -1;
        for(Arc a: predecesseurs){
            if(a.sommetDepart == depart){
                a.duree = nouveauCout;
                res = 0;
            }
        }
        return res;
    }

    /***
     * Fonction d'ajout d'un arc
     ***/
    public Integer ajouterArc(Sommet depart, Integer cout){
        if (indicePredecesseurs == -1){
            System.out.println("Impossible d'ajouter un successeur, nombre maximal atteint");
            return -1;
        }

        this.predecesseurs[indicePredecesseurs] = new Arc(depart, cout);
        indicePredecesseurs--;
        return 0;
    }
}