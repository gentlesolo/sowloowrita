package com.sowloo.sowloowrita.service.emailcampaign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sowloo.sowloowrita.data.dto.EmailcampaignDto;
import com.sowloo.sowloowrita.data.models.Emailcampaign;
import com.sowloo.sowloowrita.data.repository.EmailcampaignRepository;
import com.sowloo.sowloowrita.service.emailcampaign.EmailcampaignService;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.EmailcampaignDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailcampaignServiceImpl implements EmailcampaignService {

    @Autowired
    private EmailcampaignRepository emailcampaignRepository;

//    @Autowired
//    private CloudService cloudService;

    @Override
    public Emailcampaign findEmailcampaignById(Long emailcampaignId) throws EmailcampaignDoesNotExistException {

        if (emailcampaignId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Emailcampaign> queryResult = emailcampaignRepository.findById(emailcampaignId);

        return queryResult.orElseThrow(()->
                new EmailcampaignDoesNotExistException("Emailcampaign with Id:" + emailcampaignId+ "Does not exist"));

    }

    @Override
    public Emailcampaign findEmailcampaignByIndustry(String industry) throws EmailcampaignDoesNotExistException {

        if (industry == null){
            throw new IllegalArgumentException("Industry cannot be null");
        }
        Optional<Emailcampaign> queryResult = emailcampaignRepository.findByIndustry(industry);

        return queryResult.orElseThrow(()->
                new EmailcampaignDoesNotExistException("Emailcampaign with Id:" + industry + "Does not exist"));

    }

    @Override
    public List<Emailcampaign> getAllEmailcampaigns() {
        return emailcampaignRepository.findAll();
    }

    @Override
    public Emailcampaign createEmailcampaign(EmailcampaignDto emailcampaignDto) {

        if (emailcampaignDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }

        Optional<Emailcampaign> query = emailcampaignRepository.findByName(emailcampaignDto.getName());
        if (query.isPresent()){
            throw new BusinessLogicException("Emailcampaign with name " + emailcampaignDto.getName()+ "already exist");
        }
        Emailcampaign emailcampaign = new Emailcampaign();

//        try {
//            if (emailcampaignDto.getImage() != null){
//                Map<?,?> uploadResult = cloudService.upload(emailcampaignDto.getImage().getBytes(), ObjectUtils.asMap(
//                        "public_id",
//                        "inventory/" + emailcampaignDto.getImage().getOriginalFilename(),
//                        "overwrite", true
//                ));
//                emailcampaign.setImageUrl(uploadResult.get("url").toString());
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        emailcampaign.setName(emailcampaignDto.getName());
        emailcampaign.setDescription(emailcampaignDto.getDescription());
        emailcampaign.setPrice(emailcampaignDto.getPrice());
        emailcampaign.setIndustry(emailcampaignDto.getIndustry());

        return emailcampaignRepository.save(emailcampaign);
    }

    @Override
    public Emailcampaign updateEmailcampaign(Long emailcampaignId, EmailcampaignDto emailcampaignDetails) {

        Emailcampaign updatedEmailcampaign = emailcampaignRepository.findById(emailcampaignId).orElseThrow(()->
                new EmailcampaignDoesNotExistException("emailcampaign with " + emailcampaignId + "does not exist" ));

        updatedEmailcampaign.setName(emailcampaignDetails.getName());
        updatedEmailcampaign.setIndustry(emailcampaignDetails.getIndustry());
        updatedEmailcampaign.setDescription(emailcampaignDetails.getDescription());
        updatedEmailcampaign.setPrice(emailcampaignDetails.getPrice());

        return emailcampaignRepository.save(updatedEmailcampaign);
    }

    private Emailcampaign saveOrUpdateEmailcampaign(Emailcampaign emailcampaign){
        if (emailcampaign == null){
            throw new BusinessLogicException("Emailcampaign cannot be null");
        }
        return emailcampaignRepository.save(emailcampaign);
    }

    @Override
    public Emailcampaign updateEmailcampaignDetails(Long emailcampaignId, JsonPatch patch) {
        Optional<Emailcampaign> emailcampaignQuery = emailcampaignRepository.findById(emailcampaignId);
        if (emailcampaignQuery.isEmpty()){
            throw new BusinessLogicException("Emailcampaign with Id" + emailcampaignId + "Does not exist");
        }
        Emailcampaign updatedEmailcampaign = emailcampaignQuery.get();

        try{
            updatedEmailcampaign = applyPatchToEmailcampaign(patch , updatedEmailcampaign);
            return saveOrUpdateEmailcampaign(updatedEmailcampaign);
        }catch (JsonPatchException | JsonProcessingException je){
            throw new BusinessLogicException("Emailcampaign update Failed");
        }

    }

    private Emailcampaign applyPatchToEmailcampaign(JsonPatch patch, Emailcampaign updatedEmailcampaign) throws JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched =  patch.apply(objectMapper.convertValue(updatedEmailcampaign, JsonNode.class));

        return objectMapper.treeToValue(patched, Emailcampaign.class);
    }
}
