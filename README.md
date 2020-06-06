# Power Plant Organizer
This is a training project, which was created as a personal project task for EPAM. 

## Table of contents
* [Links](#links)
* [General info](#general-info)
* [Technologies](#technologies)
* [Features](#features)
* [Structure](#structure)
* [Contact](#contact)

## Links
* [Registration](https://power-plant-organizer.herokuapp.com/register)
* [Login](https://power-plant-organizer.herokuapp.com/login)
* [Home page](https://power-plant-organizer.herokuapp.com/in/home)

## General info
After registration each user receives five different Power Plants and funds for 
operations in this game. In order to produce energy and earn new funds the power 
plants utilize resources. For now exist five types of power plants and five types 
of resources. They can be split on renewable and non-renewable. User is capable 
to buy non-renewable resources.

## Technologies
Project is created with:
* Java version: 11
* Servlet API version: 3.0

Database:
* PostgresSQL version: 42.2.12
* PostgresSQL remote Heroku

Logging:
* Log4j2 version: 2.6.1

Deploy:
* localhost: Tomcat version: 9.0.35
* remote: Heroku.com

Testing:
* JUnit 5 :
    * JUnit Platform 
    * JUnit Jupiter 
    * JUnit Vintage
* Mockito version: 3.3.3

## Features
User can control working process on each power plant:
* Buy resource
* Buy new power plant
* Stop or start producing energy
* Delete power plant
* Edit power plant information

Types of power plants:
1) Hydro Power Plant
2) Coal-Fired Power Plant
3) Nuclear Power Plant
4) Solar Power Plant
5) Wind Power Plant

Renewable:
* Water
* Wind
* Solar

Non-renewable:
* Uranium
* Coal

## Structure
   - [pom.xml](pom.xml)
   - __src__
     - __main__
       - __java__
         - __com__
           - __electricity__
             - __enumeration__
               - __alert__
                 - [AlertCode.java](src/main/java/com/electricity/enumeration/alert/AlertCode.java)
               - __attribute__
                 - [AESData.java](src/main/java/com/electricity/enumeration/attribute/AESData.java)
                 - [ContextAttribute.java](src/main/java/com/electricity/enumeration/attribute/ContextAttribute.java)
               - __path__
                 - [AppViewPath.java](src/main/java/com/electricity/enumeration/path/AppViewPath.java)
                 - [Driver.java](src/main/java/com/electricity/enumeration/path/Driver.java)
               - __plant__
                 - [EnergyCost.java](src/main/java/com/electricity/enumeration/plant/EnergyCost.java)
                 - [InitialWorkTimeForUnstorablePowerPlant.java](src/main/java/com/electricity/enumeration/plant/InitialWorkTimeForUnstorablePowerPlant.java)
                 - [MaxPower.java](src/main/java/com/electricity/enumeration/plant/MaxPower.java)
                 - [PowerPlantCost.java](src/main/java/com/electricity/enumeration/plant/PowerPlantCost.java)
                 - [PowerPlantType.java](src/main/java/com/electricity/enumeration/plant/PowerPlantType.java)
                 - [ResourceConsumption.java](src/main/java/com/electricity/enumeration/plant/ResourceConsumption.java)
               - __repo__
                 - [PowerPlantColumnName.java](src/main/java/com/electricity/enumeration/repo/PowerPlantColumnName.java)
                 - [TableName.java](src/main/java/com/electricity/enumeration/repo/TableName.java)
                 - [UserColumnName.java](src/main/java/com/electricity/enumeration/repo/UserColumnName.java)
               - __resource__
                 - [PurchasableResourceType.java](src/main/java/com/electricity/enumeration/resource/PurchasableResourceType.java)
                 - [ResourcePrice.java](src/main/java/com/electricity/enumeration/resource/ResourcePrice.java)
             - __exeption__
               - [InsufficientFundsException.java](src/main/java/com/electricity/exeption/InsufficientFundsException.java)
               - [NoSuchPowerPlantIdException.java](src/main/java/com/electricity/exeption/NoSuchPowerPlantIdException.java)
               - [NotEnoughStorageSpaceException.java](src/main/java/com/electricity/exeption/NotEnoughStorageSpaceException.java)
               - [UnknownPowerPlantTypeException.java](src/main/java/com/electricity/exeption/UnknownPowerPlantTypeException.java)
               - [UnknownResourceTypeException.java](src/main/java/com/electricity/exeption/UnknownResourceTypeException.java)
             - __model__
               - __dto__
                 - [PowerPlantAbstractDto.java](src/main/java/com/electricity/model/dto/PowerPlantAbstractDto.java)
                 - __impl__
                   - [PowerPlantCreatingDto.java](src/main/java/com/electricity/model/dto/impl/PowerPlantCreatingDto.java)
                   - [PowerPlantUpdatingDto.java](src/main/java/com/electricity/model/dto/impl/PowerPlantUpdatingDto.java)
               - __plant__
                 - [PowerPlant.java](src/main/java/com/electricity/model/plant/PowerPlant.java)
                 - [StorageCapableAbstractPlant.java](src/main/java/com/electricity/model/plant/StorageCapableAbstractPlant.java)
                 - [StorageIncapableAbstractPlant.java](src/main/java/com/electricity/model/plant/StorageIncapableAbstractPlant.java)
                 - __impl__
                   - [CoalFiredPowerPlant.java](src/main/java/com/electricity/model/plant/impl/CoalFiredPowerPlant.java)
                   - [HydroPowerPlant.java](src/main/java/com/electricity/model/plant/impl/HydroPowerPlant.java)
                   - [NuclearPowerPlan.java](src/main/java/com/electricity/model/plant/impl/NuclearPowerPlan.java)
                   - [SolarPowerPlant.java](src/main/java/com/electricity/model/plant/impl/SolarPowerPlant.java)
                   - [WindPowerPlant.java](src/main/java/com/electricity/model/plant/impl/WindPowerPlant.java)
               - __resource__
                 - [StorableResource.java](src/main/java/com/electricity/model/resource/StorableResource.java)
                 - [UnstorableResource.java](src/main/java/com/electricity/model/resource/UnstorableResource.java)
                 - __storable__
                   - [Coal.java](src/main/java/com/electricity/model/resource/storable/Coal.java)
                   - [Uranium.java](src/main/java/com/electricity/model/resource/storable/Uranium.java)
                   - [Water.java](src/main/java/com/electricity/model/resource/storable/Water.java)
                 - __unstorable__
                   - [SolarEnergy.java](src/main/java/com/electricity/model/resource/unstorable/SolarEnergy.java)
                   - [Wind.java](src/main/java/com/electricity/model/resource/unstorable/Wind.java)
               - __storage__
                 - [Storage.java](src/main/java/com/electricity/model/storage/Storage.java)
                 - __impl__
                   - [CoalStorage.java](src/main/java/com/electricity/model/storage/impl/CoalStorage.java)
                   - [UraniumStorage.java](src/main/java/com/electricity/model/storage/impl/UraniumStorage.java)
                   - [WaterReservoir.java](src/main/java/com/electricity/model/storage/impl/WaterReservoir.java)
               - __transaction__
                 - [ResourceTransaction.java](src/main/java/com/electricity/model/transaction/ResourceTransaction.java)
               - __user__
                 - [User.java](src/main/java/com/electricity/model/user/User.java)
             - __repository__
               - [PowerPlantRepository.java](src/main/java/com/electricity/repository/PowerPlantRepository.java)
               - [UserRepository.java](src/main/java/com/electricity/repository/UserRepository.java)
               - __init__
                 - [PowerPlantInitializer.java](src/main/java/com/electricity/repository/init/PowerPlantInitializer.java)
               - __jdbc__
                 - [PostgresAuthenticationDataLoader.java](src/main/java/com/electricity/repository/jdbc/PostgresAuthenticationDataLoader.java)
               - __plant__
                 - [PowerPlantReaderRepository.java](src/main/java/com/electricity/repository/plant/PowerPlantReaderRepository.java)
                 - [PowerPlantSwitcher.java](src/main/java/com/electricity/repository/plant/PowerPlantSwitcher.java)
                 - [PowerPlantWriterRepository.java](src/main/java/com/electricity/repository/plant/PowerPlantWriterRepository.java)
                 - __impl__
                   - [PowerPlantReaderRepositoryImpl.java](src/main/java/com/electricity/repository/plant/impl/PowerPlantReaderRepositoryImpl.java)
                   - [PowerPlantWriterRepositoryImpl.java](src/main/java/com/electricity/repository/plant/impl/PowerPlantWriterRepositoryImpl.java)
               - __user__
                 - [UserReaderRepository.java](src/main/java/com/electricity/repository/user/UserReaderRepository.java)
                 - [UserWriterRepository.java](src/main/java/com/electricity/repository/user/UserWriterRepository.java)
                 - __impl__
                   - [UserReaderRepositoryImpl.java](src/main/java/com/electricity/repository/user/impl/UserReaderRepositoryImpl.java)
                   - [UserWriterRepositoryImpl.java](src/main/java/com/electricity/repository/user/impl/UserWriterRepositoryImpl.java)
             - __service__
               - __converter__
                 - [StringConverterIntoPlantType.java](src/main/java/com/electricity/service/converter/StringConverterIntoPlantType.java)
                 - [StringsConverterIntoResourceType.java](src/main/java/com/electricity/service/converter/StringsConverterIntoResourceType.java)
               - __login__
                 - [LoggingService.java](src/main/java/com/electricity/service/login/LoggingService.java)
               - __market__
                 - [ResourceMarket.java](src/main/java/com/electricity/service/market/ResourceMarket.java)
                 - __impl__
                   - [ResourceMarketImpl.java](src/main/java/com/electricity/service/market/impl/ResourceMarketImpl.java)
               - __operation__
                 - __plant__
                   - [PowerPlantOperationManager.java](src/main/java/com/electricity/service/operation/plant/PowerPlantOperationManager.java)
                   - [PowerPlantOperationThread.java](src/main/java/com/electricity/service/operation/plant/PowerPlantOperationThread.java)
                 - __user__
                   - [UserProcessOperationManager.java](src/main/java/com/electricity/service/operation/user/UserProcessOperationManager.java)
                   - [UserProcessOperationThread.java](src/main/java/com/electricity/service/operation/user/UserProcessOperationThread.java)
               - __password__
                 - __encryption__
                   - [PasswordCryptographer.java](src/main/java/com/electricity/service/password/encryption/PasswordCryptographer.java)
                 - __validation__
                   - [PasswordValidator.java](src/main/java/com/electricity/service/password/validation/PasswordValidator.java)
               - __plant__
                 - [EnergyProducingService.java](src/main/java/com/electricity/service/plant/EnergyProducingService.java)
                 - [PowerPlantConstructor.java](src/main/java/com/electricity/service/plant/PowerPlantConstructor.java)
                 - [PowerPlantPriceSetterService.java](src/main/java/com/electricity/service/plant/PowerPlantPriceSetterService.java)
                 - [ResourceDeliveryService.java](src/main/java/com/electricity/service/plant/ResourceDeliveryService.java)
                 - __impl__
                   - [EnergyProducingServiceImpl.java](src/main/java/com/electricity/service/plant/impl/EnergyProducingServiceImpl.java)
                   - [PowerPlantConstructorImpl.java](src/main/java/com/electricity/service/plant/impl/PowerPlantConstructorImpl.java)
                   - [PowerPlantPriceSetterServiceImpl.java](src/main/java/com/electricity/service/plant/impl/PowerPlantPriceSetterServiceImpl.java)
                   - [ResourceDeliveryServiceImpl.java](src/main/java/com/electricity/service/plant/impl/ResourceDeliveryServiceImpl.java)
               - __registration__
                 - [RegistrationService.java](src/main/java/com/electricity/service/registration/RegistrationService.java)
                 - __impl__
                   - [RegistrationServiceImpl.java](src/main/java/com/electricity/service/registration/impl/RegistrationServiceImpl.java)
               - __session__
                 - [UserSession.java](src/main/java/com/electricity/service/session/UserSession.java)
                 - [UserSessionService.java](src/main/java/com/electricity/service/session/UserSessionService.java)
                 - __impl__
                   - [UserSessionImpl.java](src/main/java/com/electricity/service/session/impl/UserSessionImpl.java)
                   - [UserSessionServiceImpl.java](src/main/java/com/electricity/service/session/impl/UserSessionServiceImpl.java)
             - __servlet__
               - [ContextListener.java](src/main/java/com/electricity/servlet/ContextListener.java)
               - [LoginFilter.java](src/main/java/com/electricity/servlet/LoginFilter.java)
               - __plant__
                 - [PowerPlantSwitchServlet.java](src/main/java/com/electricity/servlet/plant/PowerPlantSwitchServlet.java)
                 - [PowerPlantUpdatingServlet.java](src/main/java/com/electricity/servlet/plant/PowerPlantUpdatingServlet.java)
                 - [PowerPlantsServlet.java](src/main/java/com/electricity/servlet/plant/PowerPlantsServlet.java)
               - __shop__
                 - [PowerPlantShopServlet.java](src/main/java/com/electricity/servlet/shop/PowerPlantShopServlet.java)
                 - [ResourceShopServlet.java](src/main/java/com/electricity/servlet/shop/ResourceShopServlet.java)
               - __user__
                 - [HomeServlet.java](src/main/java/com/electricity/servlet/user/HomeServlet.java)
                 - [LogOutServlet.java](src/main/java/com/electricity/servlet/user/LogOutServlet.java)
                 - [LoginServlet.java](src/main/java/com/electricity/servlet/user/LoginServlet.java)
                 - [OfficeServlet.java](src/main/java/com/electricity/servlet/user/OfficeServlet.java)
                 - [RegistrationServlet.java](src/main/java/com/electricity/servlet/user/RegistrationServlet.java)
       - __resources__
         - [db.properties](src/main/resources/db.properties)
         - [log4j2.properties](src/main/resources/log4j2.properties)
         - [system.properties](src/main/resources/system.properties)
       - __webapp__
         - __WEB\-INF__
           - [web.xml](src/main/webapp/WEB-INF/web.xml)
         - __view__
           - __jsp__
             - [error\-404.jsp](src/main/webapp/view/jsp/error-404.jsp)
             - [error\-500.jsp](src/main/webapp/view/jsp/error-500.jsp)
             - [home.jsp](src/main/webapp/view/jsp/home.jsp)
             - [login.jsp](src/main/webapp/view/jsp/login.jsp)
             - [logout.jsp](src/main/webapp/view/jsp/logout.jsp)
             - [office.jsp](src/main/webapp/view/jsp/office.jsp)
             - [powerPlantUpdate.jsp](src/main/webapp/view/jsp/powerPlantUpdate.jsp)
             - [powerPlants.jsp](src/main/webapp/view/jsp/powerPlants.jsp)
             - [register.jsp](src/main/webapp/view/jsp/register.jsp)
             - [shopPlants.jsp](src/main/webapp/view/jsp/shopPlants.jsp)
             - [shopResource.jsp](src/main/webapp/view/jsp/shopResource.jsp)
     - __test__
       - __java__
         - __com__
           - __electricity__
             - __model__
               - __storage__
                 - __impl__
                   - [CoalStorageTest.java](src/test/java/com/electricity/model/storage/impl/CoalStorageTest.java)
                   - [UraniumStorageTest.java](src/test/java/com/electricity/model/storage/impl/UraniumStorageTest.java)
                   - [WaterReservoirTest.java](src/test/java/com/electricity/model/storage/impl/WaterReservoirTest.java)
             - __repository__
               - __init__
                 - [PowerPlantInitializerTest.java](src/test/java/com/electricity/repository/init/PowerPlantInitializerTest.java)
             - __service__
               - __converter__
                 - [StringConverterIntoPlantTypeTest.java](src/test/java/com/electricity/service/converter/StringConverterIntoPlantTypeTest.java)
                 - [StringsConverterIntoResourceTypeTest.java](src/test/java/com/electricity/service/converter/StringsConverterIntoResourceTypeTest.java)
               - __market__
                 - __impl__
                   - [ResourceMarketImplTest.java](src/test/java/com/electricity/service/market/impl/ResourceMarketImplTest.java)
               - __operation__
                 - __user__
                   - [UserProcessOperationManagerTest.java](src/test/java/com/electricity/service/operation/user/UserProcessOperationManagerTest.java)
                   - [UserProcessOperationThreadTest.java](src/test/java/com/electricity/service/operation/user/UserProcessOperationThreadTest.java)
               - __password__
                 - __encryption__
                   - [PasswordCryptographerTest.java](src/test/java/com/electricity/service/password/encryption/PasswordCryptographerTest.java)
                 - __validation__
                   - [TestPasswordValidator.java](src/test/java/com/electricity/service/password/validation/TestPasswordValidator.java)
               - __plant__
                 - __impl__
                   - [EnergyProducingServiceImplTest.java](src/test/java/com/electricity/service/plant/impl/EnergyProducingServiceImplTest.java)
                   - [PowerPlantConstructorImplTest.java](src/test/java/com/electricity/service/plant/impl/PowerPlantConstructorImplTest.java)
                   - [PowerPlantPriceSetterServiceImplTest.java](src/test/java/com/electricity/service/plant/impl/PowerPlantPriceSetterServiceImplTest.java)
                   - [ResourceDeliveryServiceImplTest.java](src/test/java/com/electricity/service/plant/impl/ResourceDeliveryServiceImplTest.java)
               - __registration__
               - __session__
                 - __impl__
                   - [UserSessionServiceImplTest.java](src/test/java/com/electricity/service/session/impl/UserSessionServiceImplTest.java)
             - __servlet__
               - __user__
                 - [HomeServletTest.java](src/test/java/com/electricity/servlet/user/HomeServletTest.java)
                 - [LogOutServletTest.java](src/test/java/com/electricity/servlet/user/LogOutServletTest.java)
                 - [LoginServletTest.java](src/test/java/com/electricity/servlet/user/LoginServletTest.java)
                 - [OfficeServletTest.java](src/test/java/com/electricity/servlet/user/OfficeServletTest.java)
                 - [RegistrationServletTest.java](src/test/java/com/electricity/servlet/user/RegistrationServletTest.java)
                 
## Contact
Andrii Dovbenko:
 * [GitHub](https://github.com/andriydovbenko)
 * [LinkedIn](https://www.linkedin.com/in/andrii-dovbenko-42808417b/)