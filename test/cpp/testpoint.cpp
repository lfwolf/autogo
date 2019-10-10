#include <stdio.h>
#include <time.h>
#include <iostream>

using namespace std;

void test1(int32_t* p0,int len);
void test2(int32_t* p0,int len);

int main()
{
    printf("Hello world C++\r\n");

    int len = 1000000;
    int32_t* p0 = new int[len];

    clock_t start,end;


    start = clock();
    test1(p0,len);
    //需要测试运行时间的程序段
    end = clock();
    cout<<"test1运行时间(s): "<<(double)(end-start)/CLOCKS_PER_SEC<<endl;



    start = clock();
    test2(p0,len);
    //需要测试运行时间的程序段
    end = clock();
    cout<<"test2运行时间(s): "<<(double)(end-start)/CLOCKS_PER_SEC<<endl;


    printf("Bye world\r\n");
    return 0;
}

void test1(int32_t* p0,int len)
{
    for(int k=0;k<5000;k++){
        int32_t* p= p0 +1;
        int32_t* pEnd = p + len;
        while(p < pEnd){
            *p = k;
            p++;
        }
    }
}
void test2(int32_t* p0,int len)
{
    for(int k=0;k<5000;k++){
        int32_t* p= p0 +1;
        int32_t* pEnd = p + len;
        while(p < pEnd){
            *p = *(p-1);
            p++;
        }
    }
}