package ru.gb.Client.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.Client.domain.Message;
import ru.gb.Client.domain.Profile;
import ru.gb.Client.dto.TransferRequest;
import ru.gb.Client.service.ProfileService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;



    @GetMapping("/readAllIn/{id}/{password}")
    public ResponseEntity<?> readMessagesIn(@PathVariable Long id, @PathVariable String password){
        try{
            return new ResponseEntity<>(profileService.readAllReceivedMessages(id, password), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/readAllOut/{id}/{password}")
    public ResponseEntity<?> readMessagesOut(@PathVariable Long id, @PathVariable String password){
        try{
            return new ResponseEntity<>(profileService.readAllSentMessages(id, password), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readAllOutTo/{id}/{password}/{idReceiver}")
    public ResponseEntity<?> readMessagesOutTo(@PathVariable Long id, @PathVariable String password, @PathVariable Long idReceiver){
        try{
            return new ResponseEntity<>(profileService.readAllSentMessagesToProfile(id, password, idReceiver), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/readAllInTo/{id}/{password}/{idSender}")
    public ResponseEntity<?> readMessagesInTo(@PathVariable Long id, @PathVariable String password, @PathVariable Long idSender){
        try{
            return new ResponseEntity<>(profileService.readAllSentMessagesFromProfile(id, password, idSender), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readAllFromServer/{id}/{password}")
    public ResponseEntity<?> readMessagesFromServer(@PathVariable Long id, @PathVariable String password){
        try{
            return new ResponseEntity<>(profileService.readAllMessagesFromServer(id, password), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readAllToServer/{id}/{password}")
    public ResponseEntity<?> readMessagesToServer(@PathVariable Long id, @PathVariable String password){
        try{
            return new ResponseEntity<>(profileService.readAllMessagesToServer(id, password), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sendToProfile/{password}")
    public ResponseEntity<?> sendMessageToProfile(@PathVariable("password") String password, @RequestBody TransferRequest request){
        try{
            return new ResponseEntity<>(profileService.
                    sendMessageToProfile(request.getSenderAccountID(), request.getReceiverAccountID(), request.getMessage(), password),
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sendToServer/{password}")
    public ResponseEntity<?> sendMessageToServer(@PathVariable("password") String password, @RequestBody TransferRequest request){
        try{
            return new ResponseEntity<>(profileService.sendMessageToServer(request.getSenderAccountID(),request.getMessage(), password), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/{password}/{receiverName}")
    public ResponseEntity<?> findByName(@PathVariable Long id, @PathVariable String password, @PathVariable String receiverName){
        try{
            return new ResponseEntity<>(profileService.filterMessagesByName(id,password,receiverName), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/phone/{id}/{newPhoneNumber}/{password}")
    public ResponseEntity<?> editPhoneNumber(@PathVariable Long id, @PathVariable String newPhoneNumber, @PathVariable String password){
        try{
            profileService.editPhoneNumber(id, newPhoneNumber, password);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/phone/{phoneNumber}")
    public ResponseEntity<?> filterByPhoneNumber(@PathVariable String phoneNumber){
        try{
            return new ResponseEntity<>(profileService.getByPhoneNumber(phoneNumber), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/age/{birthdate}")
    public ResponseEntity<?> filterByAge(@PathVariable String birthdate){
        try{
            return new ResponseEntity<>(profileService.filterUsersByAge(birthdate), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/name")
    public ResponseEntity<?> filterByName(@RequestParam String name){
        try{
            return new ResponseEntity<>(profileService.getByName(name),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }


}
