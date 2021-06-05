<template>
  <div>
    <transition name="alert">
      <alert v-if="show"></alert>
    </transition>
    <div class="flex flex-col h-screen">
      <nav-bar class="flex-none"></nav-bar>
      <div class="relative flex flex-grow overflow-hidden">
        <sidebar-container
          v-if="showSidebar"
          class="flex-none"
        ></sidebar-container>
        <Nuxt
          class="flex-grow overflow-auto"
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
      return ![
        'index',
        'accounts-password-recover',
        'accounts-login',
        'accounts-logout',
        'accounts-register',
      ].includes(this.$route.name)
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
