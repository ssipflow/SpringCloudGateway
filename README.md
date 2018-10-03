# Overview
* Eureka Client 없이 Marathon을 크롤링하여 Eureka Server에 Marathon으로 배포된 서비스들을 자동으로 등록하는 Spring Cloud API Gateway 입니다.

* 구조  
    ![apigateway_nexgate](https://steemitimages.com/800x0//https://raw.githubusercontent.com/TheNexCloud/NexGate/dev-mg.kim/images/customed_eureka_zuul.PNG?raw=true)

* 환경
    * JAVA 8
    * Spring 4
    * Spring boot 1.5.9
    * Maven 3.5.2

* 환경변수 설정
    * Eureka Server의 application.properties  
    Marathon을 크롤링하여 Eureka Server에 서비스를 등록하기 위해 다음과 같은 설정옵션을 입력합니다. 각 properties 변수는 자신의 환겨에 맞게 설정하면 됩니다.
        * marathon.tasks.endpont  
        : Marathon의 task 목록을 반환하는 RESTful API 입니다. 클러스터에 배포 시 **http://leder.mesos:8080/v2/tasks**. 로컬 테스트 시 **marathon-endpont/v2/tasks**
        * eureka.endpont  
        : Eureka Server에 서비스를 수동으로 등록하기 위한 RESTful API 입니다. 클러스터에 배포 시 **http://eureka-server.marathon.mesos:8770/eureka/apps** 로컬 테스트 시 **http://localhost:8770/eureka/apps**   
            ```properties
            marathon.tasks.endpoint=${MARATHON_TASKS_ENDPOINT:marathon-endpoint:v2/tasks}
            eureka.endpoint=${EUREKA_ENDPOINT:eureka-server-endpoint:8770/eureka/apps}
            ```

## What is NexEureka?
* NexEureka is the NexCloud's customed Eureka which can register service without Eureka client. In standard Eureka, you must inject dependency to registered service. But, in NexEureka, you need just NexEureka service on your server. We made functions that crawl services deployed by Marathon and register them to Eureka Server. It renews service list by 30 secs, so it is safe when an error occurred from one of services instances.

* Waht is Eureka?
    * Eureka is Spring Cloud project for service discovery in Micro Service Architecture. If you want to use service discover by Eureka, you have to initialize Eureka Server by putting ***@EnableEurekaServer*** annotation in the main class of Eureka Server Project. Then put ***@EnableEurekaClient*** annotation in the main class of service that you want to use.

    * Eureka has server - client Architecture for service registry. So you must have client source to register to Eureka server.  
        ![eureka_server_client](https://steemitimages.com/600x0//https://github.com/TheNexCloud/NexGate/blob/dev-mg.kim/images/standard_eureka.png?raw=true)

    * If Eureka client is registered to Eureka server, client replicates services registered to the server. If the service is not edge service, it is inefficient that eureka dependency is injected to project.

    * By defaults, Spring Cloud supports only JAVA application, so if you want to register NON-JAVA application, you need to adopt side-car.

* Advantages of NexEureka
    * Supports service discovery by itself.
    * Supports polyglot without side-car.
    * Supports high availability.


## What is NexZUUL?
* NexZUUL is the NexCloud's customed ZUUL. As we explained, A service initialized as a Eureka client cached service list from Eureka server. So NexZUUL is the only Eureka client on Micro Service Architecture.

* What is ZUUL?
    * ZUUL is a simple gateway service or edge service. So its main goal is routing, monitoring, error management and security. Many API gateway products offer limited functions, but ZUUL is free to customize its functions for developers.

    * API gateway using Eureka and ZUUL
        ![apigateway_eureka_zuul](https://steemitimages.com/600x0//https://github.com/TheNexCloud/NexGate/blob/dev-mg.kim/images/standard_eureka_zuul.PNG?raw=true)  
        *You must inject Eureka client dependency to you micro service*

* With NexEureka, just deploy microservices with no additional dependencies and access service through service name.