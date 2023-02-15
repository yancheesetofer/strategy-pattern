package id.ac.ui.cs.advprog.tutorial1.transport.core;
import id.ac.ui.cs.advprog.tutorial1.transport.exceptions.InvalidDistanceException;

public class CarCostCalculator implements TransportCostCalculator {
    private static final double COST_PER_KM = 3500;
    private static final double SPEED_IN_KM_PER_HOUR = 30.0;
    private static final int MAX_DISTANCE_IN_KM = 50;

    @Override
    public Cost getCosts(Double distanceInKm) {
        assertWithinDistanceLimit(distanceInKm);
        double totalCost = distanceInKm <= 2? 10000 : 10000 + (distanceInKm-2) * COST_PER_KM;
        double timeEstimationInHour = distanceInKm / SPEED_IN_KM_PER_HOUR;
        int satisfaction = getTransportSatisfaction(distanceInKm);
        return new Cost(totalCost, timeEstimationInHour, satisfaction);
    }

    @Override
    public Double getTransportFare(Double distanceInKm) {
        return getCosts(distanceInKm).getFare();
    }

    @Override
    public Double getTransportTimeInHour(Double distanceInKm) {
        return getCosts(distanceInKm).getTimeEstimationInHour();
    }

    @Override
    public Integer getTransportSatisfaction(Double distanceInKm) {
        int satisfication =10 - (int) Math.floor((distanceInKm-10)/5);
        if (satisfication > 1) {
            return satisfication;
        } else {
            return 1;
        }
    }

    private void assertWithinDistanceLimit(Double distanceInKm) {
        if (distanceInKm <= 0 || distanceInKm > MAX_DISTANCE_IN_KM) {
            throw new InvalidDistanceException();
        }
    }
}
