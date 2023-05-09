package com.example.myproject.services;

import com.example.myproject.entities.Event;
import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;
import com.example.myproject.repository.EventRepository;
import com.example.myproject.repository.IInvitationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service("invitation")
@Slf4j


public class InvitationServiceImpl implements IInvitationService {



    final IInvitationRepository invitationRepository;
    final EventRepository eventRepository;

    public InvitationServiceImpl(IInvitationRepository invitationRepository, EventRepository eventRepository) {
        this.invitationRepository = invitationRepository;
        this.eventRepository= eventRepository;
    }


    @Override
    public void addInvitation(Invitation invitation) {
      invitationRepository.save(invitation);

    }

    @Override
    public Invitation mettreAjourInvitation(int id) {
        return null;
    }

    @Override
    public void removeInvitation(int idInvitation) {
        invitationRepository.deleteById(idInvitation);

    }


    @Override
    public List<Invitation> getAllInvitation() {
        List<Invitation> invitations = new ArrayList<>();
        invitationRepository.findAll().forEach(invitations::add);
        return invitations;
    }

    @Override
    public List<Invitation> getByStatus(Status status) {
        List<Invitation> invitations = new ArrayList<>();
        invitationRepository.findByStatut(status).forEach(invitations::add);
        return invitations;
    }

    @Override
    public void assignInvitationToEvenment(int idInvitation, int idEvenment) {
        Invitation invitation = invitationRepository.findById(idInvitation).get();
        Event event= eventRepository.findById(idEvenment).get();
        invitation.setEvent(event);
        invitationRepository.save(invitation);
    }

    @Override
    public int affecterInvitationToEvenment(int i,int idi) {
        return invitationRepository.affecterInvitationToEvenment(i,idi);
    }

    public Optional<Invitation> afficherInvitation(int id) {

        Invitation invitation = invitationRepository.findById(id).orElseThrow(() -> new RuntimeException(
                        "club with Id: " + id + " does not exist")
        );
        return invitationRepository.findById(id);
    }


    @Override
    public Invitation retrieveInvitation(Integer idInvitation) {
        return invitationRepository.findById(idInvitation).get();
    }


}
