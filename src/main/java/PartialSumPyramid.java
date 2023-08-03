public class PartialSumPyramid {
    public static void main(String[] args) {
        int a = 8000, b = 10000;

        System.out.print("[");
        for (int i = a; i<=b; ++i) {
            if(selfdivide(i)) {
                System.out.print(i + ",");
            }
        }
        System.out.println("]");
    }

    private static boolean selfdivide(int i) {
        if (i == 1)
            return true;
        double a = Math.sqrt(i);
        for (int j=2; j<a+1; ++j) {
            int temp = j*j;
            while (temp <= i) {
                if (temp == i)
                    return true;
                temp*=j;
            }
        }
        return false;
    }


}
