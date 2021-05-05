<template>
  <NuxtLink :to="{ name: link.path }" class="flex items-center px-4 space-x-5">
    <ig-icon
      size="lg"
      :name="link.icon"
      no-color
      class="text-cream-white"
    ></ig-icon>
    <div
      class="text-xl text-right uppercase hover:underline hover:text-blue-400 font-poppins text-warm-gray-200"
    >
      {{ link.name }}
    </div>
  </NuxtLink>
</template>

<script>
export default {
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
