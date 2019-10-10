
/*
* 定义范型类
*/
public class Pair<T>{
    private T first;
    private T second;

    public Pair() { first = null; second = nul; }
    public Pair(T first, T second){
        this.first = first;
        this.second = second;
    }

    public T getFirst(){ return first; }
    public T getSecond(){ return second; }

    public void setFirst(T newVal){ this.first = newVal; }
    public void setSecond(T newVal){ this.second = newVal; }

}