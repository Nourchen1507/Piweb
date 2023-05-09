package com.example.myproject.Controller;

import com.example.myproject.Repository.IPost;
import com.example.myproject.entities.Don;
import com.example.myproject.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostRestController {
    @Autowired
    private IPost postser;


    @GetMapping("/post")
    @CrossOrigin
    public List<Post> retrieveAllPost() {
        return postser.retrieveAllPosts();
    }


    @GetMapping("/postByID/{id}")
    @CrossOrigin
    public Post retrievePostById(@PathVariable("id") Long id) {
        return postser.retrievePostById(id);
    }
    @PostMapping("/addpost")
    @CrossOrigin
    public Post addPost(@RequestBody Post p) {

        return postser.addPost(p);
    }


    @PutMapping("/up")
    @CrossOrigin
    public Post updatePost(@RequestBody Post p) {
        return postser.updatePost(p);
    }

    @DeleteMapping("/deletePost/{id}")
    @CrossOrigin
    public void deletepost(@PathVariable("id") Long id) {
        postser.deletepost(id);

    }


    @PostMapping("/ass/{idpost}/{iduser}")
    @CrossOrigin
    public Post assignposttouser(@PathVariable("idpost") Long idpost, @PathVariable("iduser") Long iduser) {
        return postser.assignePosttoUser(idpost, iduser);
    }

    @GetMapping("/postss")
    @CrossOrigin
    public List<Post> chercherParNom(@RequestParam("nom") String nom) {
        return postser.findByNomContaining(nom);
    }

    @PostMapping("/{idpost}/likes/{iduser}")
    @CrossOrigin
    public ResponseEntity<Void> createLike(@PathVariable Long idpost, @PathVariable Long iduser) {
        postser.createLike(idpost, iduser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{postId}/like")
    @CrossOrigin
    public ResponseEntity<?> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        postser.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idpost}/dislike")
    @CrossOrigin
    public ResponseEntity<?> dislikePost(@PathVariable Long idpost, @RequestParam Long userId) {
        postser.dislikePost(idpost, userId);
        return ResponseEntity.ok().build();


    }
    @GetMapping("/posts/user/{id}")
    @CrossOrigin
    public List<Post> getPostsByUserId(@PathVariable Long id) {
        return postser.getPostsById(id);
    }
}

