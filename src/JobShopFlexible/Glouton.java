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
    public ArrayList<Sommet> file_attente;

    /*
     *  Constructeur
     */

    public Glouton(JobShop jobshop){
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

    /** Initialisation de la file d'attente : mise de tous les sommets qui ont pour prédecesseur le debut --> Fonctionne ! **/
    private void init_file_attente(){
        for (Sommet sommet : this.jobshop.JobShopGraph.ensembleSommets) {
            for (Arc pred : sommet.predecesseurs) {
                if (pred != null) {
                    if (pred.sommetDepart.id == "debut") {
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
                s.activite.choixMachine();
                jobshop.JobShopGraph.modifierArc(s,jobshop.JobShopGraph.getSommetByID("fin"),s.activite.machineChoisie, s.activite.dureeChoisie);
            }
            else if (!(s.id.equals("debut")) && !(s.id.equals("fin"))){
                s.activite.choixMachine();
                jobshop.JobShopGraph.modifierArc(s,jobshop.JobShopGraph.suivantByID(s),s.activite.machineChoisie,s.activite.dureeChoisie);
            }
        }
    }

    /*** Fonction de transformation du graphe en matrice ***/
    public Machine[][] toMatrice(Graphe graphe){
        Integer ProcessActuel = 1;
        Integer ActiviteActuelle = 1;
        Integer indice;
        Machine[][] Matrice = new Machine[jobshop.Processus.length][graphe.cMax()];
        while (ProcessActuel < (jobshop.Processus.length+1)){
            ActiviteActuelle = 1;
            for (Sommet s : graphe.ensembleSommets){
                if (!s.id.equals("debut") && !s.id.equals("fin")) {
                    if (s.processus.id.equals(ProcessActuel)) {
                        if (s.activite.id.equals(ActiviteActuelle)) {
                            indice = s.activite.date_debut;
                            while (indice < s.activite.date_fin) {
                                Matrice[ProcessActuel - 1][indice] = s.activite.machineChoisie;
                                indice++;
                            }
                            ActiviteActuelle++;
                        }
                    }
                }
            }
            ProcessActuel++;
        }
        return Matrice;
    }

    /*** Fonction d'affichage d'une matrice ***/
    public void printMatrice(Machine[][] Matrice, Integer width, Integer height){
        Integer indiceProc;
        Integer indiceAct;
        String traits = null;
        StringBuilder stringBuilderTraits = new StringBuilder();
        StringBuilder stringBuilderEtoiles = new StringBuilder();
        stringBuilderTraits.append("---------------");
        stringBuilderEtoiles.append("***************");
        for (indiceAct=0; indiceAct<width; indiceAct++){
            stringBuilderTraits.append("-----");
            stringBuilderEtoiles.append("*****");

        }
        String etoiles = stringBuilderEtoiles.toString();
        System.out.println();
        System.out.println("Affichage temporel de la solution :");
        System.out.println(etoiles);


        for (indiceProc=0; indiceProc<height; indiceProc++){
            System.out.printf("Processus %2d : " ,(indiceProc+1));
            for(indiceAct=0; indiceAct<width; indiceAct++) {
                if (Matrice[indiceProc][indiceAct]==null){
                    System.out.printf("\033[30m"+"|   |");
                }
                else {
                    System.out.printf(Matrice[indiceProc][indiceAct].couleur+"|%3s|", Matrice[indiceProc][indiceAct].id);
                }
            }
            System.out.println();
            traits = stringBuilderTraits.toString();
            System.out.println("\033[0m"+traits);
        }
        System.out.println();
        System.out.println("CMax de la solution : " + jobshop.JobShopGraph.cMax().toString());
        System.out.println(etoiles);
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
        jobshop.JobShopGraph.majDatesAuPlusTot();

        /*** Etape 3 : Mise a jour de la solution jusqu'a ce qu'elle soit acceptable ***/

        Conflit conflit = jobshop.JobShopGraph.detecterConflit();
        while (conflit != null) {
            jobshop.JobShopGraph.gererConflit(conflit);
            jobshop.JobShopGraph.majDatesAuPlusTot();
            conflit = jobshop.JobShopGraph.detecterConflit();
        }

        /*** Etape finale : Affichage du graphe solution et calcul du cMax***/

        printMatrice(toMatrice(jobshop.JobShopGraph),jobshop.JobShopGraph.cMax(),jobshop.Processus.length);

        return 0;
    }

}