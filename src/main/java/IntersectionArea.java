public class IntersectionArea {
    public static void main(String[] args) {
        boolean t = new IntersectionArea().inRange(2L, 0, 5, 5, 2, 2);
        new IntersectionArea().addOne(new int[] {0, 2, 2, 2}, new int[] { 2, 2, 0, 2}, new int[]{5, 7, 5, 5},
                new int[]{5, 5, 5, 7},4);
    }
    public int[] addOne(int[] X1, int[] Y1, int[] X2, int[] Y2, long a){
        long x1, x2, y1, y2;
        x1 = x2 = y1 = y2 = -1L;

        int[] res = new int[4];

        if (X1.length == 0) {
            x1=0;y1=0;x2= (long) 1000000L;y2= 1000000L;
        } else {
            x1= X1[0]; y1 = Y1[0]; x2 = X2[0]; y2 = Y2[0];
        }

        for (int i=1;i<X1.length;++i) {
            if (inRange(x1,x2,y1,y2, X1[i], Y1[i]) ||
                inRange(x1,x2,y1,y2, X1[i], Y2[i]) ||
                inRange(x1,x2,y1,y2, X2[i], Y2[i]) ||
                inRange(x1,x2,y1,y2, X2[i], Y1[i]) ||
                inRange(X1[i],X2[i], Y1[i], Y2[i], x1, y1) ||
                inRange(X1[i],X2[i], Y1[i], Y2[i], x1, y2) ||
                inRange(X1[i],X2[i], Y1[i], Y2[i], x2, y1) ||
                inRange(X1[i], X2[i],Y1[i] ,Y2[i], x2, y2) ||
                (X1[i] == x1 && Y1[i] == y1 && X2[i] == x2 && Y2[i] == y2)) {
                x1 = Math.max(x1, X1[i]);
                y1 = Math.max(y1, Y1[i]);
                x2 = Math.min(x2, X2[i]);
                y2 = Math.min(y2, Y2[i]);
            } else {
                return new int[]{};
            }
        }

        x2-=x1;
        y2-=y1;


        long xf;
        long yf;
        for (long i = 1; i<=x2; ++i) {
            if (a % i == 0 && a/i <= y2 && (i * (a/i) == a)) {
                return new int[]{(int) x1, (int) y1, (int) (x1 + i), (int) (y1 + a/i)};
            }
        }
        return new int[]{};
    }

    private boolean inRange(int x1, int x2, int y1, int y2, long newx, long newy) {
        return newx < x2 && newx > x1 && newy <= y2 && newy >= y1 ||
                newx <= x2 && newx >= x1 && newy < y2 && newy > y1;
    }

    private boolean inRange(long x1, long x2, long y1, long y2, int newx, int newy) {
        return newx < x2 && newx > x1 && newy <= y2 && newy >= y1 ||
                newx <= x2 && newx >= x1 && newy < y2 && newy > y1;
    }
}
