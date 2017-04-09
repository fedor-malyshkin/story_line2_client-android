#!/bin/sh
#
# pull db
./adb shell "run-as ru.nlp_project.story_line.client_android ls /data/data/ru.nlp_project.story_line.client_android/databases/"
./adb shell "run-as ru.nlp_project.story_line.client_android  cat /data/data/ru.nlp_project.story_line.client_android/databases/story_line.db > /sdcard/story_line.db"
./adb pull /sdcard/story_line.db
