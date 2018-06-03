package JobShopFlexible;

import javax.xml.bind.SchemaOutputResolver;
import java.io.*;

public class JobShop {

    public Graphe JobShopGraph ;
    public Processus[] Processus;
    public int indiceProc;
    public Machine[] tableauMachines;

    public JobShop (File file){
        BufferedReader br;
        FileReader fr;
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
                    System.out.println("Nombre de machines a utiliser par activite : " + parts[2]);
                }
                else{
                    Integer nbActivite = 1;
                    int indice = 1;
                    int i, nbMachines = 0;
                    this.indiceProc = currentLine - 1;
                    this.Processus[indiceProc] = new Processus(currentLine, Integer.parseInt(parts[0]));
                    int compteur_act = 0;
                    while(indice < parts.length){
                        String numActivite = currentLine.toString() + '.' + nbActivite.toString();
                        nbMachines = Integer.parseInt(parts[indice]);
                        Processus[indiceProc].ajouterActivite(new Activite(nbActivite,nbMachines,Processus[indiceProc]));
                        i = indice+1;
                        while(i <= indice+nbMachines*2){
                            Processus[indiceProc].Activites[compteur_act].ajouterMachine(machine(Integer.parseInt(parts[i])), Integer.parseInt(parts[i+1]));
                            i += 2;
                        }
                        indice = i;
                        nbActivite++;
                        nbTotalActivites++;
                        compteur_act++;
                    }
                    //System.out.println("\n*-----------------------------------*");
                }
                currentLine++;
            }
        } catch (IOException | IndexOutOfBoundsException err) {}

        /** Initialisaiton du graphe et ajout des sommets **/
        JobShopGraph = new Graphe();
        Sommet sommetDebut = new Sommet("debut",0, null, null);
        JobShopGraph.ajouterSommet(sommetDebut);
        Sommet sommetFin = new Sommet("fin",Processus.length, null, null);
        Sommet tmp;
        for (int i=0; i<Processus.length; i++){
            for (int j=0; j<Processus[i].nbActivites; j++){
                if (j==0){
                    tmp = new Sommet(Processus[i].id.toString()+"."+Processus[i].Activites[j].id.toString(),1,Processus[i], Processus[i].Activites[j]);
                    JobShopGraph.ajouterSommet(tmp);
                    JobShopGraph.ajouterArc(sommetDebut,tmp,null,0);
                }
                else {
                    tmp = new Sommet(Processus[i].id.toString() + "." + Processus[i].Activites[j].id.toString(), 1, Processus[i], Processus[i].Activites[j]);
                    for (int k = 0; k < tmp.nbpredecesseurs; k++) {
                        JobShopGraph.ajouterSommet(tmp);
                    }
                }
            }
        }
        JobShopGraph.ajouterSommet(sommetFin);
    }

    public Machine machine(Integer id){
        for (Machine m : tableauMachines){
            if (m.id.equals(id)) return m;
        }
        return null;
    }

    public static void main (String[] args){
        long debut = System.currentTimeMillis();
        File file = new File("Job_Data/Barnes/Text/mt10xyz.fjs");
        JobShop JobFlex = new JobShop(file);
        Glouton glouton = new Glouton(JobFlex);
        glouton.heuristiqueGloutonne();
        System.out.println("Temps d'exécution du programme : " + (System.currentTimeMillis()-debut) + "ms");
    }

}