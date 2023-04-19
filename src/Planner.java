import java.util.ArrayList;
import java.util.Arrays;
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
        Arrays.fill(maxWeight, -1.0);

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
        calculateCompatibility();
        System.out.println("Calculating max array\n---------------------");
        calculateMaxWeight(taskArray.length - 1);
        System.out.println("\nCalculating the dynamic solution\n--------------------------------");
        solveDynamic(taskArray.length - 1);
        System.out.println("\nDynamic Schedule\n----------------");
        for (int i = planDynamic.size() - 1; i >= 0; i--) {
            System.out.printf("At %s, %s.\n", planDynamic.get(i).getStartTime(), planDynamic.get(i).getName());
        }
        System.out.println();
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        if (i == -1 || i == 0) {
            return;
        }
        System.out.printf("Called solveDynamic(%d)\n", i);
        double curr = taskArray[i].getWeight();
        if (compatibility[i] != -1) {
            curr += maxWeight[compatibility[i]];
        }

        if (curr > maxWeight[i - 1]) {
            planDynamic.add(taskArray[i]);
            solveDynamic(compatibility[i]);
        } else {
            solveDynamic(i - 1);
        }
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /*
     * This function calculates maximum weights and prints out whether it has been
     * called before or not
     */
    public Double calculateMaxWeight(int i) {
        System.out.printf("Called calculateMaxWeight(%d)\n", i);
        if (i == -1) {
            return 0.0;
        }
        if (maxWeight[i] == -1.0) {
            maxWeight[i] = Math.max(taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]),
                    calculateMaxWeight(i - 1));
        }
        return maxWeight[i];
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
        System.out.println("Greedy Schedule\n---------------");
        planGreedy.add(taskArray[0]);
        int lastChosenIndex = 0;

        for (int i = 1; i < taskArray.length; i++) {
            if (taskArray[i].getStartTime().compareTo(taskArray[lastChosenIndex].getFinishTime()) > 0 ||
                    taskArray[i].getStartTime().compareTo(taskArray[lastChosenIndex].getFinishTime()) == 0) {
                planGreedy.add(taskArray[i]);
                lastChosenIndex = i;
            }
        }

        for (int i = 0; i < planGreedy.size(); i++) {
            System.out.printf("At %s, %s.\n", planGreedy.get(i).getStartTime(), planGreedy.get(i).getName());
        }
        return planGreedy;
    }
}
