package JobShopFlexible;

import java.util.Arrays;

public class Glouton {

    /*
     *  Classe de calcul de l'heuristique gloutonne
     */

    /*
     *  Attributs
     */

    public JobShop jobshop;
    public Infos[] tableau_solutions;
    public Sommet[] file_attente;
    private Integer indice_file;

    /*
     *  Constructeur
     */

    public Glouton(JobShop jobshop){
        this.tableau_solutions = new Infos[jobshop.Processus.length];
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
        for(Integer d:activite.Durees){
            if (activite.Durees[d] < duree) {
                duree = activite.Durees[d];
                machine = jobshop.tableauMachines[activite.MachinesNecessaires[d]-1];
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
        Integer count[] = new Integer[this.tableau_solutions.length];
        Arrays.fill(count,0);
        for(Infos i : tableau_solutions){
            System.out.println("Processus : " + i.processus.id + " - Activité : " + i.processus.Activites[count[i.processus.id-1]].id + " - Machine : " + i.machine.id + " - Date début : " +
                               i.date_debut + " - Date fin : " + i.date_fin);
            count[i.processus.id-1]++;
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
    public Infos[] choixActivite(){
        Infos[] resultats = {};
        Integer compteur = 0;
        Machine mac;
        for(Sommet s :file_attente){
            mac = choixMachine(s.activite);
            if(mac != null) {
                resultats[compteur] = new Infos(mac, s.activite, s.activite.processus,-1, s.activite.duree(mac));
                compteur++;
            }
        }
        return resultats;
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
        for (Sommet sommet : this.jobshop.JobShopGraph.ensembleSommets) {
            for (Arc pred : sommet.predecesseurs) {
                if (pred.sommetDepart.id == "debut") {
                    System.out.println("Mise du sommet " + sommet.id + " dans la file d'attente (predecesseur = " + sommet.predecesseurs[0].sommetDepart.id + ")");
                    file_attente[indice_file] = sommet;
                    indice_file++;
                }
            }
        }

        /*** Etape 2 : Parmi les activites en attente d'etre executees, choisir lesquelles on execute ***/
        boolean fini = false;
        while (!fini) {
            Infos[] tableau_infos = choixActivite();

            /*** Etape 3 : On execute chaque activite ***/
            for(Infos i:tableau_infos){
                i.date_debut = dates[i.processus.id-1];
                i.refresh();
                dates[i.processus.id-1]+=i.duree;
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
