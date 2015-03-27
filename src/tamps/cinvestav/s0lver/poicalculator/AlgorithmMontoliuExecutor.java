package tamps.cinvestav.s0lver.poicalculator;

public class AlgorithmMontoliuExecutor {
//
//    /***
//     * Montoliu Algorithm for computing points of interest, given a:
//     * @param gpsFixes Trajectory, created by a list (array) of GPS ficex
//     * @param minTime T_min parameter of algorithm
//     * @param maxTime T_max parameter of algorithm
//     * @param maxDistance D_max parameter of algorithm
//     * @return A list of the POI's found
//     */
//    public static List<StayPoint> obtainPointsOfInterest(GpsFix[] gpsFixes, long minTime, long maxTime, float maxDistance) {
//        List<StayPoint> listOfStayPoints = new ArrayList<>();
//        GpsFix pointI, pointJ, pointJprevious;
//
//        int sizeOfList = gpsFixes.length;
//        int i = 0, j;
//        long timeDifference;
//        float distDifference;
//
//
//        while (i < sizeOfList) {
//            pointI = gpsFixes[i];
//            j = i + 1;
//
//            if (j==sizeOfList) {
//                // Verify if you want to calculate the last subset!
//                break;
//            }
//
//            while (j < sizeOfList) {
//                pointJ = gpsFixes[j];
//                pointJprevious = gpsFixes[j - 1];
//
//                timeDifference = DifferenceCalculator.calculateTimeDifference(pointJprevious, pointJ);
//                if (timeDifference > maxTime) {
//                    i = j;
//                    break;
//                }
//
//                distDifference = DifferenceCalculator.calculateDistance(pointI, pointJ);
//                if (distDifference > maxDistance) {
//                    timeDifference = DifferenceCalculator.calculateTimeDifference(pointI, pointJprevious);
//                    if (timeDifference > minTime) {
//                        GpsFix[] pointsSlice = Arrays.copyOfRange(gpsFixes, i, j - 1);
//                        // GpsFix[] pointsSlice = (GpsFix[]) Arrays.asList(gpsFixes).subList(i, j - 1).toArray();
//                        StayPoint pointOfInterest = StayPoint.createStayPoint(pointsSlice);
//                        listOfStayPoints.add(pointOfInterest);
//                    }
//                    i = j;
//                    break;
//                }
//                j++;
//            }
//        }
//
//        return listOfStayPoints;
//    }
//
//    public static List<StayPoint> obtainPointsOfInteresMyVersion(GpsFix[] gpsFixes, long minTime, long maxTime, float maxDistance) {
//        ArrayList<StayPoint> listOfStayPoint = new ArrayList<>();
//        ArrayList<GpsFix> candidates = new ArrayList<>();
//        int i = 0;
//        int j = 1;
//        int sizeOfList = gpsFixes.length;
//        GpsFix p_i = gpsFixes[i];
//        candidates.add(p_i);
//        while (true) {
//            if (j>=sizeOfList) {
//                // The j index is at the last position
//                // We should decide if we process the candidates list as a POI
//                break;
//            }
//            GpsFix p_j = gpsFixes[j];
//
//            float timeDiff = DifferenceCalculator.calculateTimeDifference(gpsFixes[j-1], p_j);
//            float distDiff = DifferenceCalculator.calculateDistance(gpsFixes[j-1], p_j);
//
////            System.out.println(String.format("Comparing distances between %d and %d", i, j));
//            if (timeDiff > minTime && distDiff > maxDistance) {
////                System.out.println("Distances are in range");
////                System.out.println("adding the candidate " + j);
//                candidates.add(p_j);
//                j++;
//                continue;
//            } else {
////                System.out.println("Distances are not in range");
//                if (candidates.size() > 1) {
//                    if (timeDiff < maxTime) {
////                        System.out.println("Creating a poi with " + candidates.size() + " fixes");
//                        StayPoint stayPoint = StayPoint.createStayPoint(candidates.toArray(new GpsFix[candidates.size()]));
//                        listOfStayPoint.add(stayPoint);
//                    }
//                }
//                candidates.clear();
//                i = j + 1;
//                j = i + 1;
//                if (i >= sizeOfList) {
//                    break;
//                }
//
//                p_i = gpsFixes[i];
//                candidates.add(p_i);
//
//            }
//        }
//
//        return listOfStayPoint;
//    }
//
//    public static LinkedList<StayPoint> obtainPointsOfInterestOldVersion(LinkedList<GpsFix> entrada,
//                                               double umbralDistancia, long umbralTiempoMinimo,
//                                               long umbralTiempoMaximo, boolean debug) {
//        LinkedList<StayPoint> salida = new LinkedList<StayPoint>();
//
//        int i = 0, j = 0;
//        int n = entrada.size();
//        long t = 0L;
//        double d = 0;
//
//        while (i < n) {
//            // if (mio >= sup)
//            // System.exit(0);
//            if (j >= n)
//                break;
//            j = i + 1;
//
//            while (j < n) {
//                //d = distance(entrada.get(i), entrada.get(j));
//                d = DifferenceCalculator.calculateDistance(entrada.get(i), entrada.get(j));
//                if (debug)
//                    System.out.println("Distancia es " + d);
//                if (d > umbralDistancia) {
//
//                    //t = timespan(entrada.get(i), entrada.get(j - 1));
//                    t = DifferenceCalculator.calculateTimeDifference(entrada.get(i), entrada.get(j - 1));
//                    if (debug)
//                        System.out.println("Tiempo es " + t);
//                    if (t > umbralTiempoMinimo) {
//
//                        if (debug)
//                            System.out.println("A�adiendo sp en " + (i) + "-"
//                                    + ((j - 1)));
//                        List<GpsFix> subList = entrada.subList(i, j - 1);
//                        GpsFix[] array = subList.toArray(new GpsFix[subList.size()]);
//
//                        StayPoint sp = StayPoint.createStayPoint(array);
////                        StayPoint sp = computeEquations(entrada, i, j - 1);
////                        sp.setArriveTime(entrada.get(i).getTimestamp());
////                        sp.setLeavingTime(entrada.get(j - 1).getTimestamp());
//
//                        salida.add(sp);
//                    }
//                    i = j;
//                    // mio++;
//                    if (debug) {
//                        System.out.println("--");
//                        System.out.println("i=" + i + ", j=" + j);
//                        System.out
//                                .println("-------------------------------------");
//                    }
//                    break;
//                }
//                j++;
//                // mio++;
//                if (debug) {
//                    System.out.println("--");
//                    System.out.println("i=" + i + ", j=" + j);
//                    System.out.println("-------------------------------------");
//                }
//            }
//        }
//        // if (i != n) {
//        // tamps.cinvestav.old.StayPoint sp = computeEquations(entrada, i, j - 1);
//        // sp.setArriveTime(entrada.get(i).getTimestamp());
//        // sp.setLeavingTime(entrada.get(j - 1).getTimestamp());
//        //
//        // salida.add(sp);
//        // }
//
//        return salida;
//    }
}
