<template>
  <div
    class="absolute inset-0 z-50 w-64 transition-transform duration-200 ease-in-out transform bg-true-gray-900 sm:static"
    :class="{ '-translate-x-64 sm:translate-x-0': !show }"
  ></div>
  <!-- <NuxtLink
      v-for="(link, index) in links"
      :key="index"
      :to="{ name: link.path }"
      class="grid items-center grid-cols-2 space-x-5 hover:underline hover:text-emerald-500"
    >
      <div class="text-right">{{ link.name }}</div>
      <ig-icon
        no-color
        size="sm"
        :name="link.icon"
        class="text-black"
      ></ig-icon>
    </NuxtLink> -->
</template>

<script>
import { mapState } from 'vuex'

export default {
  data() {
    return {
      links: [
        { name: 'Home', path: 'index', icon: 'home' },
        { name: 'Register', path: 'accounts-register', icon: 'key' },
        { name: 'Login', path: 'accounts-login', icon: 'lock' },
        { name: 'Logout', path: 'accounts-logout', icon: 'unlock' },
      ],
    }
  },
  computed: {
    ...mapState({
      show: (state) => state.sidebar.show,
    }),
  },
}
</script>
