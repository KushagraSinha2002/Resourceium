<template>
  <div
    class="absolute inset-0 z-50 flex flex-col justify-between w-64 transition-transform duration-200 ease-in-out transform sm:shadow-2xl bg-light-black sm:static"
    :class="!show ? '-translate-x-64 sm:translate-x-0' : 'shadow-2xl'"
  >
    <div>
      <sidebar-header></sidebar-header>
      <div class="flex flex-col space-y-5 md:space-y-10">
        <sidebar-element
          v-for="(link, index) in links"
          :key="index"
          :link="link"
        >
        </sidebar-element>
      </div>
    </div>
    <sidebar-footer></sidebar-footer>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  data() {
    return {
      links: [
        { name: 'Dashboard', path: 'dashboard', icon: 'pie-chart' },
        { name: 'Explorer', path: 'folders', icon: 'file' },
        { name: 'Discussions', path: 'discussions', icon: 'message-circle' },
        { name: 'Profile', path: 'profile', icon: 'user' },
        { name: 'FAQs', path: 'faq', icon: 'help-circle' },
        { name: 'Your Account', path: 'profile', icon: 'user-plus' },
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
