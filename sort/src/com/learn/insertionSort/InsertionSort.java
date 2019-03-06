package com.learn.insertionSort;

/**
 * 稳定性：如果待排序的序列中存在值相等的元素，
 * 经过排序之后，
 * 相等元素之间的原先顺序不变
 */

/**
 * 插入排序算法
 *稳定性排序算法
 *
 */

public class InsertionSort {

    public static void insertionSort(int[] array){
        int j = 0;
        int key = 0;
        for(int i=1;i<array.length;i++){
            key = array[i];
            j=i-1;
            while(j>=0 && array[j]>key){
               array[j+1] = array[j];
               j--;
            }
            array[j+1] = key;
        }
    }
}
