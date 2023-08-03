import java.util.*;

public class ThreeDarts {
    List<Integer> numbers = new ArrayList<>();
    Map<Integer, List<Integer>> map = new HashMap<>();
    public int[] throwDarts(int n) {
        for (int i=1;i<=20;++i) {
            numbers.add(i);
            numbers.add(2*i);
            numbers.add(3*i);
        }
        numbers.add(25);
        numbers.add(50);

        for (int i=0;i<numbers.size();++i) {
            int key = numbers.get(i);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>(Arrays.asList(numbers.get(i))));
            }
            for (int j=0;j<numbers.size();++j) {
                key = numbers.get(i) + numbers.get(j);
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>(Arrays.asList(numbers.get(i), numbers.get(j))));
                }
                for (int k=0;k<numbers.size();++k) {
                    key = numbers.get(i) + numbers.get(j) + numbers.get(k);
                    if (!map.containsKey(key)) {
                        map.put(key, new ArrayList<>(Arrays.asList(numbers.get(i), numbers.get(j), numbers.get(k))));
                    }
                }
            }
        }

        if (map.containsKey(n)) {
            return map.get(n).stream().mapToInt(i->i).toArray();
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int arr[] = new ThreeDarts().throwDarts(50);
        type(arr);
        arr = new ThreeDarts().throwDarts(6);
        type(arr);
        arr = new ThreeDarts().throwDarts(179);
        type(arr);
        arr = new ThreeDarts().throwDarts(60);
        type(arr);
        arr = new ThreeDarts().throwDarts(23);
        type(arr);
    }

    private static void type(int[] arr) {
        System.out.print("[");
        for (int i = 0; i< arr.length; ++i) {
            System.out.print(arr[i]);
            if (i != arr.length-1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
