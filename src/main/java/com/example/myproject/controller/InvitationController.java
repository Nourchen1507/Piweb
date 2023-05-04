package com.example.myproject.controller;

import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;
import com.example.myproject.services.EventServiceImpl;
import com.example.myproject.services.IEventService;
import com.example.myproject.services.IInvitationService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Component
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/invitation")

public class InvitationController {

    @Autowired
    private IInvitationService iInvitationService;

    @PostMapping("/add")
    void addInvitation(@RequestBody Invitation invitation) {
        iInvitationService.addInvitation(invitation);
    }

    @DeleteMapping("/delete/{id}")
    void deleteInvitation(@PathVariable("id") int idInvitation) {
        iInvitationService.removeInvitation(idInvitation);
    }


    @PutMapping("/update/{id}")
    Invitation updateInvitation(@PathVariable("id") int idInvitation, @RequestBody Invitation invitation) {
        return iInvitationService.updateInvitation(idInvitation, invitation);
    }

    @GetMapping("/get/{id}")
    List<Invitation> getInvitation(@PathVariable("id") int idInvitation) {
        return iInvitationService.retrieveInvitation(idInvitation);
    }

    @GetMapping("/all")
    List<Invitation> getAllInvitation() {
        return iInvitationService.getAllInvitation();
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

    @GetMapping("addInvitToEvent/{idevent}/{idinvitation}")
    public int addinvitToevent(@PathVariable("idevent") int idevent,@PathVariable("idinvitation") int idinvitation)
    {
        return iInvitationService.affecterInvitToEvent(idevent,idinvitation);
    }


}
