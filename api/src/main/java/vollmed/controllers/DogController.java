package vollmed.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vollmed.models.dog.DataDog;
import vollmed.models.dog.Dog;
import vollmed.models.dog.DogRepository;
import vollmed.models.dog.ListDataDog;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/dogs")

public class DogController {

    @Autowired
    private DogRepository dogRepository;

//    @GetMapping
//    public DataDog listDog(DataDog data) {
//        return data;
//    }
    @GetMapping("/all-dogs")
    public List<Dog> listAllDogs() {
        return dogRepository.findAll();
    }

    @GetMapping("/page")
    public Page<ListDataDog> listDogsByPages(@PageableDefault(size=1) Pageable page){
        System.out.println("List by pages");
        return dogRepository.findAll(page).map(ListDataDog::new);
    }
     @PostMapping
    public ResponseEntity<String> saveDog(@RequestBody @Valid DataDog data) {
        dogRepository.save(new Dog(data));
        return ResponseEntity.ok("Dog object was saved successfully");
    }
}
