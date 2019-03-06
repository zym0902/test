package com.learn.selectionSort;

public class SelectionSort {

    public void selectionSort(int[] array){
        for(int i=0;i<array.length;i++){
            int min = array[i];
            int index = 0;
            for(int j=i;j<array.length;j++){
                if(min > array[j]){
                    min = array[j];
                    index = j;
                }
            }
            int tmp = array[i];
            array[i] = array[index];
            array[index] = tmp;
        }
    }
}
