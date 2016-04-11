package tamps.cinvestav.s0lver.locationentities;

import java.util.ArrayList;
import java.util.List;

/***
 * Represents a Trajectory composed by time sequenced GPS fixes
 */
public class Trajectory {
    private ArrayList<GpsFix> fixes;

    /***
     * Creates a trajectory with no GPS fixes
     */
    public Trajectory() {
        this.fixes = new ArrayList<>();
    }

    /***
     * Creates a Trajectory from a list of fixes
     * @param fixes The GPS fixes to include in the trajectory
     * @see GpsFix
     */
    public Trajectory(ArrayList<GpsFix> fixes) {
        this.fixes = fixes;
    }

    /***
     * Adds a GPS fix to the trajectory
     * @param fix The GPS fix to add
     */
    public void addFix(GpsFix fix) {
        if (fixes.size() != 0){
            GpsFix last = getLast();
            if (fix.getTimestamp().getTime() < last.getTimestamp().getTime()) {
                throw new RuntimeException("The specified GPS fix occurred before the last one in the trajectory " + fix);
            }
        }
        fixes.add(fix);
    }

    /***
     * Gets the GPS fix at the given position
     * @param index The index of the GPS fix to retrieve
     * @return The GPS fix at index idx
     */
    public GpsFix getFix(int index) {
        return fixes.get(index);
    }

    /***
     * Gets the first GPS fix of the trajectory
     * @return The first GPS fix of the trajectory
     */
    public GpsFix getFirst(){
        return getFix(0);
    }

    /***
     * Returns the last fix of the trajectory
     * @return The last fix of the trajectory
     */
    public GpsFix getLast() {
        return getFix(fixes.size() - 1);
    }

    /***
     * Gets the size of the trajectory
     * @return The size of the trajectory
     */
    public int getSize() {
        return fixes.size();
    }

    /***
     * Gets the index of the time-closest GPS fix to the specified one
     * @param fix The GPS fix for searching the time-nearest fix to
     * @param startingIndex The index to start looking for the fix (avoids unnecessary traversals)
     * @return The index of the time-closest GPS fix
     */
    public int getTimeClosestFixIndex(GpsFix fix, int startingIndex) {
        int trjSize = fixes.size();
        int i = 0;
        for (i = startingIndex; i < trjSize; i++) {
//            if (fix.getTimestamp().before(fixes.get(i).getTimestamp())) {
//                continue;
//            } else if (fix.getTimestamp().after(fixes.get(i).getTimestamp())) {
//                break;
//            }
            if (fix.getTimestamp().after(fixes.get(i).getTimestamp())) {
                break;
            }
        }
        int previousFixIndex = i - 1;
        int nextFixIndex = i;
        GpsFix previousFix = getFix(previousFixIndex);
        GpsFix nextFix = getFix(nextFixIndex);
        long timeDifferencePrevious = fix.getTimestamp().getTime() - previousFix.getTimestamp().getTime();
        long timeDifferenceNext = nextFix.getTimestamp().getTime() - fix.getTimestamp().getTime();

        if (timeDifferencePrevious < timeDifferenceNext) {
            return previousFixIndex;
        }

        return nextFixIndex;
    }

    /***
     * Gets the time-closest GPS fix to the specified one
     * @param fix The FPS fix for searching the time nearest fix to
     * @param startingIndex The index to start looking for the fix (avoids unnecessary traversals)
     * @return The time-closest GPS fix
     */
    public GpsFix getTimeClosestFix(GpsFix fix, int startingIndex) {
        int fixIndex = getTimeClosestFixIndex(fix, startingIndex);
        return getFix(fixIndex);
    }

    /***
     * Obtains a sub-Trajectory composed from startingIndex up to endIndex (both included)
     * @param startingIndex low endpoint (inclusive) of the sub-Trajectory
     * @param endIndex High endpoint (inclusive) of the sub-Trajectory
     * @return A sub-Trajectory from startingIndex to endIndex, including both fixes
     */
    public Trajectory getSubtrajectory(int startingIndex, int endIndex) {
        ArrayList<GpsFix> subList = (ArrayList<GpsFix>) fixes.subList(startingIndex, endIndex + 1);
        return new Trajectory(subList);
    }
}
