package net.marcinator.opgaver;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Used to locate a target in an array
 */
public class TargetFinder implements Iterator<Integer> {
    /**
     * Arraylist which contains the resulting array
     */
    private final ArrayList<Integer> ArrayResult;
    /**
     * Contains the target for which to search for
     */
    private final int[] Target;
    /**
     * contains an index for the current located target
     */
    private int index;
    /**
     * variable used to ensure that the finder has run without the need to evaluate arraylist size.
     */
    private boolean hasRun = false;

    public TargetFinder(int[] array, int[] target) {
        ArrayResult = (ArrayList<Integer>) Arrays.stream(array).boxed().collect(Collectors.toList());
        index = ArrayResult.size()-1;

        Utils.reverseArray(target);

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
        //if there can fit another target in the remaining array. Index starts at zero so add one to properly check it.
        if (index - Target.length + 1 < 0) {
            return false;
        }

        //find next target in array
        return findNext();
    }

    /**
     * Returns the index of the next target
     * @return the next index of the target
     */
    @Override
    public Integer next() {
        if (NextIndex == -1 && !findNext()) {
            throw new IllegalStateException("Iterator already finished");

        }

        index = NextIndex;
        NextIndex = -1;

        return index;
    }


    /**
     * contains a distance to next target used to quickly go to next target when next() is called
     */
    private int NextIndex = -1;


    /**
     * internal service method for finding the next target and save it in the iterator.
     *
     * @return <code>true</code> if another target is found
     */
    private boolean findNext() {
        int i = index;

        while (i >= 0) {
            int j = 0;
            while (j < Target.length && ArrayResult.get(i - j) == Target[j]) {
                j++;
            }
            if (j == Target.length) {
                //index is one too large because it just added to the collector
                NextIndex = i-(j-1);
                return (hasRun = true);
            }
            i--;
        }

        return false;
    }

    /**
     * Replaces the current target with inputted elements
     *
     * @param replace elements to replace target with
     * @throws IllegalStateException when the Iterator has not yet run
     */
    public void replace(int[] replace) throws IllegalStateException {
        if (!hasRun)
            throw new IllegalStateException("Iterator has not yet found a target");

        for (int i = 0; i < Target.length; i++) {
            ArrayResult.remove(index);
        }

        ArrayResult.addAll(index, Arrays.stream(replace).boxed().toList());
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
