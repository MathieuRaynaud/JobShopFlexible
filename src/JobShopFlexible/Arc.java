package JobShopFlexible;

public class Arc {

    public Sommet sommetArrive;
    public int machine;
    public int duree;

    /*
     * Constructeur
     */

    public Arc(Sommet sommetArrive, int machine, int duree){
        this.sommetArrive = sommetArrive;
        this.machine = machine;
        this.duree = duree;
    }

    public Arc() {}

}