package com.sowloo.sowloowrita.service.headline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sowloo.sowloowrita.data.dto.HeadlineDto;
import com.sowloo.sowloowrita.data.models.Headline;
import com.sowloo.sowloowrita.data.repository.HeadlineRepository;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.HeadlineDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeadlineServiceImpl implements HeadlineService{

    @Autowired
    private HeadlineRepository headlineRepository;

//    @Autowired
//    private CloudService cloudService;

    @Override
    public Headline findHeadlineById(Long headlineId) throws HeadlineDoesNotExistException {

        if (headlineId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Headline> queryResult = headlineRepository.findById(headlineId);

        return queryResult.orElseThrow(()->
                new HeadlineDoesNotExistException("Headline with Id:" + headlineId+ "Does not exist"));

    }

    @Override
    public Headline findHeadlineByIndustry(String industry) throws HeadlineDoesNotExistException {

        if (industry == null){
            throw new IllegalArgumentException("Industry cannot be null");
        }
        Optional<Headline> queryResult = headlineRepository.findByIndustry(industry);

        return queryResult.orElseThrow(()->
                new HeadlineDoesNotExistException("Headline with Id:" + industry + "Does not exist"));

    }

    @Override
    public List<Headline> getAllHeadlines() {
        return headlineRepository.findAll();
    }

    @Override
    public Headline createHeadline(HeadlineDto headlineDto) {

        if (headlineDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }

        Optional<Headline> query = headlineRepository.findByName(headlineDto.getName());
        if (query.isPresent()){
            throw new BusinessLogicException("Headline with name " + headlineDto.getName()+ "already exist");
        }
        Headline headline = new Headline();

//        try {
//            if (headlineDto.getImage() != null){
//                Map<?,?> uploadResult = cloudService.upload(headlineDto.getImage().getBytes(), ObjectUtils.asMap(
//                        "public_id",
//                        "inventory/" + headlineDto.getImage().getOriginalFilename(),
//                        "overwrite", true
//                ));
//                headline.setImageUrl(uploadResult.get("url").toString());
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        headline.setName(headlineDto.getName());
        headline.setDescription(headlineDto.getDescription());
        headline.setPrice(headlineDto.getPrice());
        headline.setIndustry(headlineDto.getIndustry());

        return headlineRepository.save(headline);
    }

    @Override
    public Headline updateHeadline(Long headlineId, HeadlineDto headlineDetails) {

        Headline updatedHeadline = headlineRepository.findById(headlineId).orElseThrow(()->
                new HeadlineDoesNotExistException("headline with " + headlineId + "does not exist" ));

        updatedHeadline.setName(headlineDetails.getName());
        updatedHeadline.setIndustry(headlineDetails.getIndustry());
        updatedHeadline.setDescription(headlineDetails.getDescription());
        updatedHeadline.setPrice(headlineDetails.getPrice());

        return headlineRepository.save(updatedHeadline);
    }

    private Headline saveOrUpdateHeadline(Headline headline){
        if (headline == null){
            throw new BusinessLogicException("Headline cannot be null");
        }
        return headlineRepository.save(headline);
    }

    @Override
    public Headline updateHeadlineDetails(Long headlineId, JsonPatch patch) {
        Optional<Headline> headlineQuery = headlineRepository.findById(headlineId);
        if (headlineQuery.isEmpty()){
            throw new BusinessLogicException("Headline with Id" + headlineId + "Does not exist");
        }
        Headline updatedHeadline = headlineQuery.get();

        try{
            updatedHeadline = applyPatchToHeadline(patch , updatedHeadline);
            return saveOrUpdateHeadline(updatedHeadline);
        }catch (JsonPatchException | JsonProcessingException je){
            throw new BusinessLogicException("Headline update Failed");
        }

    }

    private Headline applyPatchToHeadline(JsonPatch patch, Headline updatedHeadline) throws JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched =  patch.apply(objectMapper.convertValue(updatedHeadline, JsonNode.class));

        return objectMapper.treeToValue(patched, Headline.class);
    }
}
