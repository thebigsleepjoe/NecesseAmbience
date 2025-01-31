# Changelog

## 1.14

Addressing some concerns about loud/repetitive footsteps for summons and smaller creatures.

### Changes

- Summons have a -50% penalty to footstep volume

- All creatures have a "body size" which is measured based on hitbox size; used to attenuate footstep volume realistically based on mob size.

### Fixed

- Untracked tile type (Overgrown Grass) in FootstepsGrass; now makes grass sfx when walking on.

## 1.12

Started tracking changes. Addressing pervasive null-access bug.

### Fixed

- Possible null pointer bug in FootstepManager.onFootstep, when trying to access an invalid Level object.