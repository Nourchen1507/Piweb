package com.example.myproject.controller;

import com.example.myproject.entities.Invitation;
import com.example.myproject.services.IInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
@RequestMapping("/invitation")

public class InvitationController {

@Autowired
private  IInvitationService iInvitationService;

    @PostMapping("/add")
    void addInvitation(@RequestBody Invitation invitation)
    {iInvitationService.addInvitation(invitation);
    }

    @DeleteMapping("/delete/{id}")
    void deleteInvitation(@PathVariable("id") Long idInvitation){
        iInvitationService.removeInvitation(idInvitation);
    }


    @PutMapping("/update/{id}")
    Invitation updateInvitation(@PathVariable("id") Long idInvitation , @RequestBody Invitation invitation){
        return iInvitationService.updateInvitation(idInvitation, invitation);
    }

    @GetMapping("/get/{id}")
    List<Invitation> getInvitation(@PathVariable("id") Long idInvitation){
        return iInvitationService.retrieveInvitation(idInvitation);
    }

    @GetMapping ("/all")
    List<Invitation> getAllInvitation(){
        return iInvitationService.getAllInvitation();
    }
}
