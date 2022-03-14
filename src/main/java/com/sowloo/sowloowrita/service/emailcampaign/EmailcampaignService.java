package com.sowloo.sowloowrita.service.emailcampaign;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.EmailcampaignDto;
import com.sowloo.sowloowrita.data.models.Emailcampaign;
import com.sowloo.sowloowrita.web.exception.EmailcampaignDoesNotExistException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmailcampaignService {
    Emailcampaign findEmailcampaignById(Long emailcampaignId)throws EmailcampaignDoesNotExistException;

    Emailcampaign findEmailcampaignByIndustry(String industry)throws EmailcampaignDoesNotExistException;

    List<Emailcampaign> getAllEmailcampaigns(Pageable pageable);

    Emailcampaign createEmailcampaign(EmailcampaignDto emailcampaignDto);

    Emailcampaign updateEmailcampaign(Long emailcampaignId, EmailcampaignDto emailcampaignDetails);

    Emailcampaign updateEmailcampaignDetails(Long emailcampaignId, JsonPatch patch);
}
