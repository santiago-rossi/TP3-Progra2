import java.util.*;

public class Nodo<T> {
    private T dato;

    public Nodo(T dato) {
        this.dato = dato;
    }

    public T obtenerDato() {
        return dato;
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}

public class Grafo<T> {
    private List<Nodo<T>> nodos;
    private List<List<Nodo<T>>> adyacencias;
    private boolean esDirigido;

    public Grafo(boolean esDirigido) {
        this.esDirigido = esDirigido;
        nodos = new ArrayList<>();
        adyacencias = new ArrayList<>();
    }

    public void agregarNodo(Nodo<T> nodo) {
        nodos.add(nodo);
        adyacencias.add(new ArrayList<>());
    }

    public void agregarArista(Nodo<T> origen, Nodo<T> destino) {
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i != -1 && j != -1) {
            adyacencias.get(i).add(destino);
            if (!esDirigido) {
                adyacencias.get(j).add(origen);
            }
        }
    }

    private int obtenerIndice(Nodo<T> nodo) {
        for (int i = 0; i < nodos.size(); i++) {
            if (nodos.get(i) == nodo) return i; // comparación por referencia
        }
        return -1;
    }

    public void recorridoDFS(Nodo<T> inicio) {
        List<Nodo<T>> visitados = new ArrayList<>();
        System.out.print("DFS: ");
        dfsRecursivo(inicio, visitados);
        System.out.println();
    }

    private void dfsRecursivo(Nodo<T> actual, List<Nodo<T>> visitados) {
        if (visitados.contains(actual)) return;
        visitados.add(actual);
        System.out.print(actual + " ");
        int indice = obtenerIndice(actual);
        for (Nodo<T> vecino : adyacencias.get(indice)) {
            dfsRecursivo(vecino, visitados);
        }
    }

    public void recorridoBFS(Nodo<T> inicio) {
        List<Nodo<T>> visitados = new ArrayList<>();
        Queue<Nodo<T>> cola = new LinkedList<>();
        cola.add(inicio);
        visitados.add(inicio);

        System.out.print("BFS: ");
        while (!cola.isEmpty()) {
            Nodo<T> actual = cola.poll();
            System.out.print(actual + " ");
            int indice = obtenerIndice(actual);
            for (Nodo<T> vecino : adyacencias.get(indice)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
        System.out.println();
    }

    public void mostrarMatrizAdyacencia() {
        int n = nodos.size();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (Nodo<T> vecino : adyacencias.get(i)) {
                int j = obtenerIndice(vecino);
                matriz[i][j] = 1;
            }
        }

        System.out.println("\nMatriz de Adyacencia:");
        System.out.print("     ");
        for (Nodo<T> nodo : nodos) {
            System.out.print(formato(nodo) + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(formato(nodos.get(i)) + " ");
            for (int j = 0; j < n; j++) {
                System.out.print("  " + matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    private String formato(Nodo<T> nodo) {
        String s = nodo.toString();
        return s.length() > 6 ? s.substring(0, 6) : String.format("%-6s", s);
    }
}

public class Persona {
    private int dni;
    private String nombre;

    public Persona(int dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre + "(" + dni + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        
        Grafo<Persona> grafo = new Grafo<>(true);

        Nodo<Persona> ana = new Nodo<>(new Persona(33445566, "Ana"));
        Nodo<Persona> luis = new Nodo<>(new Persona(11223344, "Luis"));
        Nodo<Persona> maria = new Nodo<>(new Persona(55667788, "María"));
        Nodo<Persona> pedro = new Nodo<>(new Persona(22334455, "Pedro"));
        Nodo<Persona> sofia = new Nodo<>(new Persona(77889900, "Sofía"));

        
        grafo.agregarNodo(ana);
        grafo.agregarNodo(luis);
        grafo.agregarNodo(maria);
        grafo.agregarNodo(pedro);
        grafo.agregarNodo(sofia);

   
        grafo.agregarArista(ana, luis);
        grafo.agregarArista(luis, maria);
        grafo.agregarArista(maria, pedro);
        grafo.agregarArista(pedro, sofia);
        grafo.agregarArista(sofia, ana); // crea un ciclo

     
        System.out.println("=== Recorridos desde Ana ===");
        grafo.recorridoDFS(ana);
        grafo.recorridoBFS(ana);

    
        grafo.mostrarMatrizAdyacencia();
    }
}
