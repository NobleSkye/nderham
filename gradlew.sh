#!/usr/bin/env sh
set -e

APP_HOME=$(cd "$(dirname "$0")"; pwd -P)
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

if [ -n "$JAVA_HOME" ] ; then
  JAVA_EXE="$JAVA_HOME/bin/java"
else
  JAVA_EXE="java"
fi

if [ ! -f "$CLASSPATH" ] ; then
  echo "Gradle wrapper JAR not found at $CLASSPATH" >&2
  echo "If missing, run: gradle wrapper (with a compatible Gradle)" >&2
  exit 1
fi

exec "$JAVA_EXE" ${JAVA_OPTS:-} ${GRADLE_OPTS:-} \
  -Dorg.gradle.appname=gradlew \
  -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
