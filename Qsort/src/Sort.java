import java.util.Random;

/**
 * Created by Nick on 16/04/2015.
 */
public class Sort {

    private int[] source;

    public void setSource(int[] source) {
        this.source = source;
    }

    public int[] getSource() {
        return source;
    }

    public void sortingQ(int s, int e) {

        int start = s;
        int end = e;

        int el = source[(s+e)/2];

        while(start <= end) {
            while (source[start] < el) start++;
            while (source[end] > el) end--;
            if(start <= end) {
                int x = source[start];
                source[start] = source[end];
                source[end] = x;
                start++;
                end--;
            }
        }
        if(start < e) {
            sortingQ(start, e);
        }
        if (end > s) {
            sortingQ(s, end);
        }
    }

    public void sortingB(){
        for(int j=0; j< source.length; j++) {
            int end = source.length - 1;
            for (int i = 0; i < end - j; i++) {
                if (source[i] < source[i + 1]) {
                    int x = source[i];
                    source[i] = source[i + 1];
                    source[i + 1] = x;
                }
            }
        }

    }

    private class Test {
        String name;

        public Test(String name) {
            this.name = name;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void run(Test test) {
            System.out.println(name);
        }

    }


    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void run(Sort s) {
        s = new Sort("PPP");
    }

    public Sort(String name) {
        this.name = name;
    }
    public Sort() {}

    private void  tt(byte b) {

    }


    public static void main(String[] args) {
        Sort s = new Sort("gogo");
        Sort s2 = new Sort("GOGO2");
        s2.run(s);


        System.out.println(s.getName());







        int[] source = new int[50];
        Random r = new Random();
        int min = 0;
        int max = 50;





//        for(int i=0; i <source.length; i++) {
//            int val = r.nextInt((max-min) + 1)+ min;
//            System.out.println(val);
//            source[i] = val;
//        }
//        s.setSource(source);
//        long st = System.nanoTime();
//        s.sortingQ(0, source.length - 1);
//       // s.sortingB();
//        long ed = System.nanoTime();
//        System.out.println(ed - st);
//
//        System.out.println("Sorted: ");
//        for(int val : source) {
//            System.out.println(val);
//        }


    }


}
