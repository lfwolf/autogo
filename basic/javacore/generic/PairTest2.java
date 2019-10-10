import java.util.*;

public class PairTest2{

    public static void main(String[] args) {
        System.out.println("Hello World");
        GregorianCalendar[] birthdays = {
            new GregorianCalendar(1961, Calendar.DECEMBER, 6),
            new GregorianCalendar(1981, Calendar.DECEMBER, 6),
            new GregorianCalendar(2013, Calendar.DECEMBER, 6)
        };
        Pair<GregorianCalendar> mm = ArrayAlg2.minmax(birthdays);
        System.out.println("min = "+ mm.getFirst().getTime());
        System.out.println("max = "+ mm.getSecond().getTime());
    }
}
class ArrayAlg2{
    /*
    * 范型方法（类型变量限定）
    */
    public static <T extends Comparable> Pair<T> minmax(T[] a){
        if( a==null || a.length == 0 ) return null;
        T min = a[0];
        T max = a[0];
        for( int i=0; i< a.length; i++ ){
            if(min.compareTo(a[i])>0) min = a[i];
            if(max.compareTo(a[i])<0) max = a[i];
        }
        return new Pair<>(min,max);
    }
}