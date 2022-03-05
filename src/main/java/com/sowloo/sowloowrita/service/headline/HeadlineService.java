package com.sowloo.sowloowrita.service.headline;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.HeadlineDto;
import com.sowloo.sowloowrita.data.models.Headline;
import com.sowloo.sowloowrita.web.exception.HeadlineDoesNotExistException;

import java.util.List;

public interface HeadlineService {
    Headline findHeadlineById(Long headlineId)throws HeadlineDoesNotExistException;

    Headline findHeadlineByIndustry(String industry)throws HeadlineDoesNotExistException;

    List<Headline> getAllHeadlines();

    Headline createHeadline(HeadlineDto headlineDto);

    Headline updateHeadline(Long headlineId, HeadlineDto headlineDetails);

    Headline updateHeadlineDetails(Long headlineId, JsonPatch patch);
}
