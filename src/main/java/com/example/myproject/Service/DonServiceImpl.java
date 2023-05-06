package com.example.myproject.Service;

import com.example.myproject.Repository.DonRepository;
import com.example.myproject.Repository.PostRespository;
import com.example.myproject.Repository.UserRepository;
import com.example.myproject.entities.Don;
import com.example.myproject.entities.Post;
import com.example.myproject.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DonServiceImpl implements IDon{

@Autowired
    DonRepository donRepository ;
@Autowired
    PostRespository postRespository ;

@Autowired
    UserRepository userRepository;
    @Override
    public Don addDon(Don D) {
        User user = userRepository.findById(D.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRespository.findById(D.getPost().getId()).orElseThrow(() -> new RuntimeException("Post not found"));

        D.setUser(user);
        D.setPost(post);
        return donRepository.save(D);
    }

    @Override
    public Don updateDon(Don D ){

       Don equipeToUpdate = donRepository.findById(D.getId()).orElse(null);

        if (D.getImage() != null) {
            equipeToUpdate.setImage(D.getImage());
        }
        if (D.getType()  != null) {
            equipeToUpdate.setType(D.getType());
        }
        if (D.getUser() !=null) {
            equipeToUpdate.setUser(D.getUser());
        }
        if (D.getPost() != null ) {
            equipeToUpdate.setPost(D.getPost());
        }


        return donRepository.save(equipeToUpdate);

    }

    @Override
    public List<Don> retrieveAllDons() {
        return donRepository.findAll();
    }

    @Override
    public Don retrieveDonById(Long id) {return donRepository.findById(id).orElse(null);
    }

    @Override
    public void deletedon(Long id) {
        donRepository.deleteById(id);

    }

    @Override
    public Don assigneDontoPost(Long idon, Long idpost) {

        Post post = postRespository.findById(idpost).orElse(null);
        Don don = donRepository.findById(idon).orElse(null);
        if (don == null) {
            throw new IllegalArgumentException("Don not found");
        }

        if (post == null) {
            throw new IllegalArgumentException("post not found");
        }
        else {
            don.setPost(post);
            return donRepository.save(don);

        }




        }

    @Override
    public Map<String, Integer> getDonationStats() { List<Don> donations = donRepository.findAll();

        int clothersCount = 0;
        int schoolToolsCount = 0;
        int moneyCount = 0;
        int foodCount = 0;

        for (Don donation : donations) {
            switch (donation.getType()) {
                case Clothers:
                    clothersCount++;
                    break;
                case SchoolTools:
                    schoolToolsCount++;
                    break;
                case Money:
                    moneyCount++;
                    break;
                case Food:
                    foodCount++;
                    break;
                default:
                    break;
            }
        }

        Map<String, Integer> stats = new HashMap<>();
        stats.put("clothersCount", clothersCount);
        stats.put("schoolToolsCount", schoolToolsCount);
        stats.put("moneyCount", moneyCount);
        stats.put("foodCount", foodCount);

        return stats;
    }

    public Don addDonAndAssignToPost(Don D, Long idpost) {
        Optional<Post> postOptional = postRespository.findById(idpost);
        Post post = postOptional.get();
        D.setPost(post);
        return donRepository.save(D);
    }

    @Override
    public List<Don> retrieveDonsByPostId(Long idPost) {
        Optional<Post> post = postRespository.findById(idPost);
        if (post.isPresent()) {
            return donRepository.findByPostId(idPost);
        } else {
            return null;
        }
    }

    @Override
    public List<Don> findDonsByUserId(Long userId) {
        return donRepository.findDonsByUserId(userId);
    }
    @Override
    public List<Don> retrieveDonsByUserId(Long userId) {
        return donRepository.findDonsByUserId(userId);
    }

    @Override
    public List<Don> getDonsByUserId(Long userId) {
        return donRepository.findByUserId(userId);
    }

    @Override
    public Don addDonToPost(Long postId, Don don) {
        Optional<Post> optionalPost = postRespository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            don.setPost(post);
            return donRepository.save(don);
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

}







