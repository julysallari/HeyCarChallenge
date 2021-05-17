package com.heycarlight.services;

import com.heycarlight.entities.Dealer;
import com.heycarlight.repositories.DealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DealerServiceImpl implements DealerService{

    @Autowired
    private DealerRepository dealerRepository;

    @Override
    public void addDealer(String name) {
        this.dealerRepository.save(new Dealer(name));
    }

    @Override
    public Optional<Dealer> findById(String id) {
        return this.dealerRepository.findById(UUID.fromString(id));
    }
}
