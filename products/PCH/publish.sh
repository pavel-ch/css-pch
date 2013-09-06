#!/bin/bash

# Check parameters
VERSION=$1
BUILD_DIR="../../build"
if [ -z "$VERSION" ]
then 
  echo You must provide the product version \(e.g. \"prepare_release.sh 3.2.4\"\)
exit -1
fi

echo ::: Publish product to sourceforge :::
cd $BUILD_DIR/build/BuildDirectory/I.CSS-pch/
mv CSS-pch-linux.gtk.x86.zip CSS-pch-linux-$VERSION.zip
mv CSS-pch-linux.gtk.x86_64.zip CSS-pch-linux64-$VERSION.zip
mv CSS-pch-macosx.cocoa.x86.zip CSS-pch-macosx-$VERSION.zip
mv CSS-pch-win32.win32.x86.zip CSS-pch-win32-$VERSION.zip
mv CSS-pch-win32.win32.x86_64.zip CSS-pch-win64-$VERSION.zip
scp *.zip bnl-jenkins,cs-studio@frs.sourceforge.net:/home/frs/project/cs-studio/pch-release/