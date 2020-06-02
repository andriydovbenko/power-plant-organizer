package com.electricity.service.operation.user;

import com.electricity.exeption.InsufficientFundsException;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.plant.PowerPlantPriceSetterService;
import com.electricity.service.plant.impl.PowerPlantPriceSetterServiceImpl;
import com.electricity.service.operation.plant.PowerPlantOperationManager;
import com.electricity.service.market.ResourceMarket;
import com.electricity.service.market.impl.ResourceMarketImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserProcessOperationManager {
    private static final Logger LOGGER = LogManager.getLogger(UserProcessOperationManager.class);
    private static final int INITIAL_DELAY = 0;
    private static final int REFRESH_TIME = 2;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final int POOL_SIZE = 1;
    private final ScheduledExecutorService userScheduledService;
    private final ResourceMarket resourceMarket;
    private final PowerPlantPriceSetterService priceSetterService;
    private final UserProcessOperationThread userProcessOperationThread;
    private PowerPlantOperationManager powerPlantOperationManager;

    public UserProcessOperationManager(UserRepository userRepository, User user) {
        this.userScheduledService = Executors.newScheduledThreadPool(POOL_SIZE);
        this.resourceMarket = new ResourceMarketImpl();
        this.priceSetterService = new PowerPlantPriceSetterServiceImpl();
        this.userProcessOperationThread = new UserProcessOperationThread(userRepository, user);
        userScheduledService.scheduleWithFixedDelay(userProcessOperationThread, INITIAL_DELAY, REFRESH_TIME, TIME_UNIT);
    }

    public void injectPowerPlantExecutorReference(PowerPlantOperationManager powerPlantOperationManager) {
        this.powerPlantOperationManager = powerPlantOperationManager;
    }

    public void start(){
        userProcessOperationThread.start();
    }

    public User getUser(){
        return userProcessOperationThread.getUser();
    }

    public void buyResourceUsingTransaction(ResourceTransaction resourceTransaction) {
        ResourceTransaction processedByMarket = resourceMarket.setPriceOfTransaction(resourceTransaction);

        if (resourceTransaction.getStorableResource() != null) {
            payForTheTransaction(processedByMarket);
        }
    }

    private void payForTheTransaction(ResourceTransaction processedByMarket) {

        try {
            userProcessOperationThread.payOff(processedByMarket.getTransactionPrice());
            powerPlantOperationManager.setResourceUsingDeliveryService(processedByMarket);
        } catch (InsufficientFundsException e) {
            makeLog(e);
        }
    }

    public void buyPowerPlant(PowerPlantCreatingDto powerPlantCreatingDto) {
        BigDecimal powerPlantCost = priceSetterService.getPowerPlantCost(powerPlantCreatingDto.getType());

        try {
            userProcessOperationThread.payOff(powerPlantCost);
            powerPlantOperationManager.createPowerPlantAndAddToThread(powerPlantCreatingDto);
        } catch (InsufficientFundsException e) {
            makeLog(e);
        }
    }

    private void makeLog(InsufficientFundsException e) {
        LOGGER.error(e);
    }

    public void stopAndSave() {
        userProcessOperationThread.stopAndSave();

        userScheduledService.shutdown();
    }
}