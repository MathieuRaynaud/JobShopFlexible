package JobShopFlexible;

import java.util.ArrayList;

public class Activite {

    /*
     * Declarations
     */

    public Processus processus;
    public Integer id;
    public int nbMachinesNecessaires;
    public ArrayList<Machine> MachinesNecessaires;
    public ArrayList<Integer> Durees;
    public Integer date_debut;
    public Integer date_fin;
    public Machine machineChoisie;
    public Integer dureeChoisie;
    public ArrayList<Machine> MachinesBanies;
    public ArrayList<Integer> DureesBanies;

    private int indiceMachine;

    /*
     * Constructeur
     */
    public Activite(Integer id, int nbMachinesNecessaires, Processus processus){
        this.id = id;
        this.nbMachinesNecessaires = nbMachinesNecessaires ;
        this.MachinesNecessaires = new ArrayList<Machine>();
        this.Durees = new ArrayList<Integer>();
        this.indiceMachine = 0;
        this.processus = processus;
        this.date_debut = -1;
        this.date_fin = 0;
        this.machineChoisie = null;
        this.MachinesBanies = new ArrayList<Machine>();
        this.DureesBanies = new ArrayList<Integer>();
    }

    /*
     * Méthodes
     */

    public int ajouterMachine(Machine machine, Integer duree){

        if (indiceMachine == nbMachinesNecessaires){
            System.out.println("Impossible d'ajouter une machine, nombre maximal atteint !");
            return -1;
        }
        MachinesNecessaires.add(machine);
        Durees.add(duree);
        this.indiceMachine += 1;

        return 0;
    }

    public Integer duree(Machine mac){
        Integer result = null;
        for (Machine m : MachinesNecessaires){
            if (m.id.equals(mac.id)){
                result = Durees.get(MachinesNecessaires.indexOf(m));
            }
        }
        return result;
    }

    public Integer dureeInt(Integer machine){
        Integer result = null;
        for (Machine m : MachinesNecessaires){
            if (m.id.equals(machine)) result = this.Durees.get(MachinesNecessaires.indexOf(m));
        }
        return result;
    }

    public void refresh(){
        this.date_fin = date_debut + duree(this.machineChoisie);
    }

    public boolean autreMachine(){
        return this.nbMachinesNecessaires >1;
    }

    public void bannirMachine(Machine machine){
        MachinesBanies.add(machine);
        DureesBanies.add(Durees.get(MachinesNecessaires.indexOf(machine)));
        Durees.remove(MachinesNecessaires.indexOf(machine));
        MachinesNecessaires.remove(machine);
    }

    public boolean machinesDispo(){
        return MachinesNecessaires.size()>0;
    }

    public void flushMachinesBannies(){
        MachinesNecessaires.addAll(MachinesBanies);
        Durees.addAll(DureesBanies);
        this.MachinesBanies.clear();
        this.DureesBanies.clear();
    }

    /*************************************************************/
    /******* Choix de la machine disponible la plus rapide *******/
    /*******                                               *******/
    /****************** FONCTIONNE PARFAITEMENT ******************/
    /*******                                               *******/
    /*************************************************************/
    public void choixMachine() {
        Integer duree = 1000;
        Integer dureeCalculee;
        Machine machine = null;
        for (Machine m : this.MachinesNecessaires) {
            dureeCalculee = this.duree(m);
            if (dureeCalculee < duree) {
                machine = m;
                duree = dureeCalculee;
            }
        }
        this.machineChoisie = machine;
        this.dureeChoisie = duree(this.machineChoisie);
    }
}
