package com.codegym.case4.service.Request;

import com.codegym.case4.model.Request;
import com.codegym.case4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sun.security.krb5.internal.PAData;

public interface IRequestService extends IGeneralService<Request> {
    Page<Request> findAllByRequestStatusIs0(Pageable pageable);
    Page<Request> findAllByRequestStatusIs1(Pageable pageable);
    Page<Request> findAllByRequestStatusIs2(Pageable pageable);
    Page<Request> findAllByRequestStatusIs3(Pageable pageable);
    Page<Request> findEachUserRequest(Long id,Pageable pageable);
}
