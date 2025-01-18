./gradlew build
scp -r build/quarkus-app/ cloudru:/home/xyl1gan4eg
#java -Dquarkus.profile=prod -jar build/quarkus-app/quarkus-run.jar