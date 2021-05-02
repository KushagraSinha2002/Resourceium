<template>
  <div
    class="flex w-full px-3 py-4 font-bold text-white font-styled bg-light-black"
  >
    <button @click="toggleSidebarMutation()">Toggle</button>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  methods: {
    ...mapMutations({
      toggleSidebarMutation: 'sidebar/toggleSidebar',
    }),
  },
}
</script>
