package JobShopFlexible;

public class Graphe {

    public Sommet[] ensembleSommets;
    public int nbSommets;
    public int indiceSommets;

    /*
     * Constructeur
     */

    public Graphe(int nbSommets){
        this.nbSommets = nbSommets;
        this.ensembleSommets = new Sommet[nbSommets];
        this.indiceSommets = 0;
    }

    //A CORRIGER
    /*public int CoutTotal (){
        int coutTotal = 0;
        for(int i = 0; i < nbSommets; i++){
            coutTotal += ensembleSommets[i].predecesseurs[i].duree;
        }
        return coutTotal;
    }*/

    public int ajouterSommet (Sommet sommet){

        if (indiceSommets == nbSommets){
            System.out.println("Ajout de sommet impossible, nombre maximal atteint !");
            return -1;
        }

        this.ensembleSommets[indiceSommets] = sommet;
        this.indiceSommets++;

        return 0;
    }
}