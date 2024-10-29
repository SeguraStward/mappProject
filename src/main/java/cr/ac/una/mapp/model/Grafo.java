package cr.ac.una.mapp.model;

/**
 *
 * @author stward segura
 */
import java.util.*;

public class Grafo {
    private List<Vertice> vertices;
    private int[][] matrizPesos; 
    private int[][] predecesores;  

    public Grafo(List<Vertice> vertices) {
        this.vertices = vertices;
        this.matrizPesos = crearMatrizPesos();
        this.predecesores = new int[vertices.size()][vertices.size()];
    }
    public Grafo(){
    
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
        this.matrizPesos = crearMatrizPesos();
    }
    
     
    private int[][] crearMatrizPesos() {
        int n = vertices.size();
        int[][] matrizPesos = new int[n][n];
        
        
        for (int i = 0; i < n; i++) {
            Arrays.fill(matrizPesos[i], Integer.MAX_VALUE);
            matrizPesos[i][i] = 0; // Distancia a sí mismo es 0
        }
        
        // Rellenar matriz con pesos de las aristas
        for (int i = 0; i < n; i++) {
            Vertice vertice = vertices.get(i);
            for (Arista arista : vertice.getAristas()) {
                int j = vertices.indexOf(arista.getDestino());
                if (!arista.getIsClosed()) {
                    matrizPesos[i][j] = arista.getPeso();
                }
            }
        }
        return matrizPesos;
    }

    // Implementación de Dijkstra que retorna la lista de vértices del camino más corto
    public List<Vertice> dijkstra(int inicio, int fin) {
        int n = vertices.size();
        int[] dist = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[inicio] = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(i -> dist[i]));
        pq.add(inicio);

        while (!pq.isEmpty()) {
            int u = pq.poll();
            if (u == fin) break;

            for (int v = 0; v < n; v++) {
                if (matrizPesos[u][v] != Integer.MAX_VALUE) {
                    int nuevoDist = dist[u] + matrizPesos[u][v];
                    if (nuevoDist < dist[v]) {
                        dist[v] = nuevoDist;
                        prev[v] = u;
                        pq.add(v);
                    }
                }
            }
        }

        // Reconstruir el camino
        List<Vertice> path = new ArrayList<>();
        for (int at = fin; at != -1; at = prev[at]) {
            path.add(vertices.get(at));
        }
        Collections.reverse(path);
        
        if (path.get(0) != vertices.get(inicio)) {
            return Collections.emptyList(); // No hay ruta si el primer nodo no es el inicio
        }
        
        return path;
    }

    // Implementación de Floyd-Warshall que retorna la lista de vértices del camino más corto
    public List<Vertice> floydWarshall(int inicio, int fin) {
        int n = vertices.size();
        int[][] dist = new int[n][n];
        this.predecesores = new int[n][n];
        
        // Inicializar matrices de distancias y predecesores
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            Arrays.fill(predecesores[i], -1);
            dist[i][i] = 0;
        }

        // Configurar distancias y predecesores iniciales
        for (int i = 0; i < n; i++) {
            Vertice vertice = vertices.get(i);
            for (Arista arista : vertice.getAristas()) {
                int j = vertices.indexOf(arista.getDestino());
                if (!arista.getIsClosed()) {
                    dist[i][j] = arista.getPeso();
                    predecesores[i][j] = i;
                }
            }
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            predecesores[i][j] = predecesores[k][j];
                        }
                    }
                }
            }
        }

        // Reconstruir el camino
        List<Vertice> path = new ArrayList<>();
        int at = fin;
        while (at != -1) {
            path.add(vertices.get(at));
            at = predecesores[inicio][at];
        }
        Collections.reverse(path);

        if (path.get(0) != vertices.get(inicio)) {
            return Collections.emptyList(); // No hay ruta si el primer nodo no es el inicio
        }
        
        return path;
    }
}

