package com.example.myproject.Service;

import com.example.myproject.Repository.IPost;
import com.example.myproject.Repository.PostRespository;
import com.example.myproject.entities.*;
import com.example.myproject.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public   class PostServiceImpl implements IPost {


    @Autowired
    PostRespository Postrepo;
    @Autowired
    UserRepository userRepository;
//@Autowired
//    DonRepositor donRepository ;

    @Override
    public Post addPost(Post P) {
        return Postrepo.save(P);
    }

    @Override
    public Post updatePost(Post P) {
        {
            Post equipeToUpdate = Postrepo.findById(P.getId()).orElse(null);


            if (P.getDescription() != null) {
                equipeToUpdate.setDescription(P.getDescription());

            }
            if (P.getDatecreation() != null) {
                equipeToUpdate.setDatecreation(LocalDate.now());
            }
            if (P.getNom() != null) {
                equipeToUpdate.setNom(P.getNom());
            }
            if (P.getDons() != null) {
                equipeToUpdate.setDons(P.getDons());
            }
            if (P.getUser() != null) {
                equipeToUpdate.setUser(P.getUser());
            }


            return Postrepo.save(equipeToUpdate);

        }
    }

    @Override
    public List<Post> retrieveAllPosts() {
        return Postrepo.findAll();
    }

    @Override
    public Post retrievePostById(Long id) {
        return Postrepo.findById(id).orElse(null);
    }

    @Override
    public void deletepost(Long id) {
        Postrepo.deleteById(id);
    }

    @Override
    public Post assignePosttoUser(Long idpost, Long iduser) {

        Post post = Postrepo.findById(idpost).orElse(null);
        User user = userRepository.findById(iduser).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("user not found");
        }

        if (post == null) {
            throw new IllegalArgumentException("post not found");
        } else {
            post.setUser(user);
            return Postrepo.save(post);

        }

    }

    @Override
    public List<Post> findByNomContaining(String nom) {
        return Postrepo.findByNomContainingIgnoreCase(nom);


    }

    @Override
    public List<Post> getAllPosts() {
        return Postrepo.findAll();
    }


    @Override
    public List<Post> getPostsById(Long id) {
        return Postrepo.findAllByUserId(id);
    }

    @Override
    public void createLike(Long idpost, Long iduser) {
        User user = userRepository.findById(iduser).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Post post = Postrepo.findById(idpost).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);


        post.getLikes().add(like);
        Postrepo.save(post);
    }


    @Override
    public void dislikePost(Long postId, Long userId) {
        Post post = Postrepo.findById(postId).orElse(null);
        if (post == null) {
            throw new IllegalArgumentException("Post not found");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        like.setLikeType(LikeType.DISLIKE);

        post.getLikes().add(like);
        Postrepo.save(post);

    }




    @Override
    public void likePost(Long postId, Long userId) {

        Post post =   Postrepo.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        if (post == null) {
            throw new IllegalArgumentException("Post not found");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }


        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setLikeType(LikeType.LIKE);

        post.getLikes().add(like);
        Postrepo.save(post);

    }




}
