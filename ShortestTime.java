import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// Задача 1. Минимальное время из города s. Веса неотрицательные.
public class ShortestTime {
    static class Road {
        int to;
        int time;

        Road(int to, int time) {
            this.to = to;
            this.time = time;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            roads.get(u).add(new Road(v, w));
        }

        long inf = Long.MAX_VALUE / 4;
        long[] dist = new long[n + 1];
        Arrays.fill(dist, inf);
        dist[s] = 0;

        PriorityQueue<long[]> heap = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        heap.add(new long[]{0, s});

        while (!heap.isEmpty()) {
            long[] cur = heap.poll();
            int v = (int) cur[1];
            if (cur[0] != dist[v])
                continue;

            for (Road road : roads.get(v)) {
                long next = dist[v] + road.time;
                if (next < dist[road.to]) {
                    dist[road.to] = next;
                    heap.add(new long[]{next, road.to});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (i > 1)
                sb.append(' ');
            sb.append(dist[i] == inf ? "INF" : Long.toString(dist[i]));
        }
        out.println(sb);
        out.flush();
    }
}
