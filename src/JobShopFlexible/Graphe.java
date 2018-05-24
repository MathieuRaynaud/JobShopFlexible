package JobShopFlexible;

import java.util.ArrayList;

public class Graphe {

    public int indiceSommets;
    public ArrayList<Sommet> ensembleSommets;
    public ArrayList<Integer> datesDebutAuPlusTot;

    /*
     * Constructeur
     */

    public Graphe(){
        this.indiceSommets = -1;
    }

    public int ajouterSommet (Sommet sommet){
        ensembleSommets.add(sommet);
        indiceSommets++;
        return 0;
    }

    /***
     * Fonction de mise à jour du cout de l'arc
     */
    public Integer modifierArc(Sommet depart, Sommet arrivee, Integer nouveauCout){
        arrivee.modifierArc(depart,nouveauCout);
        return 0;
    }

    /***
     * Fonction d'ajout d'un arc
     */
    public Integer ajouterArc(Sommet depart, Sommet arrivee, Integer cout){
        arrivee.ajouterArc(depart,cout);
        return 0;
    }

    /**
     * TODO : Calcul du cmax du graphe + appel a une fonction de calcul de la date de debut au plus tot (+ detection de cycles --> solution avec -2)
     */

    /*
    ** Fonction récursive de calcul de la date au plus tot d'un sommet
     */

    public Integer dateAuPlusTot(Sommet sommet){
        Integer result = 0;
        if(datesDebutAuPlusTot.get(ensembleSommets.indexOf(sommet))!=-1){
            result = datesDebutAuPlusTot.get(ensembleSommets.indexOf(sommet));
        }
        else{
            for(Arc a : sommet.predecesseurs){
                result = Integer.max(dateAuPlusTot(a.sommetDepart),result);
            }
        }
        return result;
    }
    public Integer cMax(){
        Integer cmax = 0;

        return cmax;
    }
}