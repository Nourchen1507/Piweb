package com.example.myproject.services;

import com.example.myproject.entities.Event;
import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;

import java.util.List;

public interface IInvitationService {

    void addInvitation( Invitation invitation);

    Invitation updateInvitation(Long idInvitation, Invitation invitation);

    void removeInvitation(Long idInvitation);

    List<Invitation> retrieveInvitation(Long idInvitation);

    List<Invitation> getAllInvitation();

    List<Invitation> getByStatus(Status status);
}
