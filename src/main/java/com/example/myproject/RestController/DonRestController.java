package com.example.myproject.RestController;

import com.example.myproject.Service.IDon;
import com.example.myproject.Service.IPost;
import com.example.myproject.entities.Don;
import com.example.myproject.entities.Post;
import com.example.myproject.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class DonRestController {



    private IDon idon;



    @PostMapping("/addon")
    public Don addDon(@RequestParam("userId") Long userId,
                      @RequestParam("postId") Long postId,
                      @RequestBody Don don) {

        User user = new User();
        user.setId(userId);

        Post post = new Post();
        post.setId(postId);

        don.setUser(user);
        don.setPost(post);

        return idon.addDon(don);
    }


    @PutMapping("/updatedon")
    public ResponseEntity<Don> updatePost(@RequestBody Don don) {
        Don updated = idon.updateDon(don);
        return ResponseEntity.ok(updated);


    }

    @GetMapping("/don")
    public List<Don> retrieveAllPost() {
        return idon.retrieveAllDons();
    }


    @DeleteMapping("/deleteDon/{id}")
    public void deletepost(@PathVariable("id") Long id) {
        idon.deletedon(id);
    }


    @PostMapping("/assign/{iddon}/{idpost}")
    public Don assignedontopost(@PathVariable("iddon") Long iddon, @PathVariable("idpost") Long idpost) {
        return idon.assigneDontoPost(iddon, idpost);
    }

    @GetMapping("/stats")
    public Map<String, Integer> getDonationStats() {
        return idon.getDonationStats();
    }

    @PostMapping("/{idPost}")
    public ResponseEntity<Don> addDonAndAssignToPost(@RequestBody Don D, @PathVariable Long idPost) {
        Don don = idon.addDonAndAssignToPost(D, idPost);
        return new ResponseEntity<>(don, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public List<Don> getDonsByPostId(@PathVariable("id") Long id) {
        return idon.retrieveDonsByPostId(id);
    }

    @GetMapping("/dons/{userId}")
    public List<Don> findDonsByUserId(@PathVariable Long userId) {
        return idon.findDonsByUserId(userId);
    }

    @GetMapping("/d/{userId}")
    public List<Don> getDonsByUserId(@PathVariable Long userId) {
        return idon.retrieveDonsByUserId(userId);
    }


    @GetMapping("/x/{userId}")
    public List<Don> getDonsBy(@PathVariable Long userId) {
        return idon.getDonsByUserId(userId);
    }


    @PostMapping("/api/posts/{postId}/dons")
    public Don addDonToPost(@PathVariable Long postId, @RequestBody Don don) {
        return idon.addDonToPost(postId, don);
    }
}
