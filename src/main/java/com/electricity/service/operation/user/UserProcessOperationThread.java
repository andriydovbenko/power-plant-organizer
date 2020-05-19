package com.electricity.service.process.user;

import com.electricity.exeption.InsufficientFundsException;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.process.plant.PowerPlantProcessThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static com.electricity.enumeration.EnergyCost.ENERGY;

public class UserProcessThread implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(UserProcessThread.class);
    private final UserRepository repositoryManager;
    private final User user;
    private boolean isRunning;
    private BigDecimal pricePerMegawatt;

    public UserProcessThread(UserRepository repositoryManager, User user) {
        this.repositoryManager = repositoryManager;
        this.user = user;
        this.isRunning = false;
        this.pricePerMegawatt = ENERGY.getCost();
    }

    @Override
    public void run() {
        if (isRunning) {
            updateUserFunds();
        }
    }

    private void updateUserFunds() {
        BigDecimal newFundsAmount = PowerPlantProcessThread.sellProducedEnergy(pricePerMegawatt)
                .add(user.getCurrentFundsAmount());
        System.err.println("running.. funds=" + newFundsAmount);
//        LOGGER.debug("new amount of funds " + newFundsAmount);

        user.setCurrentFundsAmount(newFundsAmount);
    }

    public User getUser() {
        return user;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public BigDecimal getPricePerMegawatt() {
        return pricePerMegawatt;
    }

    public void setPricePerMegawatt(BigDecimal pricePerMegawatt) {
        this.pricePerMegawatt = pricePerMegawatt;
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
        if (user != null) {
            this.isRunning = false;
            repositoryManager.update(user);
        }
    }

    public void start() {
        if (user != null) {
            this.isRunning = true;
        } else {
            LOGGER.debug(" Start failed. The reason: 'User == null'");
        }
    }
}