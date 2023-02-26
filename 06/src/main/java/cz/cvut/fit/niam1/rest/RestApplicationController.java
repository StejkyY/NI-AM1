package cz.cvut.fit.niam1.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RestApplicationController {

    @Autowired
    RestApplicationRepository repository;

    @GetMapping("/tour")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> getTours() {
        return repository.getTours();
    }

    @GetMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tour getTour(@PathVariable String id) {
        return repository.getTourById(id);
    }

    @GetMapping("/tour/waiting")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> getToursWaitingForDelete () { return repository.getWaitingTours();}

    @PostMapping(value = "/tour")
    public ResponseEntity createTour(@RequestBody Tour tour) {
        repository.addTour(tour);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/tour/" + tour.getId()).build();
    }

    @DeleteMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTour(@PathVariable String id){
        repository.deleteTour(id);
    }

    @PutMapping("/tour/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelTourDeleting(@PathVariable String id){
        repository.cancelDeleting(id);
    }

}
