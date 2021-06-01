<template>
  <div class="absolute inset-0 flex items-center justify-center">
    <div class="p-10 rounded-lg sm:ml-64">
      <div class="sk-folding-cube">
        <div class="sk-cube1 sk-cube"></div>
        <div class="sk-cube2 sk-cube"></div>
        <div class="sk-cube4 sk-cube"></div>
        <div class="sk-cube3 sk-cube"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.sk-folding-cube {
  height: 80px;
  margin: 20px auto;
  position: relative;
  transform: rotateZ(45deg);
  width: 80px;
}

.sk-folding-cube .sk-cube {
  float: left;
  height: 50%;
  position: relative;
  transform: scale(1.1);
  width: 50%;
}

.sk-folding-cube .sk-cube::before {
  animation: sk-foldCubeAngle 2.4s infinite linear both;
  background-color: hsl(273, 51%, 38%);
  content: '';
  height: 100%;
  left: 0;
  position: absolute;
  top: 0;
  transform-origin: 100% 100%;
  width: 100%;
}

.sk-folding-cube .sk-cube2 {
  transform: scale(1.1) rotateZ(90deg);
}

.sk-folding-cube .sk-cube3 {
  transform: scale(1.1) rotateZ(180deg);
}

.sk-folding-cube .sk-cube4 {
  transform: scale(1.1) rotateZ(270deg);
}

.sk-folding-cube .sk-cube2::before {
  animation-delay: 0.3s;
}

.sk-folding-cube .sk-cube3::before {
  animation-delay: 0.6s;
}

.sk-folding-cube .sk-cube4::before {
  animation-delay: 0.9s;
}

@keyframes sk-foldCubeAngle {
  0%,
  10% {
    opacity: 0;
    transform: perspective(140px) rotateX(-180deg);
  }

  25%,
  75% {
    opacity: 1;
    transform: perspective(140px) rotateX(0deg);
  }

  90%,
  100% {
    opacity: 0;
    transform: perspective(140px) rotateY(180deg);
  }
}
</style>
