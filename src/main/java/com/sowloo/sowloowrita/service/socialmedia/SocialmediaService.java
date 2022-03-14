package com.sowloo.sowloowrita.service.socialmedia;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.SocialmediaDto;
import com.sowloo.sowloowrita.data.models.Socialmedia;
import com.sowloo.sowloowrita.web.exception.SocialmediaDoesNotExistException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SocialmediaService {
    Socialmedia findSocialmediaById(Long socialmediaId)throws SocialmediaDoesNotExistException;

    Socialmedia findSocialmediaByIndustry(String industry)throws SocialmediaDoesNotExistException;

    List<Socialmedia> getAllSocialmedias(Pageable pageable);

    Socialmedia createSocialmedia(SocialmediaDto socialmediaDto);

    Socialmedia updateSocialmedia(Long socialmediaId, SocialmediaDto socialmediaDetails);

    Socialmedia updateSocialmediaDetails(Long socialmediaId, JsonPatch patch);
}
