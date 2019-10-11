package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.viewmodel.LevelUpViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @RequestMapping(value = "/levelup", method = RequestMethod.GET)
    List<LevelUpViewModel> getAllLevelUpAccounts();

    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.GET)
    LevelUpViewModel getLevelUpAccount(@PathVariable("id") int id);

    @RequestMapping(value = "/levelup/points/{customerId}", method = RequestMethod.PUT)
    void updatePointsOnAccount(@PathVariable("customerId") int customerId, @RequestBody @Valid LevelUpViewModel lvm);

    @RequestMapping(value = "/levelup/points/{customerId}", method = RequestMethod.GET)
    int retrievePointsByCustomerId(@PathVariable("customerId") int customerId);

    @RequestMapping(value = "/levelup/customer/{customerId}", method = RequestMethod.GET)
    LevelUpViewModel getLevelUpAccountByCustomerId(@PathVariable("customerId") int customerId);

}
