import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
     * Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time is calculated here.
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        String[] startTimeArr = this.start.split(":");
        int finHour = Integer.parseInt(startTimeArr[0]) + duration;
        String finHourStr = Integer.toString(finHour);
        if (finHourStr.length() == 1) {
            finHourStr = "0" + finHourStr;
        }
        return finHourStr + ":" + startTimeArr[1];
    }

    /**
     * Weight calculation is performed here.
     *
     * @return calculated weight
     */
    public double getWeight() {
        double weight = (importance * (urgent ? 2000 : 1)) / duration;
        return weight;

    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()},
     * which sorts the given array easily.
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     *         If self == object, return 0
     *         If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        Task taskToCompare = (Task) o;
        return this.getFinishTime().compareTo(taskToCompare.getFinishTime());
    }
}
