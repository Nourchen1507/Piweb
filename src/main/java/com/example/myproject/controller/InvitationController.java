package com.example.myproject.controller;

import com.example.myproject.entities.Event;
import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;
import com.example.myproject.repository.IInvitationRepository;
import com.example.myproject.services.EventServiceImpl;
import com.example.myproject.services.IEventService;
import com.example.myproject.services.IInvitationService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@Component
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/invitation")

public class InvitationController {

    @Autowired
    private IInvitationService iInvitationService;
    @Autowired
    IInvitationRepository iInvitationRepository;



    @PostMapping("/add")
    void addInvitation(@RequestBody Invitation invitation) {
        iInvitationService.addInvitation(invitation);
    }

    @DeleteMapping("/delete/{id}")
    void deleteInvitation(@PathVariable("id") int idInvitation) {
        iInvitationService.removeInvitation(idInvitation);
    }




    @GetMapping("/all")
    List<Invitation> getAllInvitation() {
        return iInvitationService.getAllInvitation();
    }



    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Invitation invitation, @PathVariable Integer id) {
        try {
            Optional<Invitation> existClub = iInvitationService.afficherInvitation(id);
            invitation.setIdInvitation(id);
            iInvitationService.addInvitation(invitation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("affect/{idi}/{ide}")
    public void affect(@PathVariable("idi") int idInvitation, @PathVariable("ide") int idEvenment) {

        iInvitationService.assignInvitationToEvenment(idInvitation,idEvenment);
    }
    @GetMapping("/pdf/invitations")

    public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);
        List<Invitation> invitations = iInvitationService.getAllInvitation();

        PDFController generator = new PDFController();
        generator.setInvitations(invitations);
        generator.generate(response);


    }
    @GetMapping("/status")
    public ResponseEntity<Status[]> getStatus() {
        return ResponseEntity.ok(Status.values());

    }



    @GetMapping("addinvitationToEvent/{idevent}/{idinvitation}")
    public int addinvitationToEvent(@PathVariable("idevent") int idevent,@PathVariable("idinvitation") int idi) {;

        return iInvitationService.affecterInvitationToEvenment(idevent,idi);
    }


    @GetMapping("/DisplayById/{id}")
    public Invitation displayInvitationByID(@PathVariable("id") int id) {

        return iInvitationService.retrieveInvitation(id);
    }

}
