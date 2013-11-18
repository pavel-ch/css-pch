#!/bin/bash

# Check parameters
set VERSION=3.2.11
set BUILD_DIR="C:\DATA\Java\css-git\cs-studio\build"

echo ::: Prepare splash :::
java -jar %BUILD_DIR%/ImageLabeler-1.0.jar %VERSION% 394 53 plugins/org.csstudio.pch.product/splash-template.bmp plugins/org.csstudio.pch.product/splash.bmp

set BUILD_DIR="D:\DEVEL\CSS_Git\build"

echo ::: Prepare splash :::
java -jar %BUILD_DIR%/ImageLabeler-1.0.jar %VERSION% 394 53 plugins/org.csstudio.pch.product/splash-template.bmp plugins/org.csstudio.pch.product/splash.bmp

echo ::: Change about dialog version :::
echo 0=%VERSION% > plugins/org.csstudio.pch.product/about.mappings
