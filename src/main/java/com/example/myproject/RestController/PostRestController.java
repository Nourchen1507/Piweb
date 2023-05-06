package com.example.myproject.RestController;

import com.example.myproject.Service.IPost;
import com.example.myproject.entities.Don;
import com.example.myproject.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PostRestController {
    @Autowired
    private IPost postser;


    @GetMapping("/post")
    public List<Post> retrieveAllPost() {
        return postser.retrieveAllPosts();
    }


    @GetMapping("/postByID/{id}")
    public Post retrievePostById(@PathVariable("id") Long id) {
        return postser.retrievePostById(id);
    }
    @PostMapping("/addpost")
    public Post addPost(@RequestBody Post p) {

        return postser.addPost(p);
    }


    @PutMapping("/up")
    public Post updatePost(@RequestBody Post p) {
        return postser.updatePost(p);
    }

    @DeleteMapping("/deletePost/{id}")
    public void deletepost(@PathVariable("id") Long id) {
        postser.deletepost(id);

    }


    @PostMapping("/ass/{idpost}/{iduser}")
    public Post assignposttouser(@PathVariable("idpost") Long idpost, @PathVariable("iduser") Long iduser) {
        return postser.assignePosttoUser(idpost, iduser);
    }

    @GetMapping("/postss")
    public List<Post> chercherParNom(@RequestParam("nom") String nom) {
        return postser.findByNomContaining(nom);
    }

    @PostMapping("/{idpost}/likes/{iduser}")
    public ResponseEntity<Void> createLike(@PathVariable Long idpost, @PathVariable Long iduser) {
        postser.createLike(idpost, iduser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        postser.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idpost}/dislike")
    public ResponseEntity<?> dislikePost(@PathVariable Long idpost, @RequestParam Long userId) {
        postser.dislikePost(idpost, userId);
        return ResponseEntity.ok().build();


    }
    @GetMapping("/posts/user/{id}")
    public List<Post> getPostsByUserId(@PathVariable Long id) {
        return postser.getPostsById(id);
    }
}



