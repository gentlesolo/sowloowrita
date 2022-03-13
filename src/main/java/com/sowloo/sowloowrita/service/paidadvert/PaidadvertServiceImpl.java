package com.sowloo.sowloowrita.service.paidadvert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sowloo.sowloowrita.data.dto.PaidadvertDto;
import com.sowloo.sowloowrita.data.models.Paidadvert;
import com.sowloo.sowloowrita.data.repository.PaidadvertRepository;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.PaidadvertDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaidadvertServiceImpl implements PaidadvertService {

    @Autowired
    private PaidadvertRepository paidadvertRepository;

//    @Autowired
//    private CloudService cloudService;

    @Override
    public Paidadvert findPaidadvertById(Long paidadvertId) throws PaidadvertDoesNotExistException {

        if (paidadvertId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Paidadvert> queryResult = paidadvertRepository.findById(paidadvertId);

        return queryResult.orElseThrow(()->
                new PaidadvertDoesNotExistException("Paidadvert with Id:" + paidadvertId+ "Does not exist"));

    }

    @Override
    public Paidadvert findPaidadvertByIndustry(String industry) throws PaidadvertDoesNotExistException {

        if (industry == null){
            throw new IllegalArgumentException("Industry cannot be null");
        }
        Optional<Paidadvert> queryResult = paidadvertRepository.findByIndustry(industry);

        return queryResult.orElseThrow(()->
                new PaidadvertDoesNotExistException("Paidadvert with Id:" + industry + "Does not exist"));

    }

    @Override
    public List<Paidadvert> getAllPaidadverts(Pageable pageable) {
        return paidadvertRepository.findAll(pageable).getContent();
    }

    @Override
    public Paidadvert createPaidadvert(PaidadvertDto paidadvertDto) {

        if (paidadvertDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }

        Optional<Paidadvert> query = paidadvertRepository.findByName(paidadvertDto.getName());
        if (query.isPresent()){
            throw new BusinessLogicException("Paidadvert with name " + paidadvertDto.getName()+ "already exist");
        }
        Paidadvert paidadvert = new Paidadvert();

//        try {
//            if (paidadvertDto.getImage() != null){
//                Map<?,?> uploadResult = cloudService.upload(paidadvertDto.getImage().getBytes(), ObjectUtils.asMap(
//                        "public_id",
//                        "inventory/" + paidadvertDto.getImage().getOriginalFilename(),
//                        "overwrite", true
//                ));
//                paidadvert.setImageUrl(uploadResult.get("url").toString());
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        paidadvert.setName(paidadvertDto.getName());
        paidadvert.setDescription(paidadvertDto.getDescription());
        paidadvert.setPrice(paidadvertDto.getPrice());
        paidadvert.setIndustry(paidadvertDto.getIndustry());

        return paidadvertRepository.save(paidadvert);
    }

    @Override
    public Paidadvert updatePaidadvert(Long paidadvertId, PaidadvertDto paidadvertDetails) {

        Paidadvert updatedPaidadvert = paidadvertRepository.findById(paidadvertId).orElseThrow(()->
                new PaidadvertDoesNotExistException("paidadvert with " + paidadvertId + "does not exist" ));

        updatedPaidadvert.setName(paidadvertDetails.getName());
        updatedPaidadvert.setIndustry(paidadvertDetails.getIndustry());
        updatedPaidadvert.setDescription(paidadvertDetails.getDescription());
        updatedPaidadvert.setPrice(paidadvertDetails.getPrice());

        return paidadvertRepository.save(updatedPaidadvert);
    }

    private Paidadvert saveOrUpdatePaidadvert(Paidadvert paidadvert){
        if (paidadvert == null){
            throw new BusinessLogicException("Paidadvert cannot be null");
        }
        return paidadvertRepository.save(paidadvert);
    }

    @Override
    public Paidadvert updatePaidadvertDetails(Long paidadvertId, JsonPatch patch) {
        Optional<Paidadvert> paidadvertQuery = paidadvertRepository.findById(paidadvertId);
        if (paidadvertQuery.isEmpty()){
            throw new BusinessLogicException("Paidadvert with Id" + paidadvertId + "Does not exist");
        }
        Paidadvert updatedPaidadvert = paidadvertQuery.get();

        try{
            updatedPaidadvert = applyPatchToPaidadvert(patch , updatedPaidadvert);
            return saveOrUpdatePaidadvert(updatedPaidadvert);
        }catch (JsonPatchException | JsonProcessingException je){
            throw new BusinessLogicException("Paidadvert update Failed");
        }

    }

    private Paidadvert applyPatchToPaidadvert(JsonPatch patch, Paidadvert updatedPaidadvert) throws JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched =  patch.apply(objectMapper.convertValue(updatedPaidadvert, JsonNode.class));

        return objectMapper.treeToValue(patched, Paidadvert.class);
    }
}
