<template>
  <div
    class="flex flex-col w-full h-full p-4 md:px-5 lg:px-10 xl:px-20 2xl:px-32"
  >
    <div
      class="flex-none text-4xl text-center font-poppins md:text-5xl text-warm-gray-800"
    >
      All Users
    </div>
    <div
      class="grid gap-6 my-5 md:my-10 md:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4"
    >
      <page-socials-users-index-card
        v-for="(user, index) in users"
        :key="index"
        :user="user"
      ></page-socials-users-index-card>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      users: [],
    }
  },
  async fetch() {
    const resp = await this.$axios.$get('/accounts/all')
    this.users = resp
  },
}
</script>
