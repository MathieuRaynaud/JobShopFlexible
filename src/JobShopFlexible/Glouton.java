package JobShopFlexible;

import java.util.ArrayList;
import java.util.Arrays;

import static JobShopFlexible.Generic.*;
import static java.lang.Thread.sleep;

public class Glouton {

    /*
     *  Classe de calcul de l'heuristique gloutonne
     */

    /*
     *  Attributs
     */

    public JobShop jobshop;
    public ArrayList<Infos> tableau_solutions; // IL VA SUREMENT FALLOIR PASSER A UN TABLEAU DE SOMMETS
    public ArrayList<Sommet> file_attente;

    /*
     *  Constructeur
     */

    public Glouton(JobShop jobshop){
        this.tableau_solutions = new ArrayList<Infos>();
        this.jobshop = jobshop;
        this.file_attente = new ArrayList<Sommet>();
    }

    /*
     *  Méthodes
     */

    /********************************************************************************************************************************************************
        RAPPEL DE L'HEURISTIQUE A IMPLEMENTER :
            - Dès qu'un processus a besoin d'une machine au repos pour travailler, il prend la machine et s'execute --> executeActivite
            - Tout processus en execution ne peut pas etre interrompu --> OK !
            - Si plusieurs processus ont besoin d'une meme machine pour travailler, alors on prend celui qui l'utilisera le moins longtemps
            - Si un processus peut utiliser plusieurs machines pour s'exectuer, on utilise celle qui est libre qui prendra le moins de temps --> choixMachine
            - Pour chaque processus, l'ordre des activites doit être respecté --> Géré par la file_attente
     *******************************************************************************************************************************************************/

    /******* Fonction de choix du sommet a exectuer au plus tot ********/
    public Sommet sommetAuPlusTot(ArrayList<Sommet> list){
        Integer mem = Integer.MAX_VALUE;
        Sommet result = null;
        for(Sommet s:list){
            if (s.activite.date_debut < mem) {
                result = s;
                mem = s.activite.date_debut;
            }
        }
        return result;
    }

    /****** Fonction pour savoir si une machine est occupee ou non *****/
    public boolean isOccupied(Machine m, Integer debut, Integer fin, ArrayList<Machine> machines, ArrayList<Integer> deb, ArrayList<Integer> f){
        boolean occ = false;
        if (isIncluded(m,machines)) {
            Integer index = machines.indexOf(m);
            for (Object o : machines){
                if (o == m){
                    occ = (occ || (!((debut>f.get(index)) || fin < deb.get(index))));
                }
            }
        }
        return occ;
    }

    /*************************************************************/
    /******* Choix de la machine disponible la plus rapide *******/
    /*******                                               *******/
    /****************** FONCTIONNE PARFAITEMENT ******************/
    /*******                                               *******/
    /*************************************************************/
    public Machine choixMachine(Activite activite){
        Integer duree = 1000 ;
        Integer dureeCalculee;
        Machine machine = null;
        for (Machine m : activite.MachinesNecessaires){
            dureeCalculee = activite.duree(m);
            if (dureeCalculee < duree){
                machine = m;
                duree = dureeCalculee;
            }
        }
        /*for (Integer i : activite.MachinesNecessaires) {
            dureeCalculee = activite.duree(jobshop.getMachineByID(i));
            if (dureeCalculee < duree){
                machine = jobshop.getMachineByID(i);
                duree = dureeCalculee;
            }
        }*/
        return machine;
    }

    /******* Affichage du tableau des solutions *******/
    /*
     TODO : Créer une fonction permettant d'afficher proprement les tableaux de solutions
     */
    public Integer afficherTableau(){
        System.out.println("Affichage du tableau solution : ");
        Integer process_count = 0;
        Integer activity_count = 0;

        Integer count[] = new Integer[this.tableau_solutions.size()];
        Arrays.fill(count,0);
        for(Infos i : tableau_solutions){
            if (i != null) {
                if (i.processus.id != null) System.out.print("Processus : " + i.processus.id);
                if (i.processus.Activites[count[i.processus.id - 1]].id != null)
                    System.out.print(" - Activité : " + i.processus.Activites[count[i.processus.id - 1]].id);
                if (i.machine.id != null) System.out.print(" - Machine : " + i.machine.id);
                if (i.date_debut != null) System.out.print(" - Date début : " + i.date_debut);
                if (i.date_fin != null) System.out.print(" - Date fin : " + i.date_fin);
                count[i.processus.id - 1]++;
                System.out.println();
            }
        }
        return 0;
    }

    /** Initialisation de la file d'attente : mise de tous les sommets qui ont pour prédecesseur le debut --> Fonctionne ! **/
    private void init_file_attente(){
        for (Sommet sommet : this.jobshop.JobShopGraph.ensembleSommets) {
            for (Arc pred : sommet.predecesseurs) {
                if (pred != null) {
                    if (pred.sommetDepart.id == "debut") {
                        //System.out.println("Mise du sommet " + sommet.id + " dans la file d'attente (predecesseur = " + sommet.predecesseurs[0].sommetDepart.id + ")");
                        file_attente.add(sommet);
                    }
                }
            }
        }
    }


    /******* Initial : Créer une solution non acceptable avec les meilleures machines pour chaque activite *******/
    public void initial(){
        for (Sommet s : jobshop.JobShopGraph.ensembleSommets){
            if (jobshop.JobShopGraph.estDernier(s)){
                s.activite.machineChoisie = choixMachine(s.activite);
                jobshop.JobShopGraph.modifierArc(s,jobshop.JobShopGraph.getSommetByID("fin"),s.activite.machineChoisie, s.activite.duree(s.activite.machineChoisie));
            }
            else if (!(s.id.equals("debut")) && !(s.id.equals("fin"))){
                s.activite.machineChoisie = choixMachine(s.activite);
                jobshop.JobShopGraph.modifierArc(s,jobshop.JobShopGraph.suivantByID(s),s.activite.machineChoisie,s.activite.duree(s.activite.machineChoisie));
            }
        }
    }

    /**** Mise a jour de l'ensemble des dates au plus tot des sommets du graphe ****/
    private void majDatesAuPlusTot() {
        for (Sommet s : jobshop.JobShopGraph.ensembleSommets){
            if (!s.id.equals("debut") && !s.id.equals("fin")) {
                s.activite.date_debut = jobshop.JobShopGraph.dateAuPlusTot(s);
            }
        }
    }

    /******* FONCTION PRINCIPALE : MISE EN PLACE DE L'HEURISTIQUE *******/

    /*
       TODO : Corriger l'heuristique : le calcul doit se faire à partir des tableaux, et le graphe doit être construit en sortie en tant que représentation de résultat
     */
    public Integer heuristiqueGloutonne() {
        /*** Initialisation du tableau de dates ***/
        Integer[] dates = new Integer[this.jobshop.Processus.length];
        Arrays.fill(dates, 0);

        /*** Etape 1 : Mise de tous les sommets de depart dans la file d'attente ***/
        init_file_attente();

        /*** Etape 2 : Creation d'une solution initiale avec les machines les plus rapides pour chaque activite ***/
        initial();
        jobshop.JobShopGraph.lierSommets();
        majDatesAuPlusTot();

        /*****************************************/
        /***                                   ***/
        /*** JUSQU'ICI TOUT MARCHE COMME PREVU ***/
        /***                                   ***/
        /*****************************************/

        /*** Etape 3 : Mise a jour de la solution jusqu'a ce qu'elle soit acceptable ***/

        // TODO : Gerer les conflits entre machines

        // Prendre le moins couteux entre le decalage du depart et le changement de machine
        Conflit conflit = jobshop.JobShopGraph.detecterConflit();
        if (conflit != null){
            System.out.println("   * Conflit detecte !");
            System.out.println("      * Sommet 1 : " + conflit.sommet1.id);
            System.out.println("      * Sommet 2 : " + conflit.sommet2.id);
            System.out.println("      * Machine : " + conflit.machine.id.toString());
            System.out.println("      * Date de debut 1 : " + conflit.sommet1.activite.date_debut);
            System.out.println("      * Date de debut 2 : " + conflit.sommet2.activite.date_debut);
        }

        jobshop.JobShopGraph.gererConflit(conflit);

        /*** Etape finale : Affichage du graphe solution ***/

        jobshop.JobShopGraph.afficherGraphe();

        return 0;
    }

}