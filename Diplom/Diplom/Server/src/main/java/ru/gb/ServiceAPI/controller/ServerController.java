package ru.gb.ServiceAPI.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.ServiceAPI.domain.Message;
import ru.gb.ServiceAPI.domain.Profile;
import ru.gb.ServiceAPI.dto.TransferRequest;
import ru.gb.ServiceAPI.service.ServerService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/server")
public class ServerController {

    private final ServerService serverService;


    @GetMapping("/allProfiles")
    public ResponseEntity<?> showAllProfiles(){
        try{
            return new ResponseEntity<>(serverService.getAllProfiles(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allMessages")
    public ResponseEntity<?> showAllMessages(){
        try{
            return new ResponseEntity<>(serverService.readAllMessages(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readAllToServer")
    public ResponseEntity<?> readToServerMessages(){
        try{
            return new ResponseEntity<>(serverService.readToServerMessages(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readAllFromServer")
    public ResponseEntity<?> readFromServerMessages(){
        try{
            return new ResponseEntity<>(serverService.readFromServerMessages(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readAllToServerBy/{id}")
    public ResponseEntity<?> readToServerMessagesBy(@PathVariable Long id){
        try{
            return new ResponseEntity<>(serverService.readToServerMessagesBySingleProfile(id), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readAllFromServerBy/{id}")
    public ResponseEntity<?> readFromServerMessagesBy(@PathVariable Long id){
        try{
            return new ResponseEntity<>(serverService.readFromServerMessagesBySingleProfile(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readFromProfile/{id}")
    public ResponseEntity<?> readFromProfile(@PathVariable Long id){
        try{
            return new ResponseEntity<>(serverService.readProfilesSentMessages(id), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/readToProfile/{id}")
    public ResponseEntity<?> readToProfile(@PathVariable Long id){
        try{
            return new ResponseEntity<>(serverService.readProfilesReceivedMessages(id), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/{receiver}/{sender}")
    public ResponseEntity<?> filterMessages(@PathVariable String receiver, @PathVariable String sender){
        try{
            return new ResponseEntity<>(serverService.filterMessagesByName(receiver,sender), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessageToProfile(@RequestBody TransferRequest request){
        try{
            return new ResponseEntity<>(serverService.sendMessage(request.getSenderAccountID(),request.getMessage()), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> newClient(@RequestBody Profile profile){
        try{
            return new ResponseEntity<>(serverService.createProfile(profile), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        try{
            serverService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/phone/{phoneNumber}")
    public ResponseEntity<?> filterByPhoneNumber(@PathVariable String phoneNumber){
        try{
            return new ResponseEntity<>(serverService.getByPhoneNumber(phoneNumber), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/age/{birthdate}")
    public ResponseEntity<?> filterByAge(@PathVariable String birthdate){
        try{
            return new ResponseEntity<>(serverService.filterUsersByAge(birthdate), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/filter/name")
    public ResponseEntity<?> filterByName(@RequestParam String name){
        try{
            return new ResponseEntity<>(serverService.getByName(name),HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Check the entered data again",HttpStatus.NOT_FOUND);
        }
    }



}
