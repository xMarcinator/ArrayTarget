package net.marcinator.opgaver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int[] t = {3, 1, 2, 5, 5, 1, 6, 1, 2, 5, 4};
        int[] target = {1, 2, 5};
        int[] replace = {3, 1, 2};

        TargetFinder targeter = new TargetFinder(t,target);

        while (targeter.hasNext()){
            int index = targeter.next();
            targeter.replace(replace);
        }

        System.out.println(Arrays.toString(targeter.getResult()));


        //helo(t,target,replace);
    }


    private static void helo(int[] t, int[] target,int[] replace){
        ArrayList out = new ArrayList(Arrays.asList(t));

        ArrayList<Integer> indexs = new ArrayList<Integer>();

        for (int i = 0; i < t.length; i++) {
            int j = 0;

            while (j < target.length && t[i+j]==target[j]) {
                j++;

                if (j==target.length-1){
                    indexs.add(i);


                    replaceInArray(i, replace,t);
                }
            };

            i+=j;
        }


        System.out.println(indexs);
        System.out.println(Arrays.toString(t));

        System.out.println( "Hello World!" );
    }
    private static void replaceInArray(int index, int[] replace, int[] array){
        for (int i = 0; i < replace.length; i++) {
            array[index+i] = replace[i];
        }
    }
}
