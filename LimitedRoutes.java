import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// Задача 6. Минимальная цена пути из не более чем K рёбер и число таких путей.
public class LimitedRoutes {
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] from = new int[m];
        int[] to = new int[m];
        int[] cost = new int[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            from[i] = Integer.parseInt(st.nextToken());
            to[i] = Integer.parseInt(st.nextToken());
            cost[i] = Integer.parseInt(st.nextToken());
        }

        long inf = Long.MAX_VALUE / 4;
        long[][] dist = new long[k + 1][n + 1];
        int[][] ways = new int[k + 1][n + 1];
        for (int i = 0; i <= k; i++)
            Arrays.fill(dist[i], inf);
        dist[0][s] = 0;
        ways[0][s] = 1;

        for (int used = 0; used < k; used++) {
            for (int i = 0; i < m; i++) {
                if (dist[used][from[i]] == inf)
                    continue;
                long next = dist[used][from[i]] + cost[i];
                int v = to[i];
                if (next < dist[used + 1][v]) {
                    dist[used + 1][v] = next;
                    ways[used + 1][v] = ways[used][from[i]];
                } else if (next == dist[used + 1][v]) {
                    ways[used + 1][v] = (int) ((ways[used + 1][v] + (long) ways[used][from[i]]) % MOD);
                }
            }
        }

        long best = inf;
        long count = 0;
        for (int used = 0; used <= k; used++) {
            if (dist[used][t] < best) {
                best = dist[used][t];
                count = ways[used][t];
            } else if (dist[used][t] == best) {
                count = (count + ways[used][t]) % MOD;
            }
        }

        if (best == inf)
            out.println("IMPOSSIBLE");
        else
            out.println(best + " " + count);
        out.flush();
    }
}
