#
# install the two dependency jar files from the other Eclipse projects
#
mvn install:install-file -Dfile=../teetime/webscraper/target/webscraper-0.0.1-SNAPSHOT.jar -DgroupId=djboulia-golf -DartifactId=webscraper -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DpomFile=../teetime/webscraper/pom.xml -DlocalRepositoryPath=./.m2/repository/

mvn install:install-file -Dfile=../teetime/teetime/target/teetime-0.0.1-SNAPSHOT.jar -DgroupId=djboulia-golf -DartifactId=teetime -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DlocalRepositoryPath=./.m2/repository

mvn install:install-file -Dfile=../teetime/webscraper/target/webscraper-0.0.1-SNAPSHOT.jar -DgroupId=djboulia-golf -DartifactId=webscraper -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DpomFile=../teetime/webscraper/pom.xml

mvn install:install-file -Dfile=../teetime/teetime/target/teetime-0.0.1-SNAPSHOT.jar -DgroupId=djboulia-golf -DartifactId=teetime -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

