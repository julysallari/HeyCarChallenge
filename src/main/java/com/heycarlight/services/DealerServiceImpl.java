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
    public void addDealer(Dealer dealer) {
        this.dealerRepository.save(dealer);
    }

    @Override
    public Optional<Dealer> findById(UUID id) {
        return this.dealerRepository.findById(id);
    }
}
