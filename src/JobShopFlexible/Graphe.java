package JobShopFlexible;

import java.util.ArrayList;

import static JobShopFlexible.Generic.isIncluded;
import static java.lang.Thread.sleep;

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

    /*************************************************************/
    /******* Calcul du max de la date de debut au plus tot *******/
    /*******                                               *******/
    /****************** FONCTIONNE PARFAITEMENT ******************/
    /*******                                               *******/
    /*************************************************************/
    public Integer maxPred(Sommet sommet){
        boolean cycle = false;
        Integer result = -1;

        datesDebutAuPlusTot.set(ensembleSommets.indexOf(sommet), -2); // Mise à -2 pour la détection de cycles
        for (Arc a : sommet.predecesseurs) {
            if (a.sommetDepart.id.equals("debut")) {
                return a.duree;
            }
            else if (!datesDebutAuPlusTot.get(ensembleSommets.indexOf(a.sommetDepart)).equals(-2)){
                cycle = false;
                if (a.machine == a.sommetDepart.activite.machineChoisie) {
                    Integer duree = dateAuPlusTot(a.sommetDepart) + a.duree;
                    result = Integer.max(duree, result);
                }
            }
            else cycle = true;
        }
        if (cycle) {
            System.out.println("Cycle détecté, arrêt de la recherche de la date au plus tot pour le sommet "+sommet.id);
            return -1;
        } else return result;
    }

    /*************************************************************/
    /******* Calcul du max de la date de debut au plus tot *******/
    /*******                                               *******/
    /****************** FONCTIONNE PARFAITEMENT ******************/
    /*******                                               *******/
    /*************************************************************/

    public Integer dateAuPlusTot(Sommet sommet){
        if (!sommet.id.equals("debut") && !sommet.id.equals("fin")) {
            if (sommet.aPourPredecesseur(getSommetByID("debut"))){
                Integer result = maxPred(sommet);
                datesDebutAuPlusTot.set(ensembleSommets.indexOf(sommet),result);
                return result;
            }
            else if (datesDebutAuPlusTot.get(ensembleSommets.indexOf(sommet)) >= 0) {
                return sommet.predecesseur().activite.date_fin;
            } else {
                Integer result = maxPred(sommet);
                datesDebutAuPlusTot.set(ensembleSommets.indexOf(sommet),result);
                return result;
            }
        }
        else if (sommet.id.equals("debut")) return 0;
        else if (sommet.id.equals("fin")) return maxPred(sommet);
        else return -1;
    }

    /**** Mise a jour de l'ensemble des dates au plus tot des sommets du graphe ****/
    public void majDatesAuPlusTot() {
        for (Sommet s : ensembleSommets){
            if (!s.id.equals("debut") && !s.id.equals("fin")) {
                s.activite.date_debut = dateAuPlusTot(s);
                s.activite.refresh();
            }
        }
    }

    /***
    ** Fonction de calcul du cMax du graphe : FONCTIONNE !!!
     ***/
    public Integer cMax() {
        Integer cmax = 0;
        Sommet fin = ensembleSommets.get(ensembleSommets.size() - 1);
        for (Arc a : fin.predecesseurs) {
            cmax = Integer.max(cmax, dateAuPlusTot(a.sommetDepart) + a.duree);
        }
        return cmax;
    }

    public Sommet getSommetByID(String id){
        for (Sommet s : ensembleSommets){
            if (s.id == id){
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
        System.out.println("*************************************************************************");
        System.out.println("******************* Affichage du graphe des solutions *******************");
        System.out.println();
        for (Sommet s : ensembleSommets){
            if (s.id.equals("debut") || s.id.equals("fin")) System.out.println("Sommet de " + s.id +"...");
            else {
                System.out.println("Processus : " + s.processus.id +" Activite : " + s.activite.id.toString() + " - Date de debut : " + s.activite.date_debut.toString() + " - Machine : " + s.activite.machineChoisie.id.toString() + " - Duree : " + s.activite.dureeChoisie.toString());
                if (suivantByID(s).id.equals("fin")) compt++;
            }
            compt ++;
        }
        System.out.println("*************************************************************************");
    }

    public void lierSommets(){
        for (Sommet s : ensembleSommets){
            if (!s.id.equals("debut") && !s.id.equals("fin")) {
                Integer act = s.activite.id + 1;
                String id_suiv = s.processus.id.toString() + "." + act.toString();
                Boolean fin = true;
                for (Sommet suiv : ensembleSommets) {
                    if (suiv.id.equals(id_suiv)) {
                        ajouterArc(s, suiv, s.activite.machineChoisie, s.activite.dureeChoisie);
                        fin = false;
                    }
                }
                if (fin) {
                    ajouterArc(s, getSommetByID("fin"), s.activite.machineChoisie, s.activite.dureeChoisie);
                }
            }
        }
    }

    public Conflit detecterConflit(){
        for (Sommet s1 : ensembleSommets){
            for (Sommet s2 : ensembleSommets){
                if (s1 != s2 && !s1.id.equals("debut") && !s1.id.equals("fin") && !s2.id.equals("debut") && !s2.id.equals("fin")){
                    if (s1.activite.machineChoisie == s2.activite.machineChoisie){
                        Conflit result = new Conflit(s1,s2,s1.activite.machineChoisie);
                        if (s2.activite.date_debut>s1.activite.date_debut && s1.activite.date_fin>s2.activite.date_debut){
                            return result;
                        }
                        else if (s1.activite.date_debut>s2.activite.date_debut && s2.activite.date_fin>s1.activite.date_debut){
                            return result;
                        }
                        else if (s1.activite.date_debut.equals(s2.activite.date_debut)){
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }

    private Sommet activitesBanies(Sommet sommet){
        sommet.activite.flushMachinesBannies();
        if (sommet.aPourPredecesseur(getSommetByID("debut"))){
            Integer nouveauCout = sommet.predecesseur(getSommetByID("debut")).duree+1;
            sommet.modifierArc(getSommetByID("debut"),null,nouveauCout);
        }
        else{
            sommet.ajouterArc(getSommetByID("debut"),null,sommet.predecesseur().activite.date_fin+1);
        }
        majDatesAuPlusTot();
        return sommet;
    }

    /*** Fonction de gestion de conflit entre 2 sommets : retourne le sommet à mettre à jour ***/
    public Sommet gererConflit (Conflit conflit){

        Sommet priorite;
        Sommet esclave;

        boolean autreMachinePourEsclave = false;
        boolean autreMachinePourPriorite = false;

        /* Definition du sommet prioritaire par rapport a la date de debut de travail */
        if (conflit.sommet1.activite.date_debut < conflit.sommet2.activite.date_debut){
            priorite = conflit.sommet1;
            esclave = conflit.sommet2;
        }
        else if (conflit.sommet2.activite.date_debut < conflit.sommet1.activite.date_debut){
            priorite = conflit.sommet2;
            esclave = conflit.sommet1;
        }
        else {
            /* Definition du sommet prioritaire par rapport a la duree de travail */
            if (conflit.sommet1.activite.dureeChoisie <= conflit.sommet2.activite.dureeChoisie) {
                priorite = conflit.sommet1;
                esclave = conflit.sommet2;
            } else {
                priorite = conflit.sommet2;
                esclave = conflit.sommet1;
            }
        }

        if (esclave.activite.autreMachine()) {
            esclave.activite.bannirMachine(esclave.activite.machineChoisie);
            if (esclave.activite.machinesDispo()) {
                autreMachinePourEsclave = true;
            } else {
                return activitesBanies(esclave);
            }
        }
        else if (priorite.activite.autreMachine()) {
            priorite.activite.bannirMachine(priorite.activite.machineChoisie);
            if (priorite.activite.machinesDispo()) {
                autreMachinePourPriorite = true;
            } else {
                return activitesBanies(priorite);
            }
        }

        if (autreMachinePourEsclave){
            return esclave;
        }
        else if (autreMachinePourPriorite){
            return priorite;
        }
        else return activitesBanies(esclave);
    }

}