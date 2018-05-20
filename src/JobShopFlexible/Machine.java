package JobShopFlexible;

public class Machine {

    public int id;
    public boolean estDispo;

    /*
    ** CONSTRUCTEUR
     */
    public Machine (int id){
        this.id = id;
        this.estDispo = true;
    }

    /*
    ** METHODES
     */

    public Integer prendreMachine() {
        if (this.estDispo){
            this.estDispo = false;
            return 0;
        }
        else return -1;
    }

    public Integer rendreMachine() {
        if (this.estDispo) return -1;
        else{
            this.estDispo = true;
            return 0;
        }
    }
}
