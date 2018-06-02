package JobShopFlexible;

public class Machine {

    public Integer id;
    public String couleur;

    /*
    ** CONSTRUCTEUR
     */
    public Machine (Integer id){
        this.id = id;
        this.couleur = "\033["+(30+this.id)+"m";
    }

}
