#!/bin/bash

cd /jsass/src/main;

rm -r resources/windows-x64
mkdir -p resources/windows-x64

# *** Build libsass

make -C libsass clean
cd libsass
git clean -xdf # hard reset
cd ..

# MAKE=mingw32                      to make sure we build for windows
# CC=i686-w64-mingw32-gcc           cross compile with mingw32-gcc compiler
# CXX=i686-w64-mingw32-g++          cross compile with mingw32-g++ compiler
# WINDRES=i686-w64-mingw32-windres  cross compile with mingw32-windres
# BUILD="static"                    to make sure that we build a static library
MAKE=mingw32 \
CC=x86_64-w64-mingw32-gcc \
CXX=x86_64-w64-mingw32-g++ \
WINDRES=x86_64-w64-mingw32-windres \
BUILD=shared \
    make -C libsass -j8 lib/libsass.dll || exit 1
cp libsass/lib/libsass.dll resources/windows-x64/libsass.dll || exit 1

# *** Build libjsass

rm -r c/build
mkdir -p c/build
cd c/build
cmake -DCMAKE_TOOLCHAIN_FILE=../Toolchain-mingw64-x64.cmake ../ || exit 1
make || exit 1
cp libjsass.dll ../../resources/windows-x64/libjsass.dll || exit 1
