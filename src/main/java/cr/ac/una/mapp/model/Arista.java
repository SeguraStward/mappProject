package cr.ac.una.mapp.model;

/**
 *
 * @author stward segura
 */
public class Arista {
    
    private Vertice origen;
    private Vertice destino;
    private Integer peso;
    private Boolean disponibilidad;
    private Boolean dosDirecciones;
    public Arista() {
        this.origen = null;
        this.destino = null;
        this.peso = 0;
        this.disponibilidad = true;
        this.dosDirecciones = false;
    }

    public Boolean getDosDirecciones() {
        return dosDirecciones;
    }

    public void setDosDirecciones(Boolean dosDirecciones) {
        this.dosDirecciones = dosDirecciones;
    }

    public Vertice getOrigen() {
        return origen;
    }

    public void setOrigen(Vertice origen) {
        this.origen = origen;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
}
