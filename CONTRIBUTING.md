# Contributing

## Prerequisites

1. Obviously you need Java and Necesse to do anything for this game.
2. See the below category of contribution relevant to what you are doing
3. ffmpeg (assuming you are touching anything audio-related)

## Contributing Audio

To contribute audio, you must follow the below steps. If you do not have a valid license to use the audio
you have found, do NOT submit it to be used. If it isn't CC0 or some other license, please link to the audio source
(or follow whatever terms relevant to the audio's license)

1. Locate a workable .ogg file, or convert another audio file -> .ogg
2. Place the audio file in the ./audioproc directory
3. If your sound is a global ambience sound, run normalizer-ambient.sh; otherwise run normalizer.sh.
4. Add your sound to the correct folder in `src`, reference it in a class, test, then PR your changes.

## Contributing Code

If it works, PR it. If you want to contribute but are unsure if it will be accepted, reach out to me via
an issue (aka a feature request) or just email my email in my (@thebigsleepjoe) GitHub.