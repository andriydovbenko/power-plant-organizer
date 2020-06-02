package com.electricity.service.registration.impl;

import com.electricity.model.plant.PowerPlant;
import com.electricity.model.user.User;
import com.electricity.repository.PowerPlantRepository;
import com.electricity.repository.UserRepository;
import com.electricity.repository.init.PowerPlantInitializer;
import com.electricity.service.registration.RegistrationService;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RegistrationServiceImpl implements RegistrationService {
    private final AtomicReference<UserRepository> userRepoAtomicRef;
    private final List<PowerPlant> powerPlants;

    public RegistrationServiceImpl(AtomicReference<UserRepository> userRepoAtomicRef) {
        this.userRepoAtomicRef = userRepoAtomicRef;
        powerPlants = PowerPlantInitializer.getInitialPowerPlants();
    }

    @Override
    public void register(User user) {
        userRepoAtomicRef.get().insertUser(user);

        PowerPlantRepository powerPlantRepository = new PowerPlantRepository(user.getTableName());

        powerPlantRepository.createTableAndDropIfExist();
        powerPlantRepository.insertAllPlantsInNewTable(powerPlants);
    }
}