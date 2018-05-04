package JobShopFlexible;

public class Processus {

    /*
     * Déclarations
     */
    public Integer id;
    public Integer nbActivites;
    public Activite Activites [];
    public Integer indiceActivite;

    /*
     * Constructeur
     */
    public Processus (Integer id, Integer nbActivites){
        this.id = id;
        this.nbActivites = nbActivites;
        this.Activites = new Activite[this.nbActivites];
        this.indiceActivite = 0;
    }

    /*
     * Méthodes
     */

    public int ajouterActivite(Activite activite){

        if (indiceActivite == nbActivites){
            System.out.println("Impossible d'ajouter une activite, nombre maximal atteint");
            return -1;
        }

        Activites[indiceActivite] = activite;
        indiceActivite += 1;

        return 0;
    }
}
