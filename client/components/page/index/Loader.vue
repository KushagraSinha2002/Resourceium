<template>
  <div class="flex items-center justify-center space-x-3">
    <div>
      <div v-if="!loaded" class="spinner-1"></div>
      <ig-icon
        v-else
        name="check"
        no-size
        class="h-[50px]"
        variant="black"
      ></ig-icon>
    </div>
    <div class="text-xl font-semibold font-styled-code">
      <div v-if="!loaded" class="text-rose-800 animate-pulse">
        Waking up the server
      </div>
      <div v-else class="text-emerald-800">Server ready to go!</div>
    </div>
  </div>
</template>

<script>
export default {
  props: { loaded: { type: Boolean, default: false } },
}
</script>

<style scoped>
.spinner-1 {
  animation: s1 1s infinite;
  border: 8px solid;
  border-color: #000 #0000;
  border-radius: 50%;
  height: 50px;
  width: 50px;
}

@keyframes s1 {
  to {
    transform: rotate(0.5turn);
  }
}
</style>
