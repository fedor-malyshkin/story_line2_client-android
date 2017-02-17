#!/bin/sh
#
# pull db
adb shell "run-as ru.nlp_project.story_line.client_android ls /data/data/ru.nlp_project.story_line.client_android/databases/"
adb shell "run-as com.yourpackge.name cat /data/data/com.yourpackge.name/databases/filename.sqlite > /sdcard/filename.sqlite
adb pull /sdcard/filename.sqlite
