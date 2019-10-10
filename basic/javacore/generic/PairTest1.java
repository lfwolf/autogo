

public class PairTest1{
    public static void main(String[] args) {
        System.out.println("Hello World");
        String[] words = { "mary", "has", "a", "little", "lamb" };
        Pair<String> mm = ArrayAlg.minmax(words);
        System.out.println("min = "+ mm.getFirst());
        System.out.println("max = "+ mm.getSecond());
    }
}

class ArrayAlg{
    /*
    * 范型方法 
    */
    public static Pair<String> minmax(String[] a){
        if( a==null || a.length == 0 ) return null;
        String min = a[0];
        String max = a[0];
        for( int i=0; i< a.length; i++ ){
            if(min.compareTo(a[i])>0) min = a[i];
            if(max.compareTo(a[i])<0) max = a[i];
        }
        return new Pair<>(min,max);
    }
}

