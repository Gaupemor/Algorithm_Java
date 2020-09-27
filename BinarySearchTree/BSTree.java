//Til findInRange() - bruker Arrays.copyOfRange()
import java.util.Arrays;

public class BSTree implements BSTOper {

  //Peikar til rotnoden
  Node root;

  private class Node {
    Node left, right;
    // verdier i venstre subtre er < verdien i noden selv
    // verdier i høyre subtre er > verdien i noden selv
    int value;
    Node(int v) {
      value = v;
      left = null;
      right = null;
    }

    //Sjekker om noden er barnlaus
    public boolean isExternal() {
      if(left == null && right == null) return true;
      return false;
    }
  }

  //Konstruktoer
  public BSTree(){
    root = null;
  }

  //Finn foreldrenoden
  private Node findParent(Node n) {
    Node p = root;
    //Itererer gjennom treet
    while(p != null && n != null) {
      if(p.value > n.value) {
        if(p.left.value != n.value) p = p.left;
        else return p;
      } else if (p.value < n.value){
        if(p.right.value != n.value) p = p.right;
        else return p;
      } else p = null;
    } return null;
  }
  //Finn besteforeldrenoden
  private Node findGrandparent(Node n) {
    //Finn forelder til forelder
    return findParent(findParent(n));
  }
  //Finn noden
  private Node find(int value) {
    Node n = root;
    //Itererer gjennom treet
    while(n != null) {
      //Viss verdi erlik, returner node
      if(n.value == value) return n;
      //Viss mindre/større - gå til barna
      else if(n.left != null && n.value > value) n = n.left;
      else if(n.right != null && n.value < value) n = n.right;
      //Om ikkje finst, returner null
      else n = null;
    } return null;
  }

  // metoder fra BSTOper
  //Rekursive metoder var anbefalt

  //add() - legg til ny node med verdi i treet
  public void add(int value) {
    //Viss rot null, legg til ny rot
    if(root == null) root = new Node(value);
    //Rekursiv privat metode
    else add(root, value);
  }
  private void add(Node n, int v) {
      //viss node verdi større enn ny verdi...
      if(n.value > v) {
        //...og ikkje har venstre barn, legg til ny node
        if(n.left == null) n.left = new Node(v);
        //...og har venstre barn, gå vidare
        else add(n.left, v);
      //viss node verdi mindre enn ny verdi...
      } else if (n.value < v) {
        //...og ikkje har høgre barn, legg til ny node
        if(n.right == null) n.right = new Node(v);
        //...og har høgre barn, gå vidare
        else add(n.right, v);
      }
  }

  //remove() - fjerner ein node og gjev sanningsverdi om noden fantes
  public boolean remove(int value) {
    //Finn noden
    Node n = find(value);
    //Viss noden ikkje finst, returner false
    if(n == null) return false;
    //Viss rot
    if(n.value == root.value) {
      if(n.left == null) root = n.right;
      else if(n.right == null) root = n.left;
      else {
        root = n.right;
        n.right.left = n.left;
      } return true;
    }
    //Finn forelder
    Node parent = findParent(n);
    //Viss noden er ekstern
    if(n.isExternal()) {
      if(n.value == parent.left.value) parent.left = null;
      else parent.right = null;
    //Viss noden berre har eitt barn
    } else if(n.left == null || n.right == null) {
      if(parent.left != null && n.value == parent.left.value) {
        if(n.left == null) parent.left = n.right;
        else parent.left = n.left;
      } else {
        if(n.left == null) parent.right = n.right;
        else parent.right = n.left;
      }
    //Viss noden har to barn
    } else {
      //Finn minste i høgre subtre og foreldrenoden til minste
      Node minParent = n;
      Node min = n.right;
      while(min.left != null) {
        min = min.left;
      }
      //Bytt verdi til noden med minsteverdi i høgre subtre
      n.value = min.value;
      //Slett den minste noden (som må vera ein bladnode)
      if(minParent.left.value == min.value) minParent.left = null;
      else minParent.right = null;
    //Returner true
    } return true;
  }

  //size() - finn storleik v/sortedArray()
  public int size() {
      return sortedArray().length;
  }

  //existsInTree() - om verdi finst i treet
  public boolean existsInTree(int value){
    if (find(value) != null) return true;
    return false;
  }


  //findNearestSmallerThan() - Finn næraste mindre i verdi enn value (antek at value finst i treet)
  //Antek at 'næraste minste' er nærast i verdi, ikkje nærast i treet
  public int findNearestSmallerThan(int value) {
    //Find range frå fyrste element i treet opp til value
    int[] v = findInRange(Integer.MIN_VALUE, value);
    //Viss det ikkje finst noko mindre verdi, returner verdien sjølv
    if(v.length == 0) return value;
    //Viss det finst ein mindre verdi, returner den næraste - det siste elementet i range
    return v[v.length - 1];
  }

  //addAll() - legg alle verdiar til frå talrekke
  public void addAll(int[] integers) {
    for(int i : integers) {
      add(i);
    }
  }

  //sortedArray() - inorder talrekke v/privat rekursiv metode
  public int[] sortedArray() {
    return inorder(root);
  }
  //inorder() - returnerer ein konkatenert array av venstre subtre, noden sjølv, og høgre subtre
  private int[] inorder(Node n) {
    //Base case - Viss noden er null, returner tom array
    if(n == null) return new int[0];

    //Array som inneheld verdien til sjølve startnoden n
    int[] nodeArray = {n.value};

    //Array for venstre- og høgre subtre
    int[] leftArray = inorder(n.left);
    int[] rightArray = inorder(n.right);

    //Returner konkateneringa av alle arrayane - i inorder rekkefylgje
    int[] concatArray = new int[leftArray.length + nodeArray.length + rightArray.length];
    System.arraycopy(leftArray, 0, concatArray, 0, leftArray.length);
    System.arraycopy(nodeArray, 0, concatArray, leftArray.length, nodeArray.length);
    System.arraycopy(rightArray, 0, concatArray, leftArray.length + nodeArray.length, rightArray.length);
    return concatArray;
  }


  //findInRange() - frå-inntil ut i frå sortedArray()
  public int[] findInRange (int low, int high) {
    if(low == high) return new int[0];
    //Tek inorder array
    int[] inorderArray = sortedArray();

    //min start- og max slutverdi
    int start = 0; int end = inorderArray.length;
    //for aa sjekka om har funne start- og sluttverdi
    boolean started = false; boolean ended = false;
    //Itererer gjennom inorderArray
    for(int i = 0; i < inorderArray.length; i++) {
      if(inorderArray[i] >= low && !started) {
        start = i;
        started = true;
      } if(inorderArray[i] >= high && !ended) {
        end = i;
        ended = true;
      }
    }
    //Lager ny array av verdiane frå start opp til sluttverdi
    return Arrays.copyOfRange(inorderArray, start, end);
  }
}
