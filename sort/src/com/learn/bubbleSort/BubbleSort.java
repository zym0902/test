package com.learn.bubbleSort;

/**
 * 冒泡排序算法
 * 稳定排序
 */
public class BubbleSort {

    public static void bubbleSort(int[] array){
        for(int i=0;i<array.length;i++){
            for(int j=array.length-1;j>i;j--){
                if(array[j] > array[j-1]){
                    int tmp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tmp;
                }
                for(int a : array){
                    System.out.print(a+" ");
                }
                System.out.println();
            }

        }
    }

    public static void main(String[] args) {
        int [] array = {1,2,3,4,5,6,7,8,9,0};
        bubbleSort(array);
        for(int i : array){
            System.out.print(i+" ");
        }
    }

}
