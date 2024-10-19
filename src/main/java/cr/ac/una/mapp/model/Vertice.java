package cr.ac.una.mapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stward segura
 */
public class Vertice {
    private List<Arista> aristas;
    private Integer id;
    private double x,y;//coordenadas
    public Vertice() {
        this.x = 0;
        this.y = 0;
        this.id = 0;
        this.aristas = new ArrayList<>();
    }

    public List<Arista> getAristas() {
        return aristas;
    }

    public void setAristas(List<Arista> aristas) {
        this.aristas = aristas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
}
