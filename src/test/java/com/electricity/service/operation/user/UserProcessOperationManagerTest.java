package com.electricity.service.operation.user;

import com.electricity.enumeration.plant.PowerPlantType;
import com.electricity.enumeration.resource.PurchasableResourceType;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.plant.impl.CoalFiredPowerPlant;
import com.electricity.model.storage.impl.CoalStorage;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.operation.plant.PowerPlantOperationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProcessOperationManagerTest {
    private UserProcessOperationManager userManager;
    private User user;

    @BeforeEach
    void prepare_data() {
        UserRepository repository = mock(UserRepository.class);
        user = new User("testLogin", "testPassword");
        userManager = new UserProcessOperationManager(repository, user);
    }

    @Test
    void should_get_user() {
        assertSame(user, userManager.getUser());
    }

    @Test
    void should_buy_resource_using_transaction() {
        //Given
        PowerPlantOperationManager plantManager = mock(PowerPlantOperationManager.class);
        BigDecimal initialFunds = new BigDecimal(20_000);
        user.setCurrentFundsAmount(initialFunds);
        CoalFiredPowerPlant powerPlant = new CoalFiredPowerPlant(new CoalStorage());
        doNothing().when(plantManager).setResourceUsingDeliveryService(any());

        //When
        userManager.injectPowerPlantExecutorReference(plantManager);
        userManager.buyResourceUsingTransaction(
                new ResourceTransaction(powerPlant.getId(), 1000, PurchasableResourceType.COAL));

        //Then
        assertNotEquals(initialFunds, userManager.getUser().getCurrentFundsAmount());
        assertEquals(new BigDecimal(5000), userManager.getUser().getCurrentFundsAmount());
        verify(plantManager, times(1)).setResourceUsingDeliveryService(any());
    }

    @Test
    void should_buy_power_plant() {
        //Given
        BigDecimal initialFunds = new BigDecimal(26_000_000);
        user.setCurrentFundsAmount(initialFunds);
        PowerPlantOperationManager plantManager = mock(PowerPlantOperationManager.class);
        PowerPlantCreatingDto dto = new PowerPlantCreatingDto();
        dto.setType(PowerPlantType.COAL);
        doNothing().when(plantManager).createPowerPlantAndAddToThread(any());

        //When
        userManager.injectPowerPlantExecutorReference(plantManager);
        userManager.buyPowerPlant(dto);

        //Then
        assertNotEquals(initialFunds, userManager.getUser().getCurrentFundsAmount());
        assertEquals(new BigDecimal(1_000_000), userManager.getUser().getCurrentFundsAmount());
        verify(plantManager, times(1)).createPowerPlantAndAddToThread(any());
    }
}