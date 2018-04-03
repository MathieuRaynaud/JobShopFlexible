package JobShopFlexible;

public class JobShop {

    public Graphe JobShopGraph ;
    public int nbMachines;
    public Machine[] Machines;

    public JobShop (int nbMachines){
        this.JobShopGraph = new Graphe(10);
        this.nbMachines = nbMachines;

        //Initialisation des sommets
        this.JobShopGraph.ensembleSommets[0].id = "Origine";
        this.JobShopGraph.ensembleSommets[1].id = "O1,1";
        this.JobShopGraph.ensembleSommets[2].id = "O1,2";
        this.JobShopGraph.ensembleSommets[3].id = "O1,3";
        this.JobShopGraph.ensembleSommets[4].id = "O2,1";
        this.JobShopGraph.ensembleSommets[5].id = "O2,2";
        this.JobShopGraph.ensembleSommets[6].id = "O2,3";
        this.JobShopGraph.ensembleSommets[7].id = "O3,1";
        this.JobShopGraph.ensembleSommets[8].id = "O3,2";
        this.JobShopGraph.ensembleSommets[9].id = "Fin";

        //Initialisation des successeurs
        this.JobShopGraph.ensembleSommets[0].nbsuccesseurs = 3;
        this.JobShopGraph.ensembleSommets[1].nbsuccesseurs = 1;
        this.JobShopGraph.ensembleSommets[2].nbsuccesseurs = 1;
        this.JobShopGraph.ensembleSommets[3].nbsuccesseurs = 1;
        this.JobShopGraph.ensembleSommets[4].nbsuccesseurs = 1;
        this.JobShopGraph.ensembleSommets[5].nbsuccesseurs = 1;
        this.JobShopGraph.ensembleSommets[6].nbsuccesseurs = 1;
        this.JobShopGraph.ensembleSommets[7].nbsuccesseurs = 1;
        this.JobShopGraph.ensembleSommets[8].nbsuccesseurs = 1;
        this.JobShopGraph.ensembleSommets[9].nbsuccesseurs = 0;

        //Initialisation des coûts des arcs
        this.JobShopGraph.ensembleSommets[0].successeurs[0].cout = 0;
        this.JobShopGraph.ensembleSommets[0].successeurs[1].cout = 0;
        this.JobShopGraph.ensembleSommets[0].successeurs[2].cout = 0;
        this.JobShopGraph.ensembleSommets[1].successeurs[0].cout = 0;
        this.JobShopGraph.ensembleSommets[2].successeurs[0].cout = 0;
        this.JobShopGraph.ensembleSommets[3].successeurs[0].cout = 0;
        this.JobShopGraph.ensembleSommets[4].successeurs[0].cout = 0;
        this.JobShopGraph.ensembleSommets[5].successeurs[0].cout = 0;
        this.JobShopGraph.ensembleSommets[6].successeurs[0].cout = 0;
        this.JobShopGraph.ensembleSommets[7].successeurs[0].cout = 0;
        this.JobShopGraph.ensembleSommets[8].successeurs[0].cout = 0;

        //Connexion des sommets avec leurs successeurs (Arcs)
        this.JobShopGraph.ensembleSommets[0].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[1];
        this.JobShopGraph.ensembleSommets[0].successeurs[1].sommetArrive = this.JobShopGraph.ensembleSommets[2];
        this.JobShopGraph.ensembleSommets[0].successeurs[2].sommetArrive = this.JobShopGraph.ensembleSommets[3];
        this.JobShopGraph.ensembleSommets[1].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[4];
        this.JobShopGraph.ensembleSommets[2].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[5];
        this.JobShopGraph.ensembleSommets[3].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[6];
        this.JobShopGraph.ensembleSommets[4].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[7];
        this.JobShopGraph.ensembleSommets[5].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[8];
        this.JobShopGraph.ensembleSommets[6].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[9];
        this.JobShopGraph.ensembleSommets[7].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[9];
        this.JobShopGraph.ensembleSommets[8].successeurs[0].sommetArrive = this.JobShopGraph.ensembleSommets[9];

        this.Machines = new Machine[nbMachines];
        for (int i=0; i<this.nbMachines; i++){
            Machines[i] = new Machine(i);
        }
    }




}