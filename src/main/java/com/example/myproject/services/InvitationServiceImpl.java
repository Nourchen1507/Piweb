package com.example.myproject.services;

import com.example.myproject.entities.Event;
import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;
import com.example.myproject.repository.EventRepository;
import com.example.myproject.repository.IInvitationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service("invitation")

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
    public Invitation updateInvitation(Long idInvitation, Invitation invitation) {
        List<Invitation> newInvitation =  invitationRepository.findByIdInvitation(idInvitation);
        if (invitation.getName()!= null)
            newInvitation.get(0).setName(invitation.getName());
        return  invitationRepository.save(newInvitation.get(0));
    }

    @Override
    public void removeInvitation(Long idInvitation) {
        invitationRepository.deleteById(idInvitation);

    }

    @Override
    public List<Invitation> retrieveInvitation(Long idInvitation) {
        return invitationRepository.findByIdInvitation(idInvitation);
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
    public void assignInvitationToEvenment(Long idInvitation, Long idEvenment) {
        Invitation invitation = invitationRepository.findById(idInvitation).get();
        Event event= eventRepository.findById(idEvenment).get();
        invitation.setEvent(event);
        invitationRepository.save(invitation);
    }
}
