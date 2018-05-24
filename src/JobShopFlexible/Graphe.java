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
        this.ensembleSommets = new ArrayList<Sommet>();
        this.datesDebutAuPlusTot = new ArrayList<Integer>();
        this.indiceSommets = -1;
    }

    /** Fonction d'ajout d'un sommet --> Fonctionne ! **/
    public int ajouterSommet (Sommet sommet){
        this.ensembleSommets.add(sommet);
        this.indiceSommets++;
        //System.out.println("*** Sommet " + sommet.id + " ajouté avec succès");
        return 0;
    }

    /***
     * Fonction de mise à jour du cout de l'arc
     ***/
    public Integer modifierArc(Sommet depart, Sommet arrivee, Integer nouveauCout){
        arrivee.modifierArc(depart,nouveauCout);
        return 0;
    }

    /***
     * Fonction d'ajout d'un arc
     ***/
    public Integer ajouterArc(Sommet depart, Sommet arrivee, Integer cout){
        arrivee.ajouterArc(depart,cout);
        return 0;
    }

    /***
    ** Fonction récursive de calcul de la date au plus tot d'un sommet
     ***/

    public Integer dateAuPlusTot(Sommet sommet){
        boolean cycle = true;
        Integer result = 0;
        datesDebutAuPlusTot.set(ensembleSommets.indexOf(sommet),-2) ; // Mise à -2 pour la détection de cycles
        if(datesDebutAuPlusTot.get(ensembleSommets.indexOf(sommet))!=-1){
            result = datesDebutAuPlusTot.get(ensembleSommets.indexOf(sommet));
        }
        else{
            for(Arc a : sommet.predecesseurs){
                if (datesDebutAuPlusTot.get(ensembleSommets.indexOf(a.sommetDepart))!=-2) {
                    cycle = false;
                    result = Integer.max(dateAuPlusTot(a.sommetDepart) + a.duree, result);
                }
            }
        }
        if (cycle) {
            System.out.println("Cycle détecté, arrêt de la recherche de la date au plus tot sur le cycle");
            return -1;
        }
        else return result;
    }

    /***
    ** Fonction de calcul du cMax du graphe
     ***/
    public Integer cMax(){
        Integer cmax = 0;
        Sommet fin = ensembleSommets.get(ensembleSommets.lastIndexOf(ensembleSommets));
        for (Arc a : fin.predecesseurs){
            cmax = Integer.max(cmax, dateAuPlusTot(a.sommetDepart));
        }
        return cmax;
    }
}