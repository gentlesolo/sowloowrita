package com.sowloo.sowloowrita.service.socialmedia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sowloo.sowloowrita.data.dto.SocialmediaDto;
import com.sowloo.sowloowrita.data.models.Socialmedia;
import com.sowloo.sowloowrita.data.repository.SocialmediaRepository;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.SocialmediaDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialmediaServiceImpl implements SocialmediaService {

    @Autowired
    private SocialmediaRepository socialmediaRepository;

//    @Autowired
//    private CloudService cloudService;

    @Override
    public Socialmedia findSocialmediaById(Long socialmediaId) throws SocialmediaDoesNotExistException {

        if (socialmediaId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Socialmedia> queryResult = socialmediaRepository.findById(socialmediaId);

        return queryResult.orElseThrow(()->
                new SocialmediaDoesNotExistException("Socialmedia with Id:" + socialmediaId+ "Does not exist"));

    }

    @Override
    public Socialmedia findSocialmediaByIndustry(String industry) throws SocialmediaDoesNotExistException {

        if (industry == null){
            throw new IllegalArgumentException("Industry cannot be null");
        }
        Optional<Socialmedia> queryResult = socialmediaRepository.findByIndustry(industry);

        return queryResult.orElseThrow(()->
                new SocialmediaDoesNotExistException("Socialmedia with Id:" + industry + "Does not exist"));

    }

    @Override
    public List<Socialmedia> getAllSocialmedias(Pageable pageable) {
        return socialmediaRepository.findAll(pageable).getContent();
    }

    @Override
    public Socialmedia createSocialmedia(SocialmediaDto socialmediaDto) {

        if (socialmediaDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }

        Optional<Socialmedia> query = socialmediaRepository.findByName(socialmediaDto.getName());
        if (query.isPresent()){
            throw new BusinessLogicException("Socialmedia with name " + socialmediaDto.getName()+ "already exist");
        }
        Socialmedia socialmedia = new Socialmedia();

//        try {
//            if (socialmediaDto.getImage() != null){
//                Map<?,?> uploadResult = cloudService.upload(socialmediaDto.getImage().getBytes(), ObjectUtils.asMap(
//                        "public_id",
//                        "inventory/" + socialmediaDto.getImage().getOriginalFilename(),
//                        "overwrite", true
//                ));
//                socialmedia.setImageUrl(uploadResult.get("url").toString());
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        socialmedia.setName(socialmediaDto.getName());
        socialmedia.setDescription(socialmediaDto.getDescription());
        socialmedia.setPrice(socialmediaDto.getPrice());
        socialmedia.setIndustry(socialmediaDto.getIndustry());

        return socialmediaRepository.save(socialmedia);
    }

    @Override
    public Socialmedia updateSocialmedia(Long socialmediaId, SocialmediaDto socialmediaDetails) {

        Socialmedia updatedSocialmedia = socialmediaRepository.findById(socialmediaId).orElseThrow(()->
                new SocialmediaDoesNotExistException("socialmedia with " + socialmediaId + "does not exist" ));

        updatedSocialmedia.setName(socialmediaDetails.getName());
        updatedSocialmedia.setIndustry(socialmediaDetails.getIndustry());
        updatedSocialmedia.setDescription(socialmediaDetails.getDescription());
        updatedSocialmedia.setPrice(socialmediaDetails.getPrice());

        return socialmediaRepository.save(updatedSocialmedia);
    }

    private Socialmedia saveOrUpdateSocialmedia(Socialmedia socialmedia){
        if (socialmedia == null){
            throw new BusinessLogicException("Socialmedia cannot be null");
        }
        return socialmediaRepository.save(socialmedia);
    }

    @Override
    public Socialmedia updateSocialmediaDetails(Long socialmediaId, JsonPatch patch) {
        Optional<Socialmedia> socialmediaQuery = socialmediaRepository.findById(socialmediaId);
        if (socialmediaQuery.isEmpty()){
            throw new BusinessLogicException("Socialmedia with Id" + socialmediaId + "Does not exist");
        }
        Socialmedia updatedSocialmedia = socialmediaQuery.get();

        try{
            updatedSocialmedia = applyPatchToSocialmedia(patch , updatedSocialmedia);
            return saveOrUpdateSocialmedia(updatedSocialmedia);
        }catch (JsonPatchException | JsonProcessingException je){
            throw new BusinessLogicException("Socialmedia update Failed");
        }

    }

    private Socialmedia applyPatchToSocialmedia(JsonPatch patch, Socialmedia updatedSocialmedia) throws JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched =  patch.apply(objectMapper.convertValue(updatedSocialmedia, JsonNode.class));

        return objectMapper.treeToValue(patched, Socialmedia.class);
    }
}
