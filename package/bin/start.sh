#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf
CLASSES_DIR=$DEPLOY_DIR/classes

LOGS_DIR=""
LOGS_DIR=$DEPLOY_DIR/logs

if [ ! -d $LOGS_DIR ]; then
	mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`find $LIB_DIR|grep .jar|awk '{print $0}'|tr "\n" ":"`

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi
JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    IP=`ifconfig eth0 |awk -F '[ :]+' 'NR==2 {print $4}'`
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=$IP  -Dcom.sun.management.jmxremote.port=1090  -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=true  -Dcom.sun.management.jmxremote.password.file=/usr/local/app-cjq/Packages/.jmxremote/jmxremote.password  -Dcom.sun.management.jmxremote.access.file=/usr/local/app-cjq/Packages/.jmxremote/jmxremote.access "
fi
JAVA_MEM_OPTS=""
#BITS=`file $JAVA_HOME/bin/java | grep 64-bit`
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    let memTotal=`cat /proc/meminfo |grep MemTotal|awk '{printf "%d", $2/1024 }'`
    if [ $memTotal -gt 2500 ];then
        JAVA_MEM_OPTS="-server -Xms1024m -Xmx2048m -Xmn768m -Xss256k -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+DisableExplicitGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/cjq/logs/caijinquan-release/p2p-app/heapdump -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
    else 
        JAVA_MEM_OPTS="-server -Xms128m -Xmx1024m -Xmn256m -Xss256k -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+DisableExplicitGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/cjq/logs/caijinquan-release/p2p-app/heapdump -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
    fi
else
	JAVA_MEM_OPTS=" -server -Xms1024m -Xmx2048m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

echo -e "Starting the service ...\c"
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$CLASSES_DIR:$LIB_JARS com.daniel.test.springboot.SpringBootTestApplication > $STDOUT_FILE 2>&1 &
echo "OK!"
#PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
#echo "PID: $PIDS"
#echo "STDOUT: $STDOUT_FILE"
#
#while true; do
#    sleep 1
#done
