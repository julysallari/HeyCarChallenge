package heycarlight.services;

import heycarlight.entities.Dealer;
import heycarlight.repositories.DealerRepository;
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
