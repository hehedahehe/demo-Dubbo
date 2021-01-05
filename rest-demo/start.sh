java \
-Xmx10m -Xms10m -Xmn10m -XX:MaxMetaspaceSize=40m \
-XX:MaxDirectMemorySize=60M \
-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime \
-Xloggc:/Users/liruibo/Documents/code/temp/demo-gc.log \
-XX:ParallelGCThreads=4 \
-XX:+UseConcMarkSweepGC \
-XX:+UseParNewGC \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=../rest-demo-heap.dump \
-XX:NativeMemoryTracking=summary \
-XX:ErrorFile=../log/java_error_%p.log \
-jar target/rest-demo-0.0.1-SNAPSHOT.jar

