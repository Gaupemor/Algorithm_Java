//Foerehandslaga testklasse - har toke vekk dei implementeringane som det ikkje var krav paa i oppgaava

class BSTtest {

    public static int [] tallrekke( int antall ) {
        java.util.Random tilf = new java.util.Random(20102018);
        int[] tall = new int[antall];
        for ( int i=0; i<antall; i++) {
            tall[i] = tilf.nextInt();
        }
        return tall;
    }


    public static void main(String[] args) {

        int antall = Integer.parseInt(args[0]);

        int[] intarr = tallrekke(antall);
        BSTree testtre = new BSTree();

        if (antall == 0 ) {
	         System.out.println("n     antall     høyde");
            for (int ant=1; ant <= 10000000; ant=ant*10) {
                intarr = tallrekke(ant);
                for (int i : intarr) { testtre.add(i); }
                System.out.print(".." + ant);
                System.out.print(" " + testtre.size());
                //System.out.println(" " + testtre.height());
                System.out.println("\n[");
                for(int i : testtre.sortedArray()) {
                  System.out.print(i + ", ");
                }
            }
        } else {
            for (int i : intarr) { testtre.add(i); }
            for (int i : intarr) { System.out.println(i); }
            intarr = testtre.sortedArray();
            for (int i : intarr) { System.out.println(i); }
        }
    }
}
