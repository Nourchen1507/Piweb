package com.example.myproject.services;

import com.example.myproject.entities.Event;
import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;

public interface IInvitationService {

    void addInvitation( Invitation invitation);

    public Invitation mettreAjourInvitation(int id);

    void removeInvitation(int idInvitation);

    List<Invitation> getAllInvitation();

    List<Invitation> getByStatus(Status status);

     void assignInvitationToEvenment(int idInvitation, int idEvenment);

    public Optional<Invitation> afficherInvitation(int id);


    int affecterInvitationToEvenment(int i,int idi);


    Invitation retrieveInvitation(Integer idInvitation);


}
