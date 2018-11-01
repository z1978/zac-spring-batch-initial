# zac-spring-boot-starter-batch
Creating a Batch Service

## Run
ZacApplication.java -> Java Application  
## Run parameter
### firstJob  
-j firstJob -i sample-data.csv nonOptionParam1  
-j stressTestingJob -i result.csv nonOptionParam1  

param1=GO_TO_FLOW_1  
param1=GO_TO_FLOW_2  

https://github.com/geekyjaat/spring-batch  

## Stress testing
```
for i in `seq 1 600000`; do
echo ${i},10000000,2000-10-10 10:00:00 >> result.csv
done
```

# Build project
Maven:

```
mvn clean install
mvn package && java -jar target/xxxxx.jar  
```
Gradle:

```
gradle build && java -jar build/libs/xxxxx.jar  
gradle build -x test  
```

# DROP TABLE
```
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_SEQ;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_INSTANCE;
```

# Gradle build.gradle to Maven pom.xml

```
apply plugin: 'maven'

group = 'com.company.root'
// artifactId is taken by default, from folder name
version = '0.0.1-SNAPSHOT'

task writeNewPom << {
    pom {
        project {
            inceptionYear '2014'
            licenses {
                license {
                    name 'The Apache Software License, Version 2.0'
                    url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
                    distribution 'repo'
                }
            }
        }
    }.writeTo("pom.xml")
}
```
# to run it
```
gradle writeNewPom
```