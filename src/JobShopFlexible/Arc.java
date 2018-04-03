package JobShopFlexible;

public class Arc {

    public Sommet sommetArrive;
    public int cout;

    /*
     * Constructeur
     */

    public Arc(Sommet sommetArrive, int cout){
        this.sommetArrive = sommetArrive;
        this.cout = cout;
    }

    public Arc() {}

}