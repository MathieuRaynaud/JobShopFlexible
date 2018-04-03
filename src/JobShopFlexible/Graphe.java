package JobShopFlexible;

public class Graphe {

    public Sommet[] ensembleSommets;
    public int nbSommets;

    /* CONSTRUCTEUR */

    public Graphe(int nbSommets){
        this.nbSommets = nbSommets;
        this.ensembleSommets = new Sommet[nbSommets];
    }

    //A CORRIGER
    public int CoutTotal (){
        int coutTotal = 0;
        for(int i = 0; i < nbSommets; i++){
            coutTotal += ensembleSommets[i].successeurs[i].cout;
        }
        return coutTotal;
    }
}