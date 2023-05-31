package exercise.twelve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public ResponseEntity<Car> updateCarType(@PathVariable Long id, @RequestBody Car car) {
        Optional<Car> optionalCar = carRepo.findById(id);
        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();
            existingCar.setType(car.getType());
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
