package com.codegym.case4.service.Request;

import com.codegym.case4.model.Request;
import com.codegym.case4.repository.IRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestServiceImpl implements IRequestService{
    @Autowired
    IRequestRepository requestRepository;

    @Override
    public Page<Request> findAll(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Page<Request> findAllByRequestStatusIs0(Pageable pageable) {
        return requestRepository.findAllByRequestStatusIs0(pageable);
    }

    @Override
    public Page<Request> findAllByRequestStatusIs1(Pageable pageable) {
        return requestRepository.findAllByRequestStatusIs1(pageable);
    }

    @Override
    public Page<Request> findAllByRequestStatusIs2(Pageable pageable) {
        return requestRepository.findAllByRequestStatusIs2(pageable);
    }

    @Override
    public Page<Request> findAllByRequestStatusIs3(Pageable pageable) {
        return requestRepository.findAllByRequestStatusIs3(pageable);
    }

    @Override
    public Page<Request> findEachUserRequest(Long id, Pageable pageable) {
        return requestRepository.findEachUserRequest(id,pageable);
    }
}
