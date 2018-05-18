package JobShopFlexible;

public class Glouton {

    /*
     *  Classe de calcul de l'heuristique gloutonne
     */

    /*
     *  Attributs
     */

    public JobShop jobshop;
    public Infos[] tableau_solutions;
    public Activite[] file_attente;
    public Integer indice_file;

    /*
     *  Constructeur
     */

    public Glouton(Integer nbJobs, JobShop jobshop){
        this.tableau_solutions = new Infos[nbJobs];
        this.jobshop = jobshop;
        this.file_attente = new Activite[nbJobs];
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
            - Pour chaque processus, l'ordre des activites doit être respecté
     *******************************************************************************************************************************************************/

    /******* Choix de la machine disponible la plus rapide *******/
    public Machine choixMachine(Activite activite){
        Integer duree = 1000 ;
        Machine machine = null;
        for(Integer d:activite.Durees){
            if (jobshop.tableauMachines[activite.MachinesNecessaires[d]-1].estDispo) {
                if (activite.Durees[d] < duree) {
                    duree = activite.Durees[d];
                    machine = jobshop.tableauMachines[activite.MachinesNecessaires[d]-1];
                }
            }
        }
        return machine;
    }

    /******* Execution d'une activite et renvoi des informations liees *******/
    public Infos executeActivite(Activite activite, Integer date_debut){
        Integer duree = 0;
        Machine machine = choixMachine(activite);
        for(Integer m:activite.MachinesNecessaires){
            if(activite.MachinesNecessaires[m]==machine.id){
                duree = activite.Durees[m];
            }
        }
        Infos result = new Infos(machine,activite,date_debut,duree);
        return result;
    }

    /******* Affichage du tableau des solutions *******/
    /*
     TODO : Créer une fonction permettant d'afficher proprement les tableaux de solutions
     */
    public Integer afficherTableau(){

        return 0;
    }

    /******* Passage à l'activité suivante *******/
    public Sommet suivant(Sommet SommetActivite){
        Sommet result = null;
        for (Sommet sommet : jobshop.JobShopGraph.ensembleSommets){
            for(Arc pred : sommet.predecesseurs){
                if(pred.sommetArrive == SommetActivite){
                    result = sommet;
                }
            }
        }
        return result;
    }

    /******* FONCTION PRINCIPALE : MISE EN PLACE DE L'HEURISTIQUE *******/
    public Integer heuristiqueGloutonne(){

        /*** Etape 1 : Mise de tous les sommets de depart dans la file d'attente ***/
        for(Sommet sommet:this.jobshop.JobShopGraph.ensembleSommets){
            for(Arc pred : sommet.predecesseurs) {
                if (pred.sommetArrive.id == "debut") {
                    file_attente[indice_file] = sommet.activite;
                    indice_file++;
                }
            }
        }

        /*** Etape 2 : Parmi les activites en attente d'etre executees, choisir lesquelles on execute ***/

        /*
         TODO : Faire la fonction permettant de choisir quelle(s) activite(s) on execute parmi celles qui sont dans la file d'attente
         */

        /*** Etape 3 : On execute chaque activite ***/

        /*
         TODO : ATTENTION ! Penser à la condition d'arret ! (suivant == "fin" très certainement)
         */

        /*** Etape 4 : On affiche le resultat graphique ***/
        return 0;
    }

}
