<template>
  <div class="px-3 my-5 md:px-10">
    <div
      class="text-3xl text-center text-true-gray-700 font-poppins md:text-5xl"
    >
      All your discussions
    </div>
    <div class="grid grid-cols-1 gap-6 mt-5 lg:grid-cols-2 2xl:grid-cols-3">
      <page-socials-discussions-index-card
        v-for="discussion in discussions"
        :key="discussion.id"
        :discussion="discussion"
      ></page-socials-discussions-index-card>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      discussions: [],
    }
  },
  async fetch() {
    const id = this.$auth.user.id
    this.discussions = await this.$axios.$get(`/discussions/account/${id}`)
  },
}
</script>
