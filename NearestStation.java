import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// Задача 3. Ближайший пункт помощи. При равном расстоянии берётся меньший номер.
public class NearestStation {
    static class Road {
        int to;
        int length;

        Road(int to, int length) {
            this.to = to;
            this.length = length;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            roads.add(new ArrayList<>());

        st = new StringTokenizer(in.readLine());
        int[] station = new int[k];
        for (int i = 0; i < k; i++)
            station[i] = Integer.parseInt(st.nextToken());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            roads.get(u).add(new Road(v, w));
            roads.get(v).add(new Road(u, w));
        }

        long inf = Long.MAX_VALUE / 4;
        long[] dist = new long[n + 1];
        int[] from = new int[n + 1];
        Arrays.fill(dist, inf);

        // в куче: расстояние, номер пункта, вершина
        PriorityQueue<long[]> heap = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        for (int id : station) {
            dist[id] = 0;
            from[id] = id;
            heap.add(new long[]{0, id, id});
        }

        while (!heap.isEmpty()) {
            long[] cur = heap.poll();
            int v = (int) cur[2];
            if (cur[0] != dist[v] || cur[1] != from[v])
                continue;

            for (Road road : roads.get(v)) {
                long next = dist[v] + road.length;
                int who = from[v];
                if (next < dist[road.to] || (next == dist[road.to] && who < from[road.to])) {
                    dist[road.to] = next;
                    from[road.to] = who;
                    heap.add(new long[]{next, who, road.to});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (sb.length() > 0)
                sb.append('\n');
            if (dist[i] == inf)
                sb.append("-1 -1");
            else
                sb.append(from[i]).append(' ').append(dist[i]);
        }
        out.println(sb);
        out.flush();
    }
}
