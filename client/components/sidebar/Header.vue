<template>
  <div
    class="flex items-center px-5 py-2 mb-5 space-x-4 border-blue-500 from-cream-white bg-gradient-to-tr to-steel-300"
  >
    <img
      :src="require('~/assets/images/robot-profile.png')"
      alt="profile image"
      class="w-12 h-12"
    />

    <div class="truncate">
      <div
        class="text-lg font-semibold text-gray-900 uppercase font-styled-code"
      >
        {{ username }}
      </div>
      <div class="text-xs text-warm-gray-700 font-poppins">{{ email }}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: null,
      email: null,
    }
  },
  async mounted() {
    const resp = await this.$axios.$get(
      `/accounts/${window.localStorage.getItem('userId')}`
    )
    this.username = resp.username
    this.email = resp.email
  },
}
</script>
