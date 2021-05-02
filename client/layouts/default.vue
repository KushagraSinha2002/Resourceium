<template>
  <div>
    <transition name="alert">
      <alert v-if="show"></alert>
    </transition>
    <div
      class="flex flex-col min-h-screen"
      :class="{
        'debug-screens': mode !== 'production',
        'filter blur-[2px]  sm:blur-[1px]': show,
      }"
    >
      <nav-bar class="flex-none"></nav-bar>
      <div class="relative flex flex-grow">
        <side-bar class="flex-none"></side-bar>
        <Nuxt class="flex-grow" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  data: () => ({
    mode: process.env.NODE_ENV,
  }),
  computed: {
    ...mapState({
      show: (state) => state.alerts.show,
    }),
  },
}
</script>

<style scoped>
.alert-enter-active,
.alert-leave-active {
  transition: opacity 0.2s;
}

.alert-enter,
.alert-leave-to {
  opacity: 0;
}
</style>
