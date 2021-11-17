package com.adhd.Olivia.controllers;


import com.adhd.Olivia.models.db.Menstruation;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.MenstruationRepository;
import com.adhd.Olivia.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/menstruation")
public class MenstruationController {
    @Autowired
    private MenstruationRepository menstruationRepository;
    @Autowired
    private UserRepository userRepository;

    @PutMapping(path="/update")
    public ResponseEntity<String> updateMenstruation(@RequestBody Menstruation menstruation){
        System.out.println("Changing menstruation cycle");
        //getting the correct Id for the user name to check whether it exists
        List<User> optionalUser = userRepository.findByLogin(menstruation.getLogin()); //why not Optional<User>?
        if (optionalUser.isEmpty()){
            //user not registered? return error
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);

        } else {
            //user exists. Get its id
            //now check whether this Id already exists in the menstruationRepo! Why is here the type optional and not for the user?
            List<Menstruation> optionalMenstruation = menstruationRepository.findByLogin(menstruation.getLogin());
            if (optionalMenstruation.isEmpty()){
                //add new menstruation
                Menstruation newMenstruation = new Menstruation();
                newMenstruation.setLogin(menstruation.getLogin());
                newMenstruation.setLastPeriodStart(menstruation.getLastPeriodStart());
                newMenstruation.setPeriodCycleLength(menstruation.getPeriodCycleLength());
                newMenstruation.setPeriodLength(menstruation.getPeriodLength());
                menstruationRepository.save(newMenstruation);
                return new ResponseEntity<String>("Added new",HttpStatus.CREATED);
            } else {
                // update it 
                Menstruation oldMenstruation = optionalMenstruation.get(0);
                oldMenstruation.setLastPeriodStart(menstruation.getLastPeriodStart());
                oldMenstruation.setPeriodCycleLength(menstruation.getPeriodCycleLength());
                oldMenstruation.setPeriodLength(menstruation.getPeriodLength());
                menstruationRepository.save(oldMenstruation);
                return new ResponseEntity<String>("Updated",HttpStatus.CREATED);
            }

        }

    }

    @GetMapping("/getMenstruationData")
    public ResponseEntity<String> getLastPeriodStart(@RequestBody String userLogin){
        System.out.println("Get Start of Last Period");
        //getting the correct Id for the user name to check whether it exists
        List<Menstruation> optionalMenstruation = menstruationRepository.findByLogin(userLogin);
        if (optionalMenstruation.isEmpty()){
            return new ResponseEntity<String>("Doesn't exist", HttpStatus.NOT_FOUND);
        } else{
            String response = "{ 'userId':"+optionalMenstruation.get(0).getId()+
                    ", 'login':"+optionalMenstruation.get(0).getLogin()+
                    ", 'lastPeriodStart':"+optionalMenstruation.get(0).getLastPeriodStart()+
                    ", 'periodCycleLength':"+optionalMenstruation.get(0).getPeriodCycleLength()+
                    ", 'PeriodLength':"+optionalMenstruation.get(0).getPeriodLength()+
                    "}";
            return new ResponseEntity<String>(response,HttpStatus.OK);
        }
    }

}
