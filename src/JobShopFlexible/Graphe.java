package JobShopFlexible;

import java.util.ArrayList;

import static JobShopFlexible.Generic.isIncluded;

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
        this.datesDebutAuPlusTot.add(-1);
        this.indiceSommets++;
        //System.out.println("*** Sommet " + sommet.id + " ajouté avec succès");
        return 0;
    }

    /***
     * Fonction de mise à jour du cout de l'arc
     ***/
    public Integer modifierArc(Sommet depart, Sommet arrivee, Machine nouvelleMachine, Integer nouveauCout){
        arrivee.modifierArc(depart,nouvelleMachine,nouveauCout);
        return 0;
    }

    /***
     * Fonction d'ajout d'un arc
     ***/
    public Integer ajouterArc(Sommet depart, Sommet arrivee, Machine machine, Integer cout){
        arrivee.ajouterArc(depart,machine,cout);
        return 0;
    }

    public Integer maxPred(Sommet sommet){
        boolean cycle = false;
        Integer result = 0;
        System.out.println("maxPred("+sommet.id+")...");

        datesDebutAuPlusTot.set(ensembleSommets.indexOf(sommet), -2); // Mise à -2 pour la détection de cycles
        for (Arc a : sommet.predecesseurs) {
            System.out.println("Sommet precedent : "+a.sommetDepart.id);
            if (!datesDebutAuPlusTot.get(ensembleSommets.indexOf(a.sommetDepart)).equals(-2)) {
                System.out.println("Calcul de la date au plus tot pour " + a.sommetDepart.id);
                cycle = false;
                Integer duree = dateAuPlusTot(a.sommetDepart) + a.duree;
                result = Integer.max(duree, result);
            }
            else cycle = true;
        }
        if (cycle) {
            System.out.println("Cycle détecté, arrêt de la recherche de la date au plus tot pour le sommet "+sommet.id);
            return -1;
        } else return result;
    }
    /***
    ** Fonction récursive de calcul de la date au plus tot d'un sommet
     ***/

    public Integer dateAuPlusTot(Sommet sommet){
        System.out.println("Appel date au plus tot " + sommet.id);
        if (!sommet.id.equals("debut") && !sommet.id.equals("fin")) {
            if (datesDebutAuPlusTot.get(ensembleSommets.indexOf(sommet)) >= 0) {
                return datesDebutAuPlusTot.get(ensembleSommets.indexOf(sommet));
            } else {
                return maxPred(sommet);
            }
        }
        else if (sommet.id.equals("debut")) return 0;
        else if (sommet.id.equals("fin")) return maxPred(sommet);
        else return -1;
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

    public Sommet getSommetByActivite(Activite act){
        for(Sommet s:ensembleSommets){
            if ((s.processus==act.processus) & (s.activite == act)){
                return s;
            }
        }
        return null;
    }

    public Sommet getSommetByID(String id){
        for (Sommet s : ensembleSommets){
            if (s.id == id){
                return s;
            }
        }
        return null;
    }

    /***
     * Fonction de retour du sommet suivant
     ***/

    public Sommet suivant(Sommet sommet){
        for (Sommet s:ensembleSommets){
            if(isIncluded(sommet,s.predecesseurs)){
                return s;
            }
        }
        return null;
    }

    public Sommet suivantByID(Sommet sommet){
        String id_suiv = sommet.processus.id.toString() + "." + sommet.activite.id.toString();
        for (Sommet s:ensembleSommets){
            if(s.id.equals(id_suiv) ){
                return s;
            }
        }
        return null;
    }

    public boolean estDernier(Sommet sommet){
        if (sommet.id.equals("debut")) return false;
        else if (sommet.id.equals("fin")) return false;
        else {
            Integer act = sommet.activite.id + 1;
            String id_suiv = sommet.processus.id.toString() + "." + act.toString();
            for (Sommet s : ensembleSommets) {
                if (s.id.equals(id_suiv)) {
                    return false;
                }
            }
            return true;
        }
    }
    /***
     * Fonction d'affichage du graphe
     ***/

    public void afficherGraphe(){
        Integer compt = 1;
        boolean fini = false;
        for (Sommet s : ensembleSommets){
            if (s.id.equals("debut") || s.id.equals("fin")) System.out.println("Sommet de " + s.id +"...");
            else {
                System.out.println("Processus : " + s.processus.id +" Activite : " + s.activite.id.toString() + " - Machine : " + s.activite.machineChoisie.id.toString() + " - Date de debut : " + s.activite.date_debut.toString() + " - Duree : " + s.activite.duree(s.activite.machineChoisie).toString());
                if (suivantByID(s).id.equals("fin")) compt++;
            }
            compt ++;
        }
    }

}