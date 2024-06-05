#!/bin/bash


major_version=0
minor_version=0
patch_version=1

patch_version=$((patch_version + 1))

if [ "$patch_version" -eq 10 ]; then
    minor_version=$((minor_version + 1))
    patch_version=0
fi

if [ "$minor_version" -eq 10 ]; then
    major_version=$((major_version + 1))
    minor_version=0
    patch_version=0
fi

echo "$major_version.$minor_version.$patch_version"