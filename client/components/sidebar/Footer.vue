<template>
  <div
    class="flex items-center justify-center px-2 py-1 space-x-8 cursor-pointer md:py-3 rounded-xl bg-primary border border-white my-10 mx-12"
    @click="logout()"
  >
    <!-- <ig-icon
      name="power"
      variant="black"
      size="lg"
      class="transition-transform transform hover:scale-110"
    ></ig-icon> -->
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
