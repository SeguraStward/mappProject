package cr.ac.una.mapp.controller;

import cr.ac.una.mapp.model.Arista;
import cr.ac.una.mapp.model.Vertice;
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
public class PrincipalController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView mapaImg;
    private List<Circle> circulos = new ArrayList<>();
    private Integer click = 0;
    private Circle origen;
    private Circle destino;
    private List<Line> lineas = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapaImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            colocarCirculo(event.getX(), event.getY());
        });
    }

    @Override
    public void initialize() {
    }

    private void colocarCirculo(double x, double y) {

        Circle circle = new Circle(x, y, 5);
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        Vertice vertice = new Vertice();
        vertice.setX(x);
        vertice.setY(y);

        circle.setUserData(vertice);
        circulos.add(circle);
        System.out.println("se creo un circulo");

        root.getChildren().add(circle);

        circle.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                crearVinculo(circle, e);

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

    private Circle obtenerCirculoEnPosicion(double x, double y) {
        for (Circle circle : circulos) {

            double distancia = Math.sqrt(Math.pow(circle.getCenterX() - x, 2) + Math.pow(circle.getCenterY() - y, 2));
            if (distancia <= circle.getRadius()) {
                return circle;
            }
        }
        return null;
    }

    private void drawLine(Arista arista) {
        Line line = new Line(origen.getCenterX(), origen.getCenterY(),
                destino.getCenterX(), destino.getCenterY());
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        line.setUserData(arista);
        Circle origenCircle = obtenerCirculoDesdeVertice(arista.getOrigen());
        Circle destinoCircle = obtenerCirculoDesdeVertice(arista.getDestino());

        line.setOnMouseClicked(event -> {//linea seleccionada
            //efecto hover
         
         efectoDeLinea(line,origenCircle,destinoCircle,event);
         
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
        root.getChildren().add(line);
    }

    private void circleInfo(Circle cir) {
        Vertice ver = (Vertice) cir.getUserData();
        int i = 0;
        for (Arista arista : ver.getAristas()) {
            System.out.println("arista" + Integer.toString(++i));
        }
    }

    private void crearVinculo(Circle circle, MouseEvent e) {
        circleInfo(circle);
        if (click == 0) {
            click++;
            origen = circle;
            System.out.println(" origen");
        } else if (click == 1) {

            destino = circle;
            if (origen != destino) {
                click++;
            }

            System.out.println(" destino");

        }

        if (click == 2) {

            //agregar la arista y la linea en pantalla
            Arista arista = new Arista();
            Vertice origenV = (Vertice) origen.getUserData();
            Vertice destinoV = (Vertice) destino.getUserData();
            origenV.getAristas().add(arista);
            destinoV.getAristas().add(arista);
            arista.setDestino(destinoV);
            arista.setOrigen(origenV);
            drawLine(arista);
            origen = null;
            destino = null;
            click = 0;

        }

        e.consume();
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
    
    private void efectoDeLinea(Line line, Circle origenCircle, Circle destinoCircle, MouseEvent event){
           if (event.getButton() == MouseButton.PRIMARY) {

                if (origenCircle != null) {
                    origenCircle.setRadius(7);
                }
                if (destinoCircle != null) {
                    destinoCircle.setRadius(7);
                }

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
}
