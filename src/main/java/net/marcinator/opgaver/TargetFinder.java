package net.marcinator.opgaver;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Used to locate a target in an array
 */
public class TargetFinder implements Iterator<Integer> {
    /**
     * Arraylist which contains the resulting array
     */
    private ArrayList<Integer> ArrayResult;
    /**
     * Contains the target for which to search for
     */
    private int[] Target;
    /**
     * contains an index for the current located target
     */
    private int index = 0;
    /**
     * cached size of result
     */
    private int size;

    public TargetFinder(int[] array, int[] target) {
        ArrayResult = (ArrayList<Integer>) Arrays.stream(array).boxed().collect(Collectors.toList());
        size = ArrayResult.size();

        Target = target;
    }

    /**
     * checks is there array contains another target
     *
     * @return whether there is another target.
     */
    @Override
    public boolean hasNext() {
        //quick check if the iter is done
        if (index + Target.length >= size) {
            return false;
        }

        //find next target in array
        if (findNext()) {
            return true;
        }

        return false;
    }

    /**
     * Returns the index of the next target
     * @return the next index of the target
     */
    @Override
    public Integer next() {
        if (distanceToNextIndex == -1 && !findNext()) {
            throw new IllegalStateException("Iterator already finished");

        }

        index += distanceToNextIndex;
        replaceDif = 0;
        distanceToNextIndex = -1;

        return index;
    }


    /**
     * contains a distance to next target used to quickly go to next target when next() is called
     */
    private int distanceToNextIndex = -1;


    /**
     * internal service method for finding the next target and save it in the iterator.
     *
     * @return <code>true</code> if another target is found
     */
    private boolean findNext() {
        int i = index + (index != 0 ? Target.length + replaceDif : 0);

        while (i < size) {
            int j = 0;
            while (j < Target.length && ArrayResult.get(i + j) == Target[j]) {
                j++;
            }
            if (j == Target.length) {
                distanceToNextIndex = i - index;
                return true;
            }
            i++;
        }

        return false;
    }

    /**
     * stores the size difference between the target and the replacement
     */
    public int replaceDif = 0;

    /**
     * Replaces the current target with inputted elements
     *
     * @param replace elements to replace target with
     * @throws IllegalStateException when the Iterator has not yet run
     */
    public void replace(int[] replace) throws IllegalStateException {
        //if distanceToNextIndex is -1 at the index of 0 then the iterator has yet to run
        if (distanceToNextIndex == -1 && index == 0)
            throw new IllegalStateException("Iterator has not yet found a target");

        for (int i = 0; i < Target.length; i++) {
            ArrayResult.remove(index);
        }

        ArrayResult.addAll(index, Arrays.stream(replace).boxed().collect(Collectors.toList()));

        //                            current target length with longer replace
        replaceDif = replace.length - (Target.length + replaceDif);

        size += replaceDif;
    }

    /**
     * Gets the result after all targets have be found
     * @return return the result of the iteration
     * @throws IllegalStateException when the iterator has not finished yet
     */
    public int[] getResult() throws IllegalStateException {
        if (hasNext())
            throw new IllegalStateException("iterator hasn't finished");
        return ArrayResult.stream().mapToInt(i -> i).toArray();
    }
}
