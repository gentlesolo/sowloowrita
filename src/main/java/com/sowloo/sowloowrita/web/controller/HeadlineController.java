package com.sowloo.sowloowrita.web.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.ApiResponse;
import com.sowloo.sowloowrita.data.dto.HeadlineDto;
import com.sowloo.sowloowrita.data.models.Headline;
import com.sowloo.sowloowrita.service.headline.HeadlineService;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.HeadlineDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/headline")
@CrossOrigin(methods = RequestMethod.GET)
public class HeadlineController {

    @Autowired
    private HeadlineService headlineService;


    @GetMapping()
    public ResponseEntity<?> findAllHeadlines(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", required = false, defaultValue = "30") int pageSize,
                                              @RequestParam(name = "sort", required = false, defaultValue = "dateCreated") String sortTerm){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortTerm).descending());
        List<Headline> headlines = headlineService.getAllHeadlines(pageable);

        HttpHeaders headers = new HttpHeaders(){
            {
                add("Access-Control-Expose-Headers", "Content-Range");
                add("Content-Range", String.valueOf(headlineService.getHeadlinesCount()));
            }
        };

//        return new ResponseEntity<>(headlines, HttpStatus.OK);
        return ResponseEntity.ok().headers(headers).body(headlines);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createHeadline(@RequestBody HeadlineDto headlineDto){
        try {
            Headline headline = headlineService.createHeadline(headlineDto);
            return new ResponseEntity<>(headline, HttpStatus.CREATED);
        }catch (HeadlineDoesNotExistException | BusinessLogicException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{headlineId}")
    public ResponseEntity<?>findHeadline(@PathVariable Long headlineId){
        try {
            return new ResponseEntity<>(headlineService.findHeadlineById(headlineId), HttpStatus.OK);
        }catch (HeadlineDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Headline does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{industry}")
    public ResponseEntity<?>findHeadline(@PathVariable String industry){
        try {
            return new ResponseEntity<>(headlineService.findHeadlineByIndustry(industry), HttpStatus.OK);
        }catch (HeadlineDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Headline does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{headlineId}")
    public ResponseEntity<?>updateHeadline(@PathVariable Long headlineId, @RequestBody HeadlineDto headlineDetails){

        try{
            return new ResponseEntity<>(headlineService.updateHeadline(headlineId, headlineDetails),HttpStatus.OK );
        }catch (HeadlineDoesNotExistException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/{headlineId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateMyHeadline(@PathVariable Long headlineId, @RequestBody JsonPatch patch){
        try{
            Headline updatedHeadline = headlineService.updateHeadlineDetails(headlineId, patch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedHeadline);
        }catch (BusinessLogicException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
}
