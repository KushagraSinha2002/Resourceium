<template>
  <div
    class="z-50 flex flex-row-reverse items-center justify-between w-full px-3 py-1 font-bold text-white sm:flex-row font-styled bg-light-black"
  >
    <NuxtLink
      :to="{ name: 'index' }"
      class="transition-transform duration-300 transform hover:scale-105"
    >
      <img
        :src="require('~/assets/images/complete-logo.png')"
        alt="resourceium logo"
        class="hidden sm:block"
      />
      <img
        :src="require('~/assets/images/logo-bg-white.png')"
        alt="resourceium logo"
        class="w-8 h-8 sm:hidden"
      />
    </NuxtLink>
    <div class="flex items-center space-x-2 sm:space-x-4 2xl:space-x-6">
      <NuxtLink
        v-for="(social, index) in socials"
        :key="index"
        :to="{ name: 'index' }"
        class="transition transform hover:scale-105"
      >
        <FontAwesomeIcon
          :icon="social.icon"
          class="h-6 sm:h-8 text-dirty-gray hover:text-gray-200"
        ></FontAwesomeIcon>
      </NuxtLink>
    </div>
    <div v-if="showSidebar" class="sm:hidden" @click="toggleSidebarMutation()">
      <ig-icon
        :name="show ? 'x' : 'menu'"
        no-color
        class="text-white"
        size="lg"
      ></ig-icon>
    </div>
  </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex'

export default {
  data() {
    return {
      socials: [
        { icon: ['fab', 'instagram'] },
        { icon: ['fab', 'twitter'] },
        { icon: ['far', 'envelope'] },
        { icon: ['fab', 'whatsapp'] },
      ],
    }
  },
  computed: {
    ...mapState({
      show: (state) => state.sidebar.show,
    }),
    showSidebar() {
      return ![
        'index',
        'faq',
        'accounts-login',
        'accounts-logout',
        'accounts-register',
      ].includes(this.$route.name)
    },
  },
  methods: {
    ...mapMutations({
      toggleSidebarMutation: 'sidebar/toggleSidebar',
    }),
  },
}
</script>
