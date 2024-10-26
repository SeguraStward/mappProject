package cr.ac.una.mapp.controller;

import cr.ac.una.mapp.model.Arista;
import cr.ac.una.mapp.model.Grafo;
import cr.ac.una.mapp.model.Vertice;
import cr.ac.una.mapp.util.AppManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class SecundaryController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView mapaImg;
    private List<Vertice> vertices = new ArrayList<>();
    private List<Arista> aristas = new ArrayList<>();
    private List<Circle> circulos = new ArrayList<>();
    private List<Line> lineas = new ArrayList<>();
    private Grafo grafo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        //elimianr lo que haya limpiar todo y despues cargar los nodos

        aristas = AppManager.getInstance().cargar();
        if (aristas != null && !aristas.isEmpty()) {
            for (Arista arista : aristas) {
                arista.getDestino().getRecibidas().add(arista);
                arista.getDestino().getAristas().add(arista);

                if (!isUniqueVertice(arista.getDestino())) {
                    vertices.add(arista.getDestino());
                }
                if (!isUniqueVertice(arista.getOrigen())) {
                    vertices.add(arista.getOrigen());
                }
                drawLine(arista);
            }
        } else {
            System.out.println("No hay aristas");
        }
        if (vertices != null && !vertices.isEmpty()) {
            for (Vertice vertice : vertices) {
                colocarCirculo(vertice);
            }
        }
        
        
        grafo.setVertices(vertices);
        
    }

    private void colocarCirculo(Vertice vertice) {
         
        Circle circle = new Circle(vertice.getX(), vertice.getY(), 5);
        circle.setFill(Color.ALICEBLUE);
        circle.setStroke(Color.AQUA);

        circle.setUserData(vertice);
        circulos.add(circle);
        System.out.println("se creo un circulo");

        root.getChildren().add(circle);

        circle.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
               //seleccionar nodo A y B para la ruta una vez seleccionados empezar ruta segun ruta elegida
               //al seleccionar dos se debe pintar la ruta 
               //e iniciar la animacion
               //cuando se inicia la animacion y llega a un nodo entonces se recalcula la ruta y si da otra entonces marcarla con otro color
               //en el grafo y mostrarla
            } else if (e.getButton() == MouseButton.SECONDARY) {
                //change color and other things maybe
            }
        });
        circle.setOnMouseEntered(e -> {
            circle.setRadius(7);
        });

        circle.setOnMouseExited(e -> {
            circle.setRadius(5);
        });
    }

    private void drawLine(Arista arista) {

        double offset = 10;

        double startX = arista.getOrigen().getX();
        double startY = arista.getOrigen().getY();
        double endX = arista.getDestino().getX();
        double endY = arista.getDestino().getY();

        double deltaX = endX - startX;
        double deltaY = endY - startY;
        double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double scaleX = (deltaX / length) * offset;
        double scaleY = (deltaY / length) * offset;

        Line line = new Line(startX + scaleX, startY + scaleY, endX - scaleX, endY - scaleY);

        line.setStroke(Color.TRANSPARENT);
        line.setStrokeWidth(2);
        line.setUserData(arista);
        Circle origenCircle = obtenerCirculoDesdeVertice(arista.getOrigen());
        Circle destinoCircle = obtenerCirculoDesdeVertice(arista.getDestino());

        line.setOnMouseClicked(event -> {//linea seleccionada
            //efecto hover

            efectoDeLinea(line, origenCircle, destinoCircle, event);

            //ventana para agregar accidentes,inhabilitar la arista, cantidad de transito, y mas
            //configurar la linea puede ser
        });
        //efectos para hover en la linea
        line.setOnMouseEntered(e -> {
            line.setStrokeWidth(3);

            if (origenCircle != null) {
                origenCircle.setRadius(7);
            }
            if (destinoCircle != null) {
                destinoCircle.setRadius(7);
            }
        });
        //efectos hover
        line.setOnMouseExited(e -> {
            line.setStrokeWidth(2);

            if (origenCircle != null) {
                origenCircle.setRadius(5);
            }
            if (destinoCircle != null) {
                destinoCircle.setRadius(5);
            }
        });
        lineas.add(line);
        drawArrow(line);
        root.getChildren().add(line);
    }

    private Circle obtenerCirculoDesdeVertice(Vertice vertice) {
        for (Circle circle : circulos) {
            Vertice v = (Vertice) circle.getUserData();
            if (v.equals(vertice)) {
                return circle;
            }
        }
        return null;
    }

    private void efectoDeLinea(Line line, Circle origenCircle, Circle destinoCircle, MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {

            if (origenCircle != null) {
                origenCircle.setRadius(7);
            }
            if (destinoCircle != null) {
                destinoCircle.setRadius(7);
            }
            //seleccionada hacer algo para agreagar accidentes o demas
            line.setStroke(Color.RED);
        } else if (event.getButton() == MouseButton.SECONDARY) {

            if (origenCircle != null) {
                origenCircle.setRadius(5);
            }
            if (destinoCircle != null) {
                destinoCircle.setRadius(5);
            }

            line.setStroke(Color.BLACK);
        }
    }

   private void drawArrow(Line line) {
        
        double startX = line.getStartX();
        double startY = line.getStartY();
        double endX = line.getEndX();
        double endY = line.getEndY();

       
        double deltaX = endX - startX;
        double deltaY = endY - startY;
        double angle = Math.atan2(deltaY, deltaX);

        
        double arrowLength = 10;
        double arrowWidth = 2;

        
        double x1 = endX - arrowLength * Math.cos(angle - Math.PI / 6);
        double y1 = endY - arrowLength * Math.sin(angle - Math.PI / 6);
        double x2 = endX - arrowLength * Math.cos(angle + Math.PI / 6);
        double y2 = endY - arrowLength * Math.sin(angle + Math.PI / 6);

        
        Line arrow1 = new Line(endX, endY, x1, y1);
        Line arrow2 = new Line(endX, endY, x2, y2);
        arrow1.setStrokeWidth(arrowWidth);
        arrow2.setStrokeWidth(arrowWidth);
        arrow1.setStroke(Color.BLACK);
        arrow2.setStroke(Color.BLACK); 
        
        arrow1.setOnMouseClicked(event -> { 
            //ventana para agregar accidentes,inhabilitar la arista, cantidad de transito, y mas
            //configurar la linea puede ser
        });
        arrow2.setOnMouseClicked(event -> { 
            //ventana para agregar accidentes,inhabilitar la arista, cantidad de transito, y mas
            //configurar la linea puede ser
        }); 
        arrow1.setOnMouseEntered(e -> {
            line.setStroke(Color.RED);
            line.setStrokeWidth(3);
            arrow1.setStroke(Color.RED);
            arrow2.setStroke(Color.RED);
            arrow1.setStrokeWidth(4);
            arrow2.setStrokeWidth(4); 
        }); 
        arrow2.setOnMouseEntered(e -> {
            line.setStrokeWidth(3);
            line.setStroke(Color.RED);
            arrow1.setStroke(Color.RED);
            arrow2.setStroke(Color.RED);
            arrow1.setStrokeWidth(4);
            arrow2.setStrokeWidth(4); 
        }); 
        arrow1.setOnMouseExited(e -> {
            line.setStroke(Color.TRANSPARENT);
            line.setStrokeWidth(2);
            arrow1.setStroke(Color.BLACK);
            arrow2.setStroke(Color.BLACK);
            arrow1.setStrokeWidth(3);
            arrow2.setStrokeWidth(3); 
        }); 
        arrow2.setOnMouseExited(e -> {
            line.setStroke(Color.TRANSPARENT);
            line.setStrokeWidth(2);
            arrow1.setStroke(Color.BLACK);
            arrow2.setStroke(Color.BLACK);
            arrow1.setStrokeWidth(3);
            arrow2.setStrokeWidth(3); 
        });
        root.getChildren().addAll(arrow1, arrow2);
    }

    public boolean isUniqueVertice(Vertice vertice) {
        if (vertices != null && !vertices.isEmpty()) {
            for (Vertice aux : vertices) {
                if (aux.getX() == vertice.getX() && aux.getY() == vertice.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

}
