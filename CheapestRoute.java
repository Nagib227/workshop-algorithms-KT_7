import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// Задача 2. Минимальная стоимость при отрицательных рёбрах, но без отрицательного цикла из s.
public class CheapestRoute {
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
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            from[i] = Integer.parseInt(st.nextToken());
            to[i] = Integer.parseInt(st.nextToken());
            cost[i] = Integer.parseInt(st.nextToken());
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
                long next = dist[from[i]] + cost[i];
                if (next < dist[to[i]]) {
                    dist[to[i]] = next;
                    changed = true;
                }
            }
            if (!changed)
                break;
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
