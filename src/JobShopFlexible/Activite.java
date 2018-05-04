package JobShopFlexible;

import static java.lang.System.exit;

public class Activite {

    /*
     * Declarations
     */
    public Integer id;
    public int nbMachinesNecessaires;
    public Integer[] MachinesNecessaires;
    public Integer[] Durees;
    public int indiceMachine;

    /*
     * Constructeur
     */
    public Activite(Integer id, int nbMachinesNecessaires){
        this.id = id;
        this.nbMachinesNecessaires = nbMachinesNecessaires ;
        this.MachinesNecessaires = new Integer[nbMachinesNecessaires];
        this.Durees = new Integer[nbMachinesNecessaires];
        this.indiceMachine = 0;
    }

    /*
     * Méthodes
     */

    public int ajouterMachine(Integer machine, int duree){

        if (indiceMachine == nbMachinesNecessaires){
            System.out.println("Impossible d'ajouter une machine, nombre maximal atteint !");
            return -1;
        }

        MachinesNecessaires[indiceMachine] = machine;
        Durees[indiceMachine] = duree;
        this.indiceMachine += 1;

        return 0;
    }
}
