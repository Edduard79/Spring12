package exercise.twelve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarRepo carRepo;


    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carRepo.save(car);
        return ResponseEntity.created(URI.create("/cars/" + savedCar.getId())).body(savedCar);
    }

    @GetMapping
    public List<Car> getCars() {
        return carRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getOneById(@PathVariable Long id) {
        Optional<Car> optionalCar = carRepo.findById(id);
        if (optionalCar.isPresent()) {
            return ResponseEntity.ok(optionalCar.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCarType(@PathVariable Long id, @RequestParam String type) {
        Optional<Car> optionalCar = carRepo.findById(id);
        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();
            existingCar.setType(type);
            Car updatedCar = carRepo.save(existingCar);
            return ResponseEntity.ok(updatedCar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCar(@PathVariable Long id) {
        Optional<Car> optionalCar = carRepo.findById(id);
        if (optionalCar.isPresent()) {
            carRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
