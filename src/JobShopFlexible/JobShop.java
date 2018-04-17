package JobShopFlexible;

import java.io.*;

public class JobShop {

    public Graphe JobShopGraph ;
    public int nbMachines;
    public Machine[] Machines;

    public JobShop (File file){
        BufferedReader br = null;
        FileReader fr = null;
        Integer currentLine = 0;

        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.charAt(0) == ' ') sCurrentLine = sCurrentLine.substring(1, sCurrentLine.length());
                String[] parts = sCurrentLine.split("[ \\t]+");
                if(currentLine == 0){
                    System.out.println("Nombre de proc : " + parts[0]);
                    System.out.println("Nombre de machines : " + parts[1]);
                    System.out.println("Nombre de machines necessaires : " + parts[2]);
                    System.out.println("\n*-----------------------------------*");
                }
                else{
                    Integer nbActivite = 1;
                    int indice = 1;
                    int i, nbMachines = 0;
                    System.out.println("\nProcessus " + currentLine + " : " + parts[0] + " machines.");
                    while(indice < parts.length){
                        String numActivite = currentLine.toString() + '.' + nbActivite.toString();
                        System.out.println("  Activité " + numActivite + " - Machine(s) nécessaire(s)  : " + parts[indice]);
                        nbMachines = Integer.parseInt(parts[indice]);
                        i = indice+1;
                        while(i <= indice+nbMachines*2){
                            System.out.println("    Machine " + parts[i] + " - Duree : " + parts[i+1]);
                            i += 2;
                        }
                        indice = i;
                        nbActivite++;
                    }
                    System.out.println("\n*-----------------------------------*");
                }
                currentLine++;
            }

        } catch (IOException | IndexOutOfBoundsException err) {}

        /*this.JobShopGraph = new Graphe(10);
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
        }*/
    }

    public static void main (String[] args){
        File file = new File("Job_Data/Barnes/Text/mt10c1.fjs");
        JobShop JobFlex = new JobShop(file);
    }

}