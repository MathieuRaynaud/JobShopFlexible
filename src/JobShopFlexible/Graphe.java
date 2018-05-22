package JobShopFlexible;

import java.util.ArrayList;

public class Graphe {

    public int indiceSommets;
    public ArrayList<Sommet> ensembleSommets;
    public ArrayList<Integer> dates_debut_au_plus_tot;

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
    public Integer cMax(){
        return 0;
    }
}