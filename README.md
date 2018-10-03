## Customed Eureka
일반적으로 Eureka Server는 서비스 등록을 위해 Eureka Client가 필요합니다. Eureka Client로 등록할 서비스 역시 Eureka Dependeny를 추가하고 Eureka Client 어노테이션을 추가합니다. Customed Eureka는 20초마다 Marathon을 크롤링하여 배포된 서비스를 유레카 서버에 등록하고, 30초마다 서비스를 재등록하여 배포된 서비스가 다운됐을 경우 유레카에 등록정보를 삭제합니다.

* 구조  
    ![customed_eureka_zuul_apigateway](https://steemitimages.com/800x0//https://github.com/ssipflow/SpringCloudGateway/blob/ssipflow/images/customed_eureka_zuul.PNG?raw=true)

* 환경변수 설정
    * Eureka Server의 application.properties  
    Marathon을 크롤링하여 Eureka Server에 서비스를 등록하기 위해 다음과 같은 설정옵션을 입력합니다. 각 properties 변수는 자신의 환경에 맞게 설정하면 됩니다.
        * marathon.tasks.endpont  
        : Marathon의 task 목록을 반환하는 RESTful API 입니다. 클러스터에 배포 시 **http://leder.mesos:8080/v2/tasks**. 로컬 테스트 시 **marathon-endpont/v2/tasks**
        * eureka.endpont  
        : Eureka Server에 서비스를 수동으로 등록하기 위한 RESTful API 입니다. 클러스터에 배포 시 **http://eureka-server.marathon.mesos:8770/eureka/apps** 로컬 테스트 시 **http://localhost:8770/eureka/apps**   
            ```properties
            marathon.tasks.endpoint=${MARATHON_TASKS_ENDPOINT:marathon-endpoint:v2/tasks}
            eureka.endpoint=${EUREKA_ENDPOINT:eureka-server-endpoint:8770/eureka/apps}
            ```

* Customed Eureka 특징
    * Service Discovery 자동화
    * Side-car 없는 Polyglot 지원
    * 고가용성 지원

## ZUUL
ZUUL은 Eureka Server에 등록된 서비스 라우팅을 위한 Spring Cloud 프로젝트입니다. 유레카 서버에 등록된 서비스들의 ID, Endpoint를 맵핑하기 위해 ZUUL은 Eureka Client로 등록합니다. 즉, ZUUL은 Customed Eureka의 유일한 Eureka Client입니다.

* Eureka 와 ZULL을 이용한 API GATEWAY의 구조
    ![standard_eureka_zuulapigateway](https://steemitimages.com/600x0//https://github.com/ssipflow/SpringCloudGateway/blob/ssipflow/images/standard_eureka_zuul.PNG?raw=true)  