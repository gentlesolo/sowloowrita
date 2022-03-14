package com.sowloo.sowloowrita.service.paidadvert;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.PaidadvertDto;
import com.sowloo.sowloowrita.data.models.Paidadvert;
import com.sowloo.sowloowrita.web.exception.PaidadvertDoesNotExistException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaidadvertService {
    Paidadvert findPaidadvertById(Long paidadvertId)throws PaidadvertDoesNotExistException;

    Paidadvert findPaidadvertByIndustry(String industry)throws PaidadvertDoesNotExistException;

    List<Paidadvert> getAllPaidadverts(Pageable pageable);

    Paidadvert createPaidadvert(PaidadvertDto paidadvertDto);

    Paidadvert updatePaidadvert(Long paidadvertId, PaidadvertDto paidadvertDetails);

    Paidadvert updatePaidadvertDetails(Long paidadvertId, JsonPatch patch);
}
