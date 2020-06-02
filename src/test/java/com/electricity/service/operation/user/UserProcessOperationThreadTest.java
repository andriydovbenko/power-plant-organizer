package com.electricity.service.operation.user;

import com.electricity.exeption.InsufficientFundsException;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.operation.plant.PowerPlantOperationThread;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProcessOperationThreadTest {

    @Test
    void should_not_start() {
        //Given
        final UserRepository repository = mock(UserRepository.class);
        UserProcessOperationThread userManager = new UserProcessOperationThread(repository, null);

        //When
        userManager.start();
        boolean realValue = userManager.isRunning();

        //Then
        assertFalse(realValue);
    }

    @Test
    void should_start() {
        //Given
        final UserRepository repository = mock(UserRepository.class);
        final User user = new User("testLogin", "testPassword");
        UserProcessOperationThread userManager = new UserProcessOperationThread(repository, user);

        //When
        userManager.start();
        boolean realValue = userManager.isRunning();

        //Then
        assertTrue(realValue);
    }

    @Test
    void should_pay_off_for_payment() throws InsufficientFundsException {
        //Given
        final UserRepository repository = mock(UserRepository.class);
        final User user = new User("testLogin", "testPassword");
        UserProcessOperationThread userManager = new UserProcessOperationThread(repository, user);
        BigDecimal purchaseCoast = new BigDecimal(2_000_000);
        BigDecimal expectedFundsAfterTransaction = user.getCurrentFundsAmount().subtract(purchaseCoast);

        //When
        userManager.payOff(purchaseCoast);

        //Then
        assertEquals(expectedFundsAfterTransaction, userManager.getUser().getCurrentFundsAmount());
    }

    @Test
    void should_throw_InsufficientFundsException() throws InsufficientFundsException {
        //Given
        final UserRepository repository = mock(UserRepository.class);
        final User user = new User("testLogin", "testPassword");
        UserProcessOperationThread userManager = new UserProcessOperationThread(repository, user);
        BigDecimal purchaseCoast = new BigDecimal(2_000_000);
        user.setCurrentFundsAmount(new BigDecimal(0));

        //Then
        assertThrows(InsufficientFundsException.class, () -> userManager.payOff(purchaseCoast));
    }

    @Test
    void should_stop_and_save_user_data() {
        //Given
        final UserRepository repository = mock(UserRepository.class);
        final User user = new User("testLogin", "testPassword");
        UserProcessOperationThread userManager = new UserProcessOperationThread(repository, user);
        doNothing().when(repository).update(user);
        //When
        userManager.stopAndSave();
        boolean realValue = userManager.isRunning();

        //Then
        assertFalse(realValue);
        verify(repository,times(1)).update(user);
    }

    @Test
    void should_return_user() {
        //Given
        final UserRepository repository = mock(UserRepository.class);
        final User user = new User("testLogin", "testPassword");
        PowerPlantOperationThread plantOperationThread = mock(PowerPlantOperationThread.class);
        UserProcessOperationThread userManager = new UserProcessOperationThread(repository, user);

        //When
        userManager.start();
        User realUser = userManager.getUser();

        //Then
        assertEquals(user, realUser);
    }
}