<template>
  <div
    class="flex flex-col w-full h-full p-4 md:px-5 lg:px-10 xl:px-20 2xl:px-32"
  >
    <div
      class="flex-none text-4xl text-center font-poppins md:text-5xl text-warm-gray-800"
    >
      All Users
    </div>
    <div v-if="!$fetchState.pending" class="w-full h-full">
      <div
        v-if="users.length > 0"
        class="grid gap-6 my-5 md:my-10 md:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4"
      >
        <page-socials-users-index-card
          v-for="(user, index) in users"
          :key="index"
          :user="user"
        ></page-socials-users-index-card>
      </div>
      <common-empty-data v-else>
        <span>Seems like there are no other users.</span>
        <span>Maybe invite others to join?.</span>
      </common-empty-data>
    </div>
    <div v-else class="flex items-center justify-center w-full h-full">
      <base-show-loading></base-show-loading>
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
