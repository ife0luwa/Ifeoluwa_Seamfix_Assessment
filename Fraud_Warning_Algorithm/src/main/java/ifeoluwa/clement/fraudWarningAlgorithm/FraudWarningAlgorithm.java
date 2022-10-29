package ifeoluwa.clement.fraudWarningAlgorithm;

import java.util.Arrays;

public class FraudWarningAlgorithm {
    public static void main(String[] args) {

        int [] range  = {2, 3, 4, 2, 3, 6 ,8 ,4 ,5};
        System.out.println(notificationCounter(9, 5, range));
    }
    public static int notificationCounter(int totalDays, int dataCheckRange, int[]dataList){

        int notification =  0;
        for (int i = 0; i < dataList.length; i++) {
            if(i >= dataCheckRange){
                int valToCompare = dataList[i];
                int[] croppedData = transactionData(i - dataCheckRange, dataCheckRange, dataList);
                Arrays.sort(croppedData);
                int median = getMedian(croppedData);

                if(valToCompare >= (2 * median)) {
                    notification++;
                }
            }
        }

        return notification;
    }

    public static int[] transactionData(int startCropIndex, int dataRange, int[] dailyExpenditure){
        int[] newData = new int[dataRange];

        int count = startCropIndex;
        for (int x = 0; x < newData.length; x++){
            newData[x] = dailyExpenditure[count];
            count++;
        }

        return newData;
    }

    public static int getMedian(int[] intArray) {
        int median;

        int size = intArray.length;
        if (size % 2 == 1){
            median = intArray[((size+1)/2) - 1];
        } else {
            median = (intArray[(size/2)-1] + intArray[size/2]) / 2;
        }

        return median;
    }


}
