package com.sowloo.sowloowrita.web.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.ApiResponse;
import com.sowloo.sowloowrita.data.dto.EmailcampaignDto;
import com.sowloo.sowloowrita.data.models.Emailcampaign;
import com.sowloo.sowloowrita.service.emailcampaign.EmailcampaignService;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.EmailcampaignDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/emailcampaign")
//@CrossOrigin(origins = "*")
public class EmailcampaignController {

    @Autowired
    private EmailcampaignService emailcampaignService;


    @GetMapping()
    public ResponseEntity<?> findAllEmailcampaigns(){
        List<Emailcampaign> emailcampaigns = emailcampaignService.getAllEmailcampaigns();
        return new ResponseEntity<>(emailcampaigns, HttpStatus.OK);
        //return ResponseEntity.ok().body(emailcampaigns);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createEmailcampaign(@RequestBody EmailcampaignDto emailcampaignDto){
        try {
            Emailcampaign emailcampaign = emailcampaignService.createEmailcampaign(emailcampaignDto);
            return new ResponseEntity<>(emailcampaign, HttpStatus.CREATED);
        }catch (EmailcampaignDoesNotExistException | BusinessLogicException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{emailcampaignId}")
    public ResponseEntity<?>findEmailcampaign(@PathVariable Long emailcampaignId){
        try {
            return new ResponseEntity<>(emailcampaignService.findEmailcampaignById(emailcampaignId), HttpStatus.OK);
        }catch (EmailcampaignDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Emailcampaign does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{industry}")
    public ResponseEntity<?>findEmailcampaign(@PathVariable String industry){
        try {
            return new ResponseEntity<>(emailcampaignService.findEmailcampaignByIndustry(industry), HttpStatus.OK);
        }catch (EmailcampaignDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Emailcampaign does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{emailcampaignId}")
    public ResponseEntity<?>updateEmailcampaign(@PathVariable Long emailcampaignId, @RequestBody EmailcampaignDto emailcampaignDetails){

        try{
            return new ResponseEntity<>(emailcampaignService.updateEmailcampaign(emailcampaignId, emailcampaignDetails),HttpStatus.OK );
        }catch (EmailcampaignDoesNotExistException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/{emailcampaignId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateMyEmailcampaign(@PathVariable Long emailcampaignId, @RequestBody JsonPatch patch){
        try{
            Emailcampaign updatedEmailcampaign = emailcampaignService.updateEmailcampaignDetails(emailcampaignId, patch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmailcampaign);
        }catch (BusinessLogicException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
}
