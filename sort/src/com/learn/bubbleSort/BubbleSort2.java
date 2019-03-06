package com.learn.bubbleSort;

/**
 * 加入标识位，判断当前循环有无数据交换
 */
public class BubbleSort2 {

    public void bubbleSort(int[] array){
        for(int i=0;i<array.length;i++){
            boolean flag = false;
            for(int j=0;i<array.length-i-1;j++){
                if(array[j] < array[j+1]){
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                    flag = true;
                }
            }
            if(!flag){
                break;
            }
        }
    }
}
