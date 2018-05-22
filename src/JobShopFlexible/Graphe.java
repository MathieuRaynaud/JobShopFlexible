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
    }

    public int ajouterSommet (Sommet sommet){



        return 0;
    }

    public Sommet findSommetByID(String id){
        Sommet res;
        return res;
    }

    /***
     * TODO : Fonction de mise à jour du cout de l'arc : penser à coder la modif dans sommet
     */
    public Integer modifierArc(Sommet depart, Sommet arrivee, Integer nouveauCout){
        return 0;
    }

    /***
     * TODO : Fonction d'ajout d'un arc : penser à coder la modif dans sommet
     */
    public Integer ajouterArc(Sommet depart, Sommet arrivee, Integer cout){
        return 0;
    }

    /**
     * TODO : Calcul du cmax du graphe + appel a une fonction de calcul de la date de debut au plus tot (+ detection de cycles --> solution avec -2)
     */
    public Integer cMax(){
        return 0;
    }
}