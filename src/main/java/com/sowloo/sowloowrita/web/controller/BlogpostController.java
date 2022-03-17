package com.sowloo.sowloowrita.web.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.ApiResponse;
import com.sowloo.sowloowrita.data.dto.BlogpostDto;
import com.sowloo.sowloowrita.data.models.Blogpost;
import com.sowloo.sowloowrita.service.blogpost.BlogpostService;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.BlogpostDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/blogpost")
//@CrossOrigin(origins = "*")
public class BlogpostController {

    @Autowired
    private BlogpostService blogpostService;


    @GetMapping()
    public ResponseEntity<?> findAllBlogposts(){
        List<Blogpost> blogposts = blogpostService.getAllBlogposts();
        return new ResponseEntity<>(blogposts, HttpStatus.OK);
        //return ResponseEntity.ok().body(blogposts);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createBlogpost(@RequestBody BlogpostDto blogpostDto){
        try {
            Blogpost blogpost = blogpostService.createBlogpost(blogpostDto);
            return new ResponseEntity<>(blogpost, HttpStatus.CREATED);
        }catch (BlogpostDoesNotExistException | BusinessLogicException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{blogpostId}")
    public ResponseEntity<?>findBlogpost(@PathVariable Long blogpostId){
        try {
            return new ResponseEntity<>(blogpostService.findBlogpostById(blogpostId), HttpStatus.OK);
        }catch (BlogpostDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Blogpost does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{industry}")
    public ResponseEntity<?>findBlogpost(@PathVariable String industry){
        try {
            return new ResponseEntity<>(blogpostService.findBlogpostByIndustry(industry), HttpStatus.OK);
        }catch (BlogpostDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Blogpost does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{blogpostId}")
    public ResponseEntity<?>updateBlogpost(@PathVariable Long blogpostId, @RequestBody BlogpostDto blogpostDetails){

        try{
            return new ResponseEntity<>(blogpostService.updateBlogpost(blogpostId, blogpostDetails),HttpStatus.OK );
        }catch (BlogpostDoesNotExistException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/{blogpostId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateMyBlogpost(@PathVariable Long blogpostId, @RequestBody JsonPatch patch){
        try{
            Blogpost updatedBlogpost = blogpostService.updateBlogpostDetails(blogpostId, patch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedBlogpost);
        }catch (BusinessLogicException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
}
