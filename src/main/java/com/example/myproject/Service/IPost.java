package com.example.myproject.Service;

import com.example.myproject.entities.Don;
import com.example.myproject.entities.Post;

import java.util.List;

public interface IPost {




    Post addPost (Post P ) ;
    Post updatePost(Post P);
    List<Post> retrieveAllPosts();

    Post retrievePostById(Long id );
    void deletepost(Long id);

    Post assignePosttoUser (Long idpost , Long iduser );


    List<Post> findByNomContaining (String nom) ;


    List<Post> getAllPosts();
    List<Post> getPostsById(Long id);


  public void createLike(Long idpost , Long iduser);
  void likePost(Long postId, Long userId);
    void dislikePost(Long postId, Long userId);






}
