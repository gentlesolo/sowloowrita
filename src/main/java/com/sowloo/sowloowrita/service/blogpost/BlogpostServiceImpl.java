package com.sowloo.sowloowrita.service.blogpost;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sowloo.sowloowrita.data.dto.BlogpostDto;
import com.sowloo.sowloowrita.data.models.Blogpost;
import com.sowloo.sowloowrita.data.repository.BlogpostRepository;
import com.sowloo.sowloowrita.service.blogpost.BlogpostService;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.BlogpostDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogpostServiceImpl implements BlogpostService {

    @Autowired
    private BlogpostRepository blogpostRepository;

//    @Autowired
//    private CloudService cloudService;

    @Override
    public Blogpost findBlogpostById(Long blogpostId) throws BlogpostDoesNotExistException {

        if (blogpostId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Blogpost> queryResult = blogpostRepository.findById(blogpostId);

        return queryResult.orElseThrow(()->
                new BlogpostDoesNotExistException("Blogpost with Id:" + blogpostId+ "Does not exist"));

    }

    @Override
    public Blogpost findBlogpostByIndustry(String industry) throws BlogpostDoesNotExistException {

        if (industry == null){
            throw new IllegalArgumentException("Industry cannot be null");
        }
        Optional<Blogpost> queryResult = blogpostRepository.findByIndustry(industry);

        return queryResult.orElseThrow(()->
                new BlogpostDoesNotExistException("Blogpost with Id:" + industry + "Does not exist"));

    }

    @Override
    public List<Blogpost> getAllBlogposts() {
        return blogpostRepository.findAll();
    }

    @Override
    public Blogpost createBlogpost(BlogpostDto blogpostDto) {

        if (blogpostDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }

        Optional<Blogpost> query = blogpostRepository.findByName(blogpostDto.getName());
        if (query.isPresent()){
            throw new BusinessLogicException("Blogpost with name " + blogpostDto.getName()+ "already exist");
        }
        Blogpost blogpost = new Blogpost();

//        try {
//            if (blogpostDto.getImage() != null){
//                Map<?,?> uploadResult = cloudService.upload(blogpostDto.getImage().getBytes(), ObjectUtils.asMap(
//                        "public_id",
//                        "inventory/" + blogpostDto.getImage().getOriginalFilename(),
//                        "overwrite", true
//                ));
//                blogpost.setImageUrl(uploadResult.get("url").toString());
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        blogpost.setName(blogpostDto.getName());
        blogpost.setDescription(blogpostDto.getDescription());
        blogpost.setPrice(blogpostDto.getPrice());
        blogpost.setIndustry(blogpostDto.getIndustry());

        return blogpostRepository.save(blogpost);
    }

    @Override
    public Blogpost updateBlogpost(Long blogpostId, BlogpostDto blogpostDetails) {

        Blogpost updatedBlogpost = blogpostRepository.findById(blogpostId).orElseThrow(()->
                new BlogpostDoesNotExistException("blogpost with " + blogpostId + "does not exist" ));

        updatedBlogpost.setName(blogpostDetails.getName());
        updatedBlogpost.setIndustry(blogpostDetails.getIndustry());
        updatedBlogpost.setDescription(blogpostDetails.getDescription());
        updatedBlogpost.setPrice(blogpostDetails.getPrice());

        return blogpostRepository.save(updatedBlogpost);
    }

    private Blogpost saveOrUpdateBlogpost(Blogpost blogpost){
        if (blogpost == null){
            throw new BusinessLogicException("Blogpost cannot be null");
        }
        return blogpostRepository.save(blogpost);
    }

    @Override
    public Blogpost updateBlogpostDetails(Long blogpostId, JsonPatch patch) {
        Optional<Blogpost> blogpostQuery = blogpostRepository.findById(blogpostId);
        if (blogpostQuery.isEmpty()){
            throw new BusinessLogicException("Blogpost with Id" + blogpostId + "Does not exist");
        }
        Blogpost updatedBlogpost = blogpostQuery.get();

        try{
            updatedBlogpost = applyPatchToBlogpost(patch , updatedBlogpost);
            return saveOrUpdateBlogpost(updatedBlogpost);
        }catch (JsonPatchException | JsonProcessingException je){
            throw new BusinessLogicException("Blogpost update Failed");
        }

    }

    private Blogpost applyPatchToBlogpost(JsonPatch patch, Blogpost updatedBlogpost) throws JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched =  patch.apply(objectMapper.convertValue(updatedBlogpost, JsonNode.class));

        return objectMapper.treeToValue(patched, Blogpost.class);
    }
}
