//Eigen testklasse - som testar alle metodane og skriv ut resultat.
//Passer for mindre arrayer.
//Naar du køyrer programmet: KLASSEN TEK INN EIT HEILTAL SOM ARGUMENT!
//Skriv inn 'java Tre x' kor x er eit heiltal fraa 0 t.o.m. 5
//Tabellen for dei ulike er under metoden 'talrekke()'

import java.util.Arrays;
import java.util.Random;

public class Tre {

  //Tek inn indeks, og returnerar ein array med 10 element
  public static int[] talrekke(int indeks) {
    int[] i;
    switch(indeks) {
      case 0:  i = new int[]{6, 3, 8, 1, 9, 4, 7, 5, 10};
               break;
      case 1:  i = new int[]{-472, -469, -454, 149, 173, 330, 349, 360, 453, 489};
               break;
      case 2:  i = new int[]{-327, -299, -144, -128, 83, 119, 211, 341, 344, 468};
               break;
      case 3:  i = new int[]{-318, -258, -48, 41, 126, 167, 174, 370, 432, 448};
               break;
      case 4:  i = new int[]{-452, -229, -187, -123, 10, 48, 78, 297, 300, 341};
               break;
      case 5:  i = new int[]{-478, -437, -294, -272, -139, 76, 81, 178, 253, 365};
               break;
      default: i = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
               break;
    } return i;
  }

  //Main() - tek inn eit heiltal som argument, sender det som argument til talrekke()
  public static void main(String[] args) {
    int indeks = Integer.parseInt(args[0]);
    int[] tal = talrekke(indeks);
    //Opprett nytt BSTree
    BSTree t = new BSTree();


    System.out.println("\n*add(), addAll(), sortedArray()*");
    //Legg til alle tala i treet med addAll()
    t.addAll(tal); //Bruker add() i metoden addAll()
    //Sorterer treet med sortedArray()
    int[] inorder = t.sortedArray();
    //UTSKRIFTER
    System.out.println("Verdiar (skal innehalda det same)");
    System.out.println("Talrekke inn:");
    for(int i : tal) {
      System.out.print(i + " ");
    } System.out.println("\nInorder talrekke ut:");
    for(int i : inorder) {
      System.out.print(i + " ");
    } Arrays.sort(tal);
    //TEST - Sjekker om den sorterte talrekka er lik inorder talrekke
    if(Arrays.equals(tal, inorder)) System.out.println("\nOK\n");
    else System.out.println("\nFEIL! Har ikkje like verdiar!\n");





    System.out.println("*size()*");
    //Finn storleiken for treet med size()
    int storleik = t.size();
    //UTSKRIFTER
    System.out.println("Storleik (skal vera like)");
    System.out.println("Talrekke : " + tal.length);
    System.out.println("Tre      : " + storleik);
    //TEST - Sjekker om storleiken til talrekka er lik storleiken til treet
    if(tal.length == storleik) System.out.println("OK\n");
    else System.out.println("FEIL! Storleiken er ikke lik!\n");





    System.out.println("*existsInTree()*");
    //Sjekker om existsInTree() returnerar true og false riktig
    boolean[] sanne = {false, false, false};
    boolean[] usanne = {true, true};
    System.out.print("Desse skal finst i treet");
    Random r = new Random();
    int i = 0;
    for(boolean b : sanne) {
      int v = tal[r.nextInt(tal.length)];
      System.out.print("\n" + v + ": ");
      if(t.existsInTree(v)) {
        sanne[i] = true;
        System.out.print("finst");
      } else System.out.print("finst ikkje");
      i++;
    }
    System.out.println("\nDesse skal ikkje finst i treet");
    usanne[0] = t.existsInTree(Integer.MAX_VALUE);
    usanne[1] = t.existsInTree(Integer.MIN_VALUE);
    System.out.print("MAX: ");
    if(usanne[0] == false) System.out.print("finst ikkje");
    else System.out.print("finst");
    System.out.print("\nMIN: ");
    if(usanne[1] == false) System.out.print("finst ikkje");
    else System.out.print("finst");
    //TEST - viss alle er sanne i sanne[] og usanne i usanne[], er testen OK
    boolean sanneErSanne = true;
    boolean usanneErUsanne = true;
    for(boolean b : sanne) {
      if(b == false) sanneErSanne = false;
    } for(boolean b : usanne) {
      if(b == true) usanneErUsanne = false;
    }
    if(sanneErSanne && usanneErUsanne) System.out.println("\nOK\n");
    else System.out.println("\nFEIL!\n");





    System.out.println("*findInRange()*");
    //Vel to tal tilfeldig tre ganger
    for(i = 0; i < 3; i++) {
      int t1 = tal[r.nextInt(tal.length)];
      int t2 = tal[r.nextInt(tal.length)];
      System.out.print("Mellom " + t1 + " og " + t2 + ": ");
      //Og finn range mellom dei to
      if(t1 < t2) {
        for(int j : t.findInRange(t1, t2)) {
          System.out.print(j + " ");
        }
      } else {
        for(int j : t.findInRange(t2, t1)) {
          System.out.print(j + " ");
        }
      } System.out.println();
    //TEST - manuell - sjaa at range er riktig opp mot inorder
    } System.out.println("Sjekk opp mot inorder:");
    for(int j : inorder) {System.out.print(j + " ");}
    System.out.println("\n");






    System.out.println("*findNearestSmallerThan()*");
    System.out.println("Næraste mindre enn _ er _");
    //Sjekker tre tal i treet tilfeldig kva som er naeraste minste
    for(int j = 0; j < 3; j++) {
      i = r.nextInt(tal.length - 1);
      System.out.println(tal[i] + " : " + t.findNearestSmallerThan(tal[i]));
    //TEST - manuell - sjaa at naeraste minste er riktig mot inorder
    } System.out.println("Sjekk opp mot inorder:");
    for(int j : inorder) {System.out.print(j + " ");}
    System.out.println("\n");





    System.out.println("*remove()*");
    //Fjernar 3 + proever aa fjerna 2 som ikkje gaar
    System.out.println("Desse skal fjernast");
    boolean removeKlart = true;
    boolean b = false;
    //UTSKRIFTER og fjerning
    System.out.println(tal[0] + " : " + t.remove(tal[0]));
    System.out.println(tal[tal.length - 2] + " : " + t.remove(tal[tal.length - 2]));
    System.out.println(tal[2] + " : " + t.remove(tal[2]));
    System.out.println("Desse kan ikkje fjernast");
    b = t.remove(Integer.MAX_VALUE);
    System.out.println("MAX: " + b);
    if(b == true) removeKlart = false;
    b = t.remove(Integer.MIN_VALUE);
    System.out.println("MIN: " + b);
    if(b == true) removeKlart = false;
    //TEST - Sjekker om ikkje fjerna MIN eller MAX og at storleiken er 3 mindre enn foer
    //manuell - sjekk resten opp mot iorder att stemmer
    if(removeKlart && (t.size() == (inorder.length - 3))) {
      System.out.println("OK viss inorder korrekt:");
      for(int j : t.sortedArray()) {
        System.out.print(j + " ");
      } System.out.println();
    } else System.out.println("FEIL!\n");
  }

}
