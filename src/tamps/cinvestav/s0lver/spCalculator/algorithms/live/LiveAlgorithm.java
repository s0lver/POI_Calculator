package tamps.cinvestav.s0lver.spCalculator.algorithms.live;

import tamps.cinvestav.s0lver.spCalculator.classes.GpsFix;
import tamps.cinvestav.s0lver.spCalculator.classes.StayPoint;

public abstract class LiveAlgorithm {

    protected double distanceThreshold;
    protected long minTimeThreshold;

    public LiveAlgorithm(long minTimeThreshold, double distanceThreshold) {
        this.minTimeThreshold = minTimeThreshold;
        this.distanceThreshold = distanceThreshold;
    }

    public abstract StayPoint processFix(GpsFix fix);

    protected abstract StayPoint processLive();

    public abstract StayPoint processLastPart();

//    public static Location construirLocation(RegistroUbicacion registroUbicacion) {
//        Location fix = new Location("Custom");
//        fix.setAccuracy((float) registroUbicacion.getPrecision());
//        fix.setAltitude(0);
//        fix.setLatitude(registroUbicacion.getLatitud());
//        fix.setLongitude(registroUbicacion.getLongitud());
//        fix.setTime(registroUbicacion.getTimestamp().getTime());
//        return fix;
//    }
}
