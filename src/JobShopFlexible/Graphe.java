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