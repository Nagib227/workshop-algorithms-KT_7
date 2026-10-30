import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// Задача 4. Купон можно применить не больше одного раза: вместо w платим w / 2.
public class Coupon {
    static class Road {
        int to;
        int cost;

        Road(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

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
        long[][] dist = new long[n + 1][2];
        for (int i = 1; i <= n; i++)
            Arrays.fill(dist[i], inf);
        dist[s][0] = 0;

        PriorityQueue<long[]> heap = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        heap.add(new long[]{0, s, 0});

        while (!heap.isEmpty()) {
            long[] cur = heap.poll();
            int v = (int) cur[1];
            int used = (int) cur[2];
            if (cur[0] != dist[v][used])
                continue;

            for (Road road : roads.get(v)) {
                relax(heap, dist, road.to, used, dist[v][used] + road.cost);
                if (used == 0)
                    relax(heap, dist, road.to, 1, dist[v][used] + road.cost / 2);
            }
        }

        long ans = Math.min(dist[t][0], dist[t][1]);
        out.println(ans == inf ? -1 : ans);
        out.flush();
    }

    private static void relax(PriorityQueue<long[]> heap, long[][] dist, int to, int used, long next) {
        if (next < dist[to][used]) {
            dist[to][used] = next;
            heap.add(new long[]{next, to, used});
        }
    }
}
