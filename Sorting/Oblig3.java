import java.util.Random;
import java.util.Arrays;

//ACCEPTS LENGTH OF ARRAYS AS ARGUMENT

public class Oblig3 {
  public static void main(String[] args) {
    int length = Integer.parseInt(args[0]);
    int[] sorted = new int[length];
    int[] inverse = new int[length];
    int[] ctrlInv = new int[length];
    int[] random = new int[length];
    int[] ctrlRan = new int[length];

    Sorter s = new Sorter();
    SorterUt su = new SorterUt();

    for(int i = 0; i < length; i++) {sorted[i] = i;}
    for(int i = 0; i < length; i++) {inverse[i] = length-1-i;}
    Random rand = new Random();
    for(int i = 0; i < length; i++) {
      random[i] = rand.nextInt(length);
    } for(int i = 0; i < length; i++) {
      ctrlInv[i] = inverse[i];
    } for(int i = 0; i < length; i++) {
      ctrlRan[i] = random[i];
    }
    
    System.out.println("\nTEST SORTING IMPLEMENTATION");

    //SORTERT LISTE
    System.out.println("\nSORTED");
    for(int i = 0; i < length; i++) {System.out.print(sorted[i] + " ");} System.out.println();
    System.out.println("Selection");
    for(int j : su.selectionSort(sorted)) {System.out.print(sorted[j] + " ");} System.out.println();
    System.out.println("Insertion");
    for(int j : su.insertionSort(sorted)) {System.out.print(sorted[j] + " ");} System.out.println();
    System.out.println("Quick");
    for(int j : su.quickSort(sorted, 0, sorted.length-1)) {System.out.print(sorted[j] + " ");} System.out.println();
    System.out.println("Bucket");
    for(int j : su.bucketSort(sorted)) {System.out.print(sorted[j] + " ");} System.out.println();

    //INVERS LISTE
    System.out.println("\nINVERSE");
    for(int i = 0; i < length; i++) {System.out.print(inverse[i] + " ");} System.out.println();
    System.out.println("Selection");
    for(int j : su.selectionSort(inverse)) {System.out.print(inverse[j] + " ");} System.out.println();
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }
    System.out.println("Insertion");
    for(int j : su.insertionSort(inverse)) {System.out.print(inverse[j] + " ");} System.out.println();
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }
    System.out.println("Quick");
    for(int j : su.quickSort(inverse, 0, inverse.length-1)) {System.out.print(inverse[j] + " ");} System.out.println();
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }
    System.out.println("Bucket");
    for(int j : su.bucketSort(inverse)) {System.out.print(inverse[j] + " ");} System.out.println();
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }

    //RANDOM
    System.out.println("\nRANDOM");
    for(int i = 0; i < length; i++) {System.out.print(random[i] + " ");} System.out.println();
    System.out.println("Selection");
    for(int j : su.selectionSort(random)) {System.out.print(random[j] + " ");} System.out.println();
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }
    System.out.println("Insertion");
    for(int j : su.insertionSort(random)) {System.out.print(random[j] + " ");} System.out.println();
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }
    System.out.println("Quick");
    for(int j : su.quickSort(random, 0, random.length-1)) {System.out.print(random[j] + " ");} System.out.println();
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }
    System.out.println("Bucket");
    for(int j : su.bucketSort(random)) {System.out.print(random[j] + " ");} System.out.println();
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }

    //TEST HASTIGHET
    System.out.println("\nTEST CALCULATION SPEED\n" + length + " elements");

    //ARRAY SORT
    System.out.println("ARRAY SORT");
    System.out.print("Sorted: ");
    long t = System.nanoTime(); // nanosek
    Arrays.sort(sorted);
    double tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    System.out.print("Invers: ");
    t = System.nanoTime(); // nanosek
    Arrays.sort(inverse);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }
    System.out.print("Random: ");
    t = System.nanoTime(); // nanosek
    Arrays.sort(random);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }

    //SELECTION
    System.out.println("SELECTION");
    System.out.print("Sorted: ");
    t = System.nanoTime(); // nanosek
    s.selectionSort(sorted);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    System.out.print("Invers: ");
    t = System.nanoTime(); // nanosek
    s.selectionSort(inverse);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }
    System.out.print("Random: ");
    t = System.nanoTime(); // nanosek
    s.selectionSort(random);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }

    //INSERTION
    System.out.println("INSERTION");
    System.out.print("Sorted: ");
    t = System.nanoTime(); // nanosek
    s.insertionSort(sorted);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    System.out.print("Invers: ");
    t = System.nanoTime(); // nanosek
    s.insertionSort(inverse);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }
    System.out.print("Random: ");
    t = System.nanoTime(); // nanosek
    s.insertionSort(random);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }

    //QUICK
    System.out.println("QUICK");
    System.out.print("Sorted: ");
    t = System.nanoTime(); // nanosek
    s.quickSort(sorted, 0, length-1);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    System.out.print("Invers: ");
    t = System.nanoTime(); // nanosek
    s.quickSort(inverse, 0, length-1);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }
    System.out.print("Random: ");
    t = System.nanoTime(); // nanosek
    s.quickSort(random, 0, length-1);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }

    //BUCKET
    System.out.println("BUCKET");
    System.out.print("Sorted: ");
    t = System.nanoTime(); // nanosek
    s.bucketSort(sorted);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    System.out.print("Invers: ");
    t = System.nanoTime(); // nanosek
    s.bucketSort(inverse);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      inverse[i] = ctrlInv[i];
    }
    System.out.print("Random: ");
    t = System.nanoTime(); // nanosek
    s.bucketSort(random);
    tid = (System.nanoTime()-t)/1000000.0; // millisek
    System.out.println(tid);
    for(int i = 0; i < length; i++) {
      random[i] = ctrlRan[i];
    }
  }
}
