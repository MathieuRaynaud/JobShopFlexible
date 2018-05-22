package JobShopFlexible;

public class Infos {

    /*
     *  Strcuture pour les informations dans la table des solutions de l'heuristique
     */

    public Processus processus;
    public Activite activite;
    public Machine machine;
    public Integer date_debut;
    public Integer duree;
    public Integer date_fin;

    /*
     * Constructeur
     */

    public Infos(Machine machine, Activite activite, Processus processus, Integer date_debut, Integer duree){
        this.machine = machine;
        this.activite = activite;
        this.processus = processus;
        this.date_debut = date_debut;
        this.duree = duree;
        this.date_fin = date_debut+duree;
    }

    /*
     * Méthodes
     */

    public void refresh() {
        this.date_fin = this.date_debut + this.duree;
    }
}