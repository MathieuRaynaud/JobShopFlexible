package JobShopFlexible;

public class Infos {

    /*
     *  Strcuture pour les informations dans la table des solutions de l'heuristique
     */

    public Machine machine;
    public Activite activite;
    public Integer date_debut;
    public Integer duree;
    public Integer date_fin;

    /*
     * Constructeur
     */

    public Infos(Machine machine, Activite activite, Integer date_debut, Integer duree){
        this.machine = machine;
        this.activite = activite;
        this.date_debut = date_debut;
        this.duree = duree;
        this.date_fin = date_debut+duree;
    }
}
