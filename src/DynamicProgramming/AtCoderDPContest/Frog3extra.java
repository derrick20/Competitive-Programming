import java.util.LinkedList;
import java.util.Scanner;

public class Frog3extra {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long c = in.nextLong();
        LinkedList<Stone> queue = new LinkedList<>();
        long h1 = in.nextLong();
        queue.add(new Stone(h1, h1 * h1));
        for (int i = 1; i < n; i++) {
            long h = in.nextLong();
            // Maintain invariant of being increasing.
            // while there is a point where the front is more expensive than 2nd, pop it
            while (queue.size() > 1 && queue.get(1).dp - (2L * h * queue.get(1).h) <= queue.get(0).dp - (2L * h * queue.get(0).h)) {
                queue.removeFirst();
            }
            long dp = queue.get(0).dp - (2L * h * queue.get(0).h) + (2L * h * h) + c;
            Stone stone = new Stone(h, dp);
            //while (!queue.isEmpty() && dp - (2L * h * h) <= queue.getLast().dp - (2L * h * queue.getLast().h)) {
            while (queue.size() > 1 && fromLine(queue.get(queue.size() - 2), queue.getLast(), stone) <= 0L) {
                queue.removeLast();
            }
            queue.addLast(stone);
        }
        System.out.println(queue.getLast().dp - (queue.getLast().h * queue.getLast().h));
    }

    static class Stone {
        final long h;
        final long dp;

        Stone(long h, long dp) {
            this.h = h;
            this.dp = dp;
        }
    }

    static long fromLine(Stone a, Stone b, Stone point) {
        return ((point.dp - a.dp) * (b.h - a.h)) - ((point.h - a.h) * (b.dp - a.dp));
    }
}
