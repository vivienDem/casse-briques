@echo off
if "%1"=="" goto error

java --module-path %1 --add-modules javafx.base,javafx.controls,javafx.graphics,javafx.media,javafx.swing -cp "casse_briques.jar;%1\*" main.Main

goto end

:error
echo Veuillez specifier l'emplacement des jars de javaFX

:end
