package com.company.levelup.controller;

import com.company.levelup.service.ServiceLayer;
import com.company.levelup.viewmodel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/levelup")
public class LevelUpController {

    @Autowired
    private ServiceLayer serviceLayer;

    //Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUpViewModel createLevelUpAccount(@RequestBody @Valid LevelUpViewModel lvm) {

        return serviceLayer.saveLevelUp(lvm);
    }

    // Read all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUpViewModel> getAllLevelUpAccounts() {

        return serviceLayer.findAllLevelUp();
    }

    // Read by Id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getLevelUpAccount(@PathVariable("id") int id) {

        return serviceLayer.findLevelUp(id);
    }

    // Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUpAccount(@PathVariable("id") int id) {

        serviceLayer.removeLevelUp(id);
    }

    // Custom method - Update points
    @PutMapping("/points/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePointsOnAccount(@PathVariable("customerId") int customerId, @RequestBody @Valid LevelUpViewModel lvm) {

        if (customerId != lvm.getCustomerId()) {
            throw new IllegalArgumentException(String.format("Id %s in the PathVariable does not match the Id %s in the RequestBody ", customerId, lvm.getCustomerId()));
        }

        serviceLayer.updatePoints(lvm);
    }

    // Custom method - Get points by customer id
    @GetMapping("/points/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public int retrievePointsByCustomerId(@PathVariable("customerId") int customerId) {

        return serviceLayer.getPoints(customerId);
    }

    // Custom method - Get account by Customer Id
    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getLevelUpAccountByCustomerId(@PathVariable("customerId") int customerId) {

        return serviceLayer.getLevelUpByCustomerId(customerId);
    }
}
