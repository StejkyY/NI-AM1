package cz.cvut.fit.niam1.rest;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestApplicationRepository {

    private static final List<Tour> tours = new ArrayList<>();
    private static final List<Tour> waitingTours = new ArrayList<>();


    @PostConstruct
    public void initRepo() {
        tours.add(new Tour("1", "Beach tour"));
        tours.add(new Tour("2", "Ski tour"));
    }

    public void addTour(Tour t) {    
        tours.add(t);
    }

    public List<Tour> getTours() {
        return tours;
    }

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void deleteTour(String id) {
        if ( waitingTours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null) == null)
            waitingTours.add(getTourById(id));
        tours.removeIf(c -> c.getId().equals(id));

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        waitingTours.removeIf(c -> c.getId().equals(id));
                    }
                },
                10000
        );
    }

    public void cancelDeleting (String id){
        tours.add(waitingTours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null));
        waitingTours.removeIf(c -> c.getId().equals(id));
    }

    public List<Tour> getWaitingTours() {
        return waitingTours;
    }
}
