package com.company.adminapiservice.util.feign;

import com.company.adminapiservice.viewmodel.LevelUpViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpService {

    //uri: /levelup
    //Create levelup account
    @RequestMapping(value = "/levelup", method = RequestMethod.POST)
    LevelUpViewModel createLevelUpAccount(@RequestBody LevelUpViewModel lvm);

    //Get all the accounts
    @RequestMapping(value = "/levelup", method = RequestMethod.GET)
    List<LevelUpViewModel> getAllLevelUpAccounts();

    //uri: /levelup/{id}
    //Get account for the specified Id
    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.GET)
    LevelUpViewModel getLevelUpAccount(@PathVariable("id") int id);

    //Delete account for the specified Id
    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.DELETE)
    void deleteLevelUpAccount(@PathVariable("id") int id);

    //Update points by customerId
    @RequestMapping(value = "/levelup/points/{customerId}", method = RequestMethod.PUT)
    void updatePointsOnAccount(@PathVariable("customerId") int customerId, @RequestBody LevelUpViewModel lvm);

    //Get points by CustomerId
    @RequestMapping(value = "/levelup/points/{customerId}", method = RequestMethod.GET)
    int retrievePointsByCustomerId(@PathVariable("customerId") int customerId);

    //Get acoount by CustomerId
    @RequestMapping(value = "/levelup/customer/{customerId}", method = RequestMethod.GET)
    LevelUpViewModel getLevelUpAccountByCustomerId(@PathVariable("customerId") int customerId);

}
