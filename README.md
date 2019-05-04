Application Technology Assessment
===================================

This application was built using **MVP** Design Pattern, and in mind clean architecture.

The application uses the NY Times API as below;

[http://api.nytimes.com/svc/mostpopular/v2/viewed/30.json?api-key=wzXI9F5g6tpL9FSXqE9xlyde0Lk006eo](http://api.nytimes.com/svc/mostpopular/v2/viewed/30.json?api-key=wzXI9F5g6tpL9FSXqE9xlyde0Lk006eo)

Getting Started
---------------

#### Build
- Build Application from command line using gradle
    - Go to application folder
    - Run below command (without the **$** sign)
```sh
$ ./gradlew build
```

#### Unit Test
- Run JUnit Tests
```sh
$ ./gradlew connectedAndroidTest
```
Then you can see reports in build/reports/androidTests/connected/index.html path


#### [FindBugs](https://github.com/ChaitanyaPramod/findbugs-android)
- Run **FindBugs** for Static Code Analysis
```sh
$ ./gradlew findbugs
```

#### [SonarCube](https://www.sonarqube.org/)
* [Download](https://www.sonarqube.org/downloads/) the SonarQube Community Edition
* Unzip it, on windows (ex. C:\sonarqube) or on linux (ex. /opt/sonarqube)
* Start the SonarQube Server:
```sh
# On Windows, execute:
C:\sonarqube\bin\windows-x86-xx\StartSonar.bat

# On other operating systems, as a non-root user execute:
/opt/sonarqube/bin/[OS]/sonar.sh console
```

* Log in to http://localhost:9000 with System Administrator credentials (admin/admin) and follow the embedded tutorial to analyze your first project.
* Go to [http://localhost:9000/admin/projects_management](http://localhost:9000/admin/projects_management) and press "Create project" button.
* Run the below in the command line to start analysing the project code
```sh
./gradlew sonarqube   -Dsonar.host.url=http://localhost:9000   -Dsonar.login=8f0e063d6007c08d7af40c98ecce5c5cb9c284fa
```

-------------------------------------------------------
### Below are the libraries were used in this app
- Retrofit - For Network communication
- OKHttp - Interceptor to use with Retrofit
- Dagger2 - For DI
- RxAndroid - Reactive programming
- Butter Knife - for Views DI
- Picasso - Image library
- CardView - CardView compatibility on older devices
- Gson
- CircleImageView
- RecyclerView
- AutoValue - Generate Immutable value classes