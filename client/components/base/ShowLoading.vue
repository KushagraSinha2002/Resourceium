<template>
  <div class="h-32">
    <div class="loader">Loading...</div>
  </div>
</template>

<style scoped>
.loader,
.loader::before,
.loader::after {
  animation: load1 1s infinite ease-out;
  background: #2e2929;
  height: 4em;
  width: 1em;
}

.loader {
  animation-delay: -0.16s;
  color: #2e2929;
  font-size: 11px;
  margin: 20px auto;
  position: relative;
  text-indent: -9999em;
  transform: translateZ(0);
}

.loader::before,
.loader::after {
  content: '';
  position: absolute;
  top: 0;
}

.loader::before {
  animation-delay: -0.32s;
  left: -1.5em;
}

.loader::after {
  left: 1.5em;
}

@keyframes load1 {
  0%,
  80%,
  100% {
    box-shadow: 0 0;
    height: 4em;
  }

  40% {
    box-shadow: 0 -2em;
    height: 5em;
  }
}
</style>
