#
# install the two dependency jar files from the other Eclipse projects
#
mvn install:install-file -Dfile=/Users/djboulia/Development/workspace_teetime//webscraper/target/webscraper-0.0.1-SNAPSHOT.jar -DgroupId=djboulia-golf -DartifactId=webscraper -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DpomFile=/Users/djboulia/Development/workspace_teetime/webscraper/pom.xml -DlocalRepositoryPath=./.m2/repository/

mvn install:install-file -Dfile=/Users/djboulia/Development/workspace_teetime/teetime/target/teetime-0.0.1-SNAPSHOT.jar -DgroupId=djboulia-golf -DartifactId=teetime -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DlocalRepositoryPath=./.m2/repository

mvn install:install-file -Dfile=/Users/djboulia/Development/workspace_teetime//webscraper/target/webscraper-0.0.1-SNAPSHOT.jar -DgroupId=djboulia-golf -DartifactId=webscraper -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DpomFile=/Users/djboulia/Development/workspace_teetime/webscraper/pom.xml

mvn install:install-file -Dfile=/Users/djboulia/Development/workspace_teetime/teetime/target/teetime-0.0.1-SNAPSHOT.jar -DgroupId=djboulia-golf -DartifactId=teetime -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

