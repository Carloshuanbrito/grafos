import java.util.ArrayList;

public class LeitorDeGrafo {
    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Por favor, informe o nome do arquivo.");
            StdOut.println("Uso no terminal: java LeitorDeGrafo CA-GrQc.txt");
            return;
        }

        String nomeArquivo = args[0];
        StdOut.println("Lendo o arquivo: " + nomeArquivo + "...\n");

        try {
            // A classe In lê o arquivo de texto
            In in = new In(nomeArquivo);
            String[] linhas = in.readAllLines();
            
            ArrayList<int[]> arestas = new ArrayList<>();
            int maxId = 0;

            // 1. Passa por todas as linhas do arquivo e filtra os dados
            for (String linha : linhas) {
                linha = linha.trim();
                
                // Ignora linhas vazias ou comentários (que começam com #)
                if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                }

                // Divide a linha onde há espaços ou tabulações
                String[] partes = linha.split("\\s+");
                if (partes.length >= 2) {
                    int v = Integer.parseInt(partes[0]);
                    int w = Integer.parseInt(partes[1]);
                    
                    arestas.add(new int[]{v, w});
                    
                    // Descobre o maior ID para definir o tamanho da matriz do Grafo
                    if (v > maxId) maxId = v;
                    if (w > maxId) maxId = w;
                }
            }

            // 2. Cria o grafo com V = maxId + 1 (pois os IDs começam no 0)
            int V = maxId + 1;
            Graph grafo = new Graph(V);

            // 3. Adiciona as arestas no objeto grafo
            for (int[] aresta : arestas) {
                grafo.addEdge(aresta[0], aresta[1]);
            }

            StdOut.println("✅ Leitura e construção do Grafo concluidas!\n");
            
            // Exibe as estatísticas gerais
            StdOut.println("ESTATISTICAS DO GRAFO:");
            StdOut.println("Tamanho do Array de Vertices (Posiçoes criadas): " + grafo.V());
            StdOut.println("Número de Arestas inseridas: " + grafo.E());

            // 4. NOVO: Conta apenas os vértices reais (que têm alguma conexão)
            int verticesReais = 0;
            int grauMaximo = 0;
            int verticeMaximo = 0;

            for (int v = 0; v < grafo.V(); v++) {
                int grauAtual = grafo.degree(v);
                
                if (grauAtual > 0) {
                    verticesReais++; // Conta como um autor válido
                }

                if (grauAtual > grauMaximo) {
                    grauMaximo = grauAtual;
                    verticeMaximo = v;
                }
            }
            
            StdOut.println("Vertices REAIS (Autores únicos encontrados): " + verticesReais);
            StdOut.println("\n DESTAQUE:");
            StdOut.println("Autor mais colaborativo (ID do Vertice): " + verticeMaximo);
            StdOut.println("Numero de coautorias: " + grauMaximo);

        } catch (Exception e) {
            StdOut.println("Erro ao processar o arquivo.");
            e.printStackTrace();
        }
    }
}