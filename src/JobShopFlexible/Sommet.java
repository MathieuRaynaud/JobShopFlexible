package JobShopFlexible;

import java.util.ArrayList;

public class Sommet {

    /*************************************
     * Un sommet correspond à une activité
     *************************************/

    public String id;
    public int nbpredecesseurs;
    public ArrayList<Arc> predecesseurs;
    public int indicePredecesseurs;
    public Processus processus;
    public Activite activite;


    // CONSTRUCTEUR
    public Sommet (String nom, int nbpredecesseurs, Processus processus, Activite activite) {
        this.id = nom;
        this.nbpredecesseurs = nbpredecesseurs;
        this.predecesseurs = new ArrayList<Arc>();
        this.indicePredecesseurs = nbpredecesseurs-1;
        this.processus = processus;
        this.activite = activite;
    }

    /*
     * Méthodes
     */

    /***
     * Fonction de mise à jour du cout de l'arc
     ***/
    public Integer modifierArc(Sommet depart, Machine nouvelleMachine, Integer nouveauCout){
        Integer res = -1;
        for(Arc a: predecesseurs){
            if(a.sommetDepart == depart){
                a.machine = nouvelleMachine;
                a.duree = nouveauCout;
                res = 0;
            }
        }
        return res;
    }

    /***
     * Fonction d'ajout d'un arc
     ***/
    public Integer ajouterArc(Sommet depart, Machine machine, Integer cout){
       /* if (indicePredecesseurs == -1){
            System.out.println("Impossible d'ajouter un successeur, nombre maximal atteint");
            return -1;
        }*/

        this.predecesseurs.add(new Arc(depart, machine, cout));
        indicePredecesseurs--;
        return 0;
    }

    /***
     * Fonction de retour du predecesseur avec telle machine
     ***/

    public Sommet predecesseur(Machine mac){
        for (Arc a:predecesseurs){
            if(a.machine == mac) return a.sommetDepart;
        }
        return null;
    }

    public Arc predecesseur(Sommet sommet){
        for (Arc a : predecesseurs){
            if (a.sommetDepart == sommet){
                return a;
            }
        }
        return null;
    }

    public Sommet predecesseur(){
        for (Arc a : predecesseurs){
            if (a.sommetDepart.id != "debut") {
                if (a.sommetDepart.processus == this.processus) {
                    if (a.sommetDepart.activite.id == activite.id - 1) {
                        return a.sommetDepart;
                    }
                }
            }
            else return a.sommetDepart;
        }
        return null;
    }

    public boolean aPourPredecesseur(Sommet s){
        for (Arc a : predecesseurs){
            if (a.sommetDepart == s){
                return true;
            }
        }
        return false;
    }
}