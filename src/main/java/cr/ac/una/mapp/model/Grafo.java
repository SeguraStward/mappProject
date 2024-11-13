package cr.ac.una.mapp.model;

/**
 *
 * @author stward segura
 */
import java.util.*;

public class Grafo {

    private List<Vertice> vertices;
    private List<List<Integer>> matrix;  // Usamos ArrayList para la matriz de adyacencia

    public Grafo(List<Arista> aristas) {
        // Obtener lista de vértices únicos
        this.vertices = obtenerVerticesUnicos(aristas);

        // Crear la matriz de adyacencia con tamaño igual al número de vértices
        int numVertices = vertices.size();
        numVertices += 10;
        System.out.println("size vertices:" + vertices.size() +"numvertices:" + numVertices);
        matrix = new ArrayList<>(numVertices );

        // Inicializar la matriz con ArrayLists
        for (int i = 0; i < numVertices; i++) {
            matrix.add(new ArrayList<>(Collections.nCopies(numVertices, Integer.MAX_VALUE)));  // Rellenamos con Integer.MAX_VALUE
        }

        for (Arista arista : aristas) {
            int origenIndex = arista.getOrigen().getId();
            int destinoIndex = arista.getDestino().getId();

            matrix.get(origenIndex).set(destinoIndex, arista.getPeso());
        }
    }

    private List<Vertice> obtenerVerticesUnicos(List<Arista> aristas) {
        Set<Vertice> verticesSet = new HashSet<>();
        for (Arista arista : aristas) {
            verticesSet.add(arista.getOrigen());
            verticesSet.add(arista.getDestino());
        }
        return new ArrayList<>(verticesSet);
    }

    public void mostrarMatrizAdyacencia() {
        System.out.println("Matriz de Adyacencia:");
        for (List<Integer> fila : matrix) {
            for (Integer valor : fila) {
                if (valor == Integer.MAX_VALUE) {
                    System.out.print("∞ ");
                } else {
                    System.out.print(valor + " ");
                }
            }
            System.out.println();
        }
    }
    
     public List<Vertice> dijkstra(int origenId, int destinoId) {
      
        int numVertices = vertices.size();
           numVertices += 10;
        // Distancias mínimas desde el origen
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[origenId] = 0;

        // Predecesores para reconstruir el camino
        int[] predecesores = new int[numVertices];
        Arrays.fill(predecesores, -1);

        // Min-heap para seleccionar el vértice con la distancia mínima
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(v -> distancias[v]));
        pq.add(origenId);

        while (!pq.isEmpty()) {
            int actual = pq.poll();

            // Si llegamos al destino, reconstruimos el camino
            if (actual == destinoId) {
                return reconstruirCamino(predecesores, origenId, destinoId);
            }

            // Recorremos los vecinos del vértice actual
            for (int vecino = 0; vecino < numVertices; vecino++) {
                if (matrix.get(actual).get(vecino) != Integer.MAX_VALUE) {
                    int nuevoDistancia = distancias[actual] + matrix.get(actual).get(vecino);
                    if (nuevoDistancia < distancias[vecino]) {
                        distancias[vecino] = nuevoDistancia;
                        predecesores[vecino] = actual;
                        pq.add(vecino);
                    }
                }
            }
        }

        return new ArrayList<>();  // Si no hay camino
    }
     private List<Vertice> reconstruirCamino(int[] predecesores, int origen, int destino) {
        List<Vertice> camino = new ArrayList<>();
        for (int at = destino; at != -1; at = predecesores[at]) {
            camino.add(vertices.get(at));
        }
        Collections.reverse(camino);
        return camino;
    }

}
