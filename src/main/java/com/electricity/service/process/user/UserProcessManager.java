package com.electricity.service.process.user;

import com.electricity.exeption.InsufficientFundsException;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepositoryManager;
import com.electricity.service.plant.PowerPlantPriceSettingService;
import com.electricity.service.plant.impl.PowerPlantPriceSettingServiceImpl;
import com.electricity.service.process.plant.PowerPlantProcessManager;
import com.electricity.service.market.ResourceMarket;
import com.electricity.service.market.impl.ResourceMarketImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserProcessManager {
    private static final Logger LOGGER = LogManager.getLogger(UserProcessManager.class);

    private static final int INITIAL_DELAY = 0;
    private static final int REFRESH_TIME = 2;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final int POOL_SIZE = 1;

    private final ScheduledExecutorService userScheduledService;
    private final ResourceMarket resourceMarket;
    private final PowerPlantPriceSettingService priceSettingService;

    private final UserProcessThread userProcessThread;
    private PowerPlantProcessManager powerPlantProcessManager;

    public UserProcessManager(UserRepositoryManager repositoryManager, User user) {
        this.userScheduledService = Executors.newScheduledThreadPool(POOL_SIZE);
        this.resourceMarket = new ResourceMarketImpl();
        this.priceSettingService = new PowerPlantPriceSettingServiceImpl();
        this.userProcessThread = new UserProcessThread(repositoryManager, user);
        userScheduledService.scheduleWithFixedDelay(userProcessThread, INITIAL_DELAY, REFRESH_TIME, TIME_UNIT);

    }

    public void injectPowerPlantExecutorReference(PowerPlantProcessManager powerPlantProcessManager) {
        this.powerPlantProcessManager = powerPlantProcessManager;
    }

    public void buyResourceUsingTransaction(ResourceTransaction resourceTransaction) {
        ResourceTransaction processedByMarket = resourceMarket.setPriceOfTransaction(resourceTransaction);

        if (resourceTransaction.getStorableResource() != null) {
            payForTheTransaction(processedByMarket);
        }
    }

    private void payForTheTransaction(ResourceTransaction processedByMarket) {

        try {
            userProcessThread.payOff(processedByMarket.getTransactionPrice());
            powerPlantProcessManager.setResourceUsingDeliveryService(processedByMarket);
        } catch (InsufficientFundsException e) {
            makeLog(e);
        }
    }

    public void buyPowerPlant(PowerPlantCreatingDto powerPlantCreatingDto) {
        BigDecimal powerPlantCost = priceSettingService.getPowerPlantCost(powerPlantCreatingDto.getType());

        try {
            userProcessThread.payOff(powerPlantCost);
            powerPlantProcessManager.createPowerPlantAndAddToThread(powerPlantCreatingDto);
        } catch (InsufficientFundsException e) {
            makeLog(e);
        }
    }

    private void makeLog(InsufficientFundsException e) {
        LOGGER.error(e);
    }

    public void stopAndSave() {
        userProcessThread.stopAndSave();
    }

    public void start(){
        userProcessThread.start();
    }

    public void shutdownScheduledService(){
        userScheduledService.shutdown();
    }
}