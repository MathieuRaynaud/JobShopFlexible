package JobShopFlexible;

public class Arc {

    public Sommet sommetDepart;
    public Machine machine;
    public int duree;

    /*
     * Constructeur
     */

    public Arc(Sommet sommetDepart, Machine machine, int duree){
        this.sommetDepart = sommetDepart;
        this.machine = machine;
        this.duree = duree;
    }


}