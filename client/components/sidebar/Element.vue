<template>
  <NuxtLink :to="{ name: link.path }" class="flex items-center px-4 space-x-5">
    <component :is="link.icon" size="32" class="text-cream-white"></component>
    <div
      class="text-xl text-right uppercase hover:underline hover:text-blue-400 font-poppins text-warm-gray-200"
    >
      {{ link.name }}
    </div>
  </NuxtLink>
</template>

<script>
import {
  PieChartIcon,
  FileIcon,
  MessageCircleIcon,
  UsersIcon,
  HelpCircleIcon,
  EditIcon,
} from 'vue-feather-icons'

export default {
  components: {
    /* eslint-disable */
    PieChartIcon,
    FileIcon,
    MessageCircleIcon,
    UsersIcon,
    HelpCircleIcon,
    EditIcon,
    /* eslint-enable */
  },
  props: {
    link: {
      type: Object,
      required: true,
    },
  },
}
</script>

<style lang="scss" scoped>
.nuxt-link-exact-active.nuxt-link-active {
  @apply border-l-4 border-lime-300;
}
</style>
