package com.example.myproject.repository;

import com.example.myproject.entities.Invitation;
import com.example.myproject.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface IInvitationRepository extends JpaRepository<Invitation,Integer> {


    @Modifying
    @Transactional
    @Query("update  Invitation as i set i.event.idEvent= :idevent where i.idInvitation= :idinvitation")
    int affecterInvitationToEvenment(@Param("idevent") int i, @Param("idinvitation") int j );

    List<Invitation>  findByStatut(Status status);


}
