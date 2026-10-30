import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

// Задача 5. Конечная цена, недостижимость или цена уходит в минус бесконечность.
public class Unbounded {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        int[] from = new int[m];
        int[] to = new int[m];
        int[] cost = new int[m];
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            next.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            from[i] = Integer.parseInt(st.nextToken());
            to[i] = Integer.parseInt(st.nextToken());
            cost[i] = Integer.parseInt(st.nextToken());
            next.get(from[i]).add(to[i]);
        }

        long inf = Long.MAX_VALUE / 4;
        long[] dist = new long[n + 1];
        Arrays.fill(dist, inf);
        dist[s] = 0;

        for (int step = 0; step < n - 1; step++) {
            boolean changed = false;
            for (int i = 0; i < m; i++) {
                if (dist[from[i]] == inf)
                    continue;
                long cand = dist[from[i]] + cost[i];
                if (cand < dist[to[i]]) {
                    dist[to[i]] = cand;
                    changed = true;
                }
            }
            if (!changed)
                break;
        }

        boolean[] bad = new boolean[n + 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            if (dist[from[i]] == inf)
                continue;
            if (dist[from[i]] + cost[i] < dist[to[i]] && !bad[to[i]]) {
                bad[to[i]] = true;
                queue.add(to[i]);
            }
        }

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int u : next.get(v)) {
                if (!bad[u]) {
                    bad[u] = true;
                    queue.add(u);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (sb.length() > 0)
                sb.append('\n');
            if (bad[i])
                sb.append('-');
            else if (dist[i] == inf)
                sb.append('*');
            else
                sb.append(dist[i]);
        }
        out.println(sb);
        out.flush();
    }
}
