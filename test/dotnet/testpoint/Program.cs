using System;
using System.Diagnostics;

namespace testpoint
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World C#!");
            Stopwatch stopwatch = new Stopwatch();
            int len = 1000000;
            int loops = 5000;
            int[] a = new int[len];
            stopwatch.Start();
            testpoint1(a, loops);
            stopwatch.Stop();
            Console.WriteLine("test1运行时间(s): " + stopwatch.Elapsed.TotalSeconds);
            stopwatch.Restart();
            testpoint2(a, loops);
            stopwatch.Stop();
            Console.WriteLine("test2运行时间(s): " + stopwatch.Elapsed.TotalSeconds);
            Console.WriteLine("Bye World C#");
        }

        public static void testpoint1(int[] array, int loops)
        {
            for (int k = 0; k < loops; k++)
            {
                for (int i = 1; i < array.Length; i++)
                {
                    array[i] = k;
                }
            }

        }
        public static void testpoint2(int[] array, int loops)
        {
            for (int k = 0; k < loops; k++)
            {
                for (int i = 1; i < array.Length; i++)
                {
                    array[i] = array[i - 1];
                }
            }

        }
    }

}
