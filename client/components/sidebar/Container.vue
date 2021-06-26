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
          @click.native="closeSidebarMutation"
        >
        </sidebar-element>
      </div>
    </div>
    <sidebar-footer></sidebar-footer>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'

export default {
  data() {
    return {
      links: [
        { name: 'Dashboard', path: 'dashboard', icon: 'pie-chart-icon' },
        { name: 'Explorer', path: 'folders', icon: 'file-icon' },
        {
          name: 'Discussions',
          path: 'socials-discussions',
          icon: 'message-circle-icon',
        },
        { name: 'Users', path: 'socials-users', icon: 'users-icon' },
        { name: 'FAQs', path: 'faq', icon: 'help-circle-icon' },
        { name: 'Edit profile', path: 'profile-edit', icon: 'edit-icon' },
      ],
    }
  },
  computed: {
    ...mapState({
      show: (state) => state.sidebar.show,
    }),
  },
  methods: {
    ...mapMutations({
      closeSidebarMutation: 'sidebar/closeSidebar',
    }),
  },
}
</script>
