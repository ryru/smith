# Agent Smith

To see the agent in action run:

1. `./gradlew clean :matrix:install`
2. `./gradlew :agent:build`
3. ```bash
    java -javaagent:agent/build/libs/agent-1.0-SNAPSHOT.jar -cp matrix/build/install/matrix/lib/matrix-1.0-SNAPSHOT.jar:matrix/build/install/matrix/lib/* ch.addere.matrix.App
    ```

Only run the application (without agent)

1. `./gradlew :matrix:install`
2. ```bash
    java -cp matrix/build/install/matrix/lib/matrix-1.0-SNAPSHOT.jar:matrix/build/install/matrix/lib/* ch.addere.matrix.App
   ```
