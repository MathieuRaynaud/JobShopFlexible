package JobShopFlexible;

import java.util.ArrayList;
import java.util.Arrays;

public class Glouton {

    /*
     *  Classe de calcul de l'heuristique gloutonne
     */

    /*
     *  Attributs
     */

    public JobShop jobshop;
    public ArrayList<Infos> tableau_solutions;
    public Sommet[] file_attente;
    private Integer indice_file;

    /*
     *  Constructeur
     */

    public Glouton(JobShop jobshop){
        this.tableau_solutions = new ArrayList<Infos>();
        this.jobshop = jobshop;
        this.file_attente = new Sommet[jobshop.Processus.length];
        this.indice_file = 0;
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

    /******* Choix de la machine disponible la plus rapide *******/
    public Machine choixMachine(Activite activite){
        Integer duree = 1000 ;
        Machine machine = null;
        for(Integer d=0; d<activite.Durees.length; d++){
            if (activite.Durees[d] < duree) {
                duree = activite.Durees[d];
                machine = jobshop.tableauMachines[activite.MachinesNecessaires[d]];
            }
        }
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

    /******* Passage à l'activité suivante *******/
    public Sommet suivant(Sommet SommetActivite){
        Sommet result = null;
        /* Passage à l'activite suivante dans le graphe */
        for (Sommet sommet : jobshop.JobShopGraph.ensembleSommets){
            for(Arc pred : sommet.predecesseurs){
                if(pred.sommetDepart == SommetActivite){
                    result = sommet;
                }
            }
        }
        /* Passage à l'activite suivante dans la file d'attente */
        for(int i=0; i<file_attente.length; i++){
            if(file_attente[i] == SommetActivite) file_attente[i] = result;
        }
        return result;
    }

    /******* Choix des prochaines activites a executer *******/
    public ArrayList<Infos> choixActivite(){
        ArrayList<Infos> resultats = new ArrayList<Infos>();
        Integer compteur = 0;
        Machine mac;
        for(Sommet s :file_attente){
            mac = choixMachine(s.activite);
            if(mac != null) {
                resultats.add(new Infos(mac, s.activite, s.processus, -1, s.activite.duree(mac)));
                System.out.println("Ajout de l'activite " + s.processus.id +"."+s.activite.id + " à l'ensemble des solutions");
                compteur++;
            }
        }
        return resultats;
    }

    /** Initialisation de la file d'attente : mise de tous les sommets qui ont pour prédecesseur le debut --> Fonctionne ! **/
    private void init_file_attente(){
        for (Sommet sommet : this.jobshop.JobShopGraph.ensembleSommets) {
            for (Arc pred : sommet.predecesseurs) {
                if (pred != null) {
                    if (pred.sommetDepart.id == "debut") {
                        //System.out.println("Mise du sommet " + sommet.id + " dans la file d'attente (predecesseur = " + sommet.predecesseurs[0].sommetDepart.id + ")");
                        file_attente[indice_file] = sommet;
                        indice_file++;
                    }
                }
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

        /*** Etape 2 : Parmi les activites en attente d'etre executees, choisir lesquelles on execute ***/
        boolean fini = false;
        while (!fini) {
            tableau_solutions = choixActivite();

            /*** Etape 3 : On execute chaque activite ***/
            for(Infos i:tableau_solutions){
                i.date_debut = dates[i.processus.id-1];
                i.refresh();
                if (i.duree != null){
                    dates[i.processus.id-1]+=i.duree;
                }
            }

            /*** Etape 4 : On verifie si on a fini ***/
            fini = true;
            for (Sommet s : file_attente) {
                if (s.id == "fin") fini = false;
            }
        }

        /*** Etape 4 : On affiche le resultat graphique ***/
        this.afficherTableau();

        return 0;
    }

}




/******************** FONCTION QUI NE SERT A PRIORI A RIEN DU TOUT DU TOUT ... ********************/
/******* Execution d'une activite et renvoi des informations liees *******/
    /*public Infos executeActivite(Activite activite, Integer date_debut){
        Integer duree = 0;
        Machine machine = choixMachine(activite);
        for(Integer m:activite.MachinesNecessaires){
            if(activite.MachinesNecessaires[m]==machine.id){
                jobshop.tableauMachines[activite.MachinesNecessaires[m]-1].estDispo = false;
                duree = activite.Durees[m];
            }
        }
        Infos result = new Infos(machine,activite,activite.processus,date_debut,duree);
        return result;
    }*/
/**************************************************************************************************/
