package com.sowloo.sowloowrita.web.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.ApiResponse;
import com.sowloo.sowloowrita.data.dto.SocialmediaDto;
import com.sowloo.sowloowrita.data.models.Socialmedia;
import com.sowloo.sowloowrita.service.socialmedia.SocialmediaService;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.SocialmediaDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/socialmedia")
//@CrossOrigin(origins = "*")
public class SocialmediaController {

    @Autowired
    private SocialmediaService socialmediaService;


    @GetMapping()
    public ResponseEntity<?> findAllSocialmedias(){
        List<Socialmedia> socialmedias = socialmediaService.getAllSocialmedias();
        return new ResponseEntity<>(socialmedias, HttpStatus.OK);
        //return ResponseEntity.ok().body(socialmedias);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createSocialmedia(@RequestBody SocialmediaDto socialmediaDto){
        try {
            Socialmedia socialmedia = socialmediaService.createSocialmedia(socialmediaDto);
            return new ResponseEntity<>(socialmedia, HttpStatus.CREATED);
        }catch (SocialmediaDoesNotExistException | BusinessLogicException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{socialmediaId}")
    public ResponseEntity<?>findSocialmedia(@PathVariable Long socialmediaId){
        try {
            return new ResponseEntity<>(socialmediaService.findSocialmediaById(socialmediaId), HttpStatus.OK);
        }catch (SocialmediaDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Socialmedia does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{industry}")
    public ResponseEntity<?>findSocialmedia(@PathVariable String industry){
        try {
            return new ResponseEntity<>(socialmediaService.findSocialmediaByIndustry(industry), HttpStatus.OK);
        }catch (SocialmediaDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Socialmedia does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{socialmediaId}")
    public ResponseEntity<?>updateSocialmedia(@PathVariable Long socialmediaId, @RequestBody SocialmediaDto socialmediaDetails){

        try{
            return new ResponseEntity<>(socialmediaService.updateSocialmedia(socialmediaId, socialmediaDetails),HttpStatus.OK );
        }catch (SocialmediaDoesNotExistException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/{socialmediaId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateMySocialmedia(@PathVariable Long socialmediaId, @RequestBody JsonPatch patch){
        try{
            Socialmedia updatedSocialmedia = socialmediaService.updateSocialmediaDetails(socialmediaId, patch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSocialmedia);
        }catch (BusinessLogicException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
}
