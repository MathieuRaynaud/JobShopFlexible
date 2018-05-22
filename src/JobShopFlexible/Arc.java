package JobShopFlexible;

public class Arc {

    public Sommet sommetDepart;
    public int duree;

    /*
     * Constructeur
     */

    public Arc(Sommet sommetDepart, int duree){
        this.sommetDepart = sommetDepart;
        this.duree = duree;
    }


}