<template>
  <div>
    <transition name="alert">
      <alert v-if="show"></alert>
    </transition>
    <div
      :class="{
        'debug-screens': mode !== 'production',
        'filter blur-[2px]  sm:blur-[1px]': show,
      }"
    >
      <nav-bar></nav-bar>
      <Nuxt />
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
