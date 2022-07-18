#!/bin/bash

if [ -n "$1" ] 
then 
    java --module-path $1 --add-modules javafx.base,javafx.controls,javafx.graphics,javafx.media,javafx.swing -cp "casse_briques.jar:$1\*" main.Main

else
    echo "Veuillez specifier l'emplacement des jars de javaFX"
fi
