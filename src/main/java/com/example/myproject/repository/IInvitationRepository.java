package com.example.myproject.repository;

import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IInvitationRepository extends JpaRepository<Invitation,Long> {

    List<Invitation> findByIdInvitation(Long idInvitation);


    List<Invitation>  findByStatut(Status status);


}
