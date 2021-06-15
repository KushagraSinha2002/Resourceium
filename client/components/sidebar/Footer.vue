<template>
  <div
    class="flex items-center justify-center px-2 py-1 mx-12 my-10 space-x-8 transition-transform duration-300 transform border shadow-2xl cursor-pointer md:py-3 rounded-xl bg-primary border-warm-gray-600 hover:scale-105"
    @click="logout()"
  >
    <div class="text-xl text-white font-poppins">Logout</div>
  </div>
</template>

<script>
export default {
  methods: {
    logout() {
      this.$auth.logout()
    },
  },
}
</script>
