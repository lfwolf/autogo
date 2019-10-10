public class testpoint{
    public static void main(String[] args){
        System.out.println("Hello World Java");
        int len = 1000000;
        int loops = 5000;
        int[] a = new int[len];
        long time1=System.currentTimeMillis();	
        testpoint1(a,loops);
        long time2=System.currentTimeMillis();	
        System.out.println("test1运行时间(ms): "+(double)((time2-time1)));
        time1=System.currentTimeMillis();	
        testpoint2(a,loops);
        time2=System.currentTimeMillis();	
        System.out.println("test2运行时间(ms): "+(time2-time1));
        time1=System.currentTimeMillis();	
        testpoint11(a,loops,1);
        time2=System.currentTimeMillis();	
        System.out.println("test11运行时间(ms): "+(time2-time1));
        time1=System.currentTimeMillis();	
        testpoint22(a,loops,1);
        time2=System.currentTimeMillis();	
        System.out.println("test22运行时间(ms): "+(time2-time1));
        System.out.println("Bye World Java");
    }

    public static void testpoint1(int[] array,int loops){ 
        for(int k =0;k< loops;k++)
        {
            for(int i=1;i< array.length;i++)
            {
                array[i] = k;
            }
        }

    }
    public static void testpoint2(int[] array,int loops){ 
        for(int k =0;k< loops;k++)
        {
            for(int i=1;i< array.length;i++)
            {
                array[i] = array[i-1];
            }
        }

    }
    public static void testpoint22(int[] array,int loops,int step){ 
        for(int k =0;k< loops;k++)
        {
            for(int i=1;i< array.length;i+=step)
            {
                array[i] = array[i-1];
            }
        }

    }
    public static void testpoint11(int[] array,int loops,int step){ 
        for(int k =0;k< loops;k++)
        {
            for(int i=1;i< array.length;i+=step)
            {
                array[i] = k;
            }
        }

    }
}