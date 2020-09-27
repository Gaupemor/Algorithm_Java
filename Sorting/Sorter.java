import java.util.Random;
import java.util.ArrayList;

public class Sorter {

  public int[] selectionSort(int[] a) {
    for(int i = 0; i < a.length - 1; i++) {
      int s = i;
      for(int j = i + 1; j < a.length; j++) {
        if(a[j] < a[s]) s = j;
      }
      if(i != s) {
        int t = a[s];
        a[s] = a[i];
        a[i] = t;
      }
    } return a;
  }

  public int[] insertionSort(int[] a) {
    for(int i = 1; i < a.length; i++) {
      int x = a[i];
      int j = i;
      while(j > 0 && x < a[j - 1]) {
        a[j]= a[j-1];
        j -= 1;
      } a[j] = x;
    } return a;
  }

  public int[] quickSort(int[] arr, int a, int b) {
    while(a < b) {
      int l = inPlaceParticion(arr, a, b);
      if(l - a < b - l) {
        quickSort(arr, a, l - 1);
        a = l + 1;
      } else {
        quickSort(arr, l + 1, b);
        b = l - 1;
      }
    } return arr;
  }

  private int inPlaceParticion(int[] arr, int a, int b) {
    int r = new Random().nextInt(b - a) + a;
    int t = arr[r];
    arr[r] = arr[b];
    arr[b] = t;
    int p = arr[b];
    int l = a;
    r = b - 1;
    while(l <= r) {
      while(l <= r && arr[l] <= p) {l += 1;}
      while(r >= l && arr[r] >= p) {r -= 1;}
      if(l < r) {
        t = arr[l];
        arr[l] = arr[r];
        arr[r] = t;
      }
    }
    t = arr[l];
    arr[l] = arr[b];
    arr[b] = t;
    return l;
  }

  public int[] bucketSort(int[] arr) {
    int max = arr[0];
    for(int i = 0; i < arr.length; i++) {
      if(max < arr[i]) max = arr[i];
    }
    ArrayList<Integer>[] bu = new ArrayList[max + 1];
    for(int i = 0; i < arr.length; i++) {
      int k = arr[i];
      if(bu[k] == null) bu[k] = new ArrayList<Integer>();
      bu[k].add(k);
    }
    int j = 0;
    for(int i = 0; i < bu.length; i++) {
      if(bu[i] != null) {
        for(int b : bu[i]) {
          arr[j] = b;
          j++;
        }
      }
    } return arr;
  }
}
