import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with a Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     *         returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        int low = 0;
        int high = index;
        while (high - low > 1) {
            int mid = (high + low) / 2;
            if (taskArray[index].getStartTime().compareTo(taskArray[mid].getFinishTime()) > 0 ||
                    taskArray[index].getStartTime().compareTo(taskArray[mid].getFinishTime()) == 0) {
                low = mid;
            } else {
                high = mid;
            }
        }
        if (taskArray[index].getStartTime().compareTo(taskArray[low].getFinishTime()) > 0
                || taskArray[index].getStartTime().compareTo(taskArray[low].getFinishTime()) == 0) {
            return low;
        } else if (taskArray[index].getStartTime().compareTo(taskArray[high].getFinishTime()) > 0
                || taskArray[index].getStartTime().compareTo(taskArray[high].getFinishTime()) == 0) {
            return high;
        }
        return -1;
    }

    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        for (int index = 0; index < taskArray.length; index++) {
            compatibility[index] = binarySearch(index);
        }
    }

    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming
     * approach.
     * 
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        // YOUR CODE HERE
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        // YOUR CODE HERE
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /*
     * This function calculates maximum weights and prints out whether it has been
     * called before or not
     */
    public Double calculateMaxWeight(int i) {
        // YOUR CODE HERE
        return -1.0;
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     */
    public ArrayList<Task> planGreedy() {
        planGreedy.add(taskArray[0]);
        int lastChosenIndex = 0;

        for (int i = 1; i < compatibility.length; i++) {
            if (compatibility[i] == lastChosenIndex) {
                planGreedy.add(taskArray[i]);
                lastChosenIndex = i;
            }
        }

        return planGreedy;
    }
}
