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
    public Integer[] Durees;
    public Integer date_debut;
    public Integer date_fin;
    public Machine machineChoisie;
    public ArrayList<Machine> MachinesBanies;

    private int indiceMachine;

    /*
     * Constructeur
     */
    public Activite(Integer id, int nbMachinesNecessaires, Processus processus){
        this.id = id;
        this.nbMachinesNecessaires = nbMachinesNecessaires ;
        this.MachinesNecessaires = new ArrayList<Machine>();
        this.Durees = new Integer[nbMachinesNecessaires];
        this.indiceMachine = 0;
        this.processus = processus;
        this.date_debut = -1;
        this.date_fin = 0;
        this.machineChoisie = null;
        this.MachinesBanies = new ArrayList<Machine>();
    }

    /*
     * Méthodes
     */

    public int ajouterMachine(Machine machine, int duree){

        if (indiceMachine == nbMachinesNecessaires){
            System.out.println("Impossible d'ajouter une machine, nombre maximal atteint !");
            return -1;
        }
        MachinesNecessaires.add(machine);
        Durees[indiceMachine] = duree;
        this.indiceMachine += 1;

        return 0;
    }

    public Integer duree(Machine mac){
        Integer result = null;
        for (Machine m : MachinesNecessaires){
            if (m.id.equals(mac.id)){
                result = Durees[MachinesNecessaires.indexOf(m)];
            }
        }
        /*for (Integer m=0; m<MachinesNecessaires.length; m++) {
            if (MachinesNecessaires[m].equals(mac.id)) {
                result = this.Durees[m];
                //System.out.println("Activite " + this.id.toString() + " Machine " + mac.id.toString() + " : " + result.toString());
            }
        }*/
        return result;
    }

    public Integer dureeInt(Integer machine){
        Integer result = null;
        for (Machine m : MachinesNecessaires){
            if (m.id.equals(machine)) result = this.Durees[MachinesNecessaires.indexOf(m)];
        }
        /*for(Integer m: this.MachinesNecessaires){
            if(m==machine){
                result = this.Durees[m-1];
            }
        }*/
        return result;
    }

    public void refresh(){
        this.date_fin = date_debut + duree(this.machineChoisie);
        /*for (Integer mac: MachinesNecessaires) {
            this.date_fin = date_debut + dureeInt(mac);
        }*/
    }

    public boolean autreMachine(){
        return this.nbMachinesNecessaires >1;
    }

    public void bannirMachine(Machine machine){
        MachinesBanies.add(machine);
    }

    public boolean machinesDispo(){
        return MachinesNecessaires.size()>0;
    }

    public void flushMachinesBannies(){
        MachinesNecessaires.addAll(MachinesBanies);
        this.MachinesBanies.clear();
    }
}
