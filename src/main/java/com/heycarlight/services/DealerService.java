package com.heycarlight.services;

import com.heycarlight.entities.Dealer;
import com.heycarlight.repositories.DealerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DealerService {

    private static Logger LOGGER = LoggerFactory.getLogger(DealerService.class);

    private DealerRepository dealerRepository;

    public DealerService(@Autowired DealerRepository dealerRepository){
        this.dealerRepository = dealerRepository;
    }

    public Dealer addDealer(Dealer dealer) {
        Dealer created = this.dealerRepository.save(dealer);
        LOGGER.info("Dealer " + dealer.getId() + " created.");
        return created;
    }

    public Optional<Dealer> findById(UUID id) {
        return this.dealerRepository.findById(id);
    }
}
