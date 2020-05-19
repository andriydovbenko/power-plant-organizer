package com.electricity.service.operation.user;

import com.electricity.exeption.InsufficientFundsException;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.operation.plant.PowerPlantOperationThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static com.electricity.enumeration.EnergyCost.ENERGY;

public class UserProcessOperationThread implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(UserProcessOperationThread.class);
    private final UserRepository repositoryManager;
    private final User user;
    private boolean isRunning;
    private final BigDecimal pricePerMegawatt;

    public UserProcessOperationThread(UserRepository repositoryManager, User user) {
        this.repositoryManager = repositoryManager;
        this.user = user;
        this.isRunning = false;
        this.pricePerMegawatt = ENERGY.getCost();
    }

    @Override
    public void run() {
        if (isRunning) {
            BigDecimal newFundsAmount = PowerPlantOperationThread.sellProducedEnergy(pricePerMegawatt)
                    .add(user.getCurrentFundsAmount());

            user.setCurrentFundsAmount(newFundsAmount);
        }
    }

    public User getUser() {
        return user;
    }

    public void payOff(BigDecimal cost) throws InsufficientFundsException {
        BigDecimal currentFunds = user.getCurrentFundsAmount();

        if (currentFunds.compareTo(cost) >= 0) {
            user.setCurrentFundsAmount(currentFunds.subtract(cost));
        } else {
            throw new InsufficientFundsException("Operation cannot be completed. " +
                    "Deficiency of money =" + cost.subtract(currentFunds));
        }
    }

    public void stopAndSave() {
        this.isRunning = false;

        repositoryManager.update(user);
    }

    public void start() {
        if (user != null) {
            this.isRunning = true;
        } else {
            LOGGER.debug("Start failed. The reason: 'User == null'");
        }
    }
}