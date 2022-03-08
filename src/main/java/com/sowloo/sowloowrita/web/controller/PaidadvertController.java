package com.sowloo.sowloowrita.web.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.ApiResponse;
import com.sowloo.sowloowrita.data.dto.PaidadvertDto;
import com.sowloo.sowloowrita.data.models.Paidadvert;
import com.sowloo.sowloowrita.service.paidadvert.PaidadvertService;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.PaidadvertDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/paidadvert")
//@CrossOrigin(origins = "*")
public class PaidadvertController {

    @Autowired
    private PaidadvertService paidadvertService;



    @GetMapping()
    public ResponseEntity<?> findAllPaidadverts(){
        List<Paidadvert> paidadverts = paidadvertService.getAllPaidadverts();
        return new ResponseEntity<>(paidadverts, HttpStatus.OK);
        //return ResponseEntity.ok().body(paidadverts);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createPaidadvert(@RequestBody PaidadvertDto paidadvertDto){
        try {
            Paidadvert paidadvert = paidadvertService.createPaidadvert(paidadvertDto);
            return new ResponseEntity<>(paidadvert, HttpStatus.CREATED);
        }catch (PaidadvertDoesNotExistException | BusinessLogicException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{paidadvertId}")
    public ResponseEntity<?>findPaidadvert(@PathVariable Long paidadvertId){
        try {
            return new ResponseEntity<>(paidadvertService.findPaidadvertById(paidadvertId), HttpStatus.OK);
        }catch (PaidadvertDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Paidadvert does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{industry}")
    public ResponseEntity<?>findPaidadvert(@PathVariable String industry){
        try {
            return new ResponseEntity<>(paidadvertService.findPaidadvertByIndustry(industry), HttpStatus.OK);
        }catch (PaidadvertDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Paidadvert does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{paidadvertId}")
    public ResponseEntity<?>updatePaidadvert(@PathVariable Long paidadvertId, @RequestBody PaidadvertDto paidadvertDetails){

        try{
            return new ResponseEntity<>(paidadvertService.updatePaidadvert(paidadvertId, paidadvertDetails),HttpStatus.OK );
        }catch (PaidadvertDoesNotExistException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/{paidadvertId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateMyPaidadvert(@PathVariable Long paidadvertId, @RequestBody JsonPatch patch){
        try{
            Paidadvert updatedPaidadvert = paidadvertService.updatePaidadvertDetails(paidadvertId, patch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPaidadvert);
        }catch (BusinessLogicException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
}
