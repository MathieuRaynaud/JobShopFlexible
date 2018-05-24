package JobShopFlexible;

public class Activite {

    /*
     * Declarations
     */

    public Processus processus;
    public Integer id;
    public int nbMachinesNecessaires;
    public Integer[] MachinesNecessaires;
    public Integer[] Durees;

    private int indiceMachine;

    /*
     * Constructeur
     */
    public Activite(Integer id, int nbMachinesNecessaires, Processus processus){
        this.id = id;
        this.nbMachinesNecessaires = nbMachinesNecessaires ;
        this.MachinesNecessaires = new Integer[nbMachinesNecessaires];
        this.Durees = new Integer[nbMachinesNecessaires];
        this.indiceMachine = 0;
        this.processus = processus;
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

    public Integer duree(Machine mac){
        Integer result = null;
        for(Integer m: this.MachinesNecessaires){
            if(m==mac.id){
                result = this.Durees[m-1];
            }
        }
        return result;
    }
}
