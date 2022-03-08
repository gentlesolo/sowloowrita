package com.sowloo.sowloowrita.service.blogpost;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.BlogpostDto;
import com.sowloo.sowloowrita.data.models.Blogpost;
import com.sowloo.sowloowrita.web.exception.BlogpostDoesNotExistException;

import java.util.List;

public interface BlogpostService {
    Blogpost findBlogpostById(Long blogpostId)throws BlogpostDoesNotExistException;

    Blogpost findBlogpostByIndustry(String industry)throws BlogpostDoesNotExistException;

    List<Blogpost> getAllBlogposts();

    Blogpost createBlogpost(BlogpostDto blogpostDto);

    Blogpost updateBlogpost(Long blogpostId, BlogpostDto blogpostDetails);

    Blogpost updateBlogpostDetails(Long blogpostId, JsonPatch patch);
}
