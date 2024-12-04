#!/bin/bash

# This script runs across all .ogg files RECURSIVELY from the starting directory
# and does the following steps:
# 1. Normalize the audio to the parameters below
# 2. Ensure the audio file is 48000 Hz
# 3. Reduce every audio file to mono (useful for Necesse)

TARGET_LUFS=-18  # Integrated loudness target in LUFS
TRUE_PEAK=-1.0   # Maximum true peak in dBTP
LRA=11           # Loudness range (optional, for ambient sound dynamics)

normalize_audio() {
    local input_file="$1"
    local temp_file="${input_file%.ogg}_temp.ogg"

    echo "Normalizing: $input_file"

    ffmpeg -i "$input_file" -af loudnorm=I=$TARGET_LUFS:TP=$TRUE_PEAK:LRA=$LRA \
        -ar 48000 -ac 1 -c:a libvorbis -qscale:a 5 "$temp_file" -y

    if [[ $? -eq 0 ]]; then
        mv "$temp_file" "$input_file"
        echo "Successfully normalized: $input_file"
    else
        echo "Error normalizing: $input_file"
        rm -f "$temp_file"
    fi
}

export -f normalize_audio
export TARGET_LUFS TRUE_PEAK LRA

# Find and normalize all .ogg files recursively
find . -type f -name "*.ogg" -exec bash -c 'normalize_audio "$0"' {} \;