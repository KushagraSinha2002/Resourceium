<template>
  <div>
    <transition name="alert">
      <alert v-if="show"></alert>
    </transition>
    <div class="flex flex-col min-h-screen">
      <nav-bar class="flex-none"></nav-bar>
      <div class="relative flex flex-grow">
        <side-bar v-if="showSidebar" class="flex-none"></side-bar>
        <Nuxt
          class="flex-grow"
          :class="{
            'debug-screens': mode !== 'production',
            'filter blur-[2px]': show,
          }"
        />
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
      show: (state) => state.blur.show,
    }),
    showSidebar() {
      return !['index', 'accounts-login', 'accounts-register'].includes(
        this.$route.name
      )
    },
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
