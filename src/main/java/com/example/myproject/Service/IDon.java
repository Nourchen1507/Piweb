package com.example.myproject.Service;

import com.example.myproject.entities.Don;

import java.util.List;
import java.util.Map;

public interface IDon {



    Don addDon (Don D ) ;
    Don updateDon(Don D );
    List<Don> retrieveAllDons();

    Don retrieveDonById(Long id );
    void deletedon(Long id);

    Don assigneDontoPost (Long idon , Long idpost );


    Map<String, Integer> getDonationStats() ;
    Don addDonAndAssignToPost(Don D, Long idpost);
    List<Don> retrieveDonsByPostId(Long idpost);
    List<Don> findDonsByUserId(Long userId);
    List<Don> retrieveDonsByUserId(Long userId);
    List<Don> getDonsByUserId(Long userId);

    public Don addDonToPost(Long postId, Don don) ;

}
