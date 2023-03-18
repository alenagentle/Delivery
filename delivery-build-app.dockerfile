FROM maven:3.9.0
WORKDIR /workspace/app

COPY pom.xml .
COPY src src

CMD mvn install && mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar) && touch /tmp/flag && sleep 100