package net.marcinator.opgaver;

public class Utils {
    public static void reverseArray(int[] arr) {
        int start = 0, end = arr.length-1;

        int temp;

        for (int i = arr.length >> 1; i >= 0; i--) {
            temp = arr[start + i];
            arr[start + i] = arr[end - i];
            arr[end - i] = temp;
        }
    }
}
