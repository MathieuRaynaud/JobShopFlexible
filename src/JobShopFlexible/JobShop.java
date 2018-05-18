package JobShopFlexible;

import javax.xml.bind.SchemaOutputResolver;
import java.io.*;

public class JobShop {

    public Graphe JobShopGraph ;
    public Processus[] Processus;
    public int indiceProc;
    public Machine[] tableauMachines;

    public JobShop (File file){
        BufferedReader br = null;
        FileReader fr = null;
        Integer currentLine = 0;
        Integer nbTotalActivites = 0;

        /* Lecture du fichier et creation des processus, activites, et machines */
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.charAt(0) == ' ') sCurrentLine = sCurrentLine.substring(1, sCurrentLine.length());
                String[] parts = sCurrentLine.split("[ \\t]+");
                if(currentLine == 0){
                    System.out.println("Nombre de processus : " + parts[0]);
                    this.Processus = new Processus[Integer.parseInt(parts[0])];
                    System.out.println("Nombre de machines disponibles : " + parts[1]);
                    /************* CREATION DE TOUTES LES MACHINES *************/
                    tableauMachines = new Machine[Integer.parseInt(parts[1])];
                    for(int i=0; i<Integer.parseInt(parts[1]); i++){
                        tableauMachines[i] = new Machine(i+1);
                    }
                    /***********************************************************/
                    //System.out.println("Nombre de machines moyen : " + parts[2]);
                    System.out.println("\n*-----------------------------------*");
                }
                else{
                    Integer nbActivite = 1;
                    int indice = 1;
                    int i, nbMachines = 0;
                    System.out.println("\nProcessus " + currentLine + " : " + parts[0] + " machines.");
                    this.indiceProc = currentLine - 1;
                    this.Processus[indiceProc] = new Processus(currentLine, Integer.parseInt(parts[0]));
                    int compteur_act = 0;
                    while(indice < parts.length){
                        String numActivite = currentLine.toString() + '.' + nbActivite.toString();
                        System.out.println("  Activité " + numActivite + " - Machine(s) nécessaire(s)  : " + parts[indice]);
                        nbMachines = Integer.parseInt(parts[indice]);
                        Processus[indiceProc].ajouterActivite(new Activite(nbActivite,nbMachines));
                        i = indice+1;
                        while(i <= indice+nbMachines*2){
                            System.out.println("    Machine " + parts[i] + " - Duree : " + parts[i+1]);
                            Processus[indiceProc].Activites[compteur_act].ajouterMachine(Integer.parseInt(parts[i]), Integer.parseInt(parts[i+1]));
                            i += 2;
                        }
                        indice = i;
                        nbActivite++;
                        nbTotalActivites++;
                        compteur_act++;
                    }
                    System.out.println("\n*-----------------------------------*");
                }
                currentLine++;
            }

        } catch (IOException | IndexOutOfBoundsException err) {}

        /* Creation du graphe a partir des processus, activites, et machines */
        JobShopGraph = new Graphe(nbTotalActivites+2);
        Sommet sommetDebut = new Sommet("debut",0, null);
        Sommet sommetFin = new Sommet("fin",Processus.length, null);
        Sommet tmp;
        JobShopGraph.ajouterSommet(sommetDebut);
        for (int i=0; i<Processus.length; i++){
            for (int j=0; j<Processus[i].nbActivites; j++){
                if (j ==0){
                    tmp = new Sommet((Processus[i].Activites[j].id).toString(),1,(Processus[i].Activites[j]));
                    tmp.predecesseurs[0] = new Arc(sommetDebut,0,0);
                }
                else{
                    tmp = new Sommet((Processus[i].Activites[j].id).toString(),Processus[i].Activites[j-1].nbMachinesNecessaires,(Processus[i].Activites[j]));
                    for (int k = 0; k<Processus[i].Activites[j-1].nbMachinesNecessaires; k++) {
                        tmp.predecesseurs[k] = new Arc(JobShopGraph.ensembleSommets[j - 1], Processus[i].Activites[j - 1].MachinesNecessaires[k], Processus[i].Activites[j-1].Durees[k]);
                    }
                }
                JobShopGraph.ajouterSommet(tmp);
            }
        }
        JobShopGraph.ajouterSommet(sommetFin);

    }

    public static void main (String[] args){
        File file = new File("Job_Data/Barnes/Text/mt10c1.fjs");
        JobShop JobFlex = new JobShop(file);
    }

}