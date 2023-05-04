package com.example.myproject.services;

import com.example.myproject.entities.Event;
import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;
import lombok.extern.java.Log;

import java.util.List;

public interface IInvitationService {

    void addInvitation( Invitation invitation);

    Invitation updateInvitation(int idInvitation, Invitation invitation);

    void removeInvitation(int idInvitation);

    List<Invitation> retrieveInvitation(int idInvitation);

    List<Invitation> getAllInvitation();

    List<Invitation> getByStatus(Status status);

    public  void assignInvitationToEvenment(int idInvitation, int idEvenment);

    public int affecterInvitToEvent(int i,int ide);

}
