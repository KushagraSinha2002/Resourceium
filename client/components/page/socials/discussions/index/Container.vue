<template>
  <div class="flex flex-col h-full px-3">
    <page-socials-discussions-index-create-icon
      class="fixed z-20 bottom-3 right-3"
      @refreshFolders="$fetch()"
    ></page-socials-discussions-index-create-icon>
    <div v-if="!$fetchState.pending" class="w-full h-full">
      <div v-if="discussions.length > 0" class="h-full">
        <div
          class="flex-none my-5 text-3xl text-center md:px-10 text-true-gray-700 font-poppins md:text-5xl"
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
      <div v-else class="flex items-center justify-center flex-grow">
        <common-empty-data>
          <span>Seems like you have not joined any discussions.</span>
          <span
            >Click on the button on the bottom right to create/join one.</span
          >
        </common-empty-data>
      </div>
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
      discussions: [],
    }
  },
  async fetch() {
    const id = this.$auth.user.id
    this.discussions = await this.$axios.$get(`/discussions/account/${id}`)
  },
}
</script>
