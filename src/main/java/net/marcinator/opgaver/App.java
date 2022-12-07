package net.marcinator.opgaver;

import java.util.Arrays;

public class App 
{
    public static void main( String[] args )
    {
        int[] t = {3, 1, 2, 5, 5, 1, 6, 1, 2, 5, 4};
        int[] target = {1, 2, 5};
        int[] replace = {3, 1, 2};

        TargetFinder targeter = new TargetFinder(t,target);

        while (targeter.hasNext()){

            @SuppressWarnings("unused")
            int index = targeter.next();
            targeter.replace(replace);
        }

        System.out.println(Arrays.toString(targeter.getResult()));
    }
}
